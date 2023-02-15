package com.yhlo.oa.controller;

import com.yhlo.oa.entity.*;
import com.yhlo.oa.entity.It_MaraVO;
import com.yhlo.oa.service.PublicDataService;
import com.yhlo.oa.service.iml.PublicDataServiceImpl;
import com.yhlo.oa.util.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
public class ShowMaterialDetailsModelController implements Initializable {


    private final static String[] Rows = {"id", "matnr", "maktx", "mtart","matkl","raube","bismt",
            "spart","prdha","meins","zggxh","zcus01","zcus11"};


    @Autowired
    private PublicDataService pdService;


    @FXML
    public Button closeButton;
    @FXML
    public TableView<It_MaraVO> tableView;
    @FXML
    public TableColumn<It_MaraVO, CheckBox> mColumnSelect;

    private CreateOrderDetailComboBoxModel appController; //声明父类

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        pdService = SpringBeanUtil.getBean(PublicDataServiceImpl.class);


        mColumnSelect.setCellValueFactory(cellData -> cellData.getValue().getCb().getCheckBox());


        // 单击选中行
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (Objects.nonNull(newValue)) {
                Checkbox newCheckbox = newValue.getCb();

                ObservableList<It_MaraVO> items = tableView.getItems();
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
    public void setMainController(CreateOrderDetailComboBoxModel cm) {
        appController = cm;
    }


    //传输数据并关闭窗口
    public void closeView() {

        ObservableList<It_MaraVO> dataLsit = tableView.getItems();
        List<It_MaraVO> list = new ArrayList<>();
        int size = dataLsit.size();

        if (size <= 0) {
            CommonUtil._alertInformation("无可选列表");
            return;
        }

        for (int i = 0; i < size; i++) {
            It_MaraVO s = dataLsit.get(i);
            if (s.getCb().isSelected()) {
                list.add(s);
            }
        }

        if (list.size() != 1) {
            CommonUtil._alertInformation("请选择一行要传输的数据");
            return;
        }

        Map m = new HashMap<>();
        if(null != list && list.size()>0){
            for (It_MaraVO item :list ) {
                m.put("matnr",item.getMatnr());
                m.put("maktx",item.getMaktx());
                m.put("mtart",item.getMtart());
                m.put("matkl",item.getMatkl());
                m.put("raube",item.getRaube());
                m.put("bismt",item.getBismt());
                m.put("spart",item.getSpart());
                m.put("prdha",item.getPrdha());
                m.put("meins",item.getMeins());
                m.put("zggxh",item.getZggxh());
                m.put("zcus01",item.getZcus01());
                m.put("zcus11",item.getZcus11());
                m.put("zcus12",item.getZcus12());
            }
        }

        appController.setComboBoxValue(m);
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }




    /**
     * @Author cy
     * @Description 获取数据列表
     * @Return
     * @Date 2022/10/9 14:22
     */
    public void getDataListModel(String param){

        ObservableList<It_MaraVO> items = tableView.getItems();

        List<It_MaraVO> itemList = pdService.queryMaterialList(param,"");

        items.clear();
        items.addAll(itemList);

        ObservableList<TableColumn<It_MaraVO, ?>> columns = tableView.getColumns();

        List<KeyList> keyList = DataTypeWrapper.getKeyList(It_MaraVO.class);
        for (int i = 0; i < Rows.length; i++) {
            String key = Rows[i];
            Optional<KeyList> keys = keyList.stream().filter(e-> e.getKey().equals(key)).findFirst();
            TableColumn<It_MaraVO, Object> column = new TableColumn();
            column.setText(keys.get().getLabel());
            column.setCellValueFactory(new PropertyValueFactory(key));
            column.setPrefWidth(100);
            columns.add(column);
        }
        tableView.refresh();
    }


}
