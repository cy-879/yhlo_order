package com.yhlo.oa.controller;

import com.yhlo.oa.entity.*;
import com.yhlo.oa.service.SearchCustomerDetailService;
import com.yhlo.oa.service.iml.SearchCustomerDetailServiceImpl;
import com.yhlo.oa.util.DataTypeWrapper;
import com.yhlo.oa.util.SpringBeanUtil;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author cy
 * @ClassName: SearchCustomerDetail
 * @Description:
 * @date 2022/6/911:16
 */
public class SearchCustomerDetailController implements Initializable {


    private final static String[] companyCodeROWS = {"id", "kunnr", "bukrs", "erdat","ernam","akont","zwels","zterm","sperr","loevm"};

    private final static String[] saleROWS = {"id", "kunnr", "vkorg", "vtweg","spart","ernam","erdat",
            "begru","loevm","versg","aufsd","kalks","kdgrp","bzirk","konda","pltyp","inco1","inco2",
            "lifsd","kzazu","vsbed","faksd","waers","klabc","ktgrd","zterm","vwerk","vkgrp","vkbur",
            "prfre","kkber","podkz"};

    private final static String[] cooperativePartnerROWS = {"id", "kunnr", "vkorg", "vtweg","spart","parvw","parza","kunn2",
                                         "lifnr","pernr","parnr","knref","defpa"};

    private final static String[] addrROWS = {"id", "addrnumber", "name1", "name2","name3","name4","str_suppl1","str_suppl2",
                                         "str_suppl3","location","remark"};

    private final static String[] customerMaterialROWS = {"id", "kunnr", "vkorg", "vtweg","matnr","name1","arktx","kdmat",
                                         "postx","zkdmat_2","zkdmat_3","zkdmat_4","zernam1","zerdat1","zernam2","zerdat2"};

    @FXML
    public TextField kunnrTextField;
    @FXML
    public TextField name1TextField;
    @FXML
    public TextField ktokdTextField;
    @FXML
    public TextField adrnrTextField;
    @FXML
    public TabPane tabPane;
    @FXML
    public Button closeButton;
    @FXML
    public TableView companyCodeTableView;
    @FXML
    public Tab companyCodeTab;
    @FXML
    public TableView saleTableView;
    @FXML
    public Tab saleTab;
    @FXML
    public TableView cooperativePartnerTableView;
    @FXML
    public Tab cooperativePartnerTab;
    @FXML
    public TableView addrTableView;
    @FXML
    public Tab addrTab;
    @FXML
    public TableView customerMaterialTableView;
    @FXML
    public Tab customerMaterialTab;

    @Autowired
    private SearchCustomerDetailService scService;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        scService = SpringBeanUtil.getBean(SearchCustomerDetailServiceImpl.class);

        tabPane.getSelectionModel().select(companyCodeTab);//默认进去就显示companyCodeTab

        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {//监听点击的哪个
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {

                if("公司代码".equals(newValue.getText())){
                    setCompanyCodeTable();
                }else if("销售".equals(newValue.getText())){
                    setSaleCodeTable();
                }else if("合作伙伴".equals(newValue.getText())){
                    setCooperativePartnerTable();
                }else if("地址".equals(newValue.getText())){
                    setAddrTable();
                }else if("客户物料".equals(newValue.getText())){
                    setCustomerMaterialTable();
                }
            }
        });
    }

    //设置主表信息
    public void setCustomerData(It_Kna1VO knData){

        kunnrTextField.setText(knData.getKunnr());
        name1TextField.setText(knData.getName1());
        ktokdTextField.setText(knData.getKtokd());
        adrnrTextField.setText(knData.getAdrnr());

    }



    @FXML
    public void searchTable(ActionEvent actionEvent) {
        String tabName = tabPane.getSelectionModel().getSelectedItem().getText();//获取页签名称
        if("公司代码".equals(tabName)){
            setCompanyCodeTable();
        }else if("销售".equals(tabName)){
            setSaleCodeTable();
        }else if("合作伙伴".equals(tabName)){
            setCooperativePartnerTable();
        }else if("地址".equals(tabName)){
            setAddrTable();
        }else if("客户物料".equals(tabName)){
            setCustomerMaterialTable();
        }else{

        }
    }

    /**
     * @Author cy
     * @Description
     * * @param null 获取客户主数据公司代码数据
     * @Return 
     * @Date 2022/6/9 17:00
     */
    private List<It_Knb1VO> queryKnb1List() {
        String kunnr = kunnrTextField.getText();
        return scService.queryKnb1List(kunnr);
    }

    /**
     * @Author cy
     * @Description
     * * @param null 获取客户主数据销售视图数据
     * @Return
     * @Date 2022/6/9 17:00
     */
    private List<It_KnvvVO> queryKnvvList() {
        String kunnr = kunnrTextField.getText();
        return scService.queryKnvvList(kunnr);
    }

    /**
     * @Author cy
     * @Description 获取客户主数据合作伙伴数据
     * * @param null
     * @Return
     * @Date 2022/6/10 9:02
     */
    private List<It_KnvpVO> queryKnvpList() {
        String kunnr = kunnrTextField.getText();
        return scService.queryKnvpList(kunnr);
    }

    /**
     * @Author cy
     * @Description 获取客户主数据地址信息数据
     * * @param null
     * @Return
     * @Date 2022/6/10 9:14
     */
    private List<It_AdrcVO> queryAdrcList() {
        String addrnumber = adrnrTextField.getText();
        return scService.queryAdrcList(addrnumber);
    }

    /**
     * @Author cy
     * @Description 获取客户物料数据
     * * @param null
     * @Return 
     * @Date 2022/6/10 9:42
     */
    private List<It_Ztsd_012VO> queryZtsdList() {
        String kunnr = kunnrTextField.getText();
        return scService.queryZtsdList(kunnr);
    }

    
    /**
     * @Author cy
     * @Description 设置公司代码页签表格
     * * @param null
     * @Return 
     * @Date 2022/6/9 17:05
     */
    public void setCompanyCodeTable(){
        ObservableList<It_Knb1VO> items = companyCodeTableView.getItems();
        items.clear();
        items.addAll(queryKnb1List());

        ObservableList<TableColumn<It_Knb1VO, ?>> columns = companyCodeTableView.getColumns();
        columns.clear();
        List<KeyList> keyList = DataTypeWrapper.getKeyList(It_Knb1VO.class);
        for (int i = 0; i < companyCodeROWS.length; i++) {
            String key = companyCodeROWS[i];
            Optional<KeyList> keys = keyList.stream().filter(e-> e.getKey().equals(key)).findFirst();
            TableColumn<It_Knb1VO, Object> column = new TableColumn();
            column.setText(keys.get().getLabel());
            column.setCellValueFactory(new PropertyValueFactory(key));
            column.setPrefWidth(100);
            columns.add(column);
        }
    }


    /**
     * @Author cy
     * @Description 设置销售页签表格
     * * @param null
     * @Return 
     * @Date 2022/6/9 18:05
     */
    public void setSaleCodeTable(){
        ObservableList<It_KnvvVO> items = saleTableView.getItems();
        items.clear();
        items.addAll(queryKnvvList());

        ObservableList<TableColumn<It_KnvvVO, ?>> columns = saleTableView.getColumns();
        columns.clear();
        List<KeyList> keyList = DataTypeWrapper.getKeyList(It_KnvvVO.class);
        for (int i = 0; i < saleROWS.length; i++) {
            String key = saleROWS[i];
            Optional<KeyList> keys = keyList.stream().filter(e-> e.getKey().equals(key)).findFirst();
            TableColumn<It_KnvvVO, Object> column = new TableColumn();
            column.setText(keys.get().getLabel());
            column.setCellValueFactory(new PropertyValueFactory(key));
            column.setPrefWidth(100);
            columns.add(column);
        }
    }

    /**
     * @Author cy
     * @Description 设置合作伙伴页签表格
     * * @param null
     * @Return
     * @Date 2022/6/10 9:03
     */
    public void setCooperativePartnerTable(){
        ObservableList<It_KnvpVO> items = cooperativePartnerTableView.getItems();
        items.clear();
        items.addAll(queryKnvpList());

        ObservableList<TableColumn<It_KnvpVO, ?>> columns = cooperativePartnerTableView.getColumns();
        columns.clear();
        List<KeyList> keyList = DataTypeWrapper.getKeyList(It_KnvpVO.class);
        for (int i = 0; i < cooperativePartnerROWS.length; i++) {
            String key = cooperativePartnerROWS[i];
            Optional<KeyList> keys = keyList.stream().filter(e-> e.getKey().equals(key)).findFirst();
            TableColumn<It_KnvpVO, Object> column = new TableColumn();
            column.setText(keys.get().getLabel());
            column.setCellValueFactory(new PropertyValueFactory(key));
            column.setPrefWidth(100);
            columns.add(column);
        }
    }
    
    /**
     * @Author cy
     * @Description 设置客户地址页签表格
     * * @param null
     * @Return 
     * @Date 2022/6/10 9:15
     */
    public void setAddrTable(){
        ObservableList<It_AdrcVO> items = addrTableView.getItems();
        items.clear();
        items.addAll(queryAdrcList());

        ObservableList<TableColumn<It_AdrcVO, ?>> columns = addrTableView.getColumns();
        columns.clear();
        List<KeyList> keyList = DataTypeWrapper.getKeyList(It_AdrcVO.class);
        for (int i = 0; i <  addrROWS.length; i++) {
            String key =  addrROWS[i];
            Optional<KeyList> keys = keyList.stream().filter(e-> e.getKey().equals(key)).findFirst();
            TableColumn<It_AdrcVO, Object> column = new TableColumn();
            column.setText(keys.get().getLabel());
            column.setCellValueFactory(new PropertyValueFactory(key));
            column.setPrefWidth(100);
            columns.add(column);
        }
    }


    /**
     * @Author cy
     * @Description 设置客户物料页签表格
     * * @param null
     * @Return
     * @Date 2022/6/10 10:09
     */
    public void setCustomerMaterialTable(){
        ObservableList<It_Ztsd_012VO> items = customerMaterialTableView.getItems();
        items.clear();
        items.addAll(queryZtsdList());

        ObservableList<TableColumn<It_Ztsd_012VO, ?>> columns = customerMaterialTableView.getColumns();
        columns.clear();
        List<KeyList> keyList = DataTypeWrapper.getKeyList(It_Ztsd_012VO.class);
        for (int i = 0; i <  customerMaterialROWS.length; i++) {
            String key =  customerMaterialROWS[i];
            Optional<KeyList> keys = keyList.stream().filter(e-> e.getKey().equals(key)).findFirst();
            TableColumn<It_Ztsd_012VO, Object> column = new TableColumn();
            column.setText(keys.get().getLabel());
            column.setCellValueFactory(new PropertyValueFactory(key));
            column.setPrefWidth(100);
            columns.add(column);
        }
    }



    //关闭窗口
    public void closeView(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
