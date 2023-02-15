package com.yhlo.oa.controller;

import com.github.pagehelper.PageInfo;
import com.sun.javafx.robot.impl.FXRobotHelper;
import com.sun.javafx.scene.control.skin.TableHeaderRow;
import com.sun.javafx.scene.control.skin.TableViewSkinBase;
import com.yhlo.oa.entity.*;
import com.yhlo.oa.service.MateriaMasterDataService;
import com.yhlo.oa.service.SearchMaterDetailService;
import com.yhlo.oa.service.iml.MateriaMasterDataServiceImpl;
import com.yhlo.oa.service.iml.SearchMaterDetailServiceImpl;
import com.yhlo.oa.util.*;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cy
 * @ClassName: ProductDataController
 * @Description:
 * @date 2022/6/114:04
 */

@Slf4j
@FXMLController
@RestController
public class MateriaMasterDataController implements Initializable {

    private final static String[] ROWS = {"id", "matnr", "maktx", "mtart","matkl","raube","bismt","spart",
            "prdha","meins","mstae","xchpf","extwg","mbrsh","mhdrz","mhdhb","mtpos_mara","zggxh","zcus01",
            "zcus02","zcus02_1","zcus02_2","zcus03","zcus04","zcus05","zcus06","zcus07","zcus08","zcus09",
            "zcus10","zcus11","zcus12","zcus13","zcus14","zcus15","zcus16","zcus17","zcus18","zcus19",
            "zcus20","zcus21","zcus22","zcus23","zcus24","zcus25","zcus26","zcus27","zcus28","zcus29",
            "zcus30","zcus31","zcus32","zcus33","zcus34","zcus35","create_time"};


    private final static String[] saleROWS = {"id", "matnr", "vkorg", "vtweg","vrkme","mtpos","dwerk","ktgrm",
            "lvorm","vmsta","taxm1"};

    private final static String[] costROWS = {"id", "matnr", "bwkey", "bklas","lvorm","vprsv","verpr","stprs",
            "peinh"};

    private final static String[] unitROWS = {"id", "matnr", "meinh", "umrez","umren"};

    private final static String[] stockROWS = {"id", "matnr", "werks", "lgort","lvorm","labst"};

    private final static String[] factoryROWS = {"id", "matnr", "werks", "lvorm","xchpf","xchar","ladgr","sernp"};


    @FXML
    private HBox parent;

    @FXML
    private TableView<It_MaraVO> tableView;

    @Autowired
    private MateriaMasterDataService productDataService;
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
    public TableColumn<It_MaraVO, CheckBox> mColumnSelect;
    @FXML
    private CheckBox mselectAll;
    @FXML
    public TextField matnrTextField;

    @FXML
    public Button searchTableBt;
    @FXML
    public Button exportAllMateriaBt;


    @Autowired
    private SearchMaterDetailService scService;//物料明细services

    @FXML
    public TabPane tabPane;
    @FXML
    public TableView saleTableView;
    @FXML
    public Tab saleTab;
    @FXML
    public TableView costTableView;
    @FXML
    public Tab costTab;
    @FXML
    public TableView unitTableView;
    @FXML
    public Tab unitTab;
    @FXML
    public TableView stockTableView;
    @FXML
    public Tab stockTab;
    @FXML
    public TableView factoryTableView;
    @FXML
    public Tab factoryTab;


    // 删除集合
    private List<String> deleteList = new ArrayList<>();

    // 编辑集合
    private List<String> updateList = new ArrayList<>();

    public List<String> checkedIdList = new ArrayList<>();

    Integer currentPage = 1;
    int count = 0;

    List<Button> buttonList = new ArrayList<>();




    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ContextMenu contextMenu = new ContextMenu();

        MenuItem searchDetail = new MenuItem("查看明细",new ImageView("/img/icon/ordSearch.png"));
        //MenuItem setColumnName = new MenuItem("设置显示列",new ImageView("/img/icon/set.png"));

        contextMenu.getItems().add(searchDetail);
       // contextMenu.getItems().add(setColumnName);

        tableView.setTableMenuButtonVisible(true);



        productDataService = SpringBeanUtil.getBean(MateriaMasterDataServiceImpl.class);

        scService = SpringBeanUtil.getBean(SearchMaterDetailServiceImpl.class);

        KeyCodeCombination keyCodeCopy = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY);


        ImageView searchTableImage = new ImageView( CommonUtil.getImage("/img/icon/search.png"));
        searchTableBt.setGraphic(searchTableImage);

        ImageView exportAllImage = new ImageView( CommonUtil.getImage("/img/icon/excel.png"));
        exportAllMateriaBt.setGraphic(exportAllImage);


        final List<String> stringList = pageSizeSelectData().values().stream().sorted(Comparator.comparingInt(String::hashCode)).collect(Collectors.toList());

        selectPage.getItems().addAll(FXCollections.observableList(stringList));
        selectPage.setValue(stringList.get(0));

        selectPage.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            int pageSize = getPageSize((Integer) newValue);
            final PageInfo<It_MaraVO> page = getTablePage(currentPage, pageSize);
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


            tabPane.getSelectionModel().select(saleTab);//默认进去就显示saleTab




           /* // 设置自适应宽度
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tableView.setStyle(String.format("-fx-font-size: %dpx;", (int) (0.45 * 30)));
            tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            tableView.setEditable(true);*/
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


        // 单击选中行
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (Objects.nonNull(newValue)) {
                final Checkbox checkbox = newValue.getCb();
                final ObservableList<It_MaraVO> items = tableView.getItems();
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

        tableView.setRowFactory( tv -> {
            TableRow<It_MaraVO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                String name = event.getButton().name();
                //右击事件
                if((!row.isEmpty()) && event.getClickCount()==1 && name.equals(MouseButton.SECONDARY.name())){

                    row.setContextMenu(contextMenu);

                    searchDetail.setOnAction((event1) -> {
                        It_MaraVO rowData = row.getItem();
                        tabPane.getSelectionModel().select(saleTab);//默认进去就显示saleTab
                       // System.out.println("rowData=="+rowData.getId());
                        setSaleTable(rowData);

                        //监控tab页变化
                        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {//监听点击的哪个
                            @Override
                            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                                // System.out.println("tableViewName=="+newValue.getText());
                                if("销售".equals(newValue.getText())){
                                    setSaleTable(rowData);
                                }else if("成本".equals(newValue.getText())){
                                    setCostTable(rowData);
                                }else if("单位".equals(newValue.getText())){
                                    setUnitTable(rowData);
                                }else if("库存".equals(newValue.getText())){
                                    setStockTable(rowData);
                                }else if("工厂".equals(newValue.getText())){
                                    setFactoryTable(rowData);
                                }
                            }
                        });
                    });

                }

            });
            return row ;
        });

        // 初始化所有checkbox复选框
        mColumnSelect.setCellValueFactory(param -> {
            final ObservableValue<CheckBox> observableValue = param.getValue().getCb().getCheckBox();
            return observableValue;
        });

        setTableTitle();


        btnNext.setDisable(true);
        btnPrev.setDisable(true);


    }




    public void setTableTitle(){
        ObservableList<TableColumn<It_MaraVO, ?>> columns = tableView.getColumns();
        List<KeyList> keyList = DataTypeWrapper.getKeyList(It_MaraVO.class);
        for (int i = 0; i < ROWS.length; i++) {
            String key = ROWS[i];
            Optional<KeyList> keys = keyList.stream().filter(e-> e.getKey().equals(key)).findFirst();
            TableColumn<It_MaraVO, Object> column = new TableColumn();
            column.setText(keys.get().getLabel());
            column.setCellValueFactory(new PropertyValueFactory(key));
            column.setPrefWidth(100);
            columns.add(column);
        }
    }


    /**
     * @Author cy
     * @Description 加载数据列表
     * * @param null
     * @Return
     * @Date 2022/6/2 11:40
     */
    public void loadTable(ActionEvent actionEvent) {

        String value = selectPage.getItems().get(selectPage.getSelectionModel().selectedIndexProperty().getValue());
        int pageSize = pageSizeSelected(pageSizeSelectData(), value);
        PageInfo<It_MaraVO> tablePage = getTablePage(currentPage, pageSize);
        long pageTotal = tablePage.getTotal();
        if (pageTotal == 0) {
            btnPrev.setDisable(true);
            btnNext.setDisable(true);
        } else {
            btnPrev.setDisable(false);
            btnNext.setDisable(false);
        }

        int[] pageNums = tablePage.getNavigatepageNums();
        log.info("分页组件----{}", pageNums);
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
     * @Description 全选
     * * @param null
     * @Return
     * @Date 2022/6/2 11:22
     */
    public void selectAll(ActionEvent actionEvent) {
        checkedIdList.clear();
        ObservableList<It_MaraVO> items = tableView.getItems();
        //System.err.println("items=="+items.size()+"---"+items);
        if (mselectAll.isSelected()) {
            for (It_MaraVO ma : items) {
                ma.getCb().setSelected(true);
                checkedIdList.add(String.valueOf(ma.getId()));
            }
        } else {
            for (It_MaraVO ma : items) {
                ma.getCb().setSelected(false);
            }
            checkedIdList.clear();
        }
    }



    /**
     * @Author cy
     * @Description 添加分页控件按钮
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
                    PageInfo<It_MaraVO> pageInfo = getTablePage(pageNum, pageSize);
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

    private PageInfo<It_MaraVO> getTablePage(Integer currentPage, Integer pageSize) {
        String matnr = matnrTextField.getText();
        return productDataService.getMaraPageList(currentPage, pageSize,matnr);
    }

    private List<It_MaraVO> queryMaraList() {
        String matnr = matnrTextField.getText();
        return productDataService.queryMaraList(matnr);
    }


    public void setTableViewItems(List<It_MaraVO> list) {
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
        map.put("10", "10条/页");
        map.put("20", "20条/页");
        map.put("50", "50条/页");
        map.put("100", "100条/页");
        map.put("200", "200条/页");
        return map;
    }




    /**
     * @Author cy
     * @Description 上一页
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
                CommonUtil._alertInformation("已经到第一页啦");
                buttonPrev.setDisable(true);
                btnNext.setDisable(false);
                return;
            }
            buttonPrev.setDisable(false);
            btnNext.setDisable(false);
            final String value = selectPage.getItems().get(selectPage.getSelectionModel().selectedIndexProperty().getValue());
            int pageSize = pageSizeSelected(pageSizeSelectData(), value);
            final PageInfo<It_MaraVO> page = getTablePage(currentPage, pageSize);
            totalCountText.setText(page.getTotal() + "");
            totalPageText.setText(page.getPages() + "");
            setTableViewItems(page.getList());
            final int[] pageNums = page.getNavigatepageNums();
            log.info("上一页分页页码数组----{}", pageNums);
            setHBoxPaginationBtn(pageSize, pageNums);
        }
    }

    /**
     * @Author cy
     * @Description 下一页
     * * @param null
     * @Return
     * @Date 2022/6/2 11:44
     */
    public void nextPage(ActionEvent actionEvent) {
        final String value = selectPage.getItems().get(selectPage.getSelectionModel().selectedIndexProperty().getValue());
        int pageSize = pageSizeSelected(pageSizeSelectData(), value);

        // 当前页码
        final String pageNoTextText = toPageNoText.getText();
        this.currentPage = Integer.parseInt(pageNoTextText) + 1;

        Button buttonNext = (Button) actionEvent.getSource();

        final String countTextText = totalCountText.getText();
        if (countTextText.equals("0")) {
            buttonNext.setDisable(true);
            btnPrev.setDisable(true);
        } else {
            toPageNoText.setText(this.currentPage + "");
            final PageInfo<It_MaraVO> page = getTablePage(currentPage, pageSize);
            final int pagePages = page.getPages();
            if (currentPage > pagePages) {
                toPageNoText.setText(String.valueOf(pagePages));
                buttonNext.setDisable(true);
                btnPrev.setDisable(false);
                CommonUtil._alertInformation("已经到最后一页啦");
                return;
            }
            buttonNext.setDisable(false);
            btnPrev.setDisable(false);
            totalCountText.setText(page.getTotal() + "");
            totalPageText.setText(page.getPages() + "");
            setTableViewItems(page.getList());
            final int[] pageNums = page.getNavigatepageNums();
            log.info("下一页分页页码数组----{}", pageNums);
            setHBoxPaginationBtn(pageSize, pageNums);
        }
    }



    /**
     * @Author cy
     * @Description 导出表格
     * * @param null
     * @Return
     * @Date 2022/6/15 9:30
     */
    @FXML
    protected void exportAllMateria(ActionEvent event) {
        // ShowDialog.showConfirmDialog(FXRobotHelper.getStages().get(0),
        // "是否导出数据到txt？", "信息");
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("files (*.xlsx)", "*.xlsx");
        fileChooser.getExtensionFilters().add(extFilter);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        File file = fileChooser.showSaveDialog(stage);
        if (file == null)
            return;
        if(file.exists()){//文件已存在，则删除覆盖文件
            file.delete();
        }
        String exportFilePath = file.getAbsolutePath();
        System.out.println("导出文件的路径" + exportFilePath);
        List<It_MaraVO> list = queryMaraList();
       // ObservableList<It_MaraVO> list = tableView.getItems();

        ExcelUtil<It_MaraVO> util = new ExcelUtil<It_MaraVO>(It_MaraVO.class);
        String rs = util.exportExcelCustomPath(list, "物料主数据",exportFilePath);
        System.err.println("rs=="+rs);



        /*StringBuilder sBuilder=new StringBuilder();
        if (list.size() > 0) {
            for (It_MaraVO model : list) {
                sBuilder.append(model.getId()+","+model.getMatnr()+","+model.getMaktx()+" ");
            }
        }
        FileWriteUtil.WriteDocument(exportFilePath, sBuilder.toString());
        ShowDialog.showMessageDialog(FXRobotHelper.getStages().get(0), "导出成功!保存路径: "+exportFilePath, "提示");*/
    }


    /**
     * @Author cy
     * @Description 获取销售物料视图数据
     * @Return
     * @Date 2022/6/8 11:27
     */
    private List<It_MvkeVO> queryMvkeList(String matnr) {
        return scService.queryMvkeList(matnr);
    }

    /**
     * @Author cy
     * @Description 获取物料评估类型视图数据
     * @Return
     * @Date 2022/6/8 11:27
     */
    private List<It_MbewVO> queryMbewList(String matnr) {
        return scService.queryMbewList(matnr);
    }

    /**
     * @Author cy
     * @Description 获取物料计量单位视图数据
     * * @param null
     * @Return
     * @Date 2022/6/8 16:59
     */
    private List<It_MarmVO> queryMarmList(String matnr) {
        return scService.queryMarmList(matnr);
    }

    /**
     * @Author cy
     * @Description 获取物料库存相关视图数据
     * * @param null
     * @Return
     * @Date 2022/6/8 16:59
     */
    private List<It_MardVO> queryMardList(String matnr) {
        return scService.queryMardList(matnr);
    }
    /**
     * @Author cy
     * @Description 获取物料工厂视图数据
     * * @param null
     * @Return
     * @Date 2022/6/8 17:54
     */
    private List<It_MarcVO> queryMarcList(String matnr) {
        return scService.queryMarcList(matnr);
    }


    /**
     * @Author cy
     * @Description 设置销售页签表格
     * * @param null
     * @Return
     * @Date 2022/6/8 11:40
     */
    public void setSaleTable(It_MaraVO mdata){
        ObservableList<It_MvkeVO> items = saleTableView.getItems();
        items.clear();
        items.addAll(queryMvkeList(mdata.getMatnr()));

        ObservableList<TableColumn<It_MvkeVO, ?>> columns = saleTableView.getColumns();
        columns.clear();
        List<KeyList> keyList = DataTypeWrapper.getKeyList(It_MvkeVO.class);
        for (int i = 0; i < saleROWS.length; i++) {
            String key = saleROWS[i];
            Optional<KeyList> keys = keyList.stream().filter(e-> e.getKey().equals(key)).findFirst();
            TableColumn<It_MvkeVO, Object> column = new TableColumn();
            column.setText(keys.get().getLabel());
            column.setCellValueFactory(new PropertyValueFactory(key));
            column.setPrefWidth(100);
            columns.add(column);
        }
    }


    /**
     * @Author cy
     * @Description 设置成本页签表格
     * * @param null
     * @Return
     * @Date 2022/6/8 16:37
     */
    public void setCostTable(It_MaraVO mdata){
        ObservableList<It_MbewVO> items = costTableView.getItems();
        items.clear();
        items.addAll(queryMbewList(mdata.getMatnr()));

        ObservableList<TableColumn<It_MbewVO, ?>> columns = costTableView.getColumns();
        columns.clear();
        List<KeyList> keyList = DataTypeWrapper.getKeyList(It_MbewVO.class);
        for (int i = 0; i < costROWS.length; i++) {
            String key = costROWS[i];
            Optional<KeyList> keys = keyList.stream().filter(e-> e.getKey().equals(key)).findFirst();
            TableColumn<It_MbewVO, Object> column = new TableColumn();
            column.setText(keys.get().getLabel());
            column.setCellValueFactory(new PropertyValueFactory(key));
            column.setPrefWidth(100);
            columns.add(column);
        }
    }

    /**
     * @Author cy
     * @Description 设置成单位签表格
     * * @param null
     * @Return
     * @Date 2022/6/8 17:01
     */
    public void setUnitTable(It_MaraVO mdata){
        ObservableList<It_MarmVO> items = unitTableView.getItems();
        items.clear();
        items.addAll(queryMarmList(mdata.getMatnr()));

        ObservableList<TableColumn<It_MarmVO, ?>> columns = unitTableView.getColumns();
        columns.clear();
        List<KeyList> keyList = DataTypeWrapper.getKeyList(It_MarmVO.class);
        for (int i = 0; i < unitROWS.length; i++) {
            String key = unitROWS[i];
            Optional<KeyList> keys = keyList.stream().filter(e-> e.getKey().equals(key)).findFirst();
            TableColumn<It_MarmVO, Object> column = new TableColumn();
            column.setText(keys.get().getLabel());
            column.setCellValueFactory(new PropertyValueFactory(key));
            column.setPrefWidth(100);
            columns.add(column);
        }
    }

    /**
     * @Author cy
     * @Description 设置库存页签表格
     * * @param null
     * @Return
     * @Date 2022/6/8 17:29
     */
    public void setStockTable(It_MaraVO mdata){
        ObservableList<It_MardVO> items = stockTableView.getItems();
        items.clear();
        items.addAll(queryMardList(mdata.getMatnr()));

        ObservableList<TableColumn<It_MardVO, ?>> columns = stockTableView.getColumns();
        columns.clear();
        List<KeyList> keyList = DataTypeWrapper.getKeyList(It_MardVO.class);
        for (int i = 0; i < stockROWS.length; i++) {
            String key = stockROWS[i];
            Optional<KeyList> keys = keyList.stream().filter(e-> e.getKey().equals(key)).findFirst();
            TableColumn<It_MardVO, Object> column = new TableColumn();
            column.setText(keys.get().getLabel());
            column.setCellValueFactory(new PropertyValueFactory(key));
            column.setPrefWidth(100);
            columns.add(column);
        }
    }

    /**
     * @Author cy
     * @Description 设置工厂页签表格
     * * @param null
     * @Return
     * @Date 2022/6/8 17:55
     */
    public void setFactoryTable(It_MaraVO mdata){
        ObservableList<It_MarcVO> items = factoryTableView.getItems();
        items.clear();
        items.addAll(queryMarcList(mdata.getMatnr()));

        ObservableList<TableColumn<It_MarcVO, ?>> columns = factoryTableView.getColumns();
        columns.clear();
        List<KeyList> keyList = DataTypeWrapper.getKeyList(It_MarcVO.class);
        for (int i = 0; i < factoryROWS.length; i++) {
            String key = factoryROWS[i];
            Optional<KeyList> keys = keyList.stream().filter(e-> e.getKey().equals(key)).findFirst();
            TableColumn<It_MarcVO, Object> column = new TableColumn();
            column.setText(keys.get().getLabel());
            column.setCellValueFactory(new PropertyValueFactory(key));
            column.setPrefWidth(100);
            columns.add(column);
        }
    }


}
