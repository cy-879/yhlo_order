package com.yhlo.oa.controller;

import com.yhlo.oa.entity.Checkbox;
import com.yhlo.oa.entity.ShowRebatePolicyInfoVO;
import com.yhlo.oa.entity.KeyList;
import com.yhlo.oa.entity.ShowRebatePolicyInfoVO;
import com.yhlo.oa.service.PublicDataService;
import com.yhlo.oa.service.RebatePolicyService;
import com.yhlo.oa.service.iml.PublicDataServiceImpl;
import com.yhlo.oa.service.iml.RebatePolicyServiceImpl;
import com.yhlo.oa.util.CommonUtil;
import com.yhlo.oa.util.CreateOrderDetailComboBoxModel;
import com.yhlo.oa.util.DataTypeWrapper;
import com.yhlo.oa.util.SpringBeanUtil;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.*;

/**
 * @author cy
 * @ClassName: ShowDataDetail
 * @Description:
 * @date 2022/10/810:19
 */
public class ShowRebatePolicyInfoController implements Initializable {


    private final static String[] Rows = {"id", "zzbname", "zzbname_txt", "zcont","zcont_txt","kunnr",
            "name1","vkorg","vtext","zrebate","zrebate_used","zrebate_last","datab","datbi","zzkl",
            "kunnr_ec","kunnr_ec_name","matkl","wgbez","zbase"};

    @Autowired
    private static PublicDataService pdService;


    @FXML
    public Button closeButton;
    @FXML
    public TableView<ShowRebatePolicyInfoVO> tableView;
    @FXML
    public TableColumn<ShowRebatePolicyInfoVO, CheckBox> mColumnSelect;

    private GeneralOrderController faController; //声明父类

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        pdService = SpringBeanUtil.getBean(PublicDataServiceImpl.class);


        mColumnSelect.setCellValueFactory(cellData -> cellData.getValue().getCb().getCheckBox());


        // 单击选中行
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (Objects.nonNull(newValue)) {
                Checkbox newCheckbox = newValue.getCb();

                ObservableList<ShowRebatePolicyInfoVO> items = tableView.getItems();
                if (newCheckbox.isSelected()) {
                    newCheckbox.setSelected(false);
                } else {
                    newCheckbox.setSelected(true);
                }

                if (Objects.nonNull(oldValue)) {
                    Checkbox oldCheckbox = oldValue.getCb();
                    oldCheckbox.setSelected(false);
                }

            }


        });


        //双击事件
        tableView.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)->{

            if (e.getClickCount() == 2 && e.getButton().name().equals(MouseButton.PRIMARY.name())) {
                closeView();
            }
        });
    }


    //接收父对象
    public void setMainController(GeneralOrderController cm) {
        faController = cm;
    }


    //传输数据并关闭窗口
    public void closeView() {

        ObservableList<ShowRebatePolicyInfoVO> dataLsit = tableView.getItems();
        List<ShowRebatePolicyInfoVO> list = new ArrayList<>();
        int size = dataLsit.size();

        if (size <= 0) {
            CommonUtil._alertInformation("无可选列表");
            return;
        }

        for (int i = 0; i < size; i++) {
            ShowRebatePolicyInfoVO s = dataLsit.get(i);
            if (s.getCb().isSelected()) {
                list.add(s);
            }
        }

        if (list.size() != 1) {
            CommonUtil._alertInformation("请选择一行要传输的数据");
            return;
        }

        faController.setRebatePolicyObject(list.get(0));
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }




    /**
     * @Author cy
     * @Description 获取数据列表
     * @Return
     * @Date 2022/10/9 14:22
     */
    public void getDataListModel(List<ShowRebatePolicyInfoVO> reList){

        ObservableList<ShowRebatePolicyInfoVO> items = tableView.getItems();

        List<ShowRebatePolicyInfoVO> itemList = pdService.showRebatePolicyList(reList);

        items.clear();
        items.addAll(itemList);

        ObservableList<TableColumn<ShowRebatePolicyInfoVO, ?>> columns = tableView.getColumns();

        List<KeyList> keyList = DataTypeWrapper.getKeyList(ShowRebatePolicyInfoVO.class);
        for (int i = 0; i < Rows.length; i++) {
            String key = Rows[i];
            Optional<KeyList> keys = keyList.stream().filter(e-> e.getKey().equals(key)).findFirst();
            TableColumn<ShowRebatePolicyInfoVO, Object> column = new TableColumn();
            column.setText(keys.get().getLabel());
            column.setCellValueFactory(new PropertyValueFactory(key));
            column.setPrefWidth(100);
            columns.add(column);
        }
        tableView.refresh();
    }


}
