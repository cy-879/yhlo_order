package com.yhlo.oa.controller;

import com.github.pagehelper.PageInfo;
import com.yhlo.oa.entity.*;
import com.yhlo.oa.service.RebatePolicyService;
import com.yhlo.oa.service.iml.RebatePolicyServiceImpl;
import com.yhlo.oa.util.*;
import de.felixroske.jfxsupport.FXMLController;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author cy
 * @ClassName: RebatePolicyController
 * @Description:
 * @date 2022/6/248:52
 */
@Slf4j
@FXMLController
public class RebatePolicySearchController implements Initializable {

    private final static String[] ROWS = {"id", "rebateFormName", "contractNo", "rebateStrategy","name1","kunnr","vkorg_name","vkorg",
            "matkl_name","matkl","name_ec_name","name_ec","rebateLimit","z005","usedQuota","unusedQuota",
            "takeEffectTime","failureTime","remark","create_time","create_by","status","approvalTime","approvalBy"};

    private final static String[] rebateDetailROWS = {"id","rebateFormNo","matkl_name","matkl","rebateLimit","z005","usedQuota","unusedQuota","remark"};


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
    public TableView <RebatePolicyVO> tableView;
    @FXML
    public TableColumn<RebatePolicyVO, CheckBox> mColumnSelect;
    @FXML
    public TextField rebateFormNameAssembly_search;
    @FXML
    public TextField name1Assembly;
    @FXML
    public ComboBox<String> statuAssembly;
    @FXML
    private CheckBox mselectAll;

    @FXML
    public Tab rebateDetailTab;
    @FXML
    public TableView rebateDetailTableView;


    public List<String> checkedIdList = new ArrayList<>();

    Integer currentPage = 1;
    int count = 0;

    List<Button> buttonList = new ArrayList<>();


    @Autowired
    private RebatePolicyService reService;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        statuAssembly.getItems().addAll("全部","保存","待审核","已审核");
        statuAssembly.getSelectionModel().select(1);
        reService = SpringBeanUtil.getBean(RebatePolicyServiceImpl.class);


        KeyCodeCombination keyCodeCopy = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY);

        ContextMenu contextMenu = new ContextMenu();

        MenuItem searchDetail = new MenuItem("查看明细",new ImageView("/img/icon/ordSearch.png"));

        contextMenu.getItems().add(searchDetail);

        setTableTitle();
        initTable();

        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);//选多行
       // tableView.getSelectionModel().setCellSelectionEnabled(true);//点击选中单个单元格
       // tableView.setEditable(true);//表格设置为可编辑


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


        /*明细开启复制功能*/
        rebateDetailTableView.getSelectionModel().getSelectedCells().addListener(new InvalidationListener() {

            @Override
            public void invalidated(javafx.beans.Observable observable) {
                ObservableList<TablePosition> observableList = (ObservableList<TablePosition>) observable;
                for(int i=0;i<observableList.size();i++){
                    TablePosition tablePosition = observableList.get(i);
                    String TableColumnName = tablePosition.getTableColumn().getText();//列名
                    Object cellData = tablePosition.getTableColumn().getCellData(tablePosition.getRow());

                    rebateDetailTableView.setOnKeyPressed(event -> {
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
                Checkbox newCheckbox = newValue.getCb();
                ObservableList<RebatePolicyVO> items = tableView.getItems();
                //System.err.println("newValue=="+newValue);
                if (newCheckbox.isSelected()) {
                    newCheckbox.setSelected(false);
                } else {
                    newCheckbox.setSelected(true);
                }
            }

        });

        //行事件
        tableView.setRowFactory( tv -> {
            TableRow<RebatePolicyVO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                String name = event.getButton().name();

                //右击事件
                if((!row.isEmpty()) && event.getClickCount()==1 && name.equals(MouseButton.SECONDARY.name())){
                    row.setContextMenu(contextMenu);
                    searchDetail.setOnAction((event1) -> {
                        RebatePolicyVO rowData = row.getItem();
                        setRebateDetailTable(rowData);

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


        tableView.getColumns().addListener(new ListChangeListener() {//禁止列拖动
            boolean isturnback = false;

            @Override
            public void onChanged(Change c) {

                if (!isturnback) {
                    while (c.next()) {
                        if (!c.wasPermutated() && !c.wasUpdated()) {
                            isturnback = true;
                            tableView.getColumns().setAll(c.getRemoved());
                        }
                    }
                }
                else {
                    isturnback = false;
                }
            }
        });


       // Tooltip.install(saveBt, new Tooltip("鼠标悬浮显示的文字"));

    }






    /**
     * @Author cy
     * @Description 获取返利单列表
     * * @param null
     * @Return
     * @Date 2022/7/25 18:04
     */
    public List<RebatePolicyVO> queryRebatePolicyList(String rebateFormName_search,String name1,String status){
        return reService.queryRebatePolicyList(rebateFormName_search,name1,status);
    }


 /**
  * @Author cy
  * @Description 设置标题
  * * @param null
  * @Return
  * @Date 2022/7/26 11:32
  */
    public void setTableTitle(){
        ObservableList<TableColumn<RebatePolicyVO, ?>> columns = tableView.getColumns();
        List<KeyList> keyList = DataTypeWrapper.getKeyList(RebatePolicyVO.class);
        for (int i = 0; i < ROWS.length; i++) {
            String key = ROWS[i];
            Optional<KeyList> keys = keyList.stream().filter(e-> e.getKey().equals(key)).findFirst();
            TableColumn<RebatePolicyVO, Object> column = new TableColumn();
            column.setText(keys.get().getLabel());
            column.setCellValueFactory(new PropertyValueFactory(key));
            column.setPrefWidth(100);
            columns.add(column);
        }
    }

    /**
     * @Author cy
     * @Description 加载返利单明细列表
     * * @param null
     * @Return
     * @Date 2022/8/5 10:37
     */
    public void setRebateDetailTable(RebatePolicyVO rp){
        ObservableList<RebatePolicyDetailVO> items = rebateDetailTableView.getItems();
        items.clear();
        items.addAll(queryRebateDetailList(rp.getRebateFormNo()));

        ObservableList<TableColumn<RebatePolicyDetailVO, ?>> columns = rebateDetailTableView.getColumns();
        columns.clear();
        List<KeyList> keyList = DataTypeWrapper.getKeyList(RebatePolicyDetailVO.class);
        for (int i = 0; i < rebateDetailROWS.length; i++) {
            String key = rebateDetailROWS[i];
            Optional<KeyList> keys = keyList.stream().filter(e-> e.getKey().equals(key)).findFirst();
            TableColumn<RebatePolicyDetailVO, Object> column = new TableColumn();
            column.setText(keys.get().getLabel());
            column.setCellValueFactory(new PropertyValueFactory(key));
            column.setPrefWidth(150);
            columns.add(column);
        }
    }

    /**
     * @Author cy
     * @Description 根据主表的返利但编号获取明细
     * * @param null
     * @Return
     * @Date 2022/8/5 10:36
     */
    private List<RebatePolicyDetailVO> queryRebateDetailList(String rebateFormNo) {
        return reService.queryRebateDetailList(rebateFormNo);
    }

    /**
     * @Author cy
     * @Description 初始化表格控件
     * * @param null
     * @Return
     * @Date 2022/7/26 11:32
     */
    public void initTable(){

        List<String> stringList = pageSizeSelectData().values().stream().sorted(Comparator.comparingInt(String::hashCode)).collect(Collectors.toList());

        selectPage.getItems().addAll(FXCollections.observableList(stringList));
        selectPage.setValue(stringList.get(0));

        selectPage.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            int pageSize = getPageSize((Integer) newValue);
            final PageInfo<RebatePolicyVO> page = getTablePage(currentPage, pageSize);
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
        });
    }


    /**
     * @Author cy
     * @Description 加载数据列表
     * * @param null
     * @Return
     * @Date 2022/6/2 11:40
     */
    public void loadTable() {

        String value = selectPage.getItems().get(selectPage.getSelectionModel().selectedIndexProperty().getValue());
        int pageSize = pageSizeSelected(pageSizeSelectData(), value);
        PageInfo<RebatePolicyVO> tablePage = getTablePage(currentPage, pageSize);
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
     * @Date 2022/7/27 15:53
     */
    public void selectAll(ActionEvent actionEvent) {
        checkedIdList.clear();
        ObservableList<RebatePolicyVO> items = tableView.getItems();
        //System.err.println("items=="+items.size()+"---"+items);
        if (mselectAll.isSelected()) {
            for (RebatePolicyVO rp : items) {
                rp.getCb().setSelected(true);
                checkedIdList.add(String.valueOf(rp.getId()));
            }
        } else {
            for (RebatePolicyVO rp : items) {
                rp.getCb().setSelected(false);
            }
            checkedIdList.clear();
        }
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
            final PageInfo<RebatePolicyVO> page = getTablePage(currentPage, pageSize);
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
            final PageInfo<RebatePolicyVO> page = getTablePage(currentPage, pageSize);
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


    private PageInfo<RebatePolicyVO> getTablePage(Integer currentPage, Integer pageSize) {
        String rebateFormName_search = rebateFormNameAssembly_search.getText();
        String name1  = name1Assembly.getText();
        String status = statuAssembly.getValue()=="全部"?"":statuAssembly.getValue();
        return reService.getRebatePolicyPageInfoList(currentPage, pageSize,rebateFormName_search,name1,status);
    }

    public void setTableViewItems(List<RebatePolicyVO> list) {
        tableView.setItems(FXCollections.observableList(list));
        tableView.refresh();
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
                    PageInfo<RebatePolicyVO> pageInfo = getTablePage(pageNum, pageSize);
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


    /**
     * @Author cy
     * @Description 提交审批
     * * @param null
     * @Return
     * @Date 2022/7/27 16:58
     */
    public void submitForApproval(ActionEvent actionEvent) {
        log.info("提交审批");
        ObservableList<RebatePolicyVO> dataLsit = tableView.getItems();
        List<RebatePolicyVO> list = new ArrayList<>();
        int size = dataLsit.size();
        if (size <= 0) {
            CommonUtil._alertInformation("无可选列表，请勾选要操作的数据");
            return;
        }
        for (int i = 0; i < size; i++) {
            RebatePolicyVO s = dataLsit.get(i);
            if (s.getCb().isSelected()) {
                list.add(s);
            }
        }

        if (list.size() <= 0) {
            CommonUtil._alertInformation("请选择要提交审批的返利单");
            return;
        }
        String rs = reService.submitForApproval(list);
        if(rs.indexOf("error")!=-1){
            CommonUtil._alertError("出错了！！！","返利政策提交审批失败"+rs);
            return;
        }
        ToastUtil.customizeToast("操作成功",2000,500.00,300.00);
        loadTable();
    }
}
