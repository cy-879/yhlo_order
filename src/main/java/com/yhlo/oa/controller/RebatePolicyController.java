package com.yhlo.oa.controller;

import com.yhlo.oa.entity.*;
import com.yhlo.oa.service.PublicDataService;
import com.yhlo.oa.service.RebatePolicyService;
import com.yhlo.oa.service.iml.PublicDataServiceImpl;
import com.yhlo.oa.service.iml.RebatePolicyServiceImpl;
import com.yhlo.oa.util.*;
import de.felixroske.jfxsupport.FXMLController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author cy
 * @ClassName: RebatePolicyController
 * @Description:
 * @date 2022/6/248:52
 */
@Slf4j
@FXMLController
public class RebatePolicyController implements Initializable {

    private final static String[] ROWS = {"id","matkl_name","matkl","rebateLimit","z005","remark"};



    @FXML
    public TextField rebateFormNameAssembly;
    @FXML
    public TextField contractNoAssembly;
    @FXML
    public ComboBox<String> rebateStrategyAssembly;
    @FXML
    public ComboBox name1Assembly;
    @FXML
    public TextField kunnrAssembly;
    @FXML
    public ComboBox vkorg_nameAssembly;
    @FXML
    public TextField vkorgAssembly;
    @FXML
    public ComboBox name_ec_nameAssembly;
    @FXML
    public TextField name_ecAssembly;
    @FXML
    public TextField rebateLimitAssembly;
    @FXML
    public TextField z005Assembly;
    @FXML
    public DatePicker takeEffectTimeAssembly;
    @FXML
    public DatePicker failureTimeAssembly;
    @FXML
    public TextArea remarkAssembly;
    @FXML
    public Button saveBt;


    @FXML
    public TableView <RebatePolicyDetailVO> tableView;
    @FXML
    public TableColumn mColumnXuh;
    @FXML
    public TableColumn<RebatePolicyDetailVO, CheckBox> mColumnSelect;


    @FXML
    private CheckBox mselectAll;
    @FXML
    public TableColumn <RebatePolicyDetailVO, String> mColumnMatkl_name;
    @FXML
    public TableColumn <RebatePolicyDetailVO, String> mColumnMatkl;
    @FXML
    public TableColumn <RebatePolicyDetailVO, String> mColumnRemark;
    @FXML
    public Button addBt;
    @FXML
    public Button delBt;


    public List<String> checkedIdList = new ArrayList<>();

    private final ObservableList<RebatePolicyDetailVO> dataList =
            FXCollections.observableArrayList();

    public static Map mp ;



        private static Pattern decimalPattern = Pattern.compile("\\d{0,12}+(\\.\\d{0,2})?");
    // Pattern decimalPattern = Pattern.compile("-?\\d*(\\.\\d{0,2})?");//正负2位小数

    private static String pattern = "yyyy-MM-dd ";//日期格式类型样板串


    @Autowired
    private RebatePolicyService reService;
    @Autowired
    private PublicDataService pdService;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rebateStrategyAssembly.getItems().addAll("返利政策1","返利政策2","返利政策3","返利政策4","返利政策5");
        reService = SpringBeanUtil.getBean(RebatePolicyServiceImpl.class);
        pdService = SpringBeanUtil.getBean(PublicDataServiceImpl.class);




        initSaleOrgComboBox();

        mColumnXuh.setCellValueFactory(new PropertyValueFactory<>("xuh"));
        mColumnMatkl_name.setCellValueFactory(new PropertyValueFactory<>("matkl_name"));
        mColumnMatkl.setCellValueFactory(new PropertyValueFactory<>("matkl"));
        mColumnRemark.setCellValueFactory(new PropertyValueFactory<>("remark"));
        tableView.setItems(dataList);




        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);//选多行
        // tableView.getSelectionModel().setCellSelectionEnabled(true);//点击选中单个单元格
        tableView.setEditable(true);//表格设置为可编辑


        // 设置序号展示
        mColumnXuh.setCellFactory(new Callback<TableColumn<RebatePolicyDetailVO, String>, TableCell<RebatePolicyDetailVO, String>>() {
            @Override
            public TableCell<RebatePolicyDetailVO, String> call(TableColumn<RebatePolicyDetailVO, String> param) {
                TableCell<RebatePolicyDetailVO, String> cell = new TableCell<RebatePolicyDetailVO, String>(){
                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        this.setText(null);
                        this.setGraphic(null);
                        if (!empty) {
                            int rowIndex = this.getIndex() + 1;
                            this.setText(String.valueOf(rowIndex));
                        }
                    }
                };
                return cell;
            }
        });


        vkorg_nameAssembly.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(null != newValue) {
                    Map it = (Map) newValue;
                    String org = (String) it.get("vkorg");
                    initCustomerComboBox("",org);
                    initZdCustomerComboBox("",org);
                }else{
                    initCustomerComboBox("","");
                    initZdCustomerComboBox("","");
                }
            }
        });


        Callback<TableColumn<RebatePolicyDetailVO, String>,
                TableCell<RebatePolicyDetailVO, String>> cellFactory
                = (TableColumn<RebatePolicyDetailVO, String> p) -> new EditingCell();


        Callback<TableColumn<RebatePolicyDetailVO, String>, TableCell<RebatePolicyDetailVO, String>> cellFactory1
                = (TableColumn<RebatePolicyDetailVO, String> p) -> new EditingCellComboBox();

        mColumnRemark.setCellFactory(cellFactory);
        mColumnMatkl_name.setCellFactory(cellFactory1);
       // mColumnMatkl.setCellFactory(cellFactory);


        mColumnRemark.setOnEditCommit(
            (TableColumn.CellEditEvent<RebatePolicyDetailVO, String> t) -> {
                ((RebatePolicyDetailVO) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setRemark(t.getNewValue());
            });

        mColumnMatkl_name.setOnEditCommit(
            (TableColumn.CellEditEvent<RebatePolicyDetailVO, String> t) -> {

                ((RebatePolicyDetailVO) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                ).setMatkl_name(t.getNewValue());

                mColumnMatkl.getTableView().getItems().get(
                        t.getTablePosition().getRow()).setMatkl((String) mp.get("matkl"));
                tableView.refresh();
            });



        // 单击选中行
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (Objects.nonNull(newValue)) {
                Checkbox newCheckbox = newValue.getCb();
                ObservableList<RebatePolicyDetailVO> items = tableView.getItems();
                //System.err.println("newValue=="+newValue);
                if (newCheckbox.isSelected()) {
                    newCheckbox.setSelected(false);
                } else {
                    newCheckbox.setSelected(true);
                }
            }
        });



        // 初始化所有checkbox复选框
        mColumnSelect.setCellValueFactory(param -> {
            final ObservableValue<CheckBox> observableValue = param.getValue().getCb().getCheckBox();
            return observableValue;
        });



        rebateStrategyAssembly.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if("返利政策1".equals(newValue)){
                    name1Assembly.setDisable(false);
                    vkorg_nameAssembly.setDisable(false);
                    name_ec_nameAssembly.setDisable(false);
                    addBt.setDisable(false);
                    delBt.setDisable(false);
                }else if("返利政策2".equals(newValue)){
                    name1Assembly.setDisable(false);
                    vkorg_nameAssembly.setDisable(false);
                    name_ec_nameAssembly.setDisable(false);
                    addBt.setDisable(true);
                    delBt.setDisable(true);
                    dataList.removeAll(tableView.getItems());

                }else if("返利政策3".equals(newValue)){
                    name1Assembly.setDisable(false);
                    vkorg_nameAssembly.setDisable(false);
                    name_ec_nameAssembly.setDisable(true);
                    name_ec_nameAssembly.getSelectionModel().select(-1);
                    name_ec_nameAssembly.setStyle("");
                    name_ec_nameAssembly.setPromptText("请输入客户名称搜索");
                    name_ecAssembly.setText("");
                    addBt.setDisable(false);
                    delBt.setDisable(false);

                }else if("返利政策4".equals(newValue)){
                    name1Assembly.setDisable(false);
                    vkorg_nameAssembly.setDisable(false);
                    name_ec_nameAssembly.setDisable(true);
                    name_ec_nameAssembly.getSelectionModel().select(-1);
                    name_ec_nameAssembly.setStyle("");
                    name_ec_nameAssembly.setPromptText("请输入客户名称搜索");
                    name_ecAssembly.setText("");
                    addBt.setDisable(true);
                    delBt.setDisable(true);
                    dataList.removeAll(tableView.getItems());
                }else if("返利政策5".equals(newValue)){
                    name1Assembly.setDisable(false);
                    vkorg_nameAssembly.setDisable(true);
                    vkorg_nameAssembly.getSelectionModel().select(-1);
                    vkorg_nameAssembly.setStyle("");
                    vkorg_nameAssembly.setPromptText("请输入销售组织名称搜索");
                    vkorgAssembly.setText("");
                    name_ec_nameAssembly.setDisable(true);
                    name_ec_nameAssembly.getSelectionModel().select(-1);
                    name_ec_nameAssembly.setStyle("");
                    name_ec_nameAssembly.setPromptText("请输入客户名称搜索");
                    name_ecAssembly.setText("");
                    addBt.setDisable(true);
                    delBt.setDisable(true);
                    dataList.removeAll(tableView.getItems());
                }

            }
        });


        name1Assembly.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(null != newValue) {
                    Map it = (Map) newValue;
                    kunnrAssembly.setText((String) it.get("kunnr"));
                }else{
                    kunnrAssembly.setText("");
                }
            }
        });

        vkorg_nameAssembly.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(null != newValue){
                    Map  it = (Map) newValue;
                    vkorgAssembly.setText((String) it.get("vkorg"));
                }else{
                    vkorgAssembly.setText("");
                }

            }
        });



        name_ec_nameAssembly.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(null != newValue) {
                    Map it = (Map) newValue;
                    name_ecAssembly.setText((String) it.get("kunnr"));
                }else{
                    name_ecAssembly.setText("");
                }
            }
        });

        JavaFxUtil.getNumberText(decimalPattern, rebateLimitAssembly);
        JavaFxUtil.getNumberText(decimalPattern, z005Assembly);


        JavaFxUtil.setFormatDateField(pattern, takeEffectTimeAssembly);
        JavaFxUtil.setFormatDateField(pattern, failureTimeAssembly);


        takeEffectTimeAssembly.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {

                LocalDate failureTim = failureTimeAssembly.valueProperty().getValue();//失效时间
                if(null != failureTim && null != newValue){
                    if(failureTim.isBefore(newValue)){//失效时间小于生效时间
                        CommonUtil._alertInformation("生效时间不能大于失效时间！");
                        takeEffectTimeAssembly.setValue(null);
                    }
                }
            }
        });

        failureTimeAssembly.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {

                LocalDate takeEffectTime = takeEffectTimeAssembly.valueProperty().getValue();//生效时间
                if(null != takeEffectTime && null != newValue){
                    if(newValue.isBefore(takeEffectTime)){//失效时间小于生效时间
                        CommonUtil._alertInformation("生效时间不能大于失效时间！");
                        failureTimeAssembly.setValue(null);
                    }
                }
            }
        });


        JavaFxUtil.setTextFieldRequired(rebateFormNameAssembly);
        JavaFxUtil.setTextFieldRequired(contractNoAssembly);
        JavaFxUtil.setDatePickerFieldRequired(takeEffectTimeAssembly);
        JavaFxUtil.setDatePickerFieldRequired(failureTimeAssembly);

        JavaFxUtil.setComboBoxFieldRequired(rebateStrategyAssembly);
        JavaFxUtil.setComboBoxFieldRequired(name1Assembly);
        JavaFxUtil.setComboBoxFieldRequired(vkorg_nameAssembly);
        JavaFxUtil.setComboBoxFieldRequired(name_ec_nameAssembly);


       // Tooltip.install(saveBt, new Tooltip("鼠标悬浮显示的文字"));

    }


    /***
     * 初始化客户下拉控件
     */
    public void initCustomerComboBox(String kunnr,String vkorg){

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

        name1Assembly.getSelectionModel().select(-1);
        name1Assembly.getItems().clear();
        name1Assembly.getEditor().setText("");
        kunnrAssembly.setText("");

        name1Assembly.setPromptText("请输入客户名称搜索");
        name1Assembly.setPlaceholder(new Label("空值"));
        AutoCompleteComboBoxListener.getComboBox(name1Assembly, FXCollections.observableList(mpList),"name1");
    }

    /***
     * 初始化客户下拉控件
     */
    public void initZdCustomerComboBox(String kunnr,String vkorg){

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


        name_ec_nameAssembly.getSelectionModel().select(-1);
        name_ec_nameAssembly.getItems().clear();
        name_ec_nameAssembly.getEditor().setText("");
        name_ecAssembly.setText("");

        name_ec_nameAssembly.setPromptText("请输入客户名称搜索");
        name_ec_nameAssembly.setPlaceholder(new Label("空值"));
        AutoCompleteComboBoxListener.getComboBox(name_ec_nameAssembly, FXCollections.observableList(mpList),"name1");
    }


    /***
     * 初始化销售组织下拉控件
     */
    public void initSaleOrgComboBox(){

        List<TvkotVO> itemList = pdService.querySaleOrgList("");

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

        vkorg_nameAssembly.setPromptText("请输入销售组织名称搜索");
        vkorg_nameAssembly.setPlaceholder(new Label("空值"));
        AutoCompleteComboBoxListener.getComboBox(vkorg_nameAssembly, FXCollections.observableList(mpList),"vtxtk");
    }


    /***
     * 初始化物料组下拉控件
     */
    public void initMaterialGroupComboBox(ComboBox cob,String text){

        List<T023tVO> itemList = pdService.queryMaterialGroupList();

        List<Map> mpList = new ArrayList<Map>();
        if(null != itemList && itemList.size()>0){
            for (T023tVO item :itemList ) {
                Map m = new HashMap<>();
                m.put("spras",item.getSpras());
                m.put("matkl",item.getMatkl());
                m.put("wgbez60",item.getWgbez60());
                mpList.add(m);
            }
        }

        cob.setPromptText("请输入物料组搜索");
        cob.setPlaceholder(new Label("空值"));
        AutoCompleteComboBoxListener.getComboBox(cob, FXCollections.observableList(mpList),text);

    }


    /**
     * @Author cy
     * @Description 新增返利政策
     * * @param null
     * @Return
     * @Date 2022/7/20 17:30
     */
    public void saveData(ActionEvent actionEvent) {
        String rebateFormName = rebateFormNameAssembly.getText();
        String contractNo = contractNoAssembly.getText();
        String rebateStrategy = rebateStrategyAssembly.getValue()==null?"":rebateStrategyAssembly.getValue();
        Map keh = (Map) name1Assembly.getValue();
        String name1 = keh == null?"": (String) keh.get("name1");
        String kunnr = kunnrAssembly.getText();
        Map xiaoszz = (Map) vkorg_nameAssembly.getValue();
        String vkorg_name = xiaoszz == null?"": (String) xiaoszz.get("vkorg_name");
        String vkorg = vkorgAssembly.getText();
        Map zdkh = (Map) name_ec_nameAssembly.getValue();
        String name_ec_name = zdkh == null?"": (String) zdkh.get("name_ec_name");
        String name_ec = name_ecAssembly.getText();
        Float rebateLimit = Convert.toFloat(rebateLimitAssembly.getText(),0f);
        Float z005 = Convert.toFloat(z005Assembly.getText(),0f);
        String takeEffectTime = String.valueOf(takeEffectTimeAssembly.getValue());
        String failureTime = String.valueOf(failureTimeAssembly.getValue());
        String remark = remarkAssembly.getText();



        if("".equals(rebateFormName)){
            rebateFormNameAssembly.setPromptText("该字段为必填项");
            rebateFormNameAssembly.setStyle("-fx-text-box-border: red ;");
            return;
        }

        if("".equals(contractNo)){
            contractNoAssembly.setPromptText("该字段为必填项");
            contractNoAssembly.setStyle("-fx-text-box-border: red ;");
            return;
        }


        if("null".equals(takeEffectTime) || "".equals(takeEffectTime)){
            takeEffectTimeAssembly.setPromptText("该字段为必填项");
            takeEffectTimeAssembly.setStyle("-fx-text-box-border: red ;-fx-background-color: red;");
            return;
        }

        if("null".equals(failureTime) || "".equals(failureTime)){
            failureTimeAssembly.setPromptText("该字段为必填项");
            failureTimeAssembly.setStyle("-fx-text-box-border: red ;-fx-background-color: red;");
            return;
        }


        if("null".equals(rebateStrategy) || "".equals(rebateStrategy)){//返利政策
            rebateStrategyAssembly.setPromptText("该字段为必填项");
            rebateStrategyAssembly.setStyle("-fx-border-color: red;");
            return;
        }


        ObservableList<RebatePolicyDetailVO> detailList = tableView.getItems();

        if("返利政策1".equals(rebateStrategy)){

            if("".equals(name1)){
                name1Assembly.setPromptText("该字段为必填项");
                name1Assembly.setStyle("-fx-border-color: red ;");
                return;
            }

            if("".equals(vkorg_name)){
                vkorg_nameAssembly.setPromptText("该字段为必填项");
                vkorg_nameAssembly.setStyle("-fx-border-color: red ;");
                return;
            }


            if("".equals(name_ec_name)){
                name_ec_nameAssembly.setPromptText("该字段为必填项");
                name_ec_nameAssembly.setStyle("-fx-border-color: red ;");
                return;
            }


        }else if("返利政策2".equals(rebateStrategy)){
            if("".equals(name1)){
                name1Assembly.setPromptText("该字段为必填项");
                name1Assembly.setStyle("-fx-border-color: red ;");
                return;
            }

            if("".equals(vkorg_name)){
                vkorg_nameAssembly.setPromptText("该字段为必填项");
                vkorg_nameAssembly.setStyle("-fx-border-color: red ;");
                return;
            }

            if("".equals(name_ec_name)){
                name_ec_nameAssembly.setPromptText("该字段为必填项");
                name_ec_nameAssembly.setStyle("-fx-border-color: red ;");
                return;
            }

        }else if("返利政策3".equals(rebateStrategy)){
            if("".equals(name1)){
                name1Assembly.setPromptText("该字段为必填项");
                name1Assembly.setStyle("-fx-border-color: red ;");
                return;
            }

            if("".equals(vkorg_name)){
                vkorg_nameAssembly.setPromptText("该字段为必填项");
                vkorg_nameAssembly.setStyle("-fx-border-color: red ;");
                return;
            }



        }else if("返利政策4".equals(rebateStrategy)){
            if("".equals(name1)){
                name1Assembly.setPromptText("该字段为必填项");
                name1Assembly.setStyle("-fx-border-color: red ;");
                return;
            }

            if("".equals(vkorg_name)){
                vkorg_nameAssembly.setPromptText("该字段为必填项");
                vkorg_nameAssembly.setStyle("-fx-border-color: red ;");
                return;
            }


        }else if("返利政策5".equals(rebateStrategy)){
            if("".equals(name1)){
                name1Assembly.setPromptText("该字段为必填项");
                name1Assembly.setStyle("-fx-border-color: red ;");
                return;
            }
        }


        String nowDate = DateUtils.dateTime();
        Long temp = 0L;
        //FL+年+月+日+流水，后面可能从OA传进来
        List<RebatePolicyVO> rpList = reService.checkRebateNoExists(nowDate);
        if(rpList.size()>0){//表示当前日期存在编码了
            String bh = rpList.get(0).getRebateFormNo();
            if(bh.length()>4){
                temp = Long.valueOf(bh.substring(2,bh.length()))+1;
            }else{
                temp = Long.valueOf(nowDate+"0001");
            }

        }else{
            temp = Long.valueOf(nowDate+"0001");
        }

        String flbh = "FL"+temp;
        //System.err.println("flbh==="+flbh);

        RebatePolicyVO rep = new RebatePolicyVO();
        rep.setRebateFormName(rebateFormName);
        rep.setRebateFormNo(flbh);
        rep.setContractNo(contractNo);
        rep.setRebateStrategy(rebateStrategy);
        rep.setName1(name1);
        rep.setKunnr(kunnr);
        rep.setVkorg_name(vkorg_name);
        rep.setVkorg(vkorg);
        rep.setName_ec_name(name_ec_name);
        rep.setName_ec(name_ec);
        rep.setRebateLimit(rebateLimit);
        rep.setZ005(z005);
        rep.setTakeEffectTime(takeEffectTime);
        rep.setFailureTime(failureTime);
        rep.setRemark(remark);

        List<RebatePolicyDetailVO> list = new ArrayList<>();
        if("返利政策1".equals(rebateStrategy) || "返利政策3".equals(rebateStrategy)){

            if(detailList.size()<=0){
                CommonUtil._alertInformation("选择返利政策1或3时，明细必填，请检查");
                return;
            }

            for (RebatePolicyDetailVO detailVO:detailList) {
                RebatePolicyDetailVO rpDetail = new RebatePolicyDetailVO();

                rpDetail.setRebateFormNo(flbh);
                rpDetail.setRemark(detailVO.getRemark());
                rpDetail.setMatkl_name(detailVO.getMatkl_name());
                rpDetail.setMatkl(detailVO.getMatkl());
                rpDetail.setRebateLimit(rebateLimit);
                rpDetail.setZ005(z005);
                list.add(rpDetail);
            }

        }


        String rs = reService.insertRebatePolicy(rep,list);
        if(rs.indexOf("error")!=-1){
            CommonUtil._alertInformation("返利政策新增失败"+rs);
        }else{
           ToastUtil.customizeToast("返利政策新增成功",2000,200.00,300.00);

            rebateFormNameAssembly.setText("");
            contractNoAssembly.setText("");
            rebateStrategyAssembly.getSelectionModel().select(-1);
            name1Assembly.getSelectionModel().select(-1);
            kunnrAssembly.setText("");
            vkorg_nameAssembly.getSelectionModel().select(-1);
            vkorgAssembly.setText("");
            name_ec_nameAssembly.getSelectionModel().select(-1);
            name_ecAssembly.setText("");
            takeEffectTimeAssembly.setValue(null);
            rebateLimitAssembly.setText("");
            z005Assembly.setText("");
            failureTimeAssembly.setValue(null);
            remarkAssembly.setText("");

            dataList.removeAll(tableView.getItems());

        }
    }



    /**
     * @Author cy
     * @Description 全选
     * * @param null
     * @Return
     * @Date 2022/7/27 15:53
     */
    public void selectAll(ActionEvent actionEvent) {
        checkedIdList.clear();
        ObservableList<RebatePolicyDetailVO> items = tableView.getItems();
        //System.err.println("items=="+items.size()+"---"+items);
        if (mselectAll.isSelected()) {
            for (RebatePolicyDetailVO rp : items) {
                rp.getCb().setSelected(true);
                checkedIdList.add(String.valueOf(rp.getId()));
            }
        } else {
            for (RebatePolicyDetailVO rp : items) {
                rp.getCb().setSelected(false);
            }
            checkedIdList.clear();
        }
    }

    /**
     * @Author cy
     * @Description 设置标题
     * * @param null
     * @Return
     * @Date 2022/7/26 11:32
     */
    public void setTableTitle(){
        ObservableList<TableColumn<RebatePolicyDetailVO, ?>> columns = tableView.getColumns();
        List<KeyList> keyList = DataTypeWrapper.getKeyList(RebatePolicyDetailVO.class);
        for (int i = 0; i < ROWS.length; i++) {
            String key = ROWS[i];
            Optional<KeyList> keys = keyList.stream().filter(e-> e.getKey().equals(key)).findFirst();
            TableColumn<RebatePolicyDetailVO, Object> column = new TableColumn();
            column.setText(keys.get().getLabel());
            column.setCellValueFactory(new PropertyValueFactory(key));
            column.setPrefWidth(100);
            columns.add(column);
        }
    }



    /**
     * @Author cy
     * @Description 添加空明细
     * * @param null
     * @Return
     * @Date 2022/8/3 10:35
     */
    public void addDetail(ActionEvent actionEvent) {

        RebatePolicyDetailVO rp = new RebatePolicyDetailVO();
        dataList.add(rp);
    }


    /**
     * @Author cy
     * @Description 删除选中明细
     * * @param null
     * @Return
     * @Date 2022/8/3 10:34
     */
    public void deleteDetail(ActionEvent actionEvent) {
        int size = dataList.size();
        if (size <= 0) {
            return;
        }
        for (int i = size - 1; i >= 0; i--) {
            RebatePolicyDetailVO s = dataList.get(i);
            if (s.getCb().isSelected()) {
                dataList.remove(s);
            }
        }
        tableView.refresh();

    }


}
