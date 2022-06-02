package com.yhlo.oa.controller;

import com.yhlo.oa.entity.Item;
import com.yhlo.oa.entity.RolesOrderVO;
import com.yhlo.oa.service.NormalOrderService;
import com.yhlo.oa.service.RolesOrderService;
import com.yhlo.oa.service.iml.RolesOrderServiceImpl;
import com.yhlo.oa.util.AutoCompleteComboBoxListener;
import com.yhlo.oa.util.Convert;
import com.yhlo.oa.util.SpringBeanUtil;
import com.yhlo.oa.util.StageManager;
import de.felixroske.jfxsupport.FXMLController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.util.*;
import java.util.function.Predicate;

/**
 * @author cy
 * @ClassName: AddRolesController
 * @Description:
 * @date 2022/5/515:56
 */
@Slf4j
@FXMLController
public class AddRolesController implements Initializable {

    @FXML
    public ComboBox <String>cbb;//采购订单号
    @FXML
    public TextField xsddText;//销售订单号
    @FXML
    public TextField zhuddhText;//主订单号
    @FXML
    public TextField xiaoszzText;//销售组织
    @FXML
    public TextField jiaglxText;//价格类型
    @FXML
    public TextField yunfText;//运费
    @FXML
    public TextField youhqText;//优惠券
    @FXML
    public TextField zonghsjeText;//总含税金额
    @FXML
    public TextField songdfText;//送达方
    @FXML
    public TextField shoudfText;//售达方
    @FXML
    public Button closeButton;

    private RolesOrderService rolesOrderService;

    private ObservableList<Item> obsAll;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rolesOrderService = SpringBeanUtil.getBean(RolesOrderServiceImpl.class);
        initComboBox();
    }

    /***
     * 初始化下拉控件
     */
    public void initComboBox(){

       List<Item> itemList = rolesOrderService.getOrderItem();

        if(null != itemList && itemList.size()>0){
            for (Item item :itemList ) {
                cbb.getItems().add(item.getItemKey());
            }
        }

        cbb.setPromptText("请输入订单号搜索");
        cbb.setPlaceholder(new Label("空值"));
        AutoCompleteComboBoxListener auto = new AutoCompleteComboBoxListener<>(cbb);

      /*  obsAll = cbb.getItems();

        TextField tf = cbb.editorProperty().get();
        tf.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if(null == newValue){
                    cbb.setItems(null);
                    cbb.setPlaceholder(new Label("无查询列表"));
                    return;
                }
                FilteredList<String> newList = obsAll.filtered(new Predicate<String>() {
                    @Override
                    public boolean test(String s) {
                        return s.contains(newValue);
                    }
                });

                if(newList.isEmpty()){
                    cbb.setItems(null);
                    cbb.setPlaceholder(new Label("无查询列表"));
                }else{
                    cbb.setItems(newList);
                    cbb.hide();
                    cbb.show();
                }
            }
        });*/


    }

    /**
     * 添加订单
     * @param actionEvent
     */
    public void addRolesOrder(ActionEvent actionEvent) {
        String orderNo = cbb.getValue();//
        String saleOrderNo = xsddText.getText();//
        String deliveryParty = shoudfText.getText();
        String consignee = songdfText.getText();
        String totalTaxInclusiveAmount = zonghsjeText.getText();
        String coupon = youhqText.getText();
        String freight = yunfText.getText();
        String priceType = jiaglxText.getText();
        String salesOrganization = xiaoszzText.getText();
        String contractOrderNo = zhuddhText.getText();

        RolesOrderVO od  = new RolesOrderVO();
        od.setOrderNo(orderNo);
        od.setSaleOrderNo(saleOrderNo);
        od.setDeliveryParty(deliveryParty);
        od.setConsignee(consignee);
        od.setTotalTaxInclusiveAmount(Convert.toFloat(totalTaxInclusiveAmount,0f));
        od.setCoupon(coupon);
        od.setFreight(Convert.toFloat(freight,0f));
        od.setPriceType(priceType);
        od.setSalesOrganization(salesOrganization);
        od.setContractOrderNo(contractOrderNo);

        rolesOrderService.saveOrder(od);



    }

    public  void handleCloseButtonAction(ActionEvent event) {

        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

}
