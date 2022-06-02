package com.yhlo.oa.controller;

import com.yhlo.oa.entity.KeyList;
import com.yhlo.oa.entity.OrderVO;
import com.yhlo.oa.service.NormalOrderService;
import com.yhlo.oa.service.iml.NormalOrderServiceImpl;
import com.yhlo.oa.util.DataTypeWrapper;
import com.yhlo.oa.util.NodeUtil;
import com.yhlo.oa.util.ResultUtil;
import com.yhlo.oa.util.SpringBeanUtil;
import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @create: 2022-04-12 15:40
 * @description: 一般订单
 **/
@Slf4j
@FXMLController
public class NormalController implements Initializable {

    public TextField orderNo;
    public TextField status;
    public TextField createBy;
    public TextField createTime;
    public TableView<OrderVO> orderList;

    @Autowired
    private NormalOrderService normalOrderService;

    private final static String[] ROWS = {"orderNo", "status", "createBy", "createTime"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        normalOrderService = SpringBeanUtil.getBean(NormalOrderServiceImpl.class);

        log.info("进入一般订单");
       // loadTable();
        /*ObservableList<OrderVO> items = orderList.getItems();
        items.clear();
        items.addAll(getDataList());

        orderList.setRowFactory(tv -> {
            tv.setOnMouseClicked(event -> {
                OrderVO order = tv.getSelectionModel().getSelectedItem();
                if (event.getClickCount() == 1 && null != order) {
                    this.getDetailInfo(order);
                }
            });
            return new TableRow<>();
        });

        ObservableList<TableColumn<OrderVO, ?>> columns = orderList.getColumns();
        columns.clear();
        // 将列的值与当前的javabean的属性进行绑定
        List<KeyList> keyList = DataTypeWrapper.getKeyList(OrderVO.class);
        for (int i = 0; i < ROWS.length; i++) {
            String key = ROWS[i];
            Optional<KeyList> keys = keyList.stream().filter(e-> e.getKey().equals(key)).findFirst();
            TableColumn<OrderVO, Object> column = new TableColumn();
            column.setText(keys.get().getLabel());
            column.setCellValueFactory(new PropertyValueFactory(key));
            columns.add(column);
        }*/
    }


    @FXML
    private void loadTable(){
        ObservableList<OrderVO> items = orderList.getItems();
        items.clear();
        items.addAll(getDataList());

        orderList.setRowFactory(tv -> {
            tv.setOnMouseClicked(event -> {
                OrderVO order = tv.getSelectionModel().getSelectedItem();
                if (event.getClickCount() == 1 && null != order) {
                    this.getDetailInfo(order);
                }
            });
            return new TableRow<>();
        });

        ObservableList<TableColumn<OrderVO, ?>> columns = orderList.getColumns();
        columns.clear();
        // 将列的值与当前的javabean的属性进行绑定
        List<KeyList> keyList = DataTypeWrapper.getKeyList(OrderVO.class);
        for (int i = 0; i < ROWS.length; i++) {
            String key = ROWS[i];
            Optional<KeyList> keys = keyList.stream().filter(e-> e.getKey().equals(key)).findFirst();
            TableColumn<OrderVO, Object> column = new TableColumn();
            column.setText(keys.get().getLabel());
            column.setCellValueFactory(new PropertyValueFactory(key));
            columns.add(column);
        }
    }

    public List<OrderVO> getDataList() {
        return normalOrderService.queryOrderList();
    }

    private void getDetailInfo(OrderVO order) {
        orderNo.setText(order.getOrderNo());
        status.setText(order.getStatus());
        createBy.setText(order.getCreateBy());
        createTime.setText(order.getCreateTime());
    }

    public void updateData() {
        log.info("修改数据");

        OrderVO order = orderList.getSelectionModel().getSelectedItem();
        if (null == order) {
            ResultUtil.getWarringResult("请选择要修改订单！");
        }

        ArrayList<Node> nodes = NodeUtil.getAllTextFiledNodes(orderList.getScene().getRoot());
        nodes.stream().forEach(e -> e.setDisable(false));
    }

    public void saveData() {
        log.info("保存订单数据");
        ArrayList<Node> nodes = NodeUtil.getAllTextFiledNodes(orderList.getScene().getRoot());
        nodes.stream().forEach(e -> e.setDisable(true));

        OrderVO order = new OrderVO();
        order.setOrderNo(orderNo.getText());
        order.setStatus(status.getText());
        order.setCreateBy(createBy.getText());
        normalOrderService.saveOrder(order);
        ResultUtil.getWarringResult("保存成功！");
    }
}
