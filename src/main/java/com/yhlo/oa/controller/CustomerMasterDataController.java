package com.yhlo.oa.controller;

import com.github.pagehelper.PageInfo;
import com.yhlo.oa.entity.*;
import com.yhlo.oa.service.CustomerMasterDataService;
import com.yhlo.oa.service.SearchCustomerDetailService;
import com.yhlo.oa.service.iml.CustomerMasterDataServiceImpl;
import com.yhlo.oa.service.iml.SearchCustomerDetailServiceImpl;
import com.yhlo.oa.util.CommonUtil;
import com.yhlo.oa.util.DataTypeWrapper;
import com.yhlo.oa.util.SpringBeanUtil;
import com.yhlo.oa.util.poi.ExcelUtil;
import de.felixroske.jfxsupport.FXMLController;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author cy
 * @ClassName: ProductDataController
 * @Description:
 * @date 2022/6/114:04
 */

@Slf4j
@FXMLController
public class CustomerMasterDataController implements Initializable {

    private final static String[] ROWS = {"id", "kunnr", "land1", "name1","name2","ort01","pstlz","regio",
            "stras","telf1","telfx","adrnr","erdat","ernam","ktokd","spras","telf2","stcd5","kukla","create_time"};


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
    private HBox parent;

    @FXML
    private TableView<It_Kna1VO> tableView;

    @Autowired
    private CustomerMasterDataService cmDataService;
    @FXML
    public ChoiceBox<String> selectPage;
    @FXML
    public Button btnPrev;
    @FXML
    public Button btnNext;
    @FXML
    public TextField toPageNoText;
    @FXML
    public TextField totalCountText;
    @FXML
    public TextField totalPageText;
    @FXML
    public HBox pageNoHBox;
    @FXML
    public TableColumn<It_Kna1VO, CheckBox> mColumnSelect;
    @FXML
    private CheckBox mselectAll;
    @FXML
    public TextField kunnrTextField;
    @FXML
    public TextField name1TextField;


    @FXML
    public Button searchTableBt;
    @FXML
    public Button exportAllMateriaBt;


    @FXML
    public TabPane tabPane;
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



    // ????????????
    private List<String> deleteList = new ArrayList<>();

    // ????????????
    private List<String> updateList = new ArrayList<>();

    public List<String> checkedIdList = new ArrayList<>();

    Integer currentPage = 1;
    int count = 0;

    List<Button> buttonList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmDataService = SpringBeanUtil.getBean(CustomerMasterDataServiceImpl.class);
        scService = SpringBeanUtil.getBean(SearchCustomerDetailServiceImpl.class);

        //?????????????????????
        KeyCodeCombination keyCodeCopy = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY);

        ContextMenu contextMenu = new ContextMenu();

        MenuItem searchDetail = new MenuItem("????????????",new ImageView("/img/icon/ordSearch.png"));
       // MenuItem setColumnName = new MenuItem("???????????????",new ImageView("/img/icon/set.png"));

        contextMenu.getItems().add(searchDetail);
       // contextMenu.getItems().add(setColumnName);

        ImageView searchTableImage = new ImageView( CommonUtil.getImage("/img/icon/search.png"));
        searchTableBt.setGraphic(searchTableImage);

        ImageView exportAllImage = new ImageView( CommonUtil.getImage("/img/icon/excel.png"));
        exportAllMateriaBt.setGraphic(exportAllImage);


        tableView.setTableMenuButtonVisible(true);//????????????????????????column???
        //tableView.getSelectionModel().setCellSelectionEnabled(true);//???????????????????????????




        //??????????????????????????????????????????
        tableView.getSelectionModel().getSelectedCells().addListener(new InvalidationListener() {

            @Override
            public void invalidated(javafx.beans.Observable observable) {
                ObservableList<TablePosition> observableList = (ObservableList<TablePosition>) observable;
                for(int i=0;i<observableList.size();i++){
                    TablePosition tablePosition = observableList.get(i);
                    String TableColumnName = tablePosition.getTableColumn().getText();//??????
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


        final List<String> stringList = pageSizeSelectData().values().stream().sorted(Comparator.comparingInt(String::hashCode)).collect(Collectors.toList());

        selectPage.getItems().addAll(FXCollections.observableList(stringList));
        selectPage.setValue(stringList.get(0));

        selectPage.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            int pageSize = getPageSize((Integer) newValue);
            final PageInfo<It_Kna1VO> page = getTablePage(currentPage, pageSize);
            final int pagePages = page.getPages();
            if (currentPage == 1) {
                btnPrev.setDisable(true);
                btnNext.setDisable(false);
            } else if (currentPage > 1 && currentPage < pagePages) {
                btnPrev.setDisable(false);
                btnNext.setDisable(false);
            } else {
                btnPrev.setDisable(false);
                btnNext.setDisable(true);
            }
            final String text = toPageNoText.getText();
            if (page.getTotal() == 0 || text.equals("")) {
                toPageNoText.setText("1");
                totalCountText.setText("0");
            } else {
                if (currentPage > pagePages) {
                    currentPage = pagePages;
                }
                toPageNoText.setText(currentPage + "");
                totalCountText.setText(page.getTotal() + "");
            }
            totalPageText.setText(pagePages + "");
            setHBoxPaginationBtn(pageSize, page.getNavigatepageNums());
            setTableViewItems(page.getList());


           /* // ?????????????????????
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tableView.setStyle(String.format("-fx-font-size: %dpx;", (int) (0.45 * 30)));
            tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            tableView.setEditable(true);*/
        });

        // ???????????????
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (Objects.nonNull(newValue)) {
                final Checkbox checkbox = newValue.getCb();
                final ObservableList<It_Kna1VO> items = tableView.getItems();
              //  System.err.println("items=="+items);
                if (checkbox.isSelected()) {
                    checkbox.setSelected(false);
                    checkedIdList.remove(String.valueOf(newValue.getId()));
                } else {
                    checkbox.setSelected(true);
                     checkedIdList.add(String.valueOf(newValue.getId()));
                }
            }
        });

        //?????????
        tableView.setRowFactory( tv -> {
            TableRow<It_Kna1VO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                String name = event.getButton().name();
                //????????????
                if((!row.isEmpty()) && event.getClickCount()==1 && name.equals(MouseButton.SECONDARY.name())){

                    row.setContextMenu(contextMenu);

                    searchDetail.setOnAction((event1) -> {
                        It_Kna1VO rowData = row.getItem();
                        tabPane.getSelectionModel().select(companyCodeTab);//?????????????????????companyCodeTab
                        // System.out.println("rowData=="+rowData.getId());
                        setCompanyCodeTable(rowData);

                        //??????tab?????????
                        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {//?????????????????????
                            @Override
                            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                                if("????????????".equals(newValue.getText())){
                                    setCompanyCodeTable(rowData);
                                }else if("??????".equals(newValue.getText())){
                                    setSaleCodeTable(rowData);
                                }else if("????????????".equals(newValue.getText())){
                                    setCooperativePartnerTable(rowData);
                                }else if("??????".equals(newValue.getText())){
                                    setAddrTable(rowData);
                                }else if("????????????".equals(newValue.getText())){
                                    setCustomerMaterialTable(rowData);
                                }
                            }
                        });
                    });

                }
            });
            return row ;
        });

        // ???????????????checkbox?????????
        mColumnSelect.setCellValueFactory(param -> {
            final ObservableValue<CheckBox> observableValue = param.getValue().getCb().getCheckBox();
            return observableValue;
        });

        setTableTitle();


        btnNext.setDisable(true);
        btnPrev.setDisable(true);


    }


    /**
     * @Author cy
     * @Description ??????????????????
     * * @param null
     * @Return
     * @Date 2022/6/20 11:12
     */
    public void setTableTitle(){
        ObservableList<TableColumn<It_Kna1VO, ?>> columns = tableView.getColumns();
        List<KeyList> keyList = DataTypeWrapper.getKeyList(It_Kna1VO.class);
        for (int i = 0; i < ROWS.length; i++) {
            String key = ROWS[i];
            Optional<KeyList> keys = keyList.stream().filter(e-> e.getKey().equals(key)).findFirst();
            TableColumn<It_Kna1VO, Object> column = new TableColumn();
            column.setText(keys.get().getLabel());
            column.setCellValueFactory(new PropertyValueFactory(key));
            column.setPrefWidth(100);
            columns.add(column);
        }
    }


    /**
     * @Author cy
     * @Description ??????????????????
     * * @param null
     * @Return
     * @Date 2022/6/2 11:40
     */
    public void loadTable(ActionEvent actionEvent) {

        String value = selectPage.getItems().get(selectPage.getSelectionModel().selectedIndexProperty().getValue());
        int pageSize = pageSizeSelected(pageSizeSelectData(), value);
        PageInfo<It_Kna1VO> tablePage = getTablePage(currentPage, pageSize);
        long pageTotal = tablePage.getTotal();
        if (pageTotal == 0) {
            btnPrev.setDisable(true);
            btnNext.setDisable(true);
        } else {
            btnPrev.setDisable(false);
            btnNext.setDisable(false);
        }

        int[] pageNums = tablePage.getNavigatepageNums();
        log.info("????????????----{}", pageNums);
        setHBoxPaginationBtn(pageSize, pageNums);

        btnPrev.setDisable(tablePage.getPrePage() == 0);
        btnNext.setDisable(tablePage.getNextPage() == 0);

        toPageNoText.setText(currentPage + "");
        totalCountText.setText(pageTotal + "");
        totalPageText.setText(tablePage.getPages() + "");
        setTableViewItems(tablePage.getList());



    }



    /**
     * @Author cy
     * @Description ??????
     * * @param null
     * @Return
     * @Date 2022/6/2 11:22
     */
    public void selectAll(ActionEvent actionEvent) {
        checkedIdList.clear();
        ObservableList<It_Kna1VO> items = tableView.getItems();
        //System.err.println("items=="+items.size()+"---"+items);
        if (mselectAll.isSelected()) {
            for (It_Kna1VO ma : items) {
                ma.getCb().setSelected(true);
                checkedIdList.add(String.valueOf(ma.getId()));
            }
        } else {
            for (It_Kna1VO ma : items) {
                ma.getCb().setSelected(false);
            }
            checkedIdList.clear();
        }
    }



    /**
     * @Author cy
     * @Description ????????????????????????
     * * @param null
     * @Return
     * @Date 2022/6/2 11:39
     */
    private void setHBoxPaginationBtn(int pageSize, int[] pageNums) {
        if (pageNums.length > 0) {
            buttonList.clear();
            final ObservableList<Node> noHBoxChildren = pageNoHBox.getChildren();
            noHBoxChildren.clear();
            for (int pageNum : pageNums) {
                final Button button = new Button(String.valueOf(pageNum));
                button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                button.setOnAction(event -> {
                    toPageNoText.setText(pageNum + "");
                    PageInfo<It_Kna1VO> pageInfo = getTablePage(pageNum, pageSize);
                    totalCountText.setText(pageInfo.getTotal() + "");
                    setTableViewItems(pageInfo.getList());
                });
                buttonList.add(button);
            }
            noHBoxChildren.addAll(buttonList);
        }
    }


    private int getPageSize(Integer newValue) {
        final String value = selectPage.getItems().get(newValue);
        return pageSizeSelected(pageSizeSelectData(), value);
    }

    private PageInfo<It_Kna1VO> getTablePage(Integer currentPage, Integer pageSize) {
        String kunnr = kunnrTextField.getText();
        String name1 = name1TextField.getText();
        return cmDataService.getKna1PageList(currentPage, pageSize,kunnr,name1);
    }

    private List<It_Kna1VO> queryKna1List() {
        String kunnr = kunnrTextField.getText();
        String name1 = name1TextField.getText();
        return cmDataService.queryKna1List(kunnr,name1);
    }


    public void setTableViewItems(List<It_Kna1VO> list) {
        tableView.setItems(FXCollections.observableList(list));
        tableView.refresh();
    }


    public Integer pageSizeSelected(Map<String, String> map, String value) {
        int pageSize = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (Objects.equals(entry.getValue(), value)) {
                pageSize = Integer.parseInt(entry.getKey());
                break;
            }
        }
        return pageSize;
    }


    public Map<String, String> pageSizeSelectData() {
        Map<String, String> map = new HashMap<>(5);
        map.put("10", "10???/???");
        map.put("20", "20???/???");
        map.put("50", "50???/???");
        map.put("100", "100???/???");
        map.put("200", "200???/???");
        return map;
    }




    /**
     * @Author cy
     * @Description ?????????
     * * @param null
     * @Return
     * @Date 2022/6/2 11:45
     */
    public void prevPage(ActionEvent actionEvent) {

        final String pageNoTextText = toPageNoText.getText();
        this.currentPage = Integer.parseInt(pageNoTextText) - 1;

        Button buttonPrev = (Button) actionEvent.getSource();

        btnPrev.setDisable(pageNoTextText.equals("1"));

        final String countTextText = totalCountText.getText();
        if (countTextText.equals("0")) {
            buttonPrev.setDisable(true);
            btnNext.setDisable(true);
        } else {
            toPageNoText.setText(this.currentPage + "");
            if (currentPage == 0) {
                toPageNoText.setText("1");
                this.currentPage = 1;
                CommonUtil._alertInformation("?????????????????????");
                buttonPrev.setDisable(true);
                btnNext.setDisable(false);
                return;
            }
            buttonPrev.setDisable(false);
            btnNext.setDisable(false);
            final String value = selectPage.getItems().get(selectPage.getSelectionModel().selectedIndexProperty().getValue());
            int pageSize = pageSizeSelected(pageSizeSelectData(), value);
            final PageInfo<It_Kna1VO> page = getTablePage(currentPage, pageSize);
            totalCountText.setText(page.getTotal() + "");
            totalPageText.setText(page.getPages() + "");
            setTableViewItems(page.getList());
            final int[] pageNums = page.getNavigatepageNums();
            log.info("???????????????????????????----{}", pageNums);
            setHBoxPaginationBtn(pageSize, pageNums);
        }
    }

    /**
     * @Author cy
     * @Description ?????????
     * * @param null
     * @Return
     * @Date 2022/6/2 11:44
     */
    public void nextPage(ActionEvent actionEvent) {
        final String value = selectPage.getItems().get(selectPage.getSelectionModel().selectedIndexProperty().getValue());
        int pageSize = pageSizeSelected(pageSizeSelectData(), value);

        // ????????????
        final String pageNoTextText = toPageNoText.getText();
        this.currentPage = Integer.parseInt(pageNoTextText) + 1;

        Button buttonNext = (Button) actionEvent.getSource();

        final String countTextText = totalCountText.getText();
        if (countTextText.equals("0")) {
            buttonNext.setDisable(true);
            btnPrev.setDisable(true);
        } else {
            toPageNoText.setText(this.currentPage + "");
            final PageInfo<It_Kna1VO> page = getTablePage(currentPage, pageSize);
            final int pagePages = page.getPages();
            if (currentPage > pagePages) {
                toPageNoText.setText(String.valueOf(pagePages));
                buttonNext.setDisable(true);
                btnPrev.setDisable(false);
                CommonUtil._alertInformation("????????????????????????");
                return;
            }
            buttonNext.setDisable(false);
            btnPrev.setDisable(false);
            totalCountText.setText(page.getTotal() + "");
            totalPageText.setText(page.getPages() + "");
            setTableViewItems(page.getList());
            final int[] pageNums = page.getNavigatepageNums();
            log.info("???????????????????????????----{}", pageNums);
            setHBoxPaginationBtn(pageSize, pageNums);
        }
    }


    /**
     * @Author cy
     * @Description ??????????????????
     * @Return
     * @Date 2022/6/7 9:45
     */
    public void searchCustomerDetail(It_Kna1VO btData) {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/searchCustomerDetail.fxml"));
            Parent pane = (Parent)fxmlLoader.load();
            Scene scene = new Scene(pane);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.getScene().getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
            stage.setTitle("????????????");
            stage.resizableProperty().setValue(Boolean.FALSE);//???????????????
            //????????????????????????
            stage.setResizable(false);
            stage.getIcons().add(CommonUtil.getLogo());

            SearchCustomerDetailController controller = fxmlLoader.getController();
            controller.setCustomerData(btData);
            //stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            // ??????Scene

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    @FXML
    protected void exportAllCustomer(ActionEvent event) {
        // ShowDialog.showConfirmDialog(FXRobotHelper.getStages().get(0),
        // "?????????????????????txt???", "??????");
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("files (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        File file = fileChooser.showSaveDialog(stage);
        if (file == null)
            return;
        if(file.exists()){//???????????????????????????????????????
            file.delete();
        }
        String exportFilePath = file.getAbsolutePath();
        System.out.println("?????????????????????" + exportFilePath);

        List<It_Kna1VO> list = queryKna1List();
        ExcelUtil<It_Kna1VO> util = new ExcelUtil<It_Kna1VO>(It_Kna1VO.class);
        String rs = util.exportExcelCustomPath(list, "???????????????",exportFilePath);
        System.err.println("rs=="+rs);
    }



    /**
     * @Author cy
     * @Description
     * * @param null ???????????????????????????????????????
     * @Return
     * @Date 2022/6/9 17:00
     */
    private List<It_Knb1VO> queryKnb1List(String kunnr) {
        return scService.queryKnb1List(kunnr);
    }

    /**
     * @Author cy
     * @Description
     * * @param null ???????????????????????????????????????
     * @Return
     * @Date 2022/6/9 17:00
     */
    private List<It_KnvvVO> queryKnvvList(String kunnr) {
        return scService.queryKnvvList(kunnr);
    }

    /**
     * @Author cy
     * @Description ???????????????????????????????????????
     * * @param null
     * @Return
     * @Date 2022/6/10 9:02
     */
    private List<It_KnvpVO> queryKnvpList(String kunnr) {
        return scService.queryKnvpList(kunnr);
    }

    /**
     * @Author cy
     * @Description ???????????????????????????????????????
     * * @param null
     * @Return
     * @Date 2022/6/10 9:14
     */
    private List<It_AdrcVO> queryAdrcList(String addrnumber) {
        return scService.queryAdrcList(addrnumber);
    }

    /**
     * @Author cy
     * @Description ????????????????????????
     * * @param null
     * @Return
     * @Date 2022/6/10 9:42
     */
    private List<It_Ztsd_012VO> queryZtsdList(String kunnr) {
        return scService.queryZtsdList(kunnr);
    }


    /**
     * @Author cy
     * @Description ??????????????????????????????
     * * @param null
     * @Return
     * @Date 2022/6/9 17:05
     */
    public void setCompanyCodeTable(It_Kna1VO knaData){
        ObservableList<It_Knb1VO> items = companyCodeTableView.getItems();
        items.clear();
        items.addAll(queryKnb1List(knaData.getKunnr()));

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
     * @Description ????????????????????????
     * * @param null
     * @Return
     * @Date 2022/6/9 18:05
     */
    public void setSaleCodeTable(It_Kna1VO knaData){
        ObservableList<It_KnvvVO> items = saleTableView.getItems();
        items.clear();
        items.addAll(queryKnvvList(knaData.getKunnr()));

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
     * @Description ??????????????????????????????
     * * @param null
     * @Return
     * @Date 2022/6/10 9:03
     */
    public void setCooperativePartnerTable(It_Kna1VO knaData){
        ObservableList<It_KnvpVO> items = cooperativePartnerTableView.getItems();
        items.clear();
        items.addAll(queryKnvpList(knaData.getKunnr()));

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
     * @Description ??????????????????????????????
     * * @param null
     * @Return
     * @Date 2022/6/10 9:15
     */
    public void setAddrTable(It_Kna1VO knaData){
        ObservableList<It_AdrcVO> items = addrTableView.getItems();
        items.clear();
        items.addAll(queryAdrcList(knaData.getAdrnr()));

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
     * @Description ??????????????????????????????
     * * @param null
     * @Return
     * @Date 2022/6/10 10:09
     */
    public void setCustomerMaterialTable(It_Kna1VO knaData){
        ObservableList<It_Ztsd_012VO> items = customerMaterialTableView.getItems();
        items.clear();
        items.addAll(queryZtsdList(knaData.getKunnr()));

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

}
