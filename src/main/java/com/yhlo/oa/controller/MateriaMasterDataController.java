package com.yhlo.oa.controller;

import com.github.pagehelper.PageInfo;
import com.yhlo.oa.entity.Checkbox;
import com.yhlo.oa.entity.It_MaraVO;
import com.yhlo.oa.entity.KeyList;
import com.yhlo.oa.service.MateriaMasterDataService;
import com.yhlo.oa.service.iml.MateriaMasterDataServiceImpl;
import com.yhlo.oa.util.*;
import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

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
public class MateriaMasterDataController implements Initializable {

    private final static String[] ROWS = {"id", "matnr", "maktx", "mtart","matkl","raube","bismt","spart",
            "prdha","meins","mstae","xchpf","extwg","mbrsh","mhdrz","mhdhb","mtpos_mara","zggxh","zcus01",
            "zcus02","zcus02_1","zcus02_2","zcus03","zcus04","zcus05","zcus06","zcus07","zcus08","zcus09",
            "zcus10","zcus11","zcus12","zcus13","zcus14","zcus15","zcus16","zcus17","zcus18","zcus19",
            "zcus20","zcus21","zcus22","zcus23","zcus24","zcus25","zcus26","zcus27","zcus28","zcus29",
            "zcus30","zcus31","zcus32","zcus33","zcus34","zcus35","create_time"};



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

    // 删除集合
    private List<String> deleteList = new ArrayList<>();

    // 编辑集合
    private List<String> updateList = new ArrayList<>();

    public Set<String> checkedIdList = new HashSet<>();

    Integer currentPage = 1;
    int count = 0;

    List<Button> buttonList = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productDataService = SpringBeanUtil.getBean(MateriaMasterDataServiceImpl.class);


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


           /* // 设置自适应宽度
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            tableView.setStyle(String.format("-fx-font-size: %dpx;", (int) (0.45 * 30)));
            tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            tableView.setEditable(true);*/
        });

        // 单击选中行
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (Objects.nonNull(newValue)) {
                final Checkbox checkbox = newValue.getCb();
                final ObservableList<It_MaraVO> items = tableView.getItems();
              //  System.err.println("items=="+items);
                if (checkbox.isSelected()) {
                    checkbox.setSelected(false);
                    checkedIdList.remove(newValue.getId());
                } else {
                    checkbox.setSelected(true);
                     checkedIdList.add(String.valueOf(newValue.getId()));
                }
            }
        });

        tableView.setRowFactory( tv -> {
            TableRow<It_MaraVO> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
                    It_MaraVO rowData = row.getItem();
                    System.out.println(rowData);

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
        return productDataService.geteMaraPageList(currentPage, pageSize,matnr);
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


    public void searchMaterDetail() {
        // 打开新的场景需要UI更新线程执行
        Platform.runLater(()->{
            Stage saveDiary = StageManager.getStage("searchMaterDetail");
            // 每次创建场景前，判断该场景是否被创建过，创建过直接显示场景即可，无需多次创建，但是需要清除上次输入的数据
            if(Objects.isNull(saveDiary)) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/searchMaterDetail.fxml"));
                    Parent pane = (Parent)fxmlLoader.load();
                    Scene scene = new Scene(pane);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.getScene().getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
                    stage.setTitle("物料明细");
                    stage.resizableProperty().setValue(Boolean.FALSE);//禁用最大化
                    //设置窗口不可拉伸
                    stage.setResizable(false);
                    stage.getIcons().add(CommonUtil.getLogo());
                    stage.show();
                    // 存放Scene
                    StageManager.put("searchMaterDetail", stage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                saveDiary.show();
            }
        });
    }

}
