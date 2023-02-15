package com.yhlo.oa.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yhlo.oa.entity.*;
import com.yhlo.oa.enums.OrderStatuEnum;
import com.yhlo.oa.service.GeneralOrderService;
import com.yhlo.oa.service.PublicDataService;
import com.yhlo.oa.service.iml.GeneralOrderServiceImpl;
import com.yhlo.oa.service.iml.PublicDataServiceImpl;
import com.yhlo.oa.util.*;
import com.yhlo.oa.util.poi.ExcelUtil;
import com.yhlo.oa.webservice.GetSapConfigTableInfo;
import de.felixroske.jfxsupport.FXMLController;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.prefs.Preferences;
import java.util.regex.Pattern;

/**
 * @create: 2022-04-12 15:40
 * @description: 一般订单
 **/
@Slf4j
@FXMLController
public class GeneralOrderController implements Initializable {


    @FXML
    public TextField orderTypeAssembly;
    @FXML
    public ComboBox orderReferenceAssembly;
    @FXML
    public TextField sapSalesOrderNoAssembly;
    @FXML
    public TextField ddsSalesOrderNoAssembly;
    @FXML
    public ComboBox salesOrgAssembly;
    @FXML
    public ComboBox productGroupAssembly;
    @FXML
    public ComboBox distributionChannelAssembly;
    @FXML
    public ComboBox customerNameAssembly;
    @FXML
    public TextField customerCodeAssembly;
    @FXML
    public ComboBox songdfmcAssembly;
    @FXML
    public TextField songdfdmAssembly;
    @FXML
    public ComboBox saleDeptAssembly;
    @FXML
    public ComboBox termOfPaymentAssembly;
    @FXML
    public ComboBox endCustomerAssembly;
    @FXML
    public ComboBox salesmanAssembly;
    @FXML
    public DatePicker documentDateAssembly;
    @FXML
    TextField totalAmountAssembly;
    @FXML
    TextField amountNotTaxedAssembly;
    @FXML
    public ComboBox shippingConditionsAssembly;
    @FXML
    public ComboBox approvalStatusAssembly;
    @FXML
    public ComboBox deliveryStatusAssembly;
    @FXML
    public CheckBox claimAssembly;
    @FXML
    public TextField orderSummaryAssembly;
    @FXML
    public TextField shippingRemarksAssembly;
    @FXML
    public ComboBox shifflAssembly;
    @FXML
    public TextField rebateNoAssembly;


    @FXML
    public Button saveBt;
    @FXML
    public Button saveAndNewBt;
    @FXML
    public Button editBt;
    @FXML
    public Button seachBt;
    @FXML
    public Button addBt;
    @FXML
    public Button delBt;
    @FXML
    public Button copyBt;
    @FXML
    public Button importBt;

    @FXML
    public TextField dataIdField;

    private String xiaoszz;
    private ShowRebatePolicyInfoVO srpi;


    /**
     * 以下是明细内容
     */

    @FXML
    public TableView <GeneralOrderDetailVO> tableView;
    @FXML
    public TableColumn<GeneralOrderDetailVO, CheckBox> mColumnSelect;
    @FXML
    private CheckBox mselectAll;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColLineItem;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColMaterialDesc;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColMaterialNo;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColMnemonicCode;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColSpecification;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColBrand;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColMaterialGroup;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColBasicUnit;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColOrderQty;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColQtyDelivered;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColOpenQty;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColOrderUnit;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColFactory;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColInventoryLocation;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColshippingPoint;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColPricingDate;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColStandardPrice;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColManualPrice;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColOrderBuomQty;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColDiscountRate;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColRebateAmount;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColAmountBeforeDiscount;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColDiscountedAmount;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColStorageTemperature;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColTaxRate;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColCurrency;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColBatch;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColProductionLicense;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColFullNameOfManufacturer;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColApprovalNo;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColCustomerMaterial;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColCustomerMaterialDesc;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColProvincialBiddingCode;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColCityBiddingCode;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColReservedCode;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColBusinessLicense;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColProductionRecordCertificate;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColLastUnitPrice;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColMultiAngleTradeType;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColSto1No;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColSto1LineItem;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColLineItemType;
    @FXML
    public TableColumn<GeneralOrderDetailVO, String> mColLineItemText;



    private ObservableList<GeneralOrderDetailVO> dataList =
            FXCollections.observableArrayList();


    public List<String> checkedIdList = new ArrayList<>();

    public List<Integer> lineList = new ArrayList<>();



    /*指定数字格式*/  /*两位小数*/
    private static Pattern decimalPattern = Pattern.compile("\\d{0,12}+(\\.\\d{0,2})?");

    @Autowired
    private GeneralOrderService orderService;

    @Autowired
    private static PublicDataService pdService;

    private String userName ;

    private static String pattern = "yyyy-MM-dd";//日期格式类型样板串


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        log.info("进入一般订单");

        dataIdField.setVisible(false);

        KeyCodeCombination keyCodeCopy = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY);
        KeyCodeCombination keyCodeV = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_ANY);



        tableView.setOnKeyPressed(event -> {
            if (keyCodeV.match(event)) {
                Clipboard clipboard = Clipboard.getSystemClipboard();//获取操作系统的剪贴板
                String text = clipboard.getString();
                ClipboardContent clipboardContent = new ClipboardContent();;//创建剪贴板内容
                clipboardContent.putString(text);
              //  clipboard.setContent(content);//剪贴板对象中挂载剪贴板内容对象
                System.err.println("text=="+text);
            }
        });



        Preferences userPreferences = Preferences.userRoot();
        userName = userPreferences.get("userName","");

        ImageView saveImage = new ImageView( CommonUtil.getImage("/img/icon/save.png"));
        saveBt.setGraphic(saveImage);

        ImageView saveAndNewImage = new ImageView( CommonUtil.getImage("/img/icon/saveAndNew.png"));
        saveAndNewBt.setGraphic(saveAndNewImage);

        ImageView editImage = new ImageView( CommonUtil.getImage("/img/icon/edit.png"));
        editBt.setGraphic(editImage);

        ImageView seachImage = new ImageView( CommonUtil.getImage("/img/icon/search1.png"));
        seachBt.setGraphic(seachImage);

        ImageView addDetailImage = new ImageView( CommonUtil.getImage("/img/icon/addDetail.png"));
        addBt.setGraphic(addDetailImage);

        ImageView delDetailImage = new ImageView( CommonUtil.getImage("/img/icon/delDetail.png"));
        delBt.setGraphic(delDetailImage);

        ImageView copyDetailImage = new ImageView( CommonUtil.getImage("/img/icon/copyDetail.png"));
        copyBt.setGraphic(copyDetailImage);


        Tooltip.install(addBt, new Tooltip("添加明细行"));
        Tooltip.install(delBt, new Tooltip("删除明细行"));
        Tooltip.install(copyBt, new Tooltip("复制明细行"));


        orderService = SpringBeanUtil.getBean(GeneralOrderServiceImpl.class);
        pdService = SpringBeanUtil.getBean(PublicDataServiceImpl.class);


        /*将明细列和数据对象绑定*/
        mColLineItem.setCellValueFactory(new PropertyValueFactory<>("lineItem"));
        mColMaterialDesc.setCellValueFactory(new PropertyValueFactory<>("materialDesc"));
        mColMaterialNo.setCellValueFactory(new PropertyValueFactory<>("materialNo"));
        mColMnemonicCode.setCellValueFactory(new PropertyValueFactory<>("mnemonicCode"));
        mColSpecification.setCellValueFactory(new PropertyValueFactory<>("specification"));
        mColBrand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        mColMaterialGroup.setCellValueFactory(new PropertyValueFactory<>("materialGroup"));
        mColBasicUnit.setCellValueFactory(new PropertyValueFactory<>("basicUnit"));
        mColOrderQty.setCellValueFactory(new PropertyValueFactory<>("orderQty"));
        mColQtyDelivered.setCellValueFactory(new PropertyValueFactory<>("qtyDelivered"));
        mColOpenQty.setCellValueFactory(new PropertyValueFactory<>("openQty"));
        mColOrderUnit.setCellValueFactory(new PropertyValueFactory<>("orderUnit"));
        mColFactory.setCellValueFactory(new PropertyValueFactory<>("factory"));
        mColInventoryLocation.setCellValueFactory(new PropertyValueFactory<>("inventoryLocation"));
        mColshippingPoint.setCellValueFactory(new PropertyValueFactory<>("shippingPoint"));
        mColPricingDate.setCellValueFactory(new PropertyValueFactory<>("pricingDate"));
        mColStandardPrice.setCellValueFactory(new PropertyValueFactory<>("standardPrice"));
        mColManualPrice.setCellValueFactory(new PropertyValueFactory<>("manualPrice"));
        mColOrderBuomQty.setCellValueFactory(new PropertyValueFactory<>("orderBuomQty"));
        mColDiscountRate.setCellValueFactory(new PropertyValueFactory<>("discountRate"));
        mColRebateAmount.setCellValueFactory(new PropertyValueFactory<>("rebateAmount"));
        mColAmountBeforeDiscount.setCellValueFactory(new PropertyValueFactory<>("amountBeforeDiscount"));
        mColDiscountedAmount.setCellValueFactory(new PropertyValueFactory<>("discountedAmount"));
        mColStorageTemperature.setCellValueFactory(new PropertyValueFactory<>("storageTemperature"));
        mColTaxRate.setCellValueFactory(new PropertyValueFactory<>("taxRate"));
        mColCurrency.setCellValueFactory(new PropertyValueFactory<>("currency"));
        mColBatch.setCellValueFactory(new PropertyValueFactory<>("batch"));
        mColProductionLicense.setCellValueFactory(new PropertyValueFactory<>("productionLicense"));
        mColFullNameOfManufacturer.setCellValueFactory(new PropertyValueFactory<>("fullNameOfManufacturer"));
        mColApprovalNo.setCellValueFactory(new PropertyValueFactory<>("approvalNo"));
        mColCustomerMaterial.setCellValueFactory(new PropertyValueFactory<>("customerMaterial"));
        mColCustomerMaterialDesc.setCellValueFactory(new PropertyValueFactory<>("customerMaterialDesc"));
        mColProvincialBiddingCode.setCellValueFactory(new PropertyValueFactory<>("provincialBiddingCode"));
        mColCityBiddingCode.setCellValueFactory(new PropertyValueFactory<>("cityBiddingCode"));
        mColReservedCode.setCellValueFactory(new PropertyValueFactory<>("reservedCode"));
        mColBusinessLicense.setCellValueFactory(new PropertyValueFactory<>("businessLicense"));
        mColProductionRecordCertificate.setCellValueFactory(new PropertyValueFactory<>("productionRecordCertificate"));
        mColLastUnitPrice.setCellValueFactory(new PropertyValueFactory<>("lastUnitPrice"));
        mColMultiAngleTradeType.setCellValueFactory(new PropertyValueFactory<>("multiAngleTradeType"));
        mColSto1No.setCellValueFactory(new PropertyValueFactory<>("sto1No"));
        mColSto1LineItem.setCellValueFactory(new PropertyValueFactory<>("sto1LineItem"));
        mColLineItemType.setCellValueFactory(new PropertyValueFactory<>("lineItemType"));
        mColLineItemText.setCellValueFactory(new PropertyValueFactory<>("lineItemText"));



        tableView.setItems(dataList);
        tableView.setEditable(true);//表格设置为可编辑

        termOfPaymentAssembly.setDisable(true);



        initSaleOrgComboBox();
        initProductGroupComboBox();
        initDistributionChannelComboBox();
        initSalesDeptComboBox();
        initSalesmanComboBox();
        JavaFxUtil.setFormatDateField(pattern, documentDateAssembly);
        initShippingConditionsComboBox();
        initTermOfPaymentComboBox();
        initOrderReferenceComboBox();


        approvalStatusAssembly.getItems().addAll(OrderStatuEnum.ORDER_STATU_PENDING.getName(),
                    OrderStatuEnum.ORDER_STATU_BUSINESS.getName(),OrderStatuEnum.ORDER_STATU_CANCEL.getName());
        deliveryStatusAssembly.getItems().addAll(OrderStatuEnum.DELIVERY_STATU_UNDELIVERED.getName(),
                    OrderStatuEnum.DELIVERY_STATU_PARTIAL.getName(),OrderStatuEnum.DELIVERY_STATU_COMPLETELY.getName());


        approvalStatusAssembly.getSelectionModel().select(OrderStatuEnum.ORDER_STATU_PENDING.getName());
        approvalStatusAssembly.setDisable(true);
        approvalStatusAssembly.setButtonCell(new ListCell(){

            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if(empty || item==null){
                    setStyle("-fx-text-fill: derive(-fx-control-inner-background,-30%)");
                } else {
                    setStyle("-fx-text-fill: #ffffff;-fx-font-weight: Bold;-fx-font-size: 12px;-fx-background-color: #0c0c0c ;");
                    setText(item.toString());
                }
            }

        });

        deliveryStatusAssembly.getSelectionModel().select(OrderStatuEnum.DELIVERY_STATU_UNDELIVERED.getName());
        deliveryStatusAssembly.setDisable(true);
        deliveryStatusAssembly.setButtonCell(new ListCell(){

            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                if(empty || item==null){
                    setStyle("-fx-text-fill: derive(-fx-control-inner-background,-30%)");
                } else {
                    setStyle("-fx-text-fill: #ffffff;-fx-font-weight: Bold;-fx-font-size: 12px;-fx-background-color: #0c0c0c ;");
                    setText(item.toString());
                }
            }

        });


        shifflAssembly.getItems().addAll("是","否");
        shifflAssembly.getSelectionModel().select("是");


        // 初始化所有checkbox复选框
        mColumnSelect.setCellValueFactory(param -> {
            final ObservableValue<CheckBox> observableValue = param.getValue().getCb().getCheckBox();
            return observableValue;
        });


        //单独选择的监听，开启复制功能
        tableView.getSelectionModel().getSelectedCells().addListener(new InvalidationListener() {

            @Override
            public void invalidated(javafx.beans.Observable observable) {
                ObservableList<TablePosition> observableList = (ObservableList<TablePosition>) observable;
                for(int i=0;i<observableList.size();i++){
                    TablePosition tablePosition = observableList.get(i);
                    String TableColumnName = tablePosition.getTableColumn().getText();//列名
                    Object cellData = tablePosition.getTableColumn().getCellData(tablePosition.getRow());

                    tableView.setOnKeyPressed(event -> {
                        if (keyCodeCopy.match(event)) {
                            String content = cellData == null ? "" : cellData.toString();
                            ClipboardContent clipboardContent = new ClipboardContent();
                            clipboardContent.putString(content);
                            Clipboard.getSystemClipboard().setContent(clipboardContent);
                        }
                    });

                }
            }
        });

        salesOrgAssembly.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(null != newValue) {
                    Map it = (Map) newValue;
                    String org = (String) it.get("vkorg");
                    initCustomerComboBox("",org);
                    initSongdfComboBox("",org);
                    initEndCustomerComboBox("",org);
                    xiaoszz = org;
                    dataList.removeAll(tableView.getItems());//清空原有明细行
                }else{
                    xiaoszz = "";
                    initCustomerComboBox("","");
                    initSongdfComboBox("","");
                    initEndCustomerComboBox("","");
                }
            }
        });


        customerNameAssembly.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(null != newValue) {
                    Map it = (Map) newValue;
                    customerCodeAssembly.setText((String) it.get("kunnr"));
                    songdfmcAssembly.getSelectionModel().select(it);
                    songdfdmAssembly.setText((String) it.get("kunnr"));

                    if(null != salesOrgAssembly.getValue()){//选择了销售组织
                        Map orgMap = (Map) salesOrgAssembly.getValue();
                        String org = (String) orgMap.get("vkorg");
                        String kunnr = (String) it.get("kunnr");
                        List<TvzbtVO> tvList = pdService.getTermOfPayment(kunnr,org);
                        if(null != tvList && tvList.size()>0){
                            Map m = new HashMap<>();
                            m.put("id",tvList.get(0).getId());
                            m.put("spras",tvList.get(0).getSpras());
                            m.put("zterm",tvList.get(0).getZterm());
                            m.put("vtext",tvList.get(0).getVtext());
                            termOfPaymentAssembly.getSelectionModel().select(m);

                        }else{
                            termOfPaymentAssembly.getSelectionModel().select(null);
                        }
                    }else{
                        CommonUtil._alertInformation("联动带出付款条件失败，请先选择销售组织");
                        return;
                    }

                    String kunnr = (String) it.get("kunnr");//客户代码
                    String vkorg= "";
                    Object salesOrgObject = salesOrgAssembly.getValue();
                    if(null != salesOrgObject){
                        Map orgMap = (Map) salesOrgObject;
                        vkorg = (String) orgMap.get("vkorg");
                    }


                }else{
                    customerCodeAssembly.setText("");
                    songdfmcAssembly.getSelectionModel().select(null);
                    customerCodeAssembly.setText("");
                    termOfPaymentAssembly.getSelectionModel().select(null);
                }
            }
        });

        songdfmcAssembly.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(null != newValue) {
                    Map it = (Map) newValue;
                    songdfdmAssembly.setText((String) it.get("kunnr"));
                }else{
                    songdfdmAssembly.setText("");
                }
            }
        });


       // NumberTableCellFactory.createNumberColumn(mColLineItem, 1);


        /*行号*/
        mColLineItem.setCellFactory((col) -> {
            TableCell<GeneralOrderDetailVO, String> cell = new TableCell<GeneralOrderDetailVO, String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    this.setText(null);
                    this.setGraphic(null);
                    if (!empty) {
                        int rowIndex = (getIndex() + 1)*10;
                        this.setText(String.valueOf(rowIndex));
                        this.getTableView().getItems().get(getIndex()).setLineItem(String.valueOf("00"+rowIndex));
                    }
                }
            };
            return cell;
        });


        /*行号*/
        /*Callback<TableColumn<GeneralOrderDetailVO, String>,
                TableCell<GeneralOrderDetailVO, String>> lineItemCellFactory
                = (TableColumn<GeneralOrderDetailVO, String> p) -> new EditingCellRequiredText();

        mColLineItem.setCellFactory(lineItemCellFactory);

        mColLineItem.setOnEditCommit(
            (TableColumn.CellEditEvent<GeneralOrderDetailVO, String> t) -> {

                if(null == t.getNewValue()){
                    return;
                }
                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setLineItem(t.getNewValue());

                lineList.add(Integer.parseInt(t.getNewValue()));
            });*/


        /* 物料 */
        Callback<TableColumn<GeneralOrderDetailVO, String>, TableCell<GeneralOrderDetailVO, String>> materialCellFactory
                = (TableColumn<GeneralOrderDetailVO, String> p) -> new EditOrderDetailCellPublicComboBox("showName","物料",xiaoszz,"addPage");

        mColMaterialDesc.setCellFactory(materialCellFactory);

        mColMaterialDesc.setOnEditCommit(
            (TableColumn.CellEditEvent<GeneralOrderDetailVO, String> t) -> {

                if(null == t.getNewValue()){
                    return;
                }

                JSONObject newValue = JSONObject.parseObject(t.getNewValue());

                t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                    .setMaterialDesc((String) newValue.get("maktx"));

                mColMaterialNo.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setMaterialNo((String) newValue.get("matnr"));

                mColMnemonicCode.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setMnemonicCode((String) newValue.get("zcus01"));

                mColSpecification.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setSpecification((String) newValue.get("zggxh"));

                mColBrand.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setBrand((String) newValue.get("zcus22"));

                mColBasicUnit.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setBasicUnit((String) newValue.get("meins"));

                mColProductionLicense.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setProductionLicense((String) newValue.get("zcus04"));

                mColFullNameOfManufacturer.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setFullNameOfManufacturer((String) newValue.get("zcus02_1"));

                mColApprovalNo.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setApprovalNo((String) newValue.get("zcus03"));


                String materialGroupCode = (String) newValue.get("matkl");//物料组代码

                if(null != materialGroupCode && !"".equals(materialGroupCode)){

                    mColMaterialGroup.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setMaterialGroupCode(materialGroupCode);

                    List<T023tVO> mgList = pdService.getMaterialGroupListByMatkl(materialGroupCode);
                    if(null != mgList && mgList.size()>0){
                        mColMaterialGroup.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setMaterialGroup(mgList.get(0).getWgbez60());
                    }else{
                        mColMaterialGroup.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setMaterialGroup("");
                    }
                }else{

                    mColMaterialGroup.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setMaterialGroup("");

                    mColMaterialGroup.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setMaterialGroupCode("");
                }


                String storageTemperatureCode = (String) newValue.get("raube");//存储条件代码

                if(null != storageTemperatureCode && !"".equals(storageTemperatureCode)){

                    mColStorageTemperature.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setStorageTemperatureCode(storageTemperatureCode);

                    List<T142tVO> rList = pdService.getStorageTemperatureByRaube(storageTemperatureCode);

                    if(null != rList && rList.size()>0){

                        mColStorageTemperature.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setStorageTemperature(rList.get(0).getRbtxt());
                    }else{

                        mColStorageTemperature.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setStorageTemperature("");
                    }

                }else{
                    mColStorageTemperature.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setStorageTemperature("");

                    mColStorageTemperature.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setStorageTemperatureCode("");
                }




                Object salesOrgObject = salesOrgAssembly.getValue();
                if(null == salesOrgObject || "".equals(salesOrgObject)){
                    CommonUtil._alertInformation("选择物料前请选择销售组织!!!");
                }

                if(null != salesOrgObject && null != newValue){
                    Map orgMap = (Map) salesOrgObject;
                    String org = (String) orgMap.get("vkorg");
                    List<T001wVO> ls = pdService.getFactoryList((String) newValue.get("matnr"), org);
                    List<It_MvkeVO> taxList = pdService.getTaxList((String) newValue.get("matnr"), org);
                    List<CommonVo> zydList = pdService.getShippingPoint((String) newValue.get("matnr"), org);
                    List<It_MarcVO> matnrList = null;

                    if(ls.size()>0){

                        matnrList = pdService.getMarcListByParam((String) newValue.get("matnr"), ls.get(0).getWerks());

                        mColFactory.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setFactory(ls.get(0).getName1());

                        mColFactory.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setFactoryCode(ls.get(0).getWerks());

                    }else{
                        mColFactory.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setFactory("");

                        mColFactory.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setFactoryCode("");
                    }

                    if(taxList.size()>0){

                        String shuil = taxList.get(0).getTaxm1()==null?"":taxList.get(0).getTaxm1();

                        if("1".equals(shuil)){
                            mColTaxRate.getTableView().getItems().get(
                                    t.getTablePosition().getRow()).setTaxRate("13%");
                        }else if("2".equals(shuil)){
                            mColTaxRate.getTableView().getItems().get(
                                    t.getTablePosition().getRow()).setTaxRate("9%");
                        }else if("3".equals(shuil)){
                            mColTaxRate.getTableView().getItems().get(
                                    t.getTablePosition().getRow()).setTaxRate("6%");
                        }else if("4".equals(shuil)){
                            mColTaxRate.getTableView().getItems().get(
                                    t.getTablePosition().getRow()).setTaxRate("3%");
                        }else{
                            mColTaxRate.getTableView().getItems().get(
                                    t.getTablePosition().getRow()).setTaxRate("");
                        }

                    }else{
                        mColTaxRate.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setTaxRate("");
                    }

                    if(zydList.size()>0){
                        String zyd = zydList.get(0).getField1()==null?"":zydList.get(0).getField1();
                        String zydMc = zydList.get(0).getField2()==null?"":zydList.get(0).getField2();//装运点名称
                        String ladgr = zydList.get(0).getField3()==null?"":zydList.get(0).getField3();//装载组

                        if(null == zyd || "".equals(zyd) || !"0001".equals(ladgr)){

                            mColshippingPoint.getTableView().getItems().get(
                                    t.getTablePosition().getRow()).setShippingPoint("");

                            mColshippingPoint.getTableView().getItems().get(
                                    t.getTablePosition().getRow()).setShippingPointCode("");

                            CommonUtil._alertInformation("物料号："+newValue.get("matnr")
                                    +"对应的装运点为空，物料主数据不完整，请维护，谢谢");
                        }

                        mColshippingPoint.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setShippingPoint(zydMc);

                        mColshippingPoint.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setShippingPointCode(zyd);

                    }else{
                        mColshippingPoint.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setShippingPoint("");

                        mColshippingPoint.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setShippingPointCode("");

                        CommonUtil._alertInformation("物料号："+newValue.get("matnr")
                                +"对应的装运点为空，物料主数据不完整，请维护，谢谢");
                    }

                    if(null!= matnrList && matnrList.size()>0){
                        String pic = matnrList.get(0).getXchpf()==null?"":matnrList.get(0).getXchpf();
                        String xulh = matnrList.get(0).getSernp()==null?"":matnrList.get(0).getSernp();

                        if("X".equals(pic) && "".equals(xulh)){

                            mColBatch.getTableView().getItems().get(
                                    t.getTablePosition().getRow()).setBatch("批次");

                        }else if("".equals(pic) && "Z001".equals(xulh)){

                            mColBatch.getTableView().getItems().get(
                                    t.getTablePosition().getRow()).setBatch("序列号");

                        }else if("X".equals(pic) && "Z001".equals(xulh)){

                            mColBatch.getTableView().getItems().get(
                                    t.getTablePosition().getRow()).setBatch("异常");

                        }else{
                            mColBatch.getTableView().getItems().get(
                                    t.getTablePosition().getRow()).setBatch("");
                        }
                    }else{
                        mColBatch.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setBatch("");
                    }

                }else{
                    mColFactory.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setFactory("");

                    mColTaxRate.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setTaxRate("");

                    mColBatch.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setBatch("");
                }

                String customerCode = customerCodeAssembly.getText();//客户代码
                String saleCode = "";//销售组织代码
                String endCustomerCode = "";//终端客户代码
                String matkl = (String) newValue.get("matkl");//物料组
                if(null != salesOrgObject){
                    Map orgMap = (Map) salesOrgObject;
                    saleCode =  (String) orgMap.get("vkorg");
                }

                Object  endCustomerObject = endCustomerAssembly.getValue();
                if(null != endCustomerObject){
                    Map endCustomerMap = (Map) endCustomerObject;
                    endCustomerCode =  (String) endCustomerMap.get("kunnr");
                }


                String materialCode = (String) newValue.get("matnr");
                List<It_Ztsd_012VO> cmist = pdService.getCustomerMaterial(customerCode, saleCode, materialCode);
                if(null != cmist && cmist.size()>0){

                    mColCustomerMaterial.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            .setCustomerMaterial(cmist.get(0).getKdmat());

                    mColCustomerMaterialDesc.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            .setCustomerMaterialDesc(cmist.get(0).getPostx());

                    mColProvincialBiddingCode.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            .setProvincialBiddingCode(cmist.get(0).getZkdmat_2());

                    mColCityBiddingCode.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            .setCityBiddingCode(cmist.get(0).getZkdmat_3());

                    mColReservedCode.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            .setReservedCode(cmist.get(0).getZkdmat_4());
                }else{
                    mColCustomerMaterial.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            .setCustomerMaterial(null);

                    mColCustomerMaterialDesc.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            .setCustomerMaterialDesc(null);

                    mColProvincialBiddingCode.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            .setProvincialBiddingCode(null);

                    mColCityBiddingCode.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            .setCityBiddingCode(null);

                    mColReservedCode.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            .setReservedCode(null);

                }

                List<It_But000VO> cgdList = pdService.queryCustomerGeneralData(customerCode);
                if(null != cgdList && cgdList.size()>0){
                    mColBusinessLicense.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            .setBusinessLicense(cgdList.get(0).getZjynum());

                    mColProductionRecordCertificate.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            .setProductionRecordCertificate(cgdList.get(0).getZscbapz());
                }else{
                    mColBusinessLicense.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            .setBusinessLicense(null);

                    mColProductionRecordCertificate.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            .setProductionRecordCertificate(null);
                }


                GeneralOrderDetailVO gd = t.getTableView().getItems().get(
                        t.getTablePosition().getRow());//获取编辑对象

                String pricingDate = gd.getPricingDate()==null?DateUtils.getDate():gd.getPricingDate();

                Float umrez = Float.parseFloat(gd.getUmrez()==null?"1":gd.getUmrez());
                Float umren = Float.parseFloat(gd.getUmren()==null?"1":gd.getUmren());
                String basicUnit = gd.getOrderUnit()==null?(String) newValue.get("meins"):gd.getOrderUnit();//基本单位

                Map priceMap = getStandardPrice(saleCode, customerCode, endCustomerCode, materialCode,
                        pricingDate , 0f,basicUnit);

                if(null != priceMap){
                    Float price = Float.parseFloat((String) priceMap.get("price"));//价格
                    String unit = (String) priceMap.get("danw");//条件单位

                    if(basicUnit.equals(unit)){//返回的条件单位跟基本单位一致

                        mColStandardPrice.getTableView().getItems().get(
                                        t.getTablePosition().getRow())
                                .setStandardPrice(price);
                    }else{//返回的条件单位跟基本单位不一致
                        // 价格 = 乘以分母除以分子
                        price = price * umren/umrez;
                        mColStandardPrice.getTableView().getItems().get(
                                        t.getTablePosition().getRow())
                                .setStandardPrice(price);
                    }
                }


                tableView.refresh();

            });


        /*订单数量*/
        Callback<TableColumn<GeneralOrderDetailVO, String>,
                TableCell<GeneralOrderDetailVO, String>> orderQtyCellFactory
                = (TableColumn<GeneralOrderDetailVO, String> p) -> new EditingCellRequiredText();

        mColOrderQty.setCellFactory(orderQtyCellFactory);

        mColOrderQty.setOnEditCommit(
            (TableColumn.CellEditEvent<GeneralOrderDetailVO, String> t) -> {
                String shul = t.getNewValue();

                GeneralOrderDetailVO gd = t.getTableView().getItems().get(
                        t.getTablePosition().getRow());//获取编辑对象

                Float umrez = Float.parseFloat(gd.getUmrez()==null?"1":gd.getUmrez());
                Float umren = Float.parseFloat(gd.getUmren()==null?"1":gd.getUmren());


                if(!"".equals(shul) && decimalPattern.matcher(shul).matches()){

                    ((GeneralOrderDetailVO) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setOrderQty(Float.parseFloat(shul));

                    ((GeneralOrderDetailVO) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setQtyDelivered(0f);

                    ((GeneralOrderDetailVO) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setOpenQty(Float.parseFloat(shul));

                    Float orderBuomQty = Float.parseFloat(shul)*umrez/umren;

                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setOrderBuomQty(orderBuomQty);

                    Float manualPric = gd.getManualPrice()==null?0f:gd.getManualPrice();//手工价
                    Float discountRate = gd.getDiscountRate()==null?0f:gd.getDiscountRate();//折扣率
                    Float unusedQuota = gd.getUnusedQuota()==null?0f:gd.getUnusedQuota();//未使用额度


                    /* 返利金额 = 单价*数量*折扣率 */
                    Float rebateAmount = manualPric*orderBuomQty*(discountRate/100);

                    /* 折前金额 = 单价*数量 */
                    Float amountBeforeDiscount = orderBuomQty*manualPric;//折前金额
                    amountBeforeDiscount = Convert.toFloat2(amountBeforeDiscount,"0.00");

                    /* 折后金额 = 折前金额-折扣金额 */
                    Float discountedAmount = amountBeforeDiscount-rebateAmount;//折后金额
                    discountedAmount = Convert.toFloat2(discountedAmount,"0.00");

                    if(unusedQuota>rebateAmount){//未使用额度大于当前计算后的金额
                        mColRebateAmount.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setRebateAmount(rebateAmount);

                        mColAmountBeforeDiscount.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setAmountBeforeDiscount(amountBeforeDiscount);

                        mColDiscountedAmount.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setDiscountedAmount(discountedAmount);

                    }else{//未使用额度不够时，直接返回剩余额度，且折后金额 = 折前金额 -未使用额度

                        discountedAmount = amountBeforeDiscount-unusedQuota;//折后金额

                        mColRebateAmount.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setRebateAmount(unusedQuota);

                        mColAmountBeforeDiscount.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setAmountBeforeDiscount(amountBeforeDiscount);

                        mColDiscountedAmount.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setDiscountedAmount(discountedAmount);
                    }

                }else{
                    ((GeneralOrderDetailVO) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setOrderQty(0f);

                    ((GeneralOrderDetailVO) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setQtyDelivered(0f);

                    ((GeneralOrderDetailVO) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                    ).setOpenQty(0f);

                    t.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setOrderBuomQty(0f);
                }

                setTotalAmountAndAmountNotTaxed();

                tableView.refresh();

        });


        //行事件，选中行时，初始化相关需传参数的下拉组件
        tableView.setRowFactory( tv -> {
            TableRow<GeneralOrderDetailVO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                String name = event.getButton().name();
                //右击事件
                if((!row.isEmpty()) && event.getClickCount()==1 && name.equals(MouseButton.PRIMARY.name())){

                    GeneralOrderDetailVO rowData = row.getItem();


                    /*订单单位*/
                    Callback<TableColumn<GeneralOrderDetailVO, String>, TableCell<GeneralOrderDetailVO, String>> orderUnitCellFactory
                            = (TableColumn<GeneralOrderDetailVO, String> p) -> new EditCellParmaComboBox(rowData.getMaterialNo(),"meinh","订单单位");

                    mColOrderUnit.setCellFactory(orderUnitCellFactory);

                    /* 工厂 */
                    Callback<TableColumn<GeneralOrderDetailVO, String>, TableCell<GeneralOrderDetailVO, String>> factoryCellFactory
                            = (TableColumn<GeneralOrderDetailVO, String> p) -> new EditOrderDetailCellPublicComboBox("name1","工厂",xiaoszz+"|"+rowData.getMaterialNo(),"addPage");

                    mColFactory.setCellFactory(factoryCellFactory);


                    /*仓位*/
                    Callback<TableColumn<GeneralOrderDetailVO, String>, TableCell<GeneralOrderDetailVO, String>> positionCellFactory
                            = (TableColumn<GeneralOrderDetailVO, String> p) -> new EditCellParmaComboBox(rowData.getFactoryCode(),"lgobe","仓位");

                    mColInventoryLocation.setCellFactory(positionCellFactory);



                }

            });
            return row ;
        });


        /* 订单单位 */
        mColOrderUnit.setOnEditCommit(
            (TableColumn.CellEditEvent<GeneralOrderDetailVO, String> t) -> {

                if(null == t.getNewValue()){
                    return;
                }

                GeneralOrderDetailVO gd = t.getTableView().getItems().get(
                        t.getTablePosition().getRow());//获取编辑对象

                Float shul = gd.getOrderQty()==null?0f:gd.getOrderQty();

                JSONObject newValue = JSONObject.parseObject(t.getNewValue());

                Float umrez = Float.parseFloat(newValue.get("umrez")==null?"1": (String) newValue.get("umrez"));
                Float umren = Float.parseFloat(newValue.get("umren")==null?"1": (String) newValue.get("umren"));

                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setOrderUnit((String) newValue.get("meinh"));

                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setUmrez((String) newValue.get("umrez"));

                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setUmren((String) newValue.get("umren"));

                Float orderBuomQty = shul*umrez/umren;//  订单数量*分子/分母

                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setOrderBuomQty(orderBuomQty);


                Float manualPric = gd.getManualPrice()==null?0f:gd.getManualPrice();//手工价
                Float discountRate = gd.getDiscountRate()==null?0f:gd.getDiscountRate();//折扣率
                Float unusedQuota = gd.getUnusedQuota()==null?0f:gd.getUnusedQuota();//未使用额度


                /* 返利金额 = 单价*数量*折扣率 */
                Float rebateAmount = manualPric*orderBuomQty*(discountRate/100);

                /* 折前金额 = 单价*数量 */
                Float amountBeforeDiscount = orderBuomQty*manualPric;//折前金额
                amountBeforeDiscount = Convert.toFloat2(amountBeforeDiscount,"0.00");

                /* 折后金额 = 折前金额-折扣金额 */
                Float discountedAmount = amountBeforeDiscount-rebateAmount;//折后金额
                discountedAmount = Convert.toFloat2(discountedAmount,"0.00");

                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setRebateAmount(rebateAmount);//返利金额

                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setAmountBeforeDiscount(amountBeforeDiscount);//折前金额

                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setDiscountedAmount(discountedAmount);//折后金额

                
                setTotalAmountAndAmountNotTaxed();
                tableView.refresh();
        });




        /* 工厂 */
        mColFactory.setOnEditCommit(
            (TableColumn.CellEditEvent<GeneralOrderDetailVO, String> t) -> {

                if(null == t.getNewValue()){
                    return;
                }

                JSONObject newValue = JSONObject.parseObject(t.getNewValue());

                String factoryCode = (String) newValue.get("werks");//工厂代码

                ((GeneralOrderDetailVO) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setFactory((String) newValue.get("name1"));

                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setFactoryCode(factoryCode);//工厂代码

                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setInventoryLocation("");

                t.getTableView().getItems().get(
                        t.getTablePosition().getRow()
                ).setInventoryLocationCode("");


                GeneralOrderDetailVO gd = t.getTableView().getItems().get(
                        t.getTablePosition().getRow());//获取编辑对象

                String matnr = gd.getMaterialNo()==null?"":gd.getMaterialNo();//物料编码

                List<It_MarcVO> matnrList = pdService.getMarcListByParam(matnr, factoryCode);

                if(null!= matnrList && matnrList.size()>0){
                    String pic = matnrList.get(0).getXchpf()==null?"":matnrList.get(0).getXchpf();
                    String xulh = matnrList.get(0).getSernp()==null?"":matnrList.get(0).getSernp();

                    if("X".equals(pic) && "".equals(xulh)){

                        mColBatch.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setBatch("批次");

                    }else if("".equals(pic) && "Z001".equals(xulh)){

                        mColBatch.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setBatch("序列号");

                    }else if("X".equals(pic) && "Z001".equals(xulh)){

                        mColBatch.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setBatch("异常");

                    }else{
                        mColBatch.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setBatch("");
                    }
                }else{
                    mColBatch.getTableView().getItems().get(
                            t.getTablePosition().getRow()).setBatch("");
                }

                tableView.refresh();
        });



        /*库存地点*/
        mColInventoryLocation.setOnEditCommit(
            (TableColumn.CellEditEvent<GeneralOrderDetailVO, String> t) -> {

                if(null == t.getNewValue()){
                    return;
                }

                JSONObject newValue = JSONObject.parseObject(t.getNewValue());

                t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                .setInventoryLocation((String) newValue.get("lgobe"));

                t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                .setInventoryLocationCode((String) newValue.get("lgort"));//库存地点代码

                tableView.refresh();
        });


        /*手工价*/
        Callback<TableColumn<GeneralOrderDetailVO, String>,
                TableCell<GeneralOrderDetailVO, String>> manualPriceCellFactory
                = (TableColumn<GeneralOrderDetailVO, String> p) -> new EditingCellRequiredText();

        mColManualPrice.setCellFactory(manualPriceCellFactory);

        mColManualPrice.setOnEditCommit(
                (TableColumn.CellEditEvent<GeneralOrderDetailVO, String> t) -> {

                    String manualPric = t.getNewValue();

                    if(!"".equals(manualPric) && decimalPattern.matcher(manualPric).matches()){

                        ((GeneralOrderDetailVO) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setManualPrice(Float.parseFloat(manualPric));

                        GeneralOrderDetailVO gd = t.getTableView().getItems().get(
                                t.getTablePosition().getRow());//获取编辑对象

                        Float orderBuomQty = gd.getOrderBuomQty()==null?0f:gd.getOrderBuomQty();//订单基本单位数量
                        Float discountRate = gd.getDiscountRate()==null?0f:gd.getDiscountRate();//折扣率
                        Float unusedQuota = gd.getUnusedQuota()==null?0f:gd.getUnusedQuota();//未使用额度


                        /* 返利金额 = 单价*数量*折扣率 */
                        Float rebateAmount = Float.parseFloat(manualPric)*orderBuomQty*(discountRate/100);

                       /* 折前金额 = 单价*数量 */
                        Float amountBeforeDiscount = orderBuomQty*Float.parseFloat(manualPric);//折前金额
                        amountBeforeDiscount = Convert.toFloat2(amountBeforeDiscount,"0.00");

                        /* 折后金额 = 折前金额-折扣金额 */
                        Float discountedAmount = amountBeforeDiscount-rebateAmount;//折后金额
                        discountedAmount = Convert.toFloat2(discountedAmount,"0.00");

                        if(unusedQuota>rebateAmount){//未使用额度大于当前计算后的金额
                            mColRebateAmount.getTableView().getItems().get(
                                    t.getTablePosition().getRow()).setRebateAmount(rebateAmount);

                            mColAmountBeforeDiscount.getTableView().getItems().get(
                                    t.getTablePosition().getRow()).setAmountBeforeDiscount(amountBeforeDiscount);

                            mColDiscountedAmount.getTableView().getItems().get(
                                    t.getTablePosition().getRow()).setDiscountedAmount(discountedAmount);

                        }else{//未使用额度不够时，直接返回剩余额度，且折后金额 = 折前金额 -未使用额度

                            discountedAmount = amountBeforeDiscount-unusedQuota;//折后金额

                            mColRebateAmount.getTableView().getItems().get(
                                    t.getTablePosition().getRow()).setRebateAmount(unusedQuota);

                            mColAmountBeforeDiscount.getTableView().getItems().get(
                                    t.getTablePosition().getRow()).setAmountBeforeDiscount(amountBeforeDiscount);

                            mColDiscountedAmount.getTableView().getItems().get(
                                    t.getTablePosition().getRow()).setDiscountedAmount(discountedAmount);
                        }

                    }else{

                        ((GeneralOrderDetailVO) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setManualPrice(null);

                        mColRebateAmount.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setRebateAmount(null);

                        mColAmountBeforeDiscount.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setAmountBeforeDiscount(null);

                        mColDiscountedAmount.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setDiscountedAmount(null);
                    }

                    setTotalAmountAndAmountNotTaxed();

                    tableView.refresh();

                });



        /* 行项目类型 */
        Callback<TableColumn<GeneralOrderDetailVO, String>, TableCell<GeneralOrderDetailVO, String>> lineItemTypeCellFactory
                = (TableColumn<GeneralOrderDetailVO, String> p) -> new EditOrderDetailCellPublicComboBox("tkey","行项目类型",xiaoszz,"addPage");

        mColLineItemType.setCellFactory(lineItemTypeCellFactory);

        mColLineItemType.setOnEditCommit(
            (TableColumn.CellEditEvent<GeneralOrderDetailVO, String> t) -> {

                if(null == t.getNewValue()){
                    return;
                }

                JSONObject newValue = JSONObject.parseObject(t.getNewValue());

                String lineItemType = (String) newValue.get("tkey");
                if("赠转销".equals(lineItemType)){
                    t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            .setLineItemTypeCode("ZTNN");//销转赠项目类别
                }else{
                    t.getTableView().getItems().get(
                                    t.getTablePosition().getRow())
                            .setLineItemTypeCode("ZTA5");//流通订单项目类别
                }

                t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        .setLineItemType(lineItemType);



                tableView.refresh();
            });



        /*行项目文本*/
        Callback<TableColumn<GeneralOrderDetailVO, String>,
                TableCell<GeneralOrderDetailVO, String>> lineItemTextCellFactory
                = (TableColumn<GeneralOrderDetailVO, String> p) -> new EditingCell();

        mColLineItemText.setCellFactory(lineItemTextCellFactory);

        mColLineItemText.setOnEditCommit(
            (TableColumn.CellEditEvent<GeneralOrderDetailVO, String> t) -> {

                t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                            .setLineItemText(t.getNewValue());

                tableView.refresh();

            });


        /*定价日期*/
        Callback<TableColumn<GeneralOrderDetailVO, String>,
                TableCell<GeneralOrderDetailVO, String>> pricingDateCellFactory
                = (TableColumn<GeneralOrderDetailVO, String> p) -> new EditCellDatePicker(pattern);

        mColPricingDate.setCellFactory(pricingDateCellFactory);

        mColPricingDate.setOnEditCommit(
            (TableColumn.CellEditEvent<GeneralOrderDetailVO, String> t) -> {


                String pricingDate = t.getNewValue();

                t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        .setPricingDate(pricingDate);



                GeneralOrderDetailVO gd = t.getTableView().getItems().get(
                        t.getTablePosition().getRow());//获取编辑对象


                String customerCode = customerCodeAssembly.getText();//客户代码
                Object salesOrgObject = salesOrgAssembly.getValue();//销售组织
                String saleCode = "";//销售组织代码
                String endCustomerCode = "";//终端客户代码
                if(null != salesOrgObject){
                    Map orgMap = (Map) salesOrgObject;
                    saleCode =  (String) orgMap.get("vkorg");
                }

                Object  endCustomerObject = endCustomerAssembly.getValue();
                if(null != endCustomerObject){
                    Map endCustomerMap = (Map) endCustomerObject;
                    endCustomerCode =  (String) endCustomerMap.get("kunnr");
                }


                Float umrez = Float.parseFloat(gd.getUmrez()==null?"1":gd.getUmrez());
                Float umren = Float.parseFloat(gd.getUmren()==null?"1":gd.getUmren());
                String basicUnit = gd.getBasicUnit();//基本单位

                if(null != gd){

                    Map priceMap = getStandardPrice(saleCode, customerCode, endCustomerCode, gd.getMaterialNo(),
                            pricingDate, gd.getOrderQty(), gd.getOrderUnit());

                    if(null != priceMap){
                        Float price = Float.parseFloat((String) priceMap.get("price"));//价格
                        String unit = (String) priceMap.get("danw");//条件单位

                        mColOrderUnit.getTableView().getItems().get(
                                t.getTablePosition().getRow()).setOrderUnit(unit);

                        if(basicUnit.equals(unit)){//返回的条件单位跟基本单位一致

                            mColStandardPrice.getTableView().getItems().get(
                                            t.getTablePosition().getRow())
                                    .setStandardPrice(price);

                        }else{//返回的条件单位跟基本单位不一致
                          // 价格 = 乘以分母除以分子
                            price = price * umren/umrez;
                            mColStandardPrice.getTableView().getItems().get(
                                            t.getTablePosition().getRow())
                                    .setStandardPrice(price);
                        }
                    }else{
                        CommonUtil._alertInformation("请检查SAP对应的此物料是否有维护价格!!!");
                    }
                }


                tableView.refresh();

            });


        /*订单参考*/
        orderReferenceAssembly.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(null != newValue) {
                    Map it = (Map) newValue;
                    sapSalesOrderNoAssembly.setText((String) it.get("sapSalesOrderNo"));
                    ddsSalesOrderNoAssembly.setText((String) it.get("ddsSalesOrderNo"));

                    String approvalStatus = (String) it.get("approvalStatus");
                    String deliveryStatus = (String) it.get("deliveryStatus");
                    if(null != approvalStatus && !"".equals(approvalStatus)){
                        approvalStatusAssembly.getSelectionModel().select(approvalStatus);
                    }

                    if(null != deliveryStatus && !"".equals(deliveryStatus)){
                        deliveryStatusAssembly.getSelectionModel().select(deliveryStatus);
                    }

                    String salesOrgCode = (String) it.get("salesOrg");
                    if("".equals(salesOrgCode)){
                        salesOrgAssembly.getSelectionModel().select(null);
                    }else{
                        List<Map> salesOrg = querySaleOrgList(salesOrgCode);
                        if(null != salesOrg && salesOrg.size()>0){
                            salesOrgAssembly.getSelectionModel().select(salesOrg.get(0));
                        }
                    }

                    String productGroupCode = (String) it.get("productGroup");
                    if("".equals(productGroupCode)){
                        productGroupAssembly.getSelectionModel().select(null);
                    }else{
                        List<Map> productGroup = queryProductGroupList(productGroupCode);
                        if(null != productGroup && productGroup.size()>0){
                            productGroupAssembly.getSelectionModel().select(productGroup.get(0));
                        }
                    }


                    String distributionChannel = (String) it.get("distributionChannel");
                    if("".equals(distributionChannel)){
                        distributionChannelAssembly.getSelectionModel().select(null);
                    }else{
                        List<Map> distributionChannelList = queryDistributionChannelList(distributionChannel);
                        if(null != distributionChannelList && distributionChannelList.size()>0){
                            distributionChannelAssembly.getSelectionModel().select(distributionChannelList.get(0));
                        }
                    }



                    String customerCode = (String) it.get("customerCode");
                    if("".equals(customerCode)){
                        customerNameAssembly.getSelectionModel().select(null);
                        customerCodeAssembly.setText("");
                    }else{
                        List<Map> customerList = queryCustomerList(customerCode,salesOrgCode);
                        if(null != customerList && customerList.size()>0){
                            customerNameAssembly.getSelectionModel().select(customerList.get(0));
                            customerCodeAssembly.setText(customerCode);
                        }else{
                            customerCodeAssembly.setText("");
                        }

                    }


                    String songdfCode = (String) it.get("songdfdm");
                    if("".equals(songdfCode)){
                        songdfmcAssembly.getSelectionModel().select(null);
                        songdfdmAssembly.setText("");
                    }else{
                        List<Map> songdfList = queryCustomerList(songdfCode,salesOrgCode);
                        if(null != songdfList && songdfList.size()>0){
                            songdfmcAssembly.getSelectionModel().select(songdfList.get(0));
                            songdfdmAssembly.setText(songdfCode);
                        }else{
                            songdfdmAssembly.setText("");
                        }

                    }


                    String saleDeptCode = (String) it.get("saleDept");//销售部门代码
                    if("".equals(saleDeptCode)){
                        saleDeptAssembly.getSelectionModel().select(null);
                    }else{
                        List<Map> saleDeptList = querySalesDeptList(saleDeptCode);
                        if(null != saleDeptList && saleDeptList.size()>0){
                            saleDeptAssembly.getSelectionModel().select(saleDeptList.get(0));
                        }
                    }



                    String endCustomerCode = (String) it.get("endCustomer");//终端客户代码
                    if("".equals(endCustomerCode)){
                        endCustomerAssembly.getSelectionModel().select(null);
                    }else{
                        List<Map> endCustomerList = queryCustomerList(endCustomerCode,salesOrgCode);
                        if(null != endCustomerList && endCustomerList.size()>0){
                            endCustomerAssembly.getSelectionModel().select(endCustomerList.get(0));
                        }

                    }


                    String salesmanCode = (String) it.get("salesman");//销售员代码
                    if("".equals(salesmanCode)){
                        salesmanAssembly.getSelectionModel().select(null);
                    }else{
                        List<Map> salesmanList = querySalesmanList(salesmanCode);
                        if(null != salesmanList && salesmanList.size()>0){
                            salesmanAssembly.getSelectionModel().select(salesmanList.get(0));
                        }
                    }

                    documentDateAssembly.setValue(LocalDate.parse((String) it.get("documentDate")));
                    totalAmountAssembly.setText(String.valueOf(it.get("totalAmount")==null?"0":it.get("totalAmount")));
                    amountNotTaxedAssembly.setText(String.valueOf(it.get("amountNotTaxed")==null?"0":it.get("amountNotTaxed")));


                    String shippingConditionCode = (String) it.get("shippingConditions");//装运条件代码
                    if("".equals(shippingConditionCode)){
                        shippingConditionsAssembly.getSelectionModel().select(null);
                    }else{
                        List<Map> shippingConditionsList = queryshippingConditionsList(shippingConditionCode);
                        if(null != shippingConditionsList && shippingConditionsList.size()>0){
                            shippingConditionsAssembly.getSelectionModel().select(shippingConditionsList.get(0));
                        }
                    }

                    orderSummaryAssembly.setText((String) it.get("orderSummary"));//唛头/订单摘要
                    shippingRemarksAssembly.setText((String) it.get("shippingRemarks"));//发货信息备注


                    /*获取明细*/
                    List<GeneralOrderDetailVO> orderDetailList = orderService.getOrderListByDdsSalesOrderNo((String) it.get("ddsSalesOrderNo"));
                    dataList.removeAll(tableView.getItems());//清空原有明细行
                    for(GeneralOrderDetailVO gd : orderDetailList){
                        dataList.add(gd);
                    }

                }
            }
        });

    }


    /**
     * @Author cy
     * @Description 添加明细行
     * * @param null
     * @Return
     * @Date 2022/8/11 10:54
     */
    public void addDetail(ActionEvent actionEvent) {


        GeneralOrderDetailVO gd = new GeneralOrderDetailVO();
        gd.setCurrency("CNY");
        gd.setMultiAngleTradeType("1");
        gd.setPricingDate(DateUtils.getDate());
        dataList.add(gd);

        /*GeneralOrderDetailVO gd = new GeneralOrderDetailVO();
        Integer maxLine = 0;

        if(lineList.size()>0){
            maxLine = Collections.max(lineList);
        }


        if(dataList.size()<=0){
            gd.setLineItem("10");
            lineList.clear();
        }else{
            maxLine = maxLine +10;
            gd.setLineItem(maxLine.toString());
        }

        gd.setCurrency("CNY");
        gd.setMultiAngleTradeType("1");
        gd.setPricingDate(DateUtils.getDate());
        dataList.add(gd);

        lineList.add(Integer.parseInt(gd.getLineItem()));*/
    }

    /**
     * @Author cy
     * @Description 删除明细
     * * @param null
     * @Return
     * @Date 2022/8/31 10:10
     */
    public void deleteDetail(ActionEvent actionEvent) {

        int size = dataList.size();
        if (size <= 0) {
            return;
        }

        List<GeneralOrderDetailVO> removeList = new ArrayList<>();
        for (int i = size - 1; i >= 0; i--) {
            GeneralOrderDetailVO s = dataList.get(i);
            if (s.getCb().isSelected()) {
                removeList.add(s);
            }
        }
        dataList.removeAll(removeList);
        setTotalAmountAndAmountNotTaxed();

        tableView.refresh();
    }

    /**
     * @Author cy
     * @Description 复制明细行
     * * @param null
     * @Return
     * @Date 2022/10/21 17:23
     */
    public void copyDetail(ActionEvent actionEvent) {

        int size = dataList.size();
        if (size <= 0) {
            return;
        }

        for (int i = 0 ; i < size; i++) {
            GeneralOrderDetailVO s = dataList.get(i);
            if (s.getCb().isSelected()) {
                GeneralOrderDetailVO gd = new GeneralOrderDetailVO();
                gd.setMaterialNo(s.getMaterialNo());
                gd.setMaterialDesc(s.getMaterialDesc());
                gd.setSpecification(s.getSpecification());
                gd.setMnemonicCode(s.getMnemonicCode());
                gd.setOrderQty(s.getOrderQty());
                gd.setQtyDelivered(s.getQtyDelivered());
                gd.setOpenQty(s.getOpenQty());
                gd.setOrderUnit(s.getOrderUnit());
                gd.setBasicUnit(s.getBasicUnit());
                gd.setFactory(s.getFactory());
                gd.setInventoryLocation(s.getInventoryLocation());
                gd.setPricingDate(s.getPricingDate());
                gd.setStandardPrice(s.getStandardPrice());
                gd.setManualPrice(s.getManualPrice());
                gd.setOrderBuomQty(s.getOrderBuomQty());
                gd.setDiscountRate(s.getDiscountRate());
                gd.setRebateAmount(s.getRebateAmount());
                gd.setAmountBeforeDiscount(s.getAmountBeforeDiscount());
                gd.setDiscountedAmount(s.getDiscountedAmount());
                gd.setStorageTemperature(s.getStorageTemperature());
                gd.setTaxRate(s.getTaxRate());
                gd.setCurrency(s.getCurrency());
                gd.setBatch(s.getBatch());
                gd.setProductionLicense(s.getProductionLicense());
                gd.setFullNameOfManufacturer(s.getFullNameOfManufacturer());
                gd.setApprovalNo(s.getApprovalNo());
                gd.setCustomerMaterial(s.getCustomerMaterial());
                gd.setCustomerMaterialDesc(s.getCustomerMaterialDesc());
                gd.setProvincialBiddingCode(s.getProvincialBiddingCode());
                gd.setCityBiddingCode(s.getCityBiddingCode());
                gd.setReservedCode(s.getReservedCode());
                gd.setBrand(s.getBrand());
                gd.setMaterialGroup(s.getMaterialGroup());
                gd.setBusinessLicense(s.getBusinessLicense());
                gd.setProductionRecordCertificate(s.getProductionRecordCertificate());
                gd.setClaim(s.getClaim());
                gd.setLastUnitPrice(s.getLastUnitPrice());
                gd.setMultiAngleTradeCode(s.getMultiAngleTradeCode());
                gd.setMultiAngleTradeType(s.getMultiAngleTradeType());
                gd.setSto1No(s.getSto1No());
                gd.setSto1LineItem(s.getSto1LineItem());
                gd.setLineItemType(s.getLineItemType());
                gd.setLineItemText(s.getLineItemText());
                gd.setFactoryCode(s.getFactoryCode());
                gd.setUmrez(s.getUmrez());
                gd.setUmren(s.getUmren());
                gd.setUnusedQuota(s.getUnusedQuota());
                gd.setMaterialGroupCode(s.getMaterialGroupCode());
                gd.setStorageTemperatureCode(s.getStorageTemperatureCode());
                gd.setLineItemTypeCode(s.getLineItemTypeCode());
                gd.setInventoryLocationCode(s.getInventoryLocationCode());
                dataList.add(gd);
            }
        }

        setTotalAmountAndAmountNotTaxed();

        tableView.refresh();
    }


    /**
     * @Author cy
     * @Description 订单明细导入
     * * @param null
     * @Date 2022/10/25 14:33
     */
    public void ipmortDetail(ActionEvent actionEvent) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择Excel文件");
        Stage selectFile = new Stage();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Excel", "*.xlsx"),
                new FileChooser.ExtensionFilter("XLS", "*.xls"), new FileChooser.ExtensionFilter("XLSX", "*.xlsx"));
        File file = fileChooser.showOpenDialog(selectFile);

        ExcelUtil<GeneralOrderDetailVO> util = new ExcelUtil<GeneralOrderDetailVO>(GeneralOrderDetailVO.class);

        if (file != null) {
            Object salesOrgObject = salesOrgAssembly.getValue();
            String saleCode = "";//销售组织代码

            if(null == salesOrgObject || "".equals(salesOrgObject)){
                CommonUtil._alertInformation("导入明细前请选择销售组织!!!");
                return;
            }

            List<GeneralOrderDetailVO> successList = new ArrayList<>();
            List<GeneralOrderDetailVO> errorList = new ArrayList<>();
            try {
                List<GeneralOrderDetailVO> list = util.importExcel(new FileInputStream(file));
                for(GeneralOrderDetailVO s : list){
                    s.setPricingDate(DateUtils.parseCST(s.getPricingDate()));

                    List<It_MaraVO> maList = getMaterialListByMaterialNo(s.getMaterialNo(),xiaoszz);
                    if(null != maList && maList.size()>0){
                        s.setMaterialDesc(maList.get(0).getMaktx());//物料描述
                        s.setSpecification(maList.get(0).getZggxh());//规格型号
                        s.setMnemonicCode(maList.get(0).getZcus01());//助记码
                        s.setBasicUnit(maList.get(0).getMeins());//基本单位
                        s.setBrand(maList.get(0).getZcus22());//品牌

                        s.setMaterialGroupCode(maList.get(0).getMatkl());//物料组代码
                        List<T023tVO> mgList = pdService.getMaterialGroupListByMatkl(maList.get(0).getMatkl());
                        if(null != mgList && mgList.size()>0){
                           s.setMaterialGroup(mgList.get(0).getWgbez60());//物料组
                        }


                        List<It_MarmVO> ouList = pdService.queryOrderUnitList(
                                s.getMaterialNo(),s.getOrderUnit());
                        if(null != ouList && ouList.size() > 0){
                            s.setUmrez(ouList.get(0).getUmrez());//基本计量单位转换分子
                            s.setUmren(ouList.get(0).getUmren());//转换为基本计量单位的分母
                        }


                        Float shul = Convert.toFloat(s.getOrderQty());
                        Float umrez = Float.parseFloat(s.getUmrez()==null?"1":s.getUmrez());
                        Float umren = Float.parseFloat(s.getUmren()==null?"1":s.getUmren());
                        Float orderBuomQty = shul*umrez/umren;//订单基本单位数量=订单数量*分子/分母

                        s.setOrderBuomQty(orderBuomQty);//订单基本单位数量
                        s.setQtyDelivered(0f);//已交货数量（基本单位）
                        s.setOpenQty(shul);//未清数量（基本单位）

                        Float manualPric = s.getManualPrice()==null?0f:s.getManualPrice();//手工价
                        Float discountRate = s.getDiscountRate()==null?0f:s.getDiscountRate();//折扣率
                        Float unusedQuota = s.getUnusedQuota()==null?0f:s.getUnusedQuota();//未使用额度


                        /* 返利金额 = 单价*数量*折扣率 */
                        Float rebateAmount = manualPric*orderBuomQty*(discountRate/100);

                        /* 折前金额 = 单价*数量 */
                        Float amountBeforeDiscount = orderBuomQty*manualPric;//折前金额
                        amountBeforeDiscount = Convert.toFloat2(amountBeforeDiscount,"0.00");

                        /* 折后金额 = 折前金额-折扣金额 */
                        Float discountedAmount = amountBeforeDiscount-rebateAmount;//折后金额
                        discountedAmount = Convert.toFloat2(discountedAmount,"0.00");

                        if(unusedQuota>rebateAmount){//未使用额度大于当前计算后的金额

                            s.setRebateAmount(rebateAmount);//返利金额
                            s.setAmountBeforeDiscount(amountBeforeDiscount);//折前金额
                            s.setDiscountedAmount(discountedAmount);//折后金额

                        }else{//未使用额度不够时，直接返回剩余额度，且折后金额 = 折前金额 -未使用额度

                            discountedAmount = amountBeforeDiscount-unusedQuota;//折后金额
                            s.setRebateAmount(unusedQuota);
                            s.setAmountBeforeDiscount(amountBeforeDiscount);
                            s.setDiscountedAmount(discountedAmount);
                        }


                        s.setStorageTemperatureCode(maList.get(0).getRaube());//存储条件代码
                        List<T142tVO> rList = pdService.getStorageTemperatureByRaube(maList.get(0).getRaube());
                        if(null != rList && rList.size()>0){
                            s.setStorageTemperature(rList.get(0).getRbtxt());//存储温度
                        }


                        if(null != salesOrgObject) {
                            Map orgMap = (Map) salesOrgObject;
                            saleCode =  (String) orgMap.get("vkorg");
                            List<T001wVO> ls = pdService.getFactoryList(s.getMaterialNo(), saleCode);
                            List<It_MvkeVO> taxList = pdService.getTaxList(s.getMaterialNo(), saleCode);
                            List<CommonVo> zydList = pdService.getShippingPoint(s.getMaterialNo(), saleCode);
                            List<It_MarcVO> matnrList = null;
                            if(ls.size()>0){
                                matnrList = pdService.getMarcListByParam(s.getMaterialNo(), ls.get(0).getWerks());

                                s.setFactory(ls.get(0).getName1());//工厂
                                s.setFactoryCode(ls.get(0).getWerks());//工厂代码
                            }else{
                                s.setFactory("");
                                s.setFactoryCode("");
                            }

                            if(taxList.size()>0){

                                String shuil = taxList.get(0).getTaxm1()==null?"":taxList.get(0).getTaxm1();

                                if("1".equals(shuil)){
                                   s.setTaxRate("13%");//税率
                                }else if("2".equals(shuil)){
                                   s.setTaxRate("9%");
                                }else if("3".equals(shuil)){
                                    s.setTaxRate("6%");
                                }else if("4".equals(shuil)){
                                    s.setTaxRate("3%");
                                }else{
                                   s.setTaxRate("");
                                }
                            }

                            if(zydList.size()>0){
                                String zyd = zydList.get(0).getField1()==null?"":zydList.get(0).getField1();
                                String zydMc = zydList.get(0).getField2()==null?"":zydList.get(0).getField2();//装运点名称
                                String ladgr = zydList.get(0).getField3()==null?"":zydList.get(0).getField3();//装载组
                                if(null == zyd || "".equals(zyd) || !"0001".equals(ladgr)){
                                    s.setShippingPoint("");
                                    s.setShippingPointCode("");

                                    CommonUtil._alertInformation("物料号："+s.getMaterialNo()
                                            +"对应的装运点为空，物料主数据不完整，请维护，谢谢");
                                }

                                s.setShippingPoint(zydMc);
                                s.setShippingPointCode(zyd);

                            }else{
                                s.setShippingPoint("");
                                s.setShippingPointCode("");

                                CommonUtil._alertInformation("物料号："+s.getMaterialNo()
                                        +"对应的装运点为空，物料主数据不完整，请维护，谢谢");
                            }

                            if(null!= matnrList && matnrList.size()>0){
                                String pic = matnrList.get(0).getXchpf()==null?"":matnrList.get(0).getXchpf();
                                String xulh = matnrList.get(0).getSernp()==null?"":matnrList.get(0).getSernp();

                                if("X".equals(pic) && "".equals(xulh)){
                                   s.setBatch("批次");//是否批次/序列号管理
                                }else if("".equals(pic) && "Z001".equals(xulh)){
                                   s.setBatch("序列号");
                                }else if("X".equals(pic) && "Z001".equals(xulh)){
                                   s.setBatch("异常");
                                }else{
                                    s.setBatch("");
                                }

                            }else{
                                s.setBatch("");
                            }

                        }else{
                            s.setFactory("");
                            s.setTaxRate("");
                            s.setBatch("");
                        }

                        s.setCurrency("CNY");//货币
                        s.setProductionLicense(maList.get(0).getZcus04());//生产许可/备案凭证
                        s.setFullNameOfManufacturer(maList.get(0).getZcus02_1());//生产厂家全称
                        s.setApprovalNo(maList.get(0).getZcus03());//批准文号

                        List<It_Ztsd_012VO> cmist =
                                pdService.getCustomerMaterial(customerCodeAssembly.getText(),
                                            saleCode, s.getMaterialNo());
                        if(null != cmist && cmist.size()>0){

                            s.setCustomerMaterial(cmist.get(0).getKdmat());//客户物料

                            s.setCustomerMaterialDesc(cmist.get(0).getPostx());//客户物料描述

                            s.setProvincialBiddingCode(cmist.get(0).getZkdmat_2());//省招标编码

                            s.setCityBiddingCode(cmist.get(0).getZkdmat_3());//市招标编码

                            s.setReservedCode(cmist.get(0).getZkdmat_4());//预留编码
                        }else{
                            s.setCustomerMaterial("");
                            s.setCustomerMaterialDesc("");
                            s.setProvincialBiddingCode("");
                            s.setCityBiddingCode("");
                            s.setReservedCode("");
                        }


                        List<It_But000VO> cgdList = pdService.queryCustomerGeneralData(customerCodeAssembly.getText());
                        if(null != cgdList && cgdList.size()>0){
                           s.setBusinessLicense(cgdList.get(0).getZjynum());//经营许可证/执业许可证
                           s.setProductionRecordCertificate(cgdList.get(0).getZscbapz());//客户二类备案凭证
                        }else{
                           s.setBusinessLicense("");
                           s.setProductionRecordCertificate("");
                        }

                        s.setMultiAngleTradeType("1");

                        if("赠转销".equals(s.getLineItemType())){
                           s.setLineItemTypeCode("ZTNN");//销转赠项目类别
                        }else{
                           s.setLineItemTypeCode("ZTA5");//流通订单项目类别
                        }
                        successList.add(s);
                    }else{
                        errorList.add(s);
                    }
                }

                String errorMessage = "";
                if(null != errorList && errorList.size()>0){//存在未导入成功的数据时，拼接报错信息
                    for (GeneralOrderDetailVO es:errorList) {
                        errorMessage += "物料号："+es.getMaterialNo()+"、\n";
                    }
                    errorMessage += "在所选组织中不存在，请检查！！";
                    CommonUtil._alertErrorMessage("出错了",errorMessage);

                }else{
                    dataList.addAll(successList);
                    setTotalAmountAndAmountNotTaxed();
                }

            } catch (Exception e) {
                CommonUtil._alertErrorMessage("出错了","订单明细导入失败："+e.getMessage());
                e.printStackTrace();
            }
        }

    }


    /**
     * @Author cy
     * @Description 导出模板
     * @Return
     * @Date 2022/10/26 14:22
     */
    public void exportTemplet(ActionEvent actionEvent) {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("excel files (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);
        Stage s = new Stage();
        File file = fileChooser.showSaveDialog(s);
        if (file == null)
            return;
        if(file.exists()){//文件已存在，则删除覆盖文件
            file.delete();
        }
        String exportFilePath = file.getAbsolutePath();
        log.info("导出文件的路径" + exportFilePath);

        List <OrderImportTemplateVO>list = new ArrayList();
        OrderImportTemplateVO ot = new OrderImportTemplateVO();
        ot.setMaterialNo("11.01.11.005700");
        ot.setOrderQty(1);
        ot.setOrderUnit("HE");
        ot.setPricingDate("2022-01-01");
        ot.setManualPrice(1.0F);
        ot.setLineItemType("正常/退货/赠转销");
        ot.setLineItemText("导入时，将第一行删除，定价日期格式一定要为《2022-01-01》这种格式否则无法识别，" +
                "《行项目类型》列选项中，选择3个中的一个即可");
        list.add(ot);
        ExcelUtil<OrderImportTemplateVO> util = new ExcelUtil<OrderImportTemplateVO>(OrderImportTemplateVO.class);
        String rs = util.exportExcelCustomPath(list, "模板",exportFilePath);
    }

    /**
     * @Author cy
     * @Description 刷新标准价
     * @Return
     * @Date 2022/11/8 8:50
     */
    public void refreshStandardPrice(ActionEvent actionEvent) {
        ObservableList<GeneralOrderDetailVO> detailList = tableView.getItems();

        String customerCode = customerCodeAssembly.getText();//客户代码
        Object salesOrgObject = salesOrgAssembly.getValue();//销售组织对象
        String saleCode = "";//销售组织代码
        String endCustomerCode = "";//终端客户代码

        if(null != salesOrgObject){
            Map orgMap = (Map) salesOrgObject;
            saleCode =  (String) orgMap.get("vkorg");
        }

        Object endCustomerObject = endCustomerAssembly.getValue();//终端客户对象
        if(null != endCustomerObject){
            Map endCustomerMap = (Map) endCustomerObject;
            endCustomerCode =  (String) endCustomerMap.get("kunnr");
        }

        Float totalAmount = 0f;//总金额
        Float taxamount = 0f;//税额
        Float amountNotTaxed = 0f;//未税金额

        for (GeneralOrderDetailVO go:detailList) {
            Map priceMap = getStandardPrice(saleCode, customerCode, endCustomerCode, go.getMaterialNo(),
                go.getPricingDate() , 0f,go.getBasicUnit());

            if(null != priceMap){
                go.setStandardPrice(Float.parseFloat((String) priceMap.get("price")));
            }

            Float manualPric = go.getManualPrice()==null?0f:go.getManualPrice();//手工价
            Float orderBuomQty = go.getOrderBuomQty()==null?0f:go.getOrderBuomQty();//订单基本单位数量
            Float discountRate = go.getDiscountRate()==null?0f:go.getDiscountRate();//折扣率
            Float unusedQuota = go.getUnusedQuota()==null?0f:go.getUnusedQuota();//未使用额度
            String taxRate = go.getTaxRate()==null?"100%":go.getTaxRate();//税率
            taxRate = taxRate.replaceAll("%","");
            Float shuil = Convert.toFloat(taxRate,100f);


            /* 返利金额 = 单价*数量*折扣率 */
            Float rebateAmount = manualPric*orderBuomQty*(discountRate/100);

            /* 折前金额 = 单价*数量 */
            Float amountBeforeDiscount = orderBuomQty*manualPric;//折前金额
            amountBeforeDiscount = Convert.toFloat2(amountBeforeDiscount,"0.00");

            /* 折后金额 = 折前金额-折扣金额 */
            Float discountedAmount = amountBeforeDiscount-rebateAmount;//折后金额
            discountedAmount = Convert.toFloat2(discountedAmount,"0.00");

            if(unusedQuota>rebateAmount){//未使用额度大于当前计算后的金额
                go.setRebateAmount(rebateAmount);
                go.setAmountBeforeDiscount(amountBeforeDiscount);
                go.setDiscountedAmount(discountedAmount);
            }else{//未使用额度不够时，直接返回剩余额度，且折后金额 = 折前金额 -未使用额度

                discountedAmount = amountBeforeDiscount-unusedQuota;//折后金额
                go.setRebateAmount(unusedQuota);
                go.setAmountBeforeDiscount(amountBeforeDiscount);
                go.setDiscountedAmount(discountedAmount);
            }

            taxamount += discountedAmount*shuil/100;
            amountNotTaxed += discountedAmount;
        }

        totalAmount = amountNotTaxed - taxamount;
        totalAmountAssembly.setText(String.valueOf(totalAmount));
        amountNotTaxedAssembly.setText(String.valueOf(amountNotTaxed));

        tableView.refresh();

    }


    /**
     * @Author cy
     * @Description 获取订单单位列表
     * @param matnr 物料编码
     * @param matnr 单位
     * @Return
     * @Date 2022/11/8 9:33
     */
    public List<Map> queryOrderUnitList(String matnr,String meinh){

        List<It_MarmVO> itemList = pdService.queryOrderUnitList(matnr,meinh);

        List<Map> mpList = new ArrayList<Map>();
        if(null != itemList && itemList.size()>0){
            for (It_MarmVO item :itemList ) {
                Map m = new HashMap<>();
                m.put("matnr",item.getMatnr());
                m.put("meinh",item.getMeinh());
                m.put("umrez",item.getUmrez());
                m.put("umren",item.getUmren());
                mpList.add(m);
            }
        }
        return mpList;
    }


    /***
     * 初始化销售组织下拉控件
     */
    public void initSaleOrgComboBox(){

        salesOrgAssembly.setPromptText("请输入销售组织名称搜索");
        salesOrgAssembly.setPlaceholder(new Label("空值"));
        AutoCompleteComboBoxListener.getComboBox(salesOrgAssembly, FXCollections.observableList(querySaleOrgList("")),"vtxtk");
    }


    /**
     * @Author cy
     * @Description 获取销售组织列表
     * @param vkorg 销售组织代码
     * @Return
     * @Date 2022/10/20 10:53
     */
    public List<Map> querySaleOrgList(String vkorg){

        List<TvkotVO> itemList = pdService.querySaleOrgList(vkorg);

        List<Map> mpList = new ArrayList<Map>();
        if(null != itemList && itemList.size()>0){
            for (TvkotVO item :itemList ) {
                Map m = new HashMap<>();
                m.put("spras",item.getSpras());
                m.put("vkorg",item.getVkorg());
                m.put("vtxtk",item.getVtxtk());
                mpList.add(m);
            }
        }
        return mpList;
    }


    /**
     * @Author cy
     * @Description 初始化产品组下拉列表
     * @Return
     * @Date 2022/8/23 16:20
     */
    public void initProductGroupComboBox(){

        productGroupAssembly.setPromptText("请输入产品组搜索");
        productGroupAssembly.setPlaceholder(new Label("空值"));
        AutoCompleteComboBoxListener.getComboBox(productGroupAssembly, FXCollections.observableList(queryProductGroupList("")),"vtxtk");
    }


    /**
     * @Author cy
     * @Description 获取产品组列表
     * @param spart 产品组代码
     * @Return 
     * @Date 2022/10/20 11:34
     */
    public List<Map> queryProductGroupList(String spart){

        List<TspatVO> itemList = pdService.queryProductGroupList(spart);

        List<Map> mpList = new ArrayList<Map>();
        if(null != itemList && itemList.size()>0){
            for (TspatVO item :itemList ) {
                Map m = new HashMap<>();
                m.put("spras",item.getSpras());
                m.put("spart",item.getSpart());
                m.put("vtxtk",item.getVtxtk());
                mpList.add(m);
            }
        }
        return mpList;
    }

    /**
     * @Author cy
     * @Description 初始化分销渠道
     * @Return
     * @Date 2023/1/16 16:08
     */
    public void initDistributionChannelComboBox(){

        distributionChannelAssembly.setPromptText("请输入分销渠道搜索");
        distributionChannelAssembly.setPlaceholder(new Label("空值"));
        AutoCompleteComboBoxListener.getComboBox(distributionChannelAssembly, FXCollections.observableList(queryDistributionChannelList("")),"vtxtk");
    }


    /**
     * @Author cy
     * @Description 查询分销渠道
     * * @param null
     * @Date 2023/1/16 16:44
     */
    public List<Map> queryDistributionChannelList(String vtweg){

        List<TvtwtVO> itemList = pdService.queryDistributionChannelList(vtweg);

        List<Map> mpList = new ArrayList<Map>();
        if(null != itemList && itemList.size()>0){
            for (TvtwtVO item :itemList ) {
                Map m = new HashMap<>();
                m.put("spras",item.getSpras());
                m.put("vtweg",item.getVtweg());
                m.put("vtxtk",item.getVtxtk());
                mpList.add(m);
            }
        }
        return mpList;
    }

    /***
     * 初始化客户下拉控件
     */
    public void initCustomerComboBox(String kunnr,String vkorg){

        customerNameAssembly.getSelectionModel().select(-1);
        customerNameAssembly.getItems().clear();
        customerNameAssembly.getEditor().setText("");
        customerCodeAssembly.setText("");

        customerNameAssembly.setPromptText("请输入客户名称搜索");
        customerNameAssembly.setPlaceholder(new Label("空值"));
        AutoCompleteComboBoxListener.getComboBox(customerNameAssembly, FXCollections.observableList(queryCustomerList(kunnr,vkorg)),"name1");
    }


    /**
     * @Author cy
     * @Description 获取客户列表
     * @param kunnr
     * @Return
     * @Date 2022/10/20 11:52
     */
    public List<Map> queryCustomerList(String kunnr,String vkorg){

        List<CustomerSaleDataVO> itemList = pdService.queryCustomerList(kunnr,vkorg);

        List<Map> mpList = new ArrayList<Map>();
        if(null != itemList && itemList.size()>0){
            for (CustomerSaleDataVO item :itemList ) {
                Map m = new HashMap<>();
                m.put("name1",item.getName1());
                m.put("name2",item.getName2());
                m.put("kunnr",item.getKunnr());
                m.put("vkorg",item.getVkorg());
                m.put("vtweg",item.getVtweg());
                m.put("spart",item.getSpart());
                m.put("ernam",item.getErnam());
                m.put("erdat",item.getErdat());
                m.put("begru",item.getBegru());
                m.put("loevm",item.getLoevm());
                m.put("versg",item.getVersg());
                m.put("aufsd",item.getAufsd());
                m.put("kalks",item.getKalks());
                m.put("kdgrp",item.getKdgrp());
                m.put("bzirk",item.getBzirk());
                m.put("konda",item.getKonda());
                m.put("pltyp",item.getPltyp());
                m.put("inco1",item.getInco1());
                m.put("inco2",item.getInco2());
                m.put("lifsd",item.getLifsd());
                m.put("kzazu",item.getKzazu());
                m.put("vsbed",item.getVsbed());
                m.put("faksd",item.getFaksd());
                m.put("waers",item.getWaers());
                m.put("klabc",item.getKlabc());
                m.put("ktgrd",item.getKtgrd());
                m.put("zterm",item.getZterm());
                m.put("vwerk",item.getVwerk());
                m.put("vkgrp",item.getVkgrp());
                m.put("vkbur",item.getVkbur());
                m.put("prfre",item.getPrfre());
                m.put("kkber",item.getKkber());
                m.put("podkz",item.getPodkz());
                mpList.add(m);
            }
        }
        return mpList;
    }

    /**
     * @Author cy
     * @Description 初始化送达方下拉控件
     * * @param null
     * @Return
     * @Date 2022/8/23 17:16
     */
    public void initSongdfComboBox(String kunnr,String vkorg){

        songdfmcAssembly.getSelectionModel().select(-1);
        songdfmcAssembly.getItems().clear();
        songdfmcAssembly.getEditor().setText("");
        songdfdmAssembly.setText("");

        songdfmcAssembly.setPromptText("请输入送达方名称搜索");
        songdfmcAssembly.setPlaceholder(new Label("空值"));
        AutoCompleteComboBoxListener.getComboBox(songdfmcAssembly, FXCollections.observableList(queryCustomerList(kunnr,vkorg)),"name1");
    }

    /**
     * @Author cy
     * @Description 初始化终端客户下拉控件
     * * @param null
     * @Return
     * @Date 2022/8/25 17:36
     */
    public void initEndCustomerComboBox(String kunnr,String vkorg){

        endCustomerAssembly.getSelectionModel().select(-1);
        endCustomerAssembly.getItems().clear();
        endCustomerAssembly.getEditor().setText("");

        endCustomerAssembly.setPromptText("请输入客户名称搜索");
        endCustomerAssembly.setPlaceholder(new Label("空值"));
        AutoCompleteComboBoxListener.getComboBox(endCustomerAssembly, FXCollections.observableList(queryCustomerList(kunnr,vkorg)),"name1");
    }



    /**
     * @Author cy
     * @Description 初始化销售部门下拉控件
     * * @param null
     * @Return
     * @Date 2022/8/24 9:00
     */
    public void initSalesDeptComboBox(){

        saleDeptAssembly.setPromptText("请输入销售部门搜索");
        saleDeptAssembly.setPlaceholder(new Label("空值"));
        AutoCompleteComboBoxListener.getComboBox(saleDeptAssembly, FXCollections.observableList(querySalesDeptList("")),"bezei");
    }


    /**
     * @Author cy
     * @Description 获取销售办公室列表
     * @param vkbur 销售办公室(部门)代码
     * @Return 
     * @Date 2022/10/20 16:34
     */
    public List<Map> querySalesDeptList(String vkbur){

        List<TvkbtVO> itemList = pdService.querySalesDeptList(vkbur);

        List<Map> mpList = new ArrayList<Map>();
        if(null != itemList && itemList.size()>0){
            for (TvkbtVO item :itemList ) {
                Map m = new HashMap<>();
                m.put("spras",item.getSpras());
                m.put("vkbur",item.getVkbur());
                m.put("bezei",item.getBezei());
                mpList.add(m);
            }
        }
        return mpList;
    }



    /**
     * @Author cy
     * @Description 获取销售小组列表
     * @param vkgrp 销售小组代码
     * @Return 
     * @Date 2022/10/20 17:28
     */
    public List<Map> querySalesGroupList(String vkgrp){

        List<TvgrtVO> itemList = pdService.querySalesGroupList(vkgrp);

        List<Map> mpList = new ArrayList<Map>();
        if(null != itemList && itemList.size()>0){
            for (TvgrtVO item :itemList ) {
                Map m = new HashMap<>();
                m.put("spras",item.getSpras());
                m.put("vkgrp",item.getVkgrp());
                m.put("bezei",item.getBezei());
                mpList.add(m);
            }
        }
        return mpList;
    }



    /**
     * @Author cy
     * @Description 获取订单原因列表
     * @param augru 订单原因代码
     * @Return 
     * @Date 2022/10/21 9:14
     */
    public List<Map> queryOrderReasonList(String augru){

        List<TvautVO> itemList = pdService.queryOrderReasonList(augru);

        List<Map> mpList = new ArrayList<Map>();
        if(null != itemList && itemList.size()>0){
            for (TvautVO item :itemList ) {
                Map m = new HashMap<>();
                m.put("spras",item.getSpras());
                m.put("augru",item.getAugru());
                m.put("bezei",item.getBezei());
                mpList.add(m);
            }
        }
        return mpList;
    }


    /**
     * @Author cy
     * @Description 初始化业务员下拉框
     * * @param null
     * @Return
     * @Date 2022/8/26 9:57
     */
    public void initSalesmanComboBox(){

        salesmanAssembly.setPromptText("请输入销售员搜索");
        salesmanAssembly.setPlaceholder(new Label("空值"));
        AutoCompleteComboBoxListener.getComboBox(salesmanAssembly, FXCollections.observableList(querySalesmanList("")),"name1");
    }

    /**
     * @Author cy
     * @Description 获取业务员列表
     * @param kunnr 业务员代码
     * @Return 
     * @Date 2022/10/21 10:04
     */
    public List<Map> querySalesmanList(String kunnr){

        List<It_Kna1VO> itemList = pdService.querySalesmanList(kunnr);

        List<Map> mpList = new ArrayList<Map>();
        if(null != itemList && itemList.size()>0){
            for (It_Kna1VO item :itemList ) {
                Map m = new HashMap<>();
                m.put("kunnr",item.getKunnr());
                m.put("name1",item.getName1());
                m.put("name2",item.getName2());
                m.put("salesman",item.getSalesman());
                mpList.add(m);
            }
        }
        return mpList;
    }



    /**
     * @Author cy
     * @Description 获取装运点列表
     * @param vstel 装运点代码
     * @Return 
     * @Date 2022/10/21 11:13
     */
    public List<Map> queryShippingPointList(String vstel){

        List<TvsttVO> itemList = pdService.queryShippingPointList(vstel);

        List<Map> mpList = new ArrayList<Map>();
        if(null != itemList && itemList.size()>0){
            for (TvsttVO item :itemList ) {
                Map m = new HashMap<>();
                m.put("spras",item.getSpras());
                m.put("vstel",item.getVstel());
                m.put("vtext",item.getVtext());
                mpList.add(m);
            }
        }
        return mpList;
    }


    /**
     * @Author cy
     * @Description 初始化装运条件下拉框
     * * @param null
     * @Return
     * @Date 2022/8/26 14:22
     */
    public void initShippingConditionsComboBox(){

        shippingConditionsAssembly.setPromptText("请输入装运条件搜索");
        shippingConditionsAssembly.setPlaceholder(new Label("空值"));
        AutoCompleteComboBoxListener.getComboBox(shippingConditionsAssembly, FXCollections.observableList(queryshippingConditionsList("")),"vtext");
    }


    /**
     * @Author cy
     * @Description 获取装运条件列表
     * @param vsbed 装运条件代码
     * @Return
     * @Date 2022/10/21 11:21
     */
    public List<Map> queryshippingConditionsList(String vsbed){

        List<TvsbtVO> itemList = pdService.queryshippingConditionsList(vsbed);

        List<Map> mpList = new ArrayList<Map>();
        if(null != itemList && itemList.size()>0){
            for (TvsbtVO item :itemList ) {
                Map m = new HashMap<>();
                m.put("spras",item.getSpras());
                m.put("vsbed",item.getVsbed());
                m.put("vtext",item.getVtext());
                mpList.add(m);
            }
        }
        return mpList;
    }

    /**
     * @Author cy
     * @Description 初始化付款条件下拉框
     * * @param null
     * @Return
     * @Date 2022/8/30 9:23
     */
    public void initTermOfPaymentComboBox(){

        List<TvzbtVO> itemList = pdService.queryTermOfPaymentList();

        List<Map> mpList = new ArrayList<Map>();
        if(null != itemList && itemList.size()>0){
            for (TvzbtVO item :itemList ) {
                Map m = new HashMap<>();
                m.put("spras",item.getSpras());
                m.put("zterm",item.getZterm());
                m.put("vtext",item.getVtext());
                mpList.add(m);
            }
        }


        termOfPaymentAssembly.setStyle("-fx-background-color: #31b0d5 ;");
        termOfPaymentAssembly.getEditor().setStyle("-fx-text-fill: black ;-fx-font-weight: Bold;-fx-font-size: 12px;");

        termOfPaymentAssembly.setPromptText("请输入付款条件搜索");
        termOfPaymentAssembly.setPlaceholder(new Label("空值"));
        AutoCompleteComboBoxListener.getComboBox(termOfPaymentAssembly, FXCollections.observableList(mpList),"vtext");
    }



    /**
     * @Author cy
     * @Description 根据客户代码和销售组织获取对应的多角贸易代码
     * @param kunnr 客户代码
     * @param vkorg 销售组织代码
     * @Return
     * @Date 2022/10/21 11:35
     */
    public List<Map> queryMultiAngleTrade(String kunnr,String vkorg){

        List<It_Ztsd_002VO> itemList = pdService.queryMultiAngleTrade(kunnr, vkorg);

        List<Map> mpList = new ArrayList<Map>();
        if(null != itemList && itemList.size()>0){
            for (It_Ztsd_002VO item :itemList ) {
                Map m = new HashMap<>();
                m.put("kunnr",item.getKunnr());
                m.put("vkorg",item.getVkorg());
                m.put("knumh",item.getKnumh());
                m.put("name1",item.getName1());
                m.put("c_default",item.getC_default());
                m.put("name2",item.getName2());
                m.put("datab",item.getDatab());
                m.put("datbi",item.getDatbi());
                m.put("loevm_ko",item.getLoevm_ko());
                m.put("showName",item.getShowName());
                mpList.add(m);
            }
        }
        return mpList;
    }



    /**
     * @Author cy
     * @Description 通过物料编码获取相应的物料信息
     * @param matnr 物料编号
     * @Return
     * @Date 2022/10/25 11:47
     */
    public List<It_MaraVO> getMaterialListByMaterialNo(String matnr,String vkorg){

        return pdService.getMaterialListByMaterialNo(matnr,vkorg);
    }




    /**
     * @Author cy
     * @Description 初始化订单参考下拉组件
     * @Return
     * @Date 2022/10/19 17:38
     */
    public void initOrderReferenceComboBox(){

        List<GeneralOrderVO> itemList = orderService.getOrderList("");

        List<Map> mpList = new ArrayList<Map>();
        if(null != itemList && itemList.size()>0){
            for (GeneralOrderVO item :itemList ) {
                Map m = new HashMap<>();
                m.put("ddsSalesOrderNo",item.getDdsSalesOrderNo());
                m.put("sapSalesOrderNo",item.getSapSalesOrderNo());
                m.put("salesOrg",item.getSalesOrg());
                m.put("productGroup",item.getProductGroup());
                m.put("distributionChannel",item.getDistributionChannel());
                m.put("customerName",item.getCustomerName());
                m.put("customerCode",item.getCustomerCode());
                m.put("songdfmc",item.getSongdfmc());
                m.put("songdfdm",item.getSongdfdm());
                m.put("saleDept",item.getSaleDept());
                m.put("termOfPayment",item.getTermOfPayment());
                m.put("orderReason",item.getOrderReason());
                m.put("endCustomer",item.getEndCustomer());
                m.put("salesman",item.getSalesman());
                m.put("documentDate",item.getDocumentDate());
                m.put("totalAmount",item.getTotalAmount());
                m.put("amountNotTaxed",item.getAmountNotTaxed());
                m.put("shippingConditions",item.getShippingConditions());
                m.put("orderSummary",item.getOrderSummary());
                m.put("shippingRemarks",item.getShippingRemarks());
                m.put("multiAngleTradeCode",item.getMultiAngleTradeCode());
                m.put("approvalStatus",item.getApprovalStatus());
                m.put("deliveryStatus",item.getDeliveryStatus());
                mpList.add(m);
            }
        }

        orderReferenceAssembly.setPromptText("请输入DDS订单编号搜索");
        orderReferenceAssembly.setPlaceholder(new Label("空值"));
        AutoCompleteComboBoxListener.getComboBox(orderReferenceAssembly, FXCollections.observableList(mpList), "ddsSalesOrderNo");

    }





    /**
     * @Author cy
     * @Description 初始化公共下拉框
     * @param cob 下拉组件
     * @param text 显示的字段
     * @param componentName 组件名称
     * @param vkorg 销售组织，组件为物料组件时，vkorg就是单纯的销售组织，
     *              如果组件为工厂时，此时vkorg为销售组织+物料号品接，如：3000|2.1.01.00003200
     * @Return
     * @Date 2022/9/1 11:44
     */
    public static void initOrderDetailComboBox(ComboBox cob,String text,String componentName,String vkorg){


        if("物料".equals(componentName)){
            List<It_MaraVO> itemList = pdService.queryMaterialList("",vkorg);

            List<Map> mpList = new ArrayList<Map>();
            if(null != itemList && itemList.size()>0){
                for (It_MaraVO item :itemList ) {
                    Map m = new HashMap<>();
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
                    m.put("zcus22",item.getZcus22());
                    m.put("zcus03",item.getZcus03());
                    m.put("zcus04",item.getZcus04());
                    m.put("zcus02_1",item.getZcus02_1());
                    m.put("zcus02_2",item.getZcus02_2());
                    m.put("showName",item.getShowName());
                    mpList.add(m);
                }
            }

            cob.setPromptText("请输入物料搜索");
            cob.setPlaceholder(new Label("空值"));
            AutoCompleteComboBoxListener.getComboBox(cob, FXCollections.observableList(mpList),text);

        }else if("工厂".equals(componentName)){

            String saleCode = vkorg.substring(0,vkorg.indexOf("|"));
            String matnr = vkorg.substring(vkorg.indexOf("|")+1,vkorg.length());
            List<T001wVO> itemList = pdService.getFactoryList(matnr,saleCode);

            List<Map> mpList = new ArrayList<Map>();
            if(null != itemList && itemList.size()>0){
                for (T001wVO item :itemList ) {
                    Map m = new HashMap<>();
                    m.put("werks",item.getWerks());
                    m.put("name1",item.getName1());
                    mpList.add(m);
                }
            }

            cob.setPromptText("请输入工厂搜索");
            cob.setPlaceholder(new Label("空值"));
            AutoCompleteComboBoxListener.getComboBox(cob, FXCollections.observableList(mpList),text);
        }else if("行项目类型".equals(componentName)){

            List<Map> mpList = new ArrayList<Map>();
            Map mp = new HashMap();
            mp.put("tkey","退货");
            mpList.add(mp);

            Map mp2 = new HashMap();
            mp2.put("tkey","正常");
            mpList.add(mp2);

            Map mp3 = new HashMap();
            mp3.put("tkey","赠转销");
            mpList.add(mp3);

            cob.setPromptText("请输入行项目类型搜索");
            cob.setPlaceholder(new Label("空值"));
            AutoCompleteComboBoxListener.getComboBox(cob, FXCollections.observableList(mpList),text);
        }

    }



    /**
     * @Author cy
     * @Description
     * @param vkorg 销售组织
     * @param kunnr 客户代码
     * @param hienr 终端客户
     * @param matnr 物料号
     * @param prsdt 定价日期
     * @param kwmeng 订单数量（可不传）
     * @param konms 订单单位（可不传）
     * @Date 2022/9/23 11:19
     */
    public Map getStandardPrice(String vkorg,String kunnr,String hienr,String matnr,
                                   String prsdt,Float kwmeng,String konms){

        String JSONSTR1 = "<![CDATA[[{\"VKORG\":\""+vkorg+"\",\"KUNNR\":\""+kunnr+"\","
                +" \"HIENR\":\""+hienr+"\",\"MATNR\":\""+matnr+"\",\"AUART\":\"ZOR5\","
                +" \"PRSDT\":\""+prsdt+"\",\"KWMENG\":\""+kwmeng+"\",\"KONMS\":\""+konms+"\"}]]]>";


        String result = GetSapConfigTableInfo.sendRequest("SD143",JSONSTR1);

        if(!"".equals(result)&& !"error".equals(result) ){
            JSONArray objects = JSON.parseArray(result);
            if(objects.size()>0){
                JSONObject job = objects.getJSONObject(0);
                if(job.getString("TYPE").equals("S")){
                    Map mp = new HashMap();
                    mp.put("price",job.getString("KBETR"));//价格
                    mp.put("danw",job.getString("KMEIN"));//条件单位
                    return mp;
                }
            }
        }
        return null;
    }

    /**
     * @Author cy
     * @Description 全选
     * * @param null
     * @Return
     * @Date 2022/8/31 10:13
     */
    public void selectAll(ActionEvent actionEvent) {
        checkedIdList.clear();
        ObservableList<GeneralOrderDetailVO> items = tableView.getItems();
        //System.err.println("items=="+items.size()+"---"+items);
        if (mselectAll.isSelected()) {
            for (GeneralOrderDetailVO gd : items) {
                gd.getCb().setSelected(true);
                checkedIdList.add(String.valueOf(gd.getId()));
            }
        } else {
            for (GeneralOrderDetailVO rp : items) {
                rp.getCb().setSelected(false);
            }
            checkedIdList.clear();
        }
    }


    /**
     * @Author cy
     * @Description 设置主表的总金额和未税金额
     * @Date 2023/2/1 11:29
     */
    public void setTotalAmountAndAmountNotTaxed(){
        Float totalAmount = 0f;//总金额
        Float taxamount = 0f;//税额
        Float amountNotTaxed = 0f;//未税金额

        ObservableList<GeneralOrderDetailVO> detailList = tableView.getItems();
        for (GeneralOrderDetailVO go:detailList) {

            Float shougj = go.getManualPrice()==null?0f:go.getManualPrice();//手工价
            Float orderBuomQty = go.getOrderBuomQty()==null?0f:go.getOrderBuomQty();//订单基本单位数量
            Float discountRate = go.getDiscountRate()==null?0f:go.getDiscountRate();//折扣率
            Float unusedQuota = go.getUnusedQuota()==null?0f:go.getUnusedQuota();//未使用额度
            String taxRate = go.getTaxRate()==null?"100%":go.getTaxRate();//税率
            taxRate = taxRate.replaceAll("%","");
            Float shuil = Convert.toFloat(taxRate,100f);


            /* 返利金额 = 单价*数量*折扣率 */
            Float rebateAmount = shougj*orderBuomQty*(discountRate/100);

            /* 折前金额 = 单价*数量 */
            Float amountBeforeDiscount = orderBuomQty*shougj;//折前金额
            amountBeforeDiscount = Convert.toFloat2(amountBeforeDiscount,"0.00");

            /* 折后金额 = 折前金额-折扣金额 */
            Float discountedAmount = amountBeforeDiscount-rebateAmount;//折后金额
            discountedAmount = Convert.toFloat2(discountedAmount,"0.00");

            taxamount += discountedAmount*shuil/100;
            amountNotTaxed += discountedAmount;
        }

        totalAmount = amountNotTaxed - taxamount;
        totalAmountAssembly.setText(String.valueOf(totalAmount));
        amountNotTaxedAssembly.setText(String.valueOf(amountNotTaxed));
    }


    /**
     * @Author cy
     * @Description 保存订单
     * @Return
     * @Date 2022/10/10 15:41
     */
    public void saveData(ActionEvent actionEvent) {


        ObservableList<GeneralOrderDetailVO> detailList = tableView.getItems();

        String orderType = "ZOR5";//订单类型

        Map orderReferenceMap = (Map) orderReferenceAssembly.getValue();
        String orderReference = orderReferenceMap == null?"": (String) orderReferenceMap.get("ddsSalesOrderNo");//订单参考

        Map salesOrgpMap = (Map) salesOrgAssembly.getValue();
        String salesOrg = salesOrgpMap == null?"": (String) salesOrgpMap.get("vkorg");//销售组织

        Map productGroupMap = (Map) productGroupAssembly.getValue();
        String productGroup = productGroupMap == null?"": (String) productGroupMap.get("spart");//产品组

        Map distributionChannelMap = (Map) distributionChannelAssembly.getValue();
        String distributionChannel = distributionChannelMap == null?"": (String) distributionChannelMap.get("vtweg");//分销渠道

        Map customerMap = (Map) customerNameAssembly.getValue();
        String customerName = customerMap == null?"": (String) customerMap.get("name1");//客户名称
        String customerCode = customerCodeAssembly.getText();//客户代码

        Map songdfMap = (Map) songdfmcAssembly.getValue();
        String songdfmc = songdfMap == null?"": (String) songdfMap.get("name1");//送达方名称
        String songdfdm = songdfdmAssembly.getText();//送达方代码

        Map saleDeptMap = (Map) saleDeptAssembly.getValue();//销售部门对象
        String saleDept = saleDeptMap == null?"": (String) saleDeptMap.get("vkbur");

        Map termOfPaymentMap = (Map) termOfPaymentAssembly.getValue();//付款条件对象
        String termOfPayment = termOfPaymentMap == null?"": (String) termOfPaymentMap.get("zterm");

        Map endCustomerMap = (Map) endCustomerAssembly.getValue();//终端客户对象
        String endCustomer = endCustomerMap == null?"": (String) endCustomerMap.get("kunnr");

        Map salesmanMap = (Map) salesmanAssembly.getValue();//业务员对象
        String salesman = salesmanMap == null?"": (String) salesmanMap.get("kunnr");

        String preparedBy = userName;//制单人
        String documentDate = String.valueOf(documentDateAssembly.getValue());//凭证日期


        Float totalAmount = Convert.toFloat(totalAmountAssembly.getText(),0f);;//总金额
        Float amountNotTaxed = Convert.toFloat(amountNotTaxedAssembly.getText(),0f);//未税金额

        Map shippingConditionsMap = (Map) shippingConditionsAssembly.getValue();//装运条件对象
        String shippingConditions = shippingConditionsMap == null?"": (String) shippingConditionsMap.get("vsbed");

        String combinationDelivery = "X";//组合交货
        String pod = "X";// POD

        String orderSummary = orderSummaryAssembly.getText();//唛头/订单摘要
        String shippingRemarks = shippingRemarksAssembly.getText();//发货信息备注



        String nowDate = DateUtils.dateTime();
        Long temp = 0L;
        //DDS+年+月+日+流水
        List<GeneralOrderVO> rpList = orderService.checkDdsSalesOrderNoExists(nowDate);
        if(rpList.size()>0){//表示当前日期存在编码了
            String bh = rpList.get(0).getDdsSalesOrderNo();
            if(bh.length()>4){
                temp = Long.valueOf(bh.substring(3,bh.length()))+1;
            }else{
                temp = Long.valueOf(nowDate+"0001");
            }

        }else{
            temp = Long.valueOf(nowDate+"0001");
        }


        GeneralOrderVO go = new GeneralOrderVO();

        String ddsSalesOrderNo = "DDS"+temp;;//DDS销售订单号
        go.setOrderType(orderType);
        go.setOrderReference(orderReference);
        go.setDdsSalesOrderNo(ddsSalesOrderNo);
        go.setSalesOrg(salesOrg);
        go.setProductGroup(productGroup);
        go.setDistributionChannel(distributionChannel);
        go.setCustomerName(customerName);
        go.setCustomerCode(customerCode);
        go.setSongdfmc(songdfmc);
        go.setSongdfdm(songdfdm);
        go.setSaleDept(saleDept);
        go.setTermOfPayment(termOfPayment);
        go.setEndCustomer(endCustomer);
        go.setSalesman(salesman);
        go.setPreparedBy(preparedBy);
        go.setDocumentDate(documentDate);
        go.setTotalAmount(totalAmount);
        go.setAmountNotTaxed(amountNotTaxed);
        go.setShippingConditions(shippingConditions);
        go.setCombinationDelivery(combinationDelivery);
        go.setPod(pod);
        go.setOrderSummary(orderSummary);
        go.setShippingRemarks(shippingRemarks);
        go.setCreateBy(preparedBy);

        log.info(userName+"在"+DateUtils.getTime()+"执行了<保存订单>操作，操作数据："
                +"主数据【"+go+"】\n"+"明细数据【"+detailList+"】");

        //System.err.println("detailList=="+detailList);
        String rs = orderService.insertOrder(go,detailList);

        if(rs.indexOf("error")!=-1){
            CommonUtil._alertErrorMessage("出错拉！！！","订单保存失败："+rs);
        }else{
            dataIdField.setText(rs);
            ddsSalesOrderNoAssembly.setText(go.getDdsSalesOrderNo());
            ToastUtil.customizeToast("订单保存成功",2000,500.00,300.00);
            saveBt.setDisable(true);
            //orderReferenceAssembly.setDisable(true);


        }
    }


    /**
     * @Author cy
     * @Description 保存并新建，执行此操作时，页面数据会被清空
     * @Return
     * @Date 2022/10/11 14:02
     */
    public void saveAndNewData(ActionEvent actionEvent) {

        String dataId = dataIdField.getText();
        if(!"".equals(dataId)){
            CommonUtil._alertInformation("无法保存并新建待修改的数据！");
            return;
        }

        ObservableList<GeneralOrderDetailVO> detailList = tableView.getItems();

        String orderType = "ZOR5";//订单类型
        Map orderReferenceMap = (Map) orderReferenceAssembly.getValue();
        String orderReference = orderReferenceMap == null?"": (String) orderReferenceMap.get("ddsSalesOrderNo");//订单参考

        Map salesOrgpMap = (Map) salesOrgAssembly.getValue();
        String salesOrg = salesOrgpMap == null?"": (String) salesOrgpMap.get("vkorg");//销售组织
        Map productGroupMap = (Map) productGroupAssembly.getValue();
        String productGroup = productGroupMap == null?"": (String) productGroupMap.get("spart");//产品组

        Map distributionChannelMap = (Map) distributionChannelAssembly.getValue();
        String distributionChannel = distributionChannelMap == null?"": (String) distributionChannelMap.get("vtweg");//分销渠道

        Map customerMap = (Map) customerNameAssembly.getValue();
        String customerName = customerMap == null?"": (String) customerMap.get("name1");//客户名称
        String customerCode = customerCodeAssembly.getText();//客户代码

        Map songdfMap = (Map) songdfmcAssembly.getValue();
        String songdfmc = songdfMap == null?"": (String) songdfMap.get("name1");//送达方名称
        String songdfdm = songdfdmAssembly.getText();//送达方代码

        Map saleDeptMap = (Map) saleDeptAssembly.getValue();//销售部门对象
        String saleDept = saleDeptMap == null?"": (String) saleDeptMap.get("vkbur");

        Map termOfPaymentMap = (Map) termOfPaymentAssembly.getValue();//付款条件对象
        String termOfPayment = termOfPaymentMap == null?"": (String) termOfPaymentMap.get("zterm");

        Map endCustomerMap = (Map) endCustomerAssembly.getValue();//终端客户对象
        String endCustomer = endCustomerMap == null?"": (String) endCustomerMap.get("kunnr");

        Map salesmanMap = (Map) salesmanAssembly.getValue();//业务员对象
        String salesman = salesmanMap == null?"": (String) salesmanMap.get("kunnr");

        String preparedBy = userName;//制单人
        String documentDate = String.valueOf(documentDateAssembly.getValue());//凭证日期


        Float totalAmount = Convert.toFloat(totalAmountAssembly.getText(),0f);;//总金额
        Float amountNotTaxed = Convert.toFloat(amountNotTaxedAssembly.getText());//未税金额

        Map shippingConditionsMap = (Map) shippingConditionsAssembly.getValue();//装运条件对象
        String shippingConditions = shippingConditionsMap == null?"": (String) shippingConditionsMap.get("vsbed");

        String combinationDelivery = "X";//组合交货
        String pod = "X";//POD

        String orderSummary = orderSummaryAssembly.getText();//唛头/订单摘要
        String shippingRemarks = shippingRemarksAssembly.getText();//发货信息备注



        String nowDate = DateUtils.dateTime();
        Long temp = 0L;
        //DDS+年+月+日+流水
        List<GeneralOrderVO> rpList = orderService.checkDdsSalesOrderNoExists(nowDate);
        if(rpList.size()>0){//表示当前日期存在编码了
            String bh = rpList.get(0).getDdsSalesOrderNo();
            if(bh.length()>4){
                temp = Long.valueOf(bh.substring(3,bh.length()))+1;
            }else{
                temp = Long.valueOf(nowDate+"0001");
            }

        }else{
            temp = Long.valueOf(nowDate+"0001");
        }


        GeneralOrderVO go = new GeneralOrderVO();

        String ddsSalesOrderNo = "DDS"+temp;;//DDS销售订单号
        go.setOrderType(orderType);
        go.setOrderReference(orderReference);
        go.setDdsSalesOrderNo(ddsSalesOrderNo);
        go.setSalesOrg(salesOrg);
        go.setProductGroup(productGroup);
        go.setDistributionChannel(distributionChannel);
        go.setCustomerName(customerName);
        go.setCustomerCode(customerCode);
        go.setSongdfmc(songdfmc);
        go.setSongdfdm(songdfdm);
        go.setSaleDept(saleDept);
        go.setTermOfPayment(termOfPayment);
        go.setEndCustomer(endCustomer);
        go.setSalesman(salesman);
        go.setPreparedBy(preparedBy);
        go.setDocumentDate(documentDate);
        go.setTotalAmount(totalAmount);
        go.setAmountNotTaxed(amountNotTaxed);
        go.setShippingConditions(shippingConditions);
        go.setCombinationDelivery(combinationDelivery);
        go.setPod(pod);
        go.setOrderSummary(orderSummary);
        go.setShippingRemarks(shippingRemarks);
        go.setCreateBy(preparedBy);


        log.info(userName+"在"+DateUtils.getTime()+"执行了<保存并新建>操作，操作数据："
                    +"主数据【"+go+"】\n"+"明细数据【"+detailList+"】");

        //System.err.println("detailList=="+detailList);
        String rs = orderService.insertOrder(go,detailList);

        if(rs.indexOf("error")!=-1){
            CommonUtil._alertErrorMessage("出错拉！！！","保存并新建失败："+rs);
        }else{
            ToastUtil.customizeToast("订单保存成功",2000,500.00,300.00);
            orderReferenceAssembly.getEditor().setText("");
            ddsSalesOrderNoAssembly.setText("");
            sapSalesOrderNoAssembly.setText("");
            salesOrgAssembly.getEditor().setText("");
            productGroupAssembly.getEditor().setText("");
            distributionChannelAssembly.getEditor().setText("");
            customerNameAssembly.getEditor().setText("");
            customerCodeAssembly.setText("");
            songdfmcAssembly.getEditor().setText("");
            songdfdmAssembly.setText("");
            saleDeptAssembly.getEditor().setText("");
            termOfPaymentAssembly.getEditor().setText("");
            endCustomerAssembly.getEditor().setText("");
            salesmanAssembly.getEditor().setText("");
            documentDateAssembly.setValue(null);
            totalAmountAssembly.setText("");
            amountNotTaxedAssembly.setText("");
            shippingConditionsAssembly.getEditor().setText("");
            orderSummaryAssembly.setText("");
            shippingRemarksAssembly.setText("");
            dataIdField.setText("");


            saveBt.setDisable(false);
            dataList.removeAll(tableView.getItems());
            tableView.refresh();

        }
    }

    /**
     * @Author cy
     * @Description 修改订单
     * @Return
     * @Date 2022/10/11 14:03
     */
    public void editData(ActionEvent actionEvent) {

        String dataId = dataIdField.getText();
        if("".equals(dataId)){
            CommonUtil._alertInformation("无可修改数据，请先保存！");
            return;
        }

        ObservableList<GeneralOrderDetailVO> detailList = tableView.getItems();

        String orderType = "ZOR5";//订单类型
        Map orderReferenceMap = (Map) orderReferenceAssembly.getValue();
        String orderReference = orderReferenceMap == null?"": (String) orderReferenceMap.get("ddsSalesOrderNo");//订单参考

        Map salesOrgpMap = (Map) salesOrgAssembly.getValue();
        String salesOrg = salesOrgpMap == null?"": (String) salesOrgpMap.get("vkorg");//销售组织

        Map productGroupMap = (Map) productGroupAssembly.getValue();
        String productGroup = productGroupMap == null?"": (String) productGroupMap.get("spart");//产品组

        Map distributionChannelMap = (Map) distributionChannelAssembly.getValue();
        String distributionChannel = distributionChannelMap == null?"": (String) distributionChannelMap.get("vtweg");//分销渠道

        Map customerMap = (Map) customerNameAssembly.getValue();
        String customerName = customerMap == null?"": (String) customerMap.get("name1");//客户名称
        String customerCode = customerCodeAssembly.getText();//客户代码

        Map songdfMap = (Map) songdfmcAssembly.getValue();
        String songdfmc = songdfMap == null?"": (String) songdfMap.get("name1");//送达方名称
        String songdfdm = songdfdmAssembly.getText();//送达方代码

        Map saleDeptMap = (Map) saleDeptAssembly.getValue();//销售部门对象
        String saleDept = saleDeptMap == null?"": (String) saleDeptMap.get("vkbur");

        Map termOfPaymentMap = (Map) termOfPaymentAssembly.getValue();//付款条件对象
        String termOfPayment = termOfPaymentMap == null?"": (String) termOfPaymentMap.get("zterm");

        Map endCustomerMap = (Map) endCustomerAssembly.getValue();//终端客户对象
        String endCustomer = endCustomerMap == null?"": (String) endCustomerMap.get("kunnr");

        Map salesmanMap = (Map) salesmanAssembly.getValue();//业务员对象
        String salesman = salesmanMap == null?"": (String) salesmanMap.get("kunnr");

        String preparedBy = userName;//制单人
        String documentDate = String.valueOf(documentDateAssembly.getValue());//凭证日期


        Float totalAmount = Convert.toFloat(totalAmountAssembly.getText(),0f);;//总金额
        Float amountNotTaxed = Convert.toFloat(amountNotTaxedAssembly.getText(),0f);//未税金额

        Map shippingConditionsMap = (Map) shippingConditionsAssembly.getValue();//装运条件对象
        String shippingConditions = shippingConditionsMap == null?"": (String) shippingConditionsMap.get("vsbed");

        String combinationDelivery = "X";//组合交货
        String pod = "X";//POD

        String orderSummary = orderSummaryAssembly.getText();//唛头/订单摘要
        String shippingRemarks = shippingRemarksAssembly.getText();//发货信息备注



        GeneralOrderVO go = new GeneralOrderVO();

        go.setId(Integer.parseInt(dataId));
        go.setOrderType(orderType);
        go.setOrderReference(orderReference);
        go.setDdsSalesOrderNo(ddsSalesOrderNoAssembly.getText());
        go.setSalesOrg(salesOrg);
        go.setProductGroup(productGroup);
        go.setDistributionChannel(distributionChannel);
        go.setCustomerName(customerName);
        go.setCustomerCode(customerCode);
        go.setSongdfmc(songdfmc);
        go.setSongdfdm(songdfdm);
        go.setSaleDept(saleDept);
        go.setTermOfPayment(termOfPayment);
        go.setEndCustomer(endCustomer);
        go.setSalesman(salesman);
        go.setPreparedBy(preparedBy);
        go.setDocumentDate(documentDate);
        go.setTotalAmount(totalAmount);
        go.setAmountNotTaxed(amountNotTaxed);
        go.setShippingConditions(shippingConditions);
        go.setCombinationDelivery(combinationDelivery);
        go.setPod(pod);
        go.setOrderSummary(orderSummary);
        go.setShippingRemarks(shippingRemarks);
        go.setCreateBy(preparedBy);
        go.setUpdateBy(preparedBy);

        log.info(userName+"在"+DateUtils.getTime()+"执行了<修改订单>操作，操作数据："
                +"主数据【"+go+"】\n"+"明细数据【"+detailList+"】");

        String rs = orderService.updateOrder(go,detailList);
        if(rs.indexOf("error")!=-1){
            CommonUtil._alertErrorMessage("出错拉！！！","订单修改失败："+rs);
        }else{
            ToastUtil.customizeToast("订单修改成功",2000,500.00,300.00);
        }
    }


    /**
     * @Author cy
     * @Description 获取返利
     * @Date 2023/2/9 11:05
     */
    public void seachRebate(ActionEvent actionEvent) {


        List<ShowRebatePolicyInfoVO> reList = new ArrayList<>();

        Map salesOrgpMap = (Map) salesOrgAssembly.getValue();
        String salesOrg = salesOrgpMap == null?"": (String) salesOrgpMap.get("vkorg");//销售组织

        String customerCode = customerCodeAssembly.getText();//客户代码

        Map endCustomerMap = (Map) endCustomerAssembly.getValue();//终端客户对象
        String endCustomer = endCustomerMap == null?"": (String) endCustomerMap.get("kunnr");

        ObservableList<GeneralOrderDetailVO> detailList = tableView.getItems();

        if(detailList.size()>0){
            for(int i=0;i<detailList.size();i++){
                ShowRebatePolicyInfoVO rebate = new ShowRebatePolicyInfoVO();

                rebate.setVkorg(salesOrg);
                rebate.setKunnr(customerCode);
                rebate.setKunnr_ec(endCustomer);
                rebate.setMatkl(detailList.get(i).getMaterialGroupCode());

                reList.add(rebate);
            }
        }else{

            ShowRebatePolicyInfoVO rebate = new ShowRebatePolicyInfoVO();

            rebate.setVkorg(salesOrg);
            rebate.setKunnr(customerCode);
            rebate.setKunnr_ec(endCustomer);

            reList.add(rebate);
        }

        if(reList.size()<=0){
            CommonUtil._alertInformation("无任何满足条件的返利策略，请检查相关条件！！！");
            return;
        }

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/ShowRebatePolicyInfo.fxml"));
            Parent pane = (Parent)fxmlLoader.load();
            Scene scene = new Scene(pane);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.getScene().getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
            stage.setTitle("数据详情");
            stage.resizableProperty().setValue(Boolean.FALSE);//禁用最大化
            //设置窗口不可拉伸
            stage.setResizable(false);
            stage.getIcons().add(CommonUtil.getLogo());
            ShowRebatePolicyInfoController controller = fxmlLoader.getController();
            controller.setMainController(this);//传输父对象至子对象中
            controller.getDataListModel(reList);
            stage.initModality(Modality.APPLICATION_MODAL);//遮盖窗口
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Author cy
     * @Description 设置返利对象
     * @Return
     * @Date 2023/2/13 14:25
     */
    public void setRebatePolicyObject(ShowRebatePolicyInfoVO srp){
        srpi = srp;
        rebateNoAssembly.setText(srpi.getZzbname());

    }

}
