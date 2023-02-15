package com.yhlo.oa.controller;

import com.yhlo.oa.entity.*;
import com.yhlo.oa.service.SearchMaterDetailService;
import com.yhlo.oa.service.iml.SearchMaterDetailServiceImpl;
import com.yhlo.oa.util.DataTypeWrapper;
import com.yhlo.oa.util.SpringBeanUtil;
import de.felixroske.jfxsupport.FXMLController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author cy
 * @ClassName: SearchMaterDataController
 * @Description:
 * @date 2022/6/218:11
 */

@Slf4j
@FXMLController
public class SearchMaterDetailController implements Initializable {


    private final static String[] saleROWS = {"id", "matnr", "vkorg", "vtweg","vrkme","mtpos","dwerk","ktgrm",
            "lvorm","vmsta","taxm1"};

    private final static String[] costROWS = {"id", "matnr", "bwkey", "bklas","lvorm","vprsv","verpr","stprs",
            "peinh"};

    private final static String[] unitROWS = {"id", "matnr", "meinh", "umrez","umren"};

    private final static String[] stockROWS = {"id", "matnr", "werks", "lgort","lvorm","labst"};

    private final static String[] factoryROWS = {"id", "matnr", "werks", "lvorm","xchpf","xchar","ladgr","sernp"};

    @Autowired
    private SearchMaterDetailService scService;

    @FXML
    public CheckBox shifjy;
    @FXML
    public Button closeButton;
    @FXML
    public TextField matnrTextField;
    @FXML
    public TextField bismtTextField;
    @FXML
    public TextField mtartTextField;
    @FXML
    public TextField maktxTextField;
    @FXML
    public TextField zcus04TextField;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        scService = SpringBeanUtil.getBean(SearchMaterDetailServiceImpl.class);

        tabPane.getSelectionModel().select(saleTab);//默认进去就显示saleTab

        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {//监听点击的哪个
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
               // System.out.println("tableViewName=="+newValue.getText());
                if("销售".equals(newValue.getText())){
                    setSaleTable();
                }else if("成本".equals(newValue.getText())){
                    setCostTable();
                }else if("单位".equals(newValue.getText())){
                    setUnitTable();
                }else if("库存".equals(newValue.getText())){
                    setStockTable();
                }else if("工厂".equals(newValue.getText())){
                    setFactoryTable();
                }
            }
        });

    }



    //设置主表信息
    public void setMaterData(It_MaraVO mdata){

        matnrTextField.setText(mdata.getMatnr());
        bismtTextField.setText(mdata.getBismt());
        mtartTextField.setText(mdata.getMtart());
        maktxTextField.setText(mdata.getMaktx());
        zcus04TextField.setText(mdata.getZcus04());

    }


   /**
    * @Author cy
    * @Description 获取销售物料视图数据
    * @Return
    * @Date 2022/6/8 11:27
    */
    private List<It_MvkeVO> queryMvkeList() {
        String matnr = matnrTextField.getText();
        return scService.queryMvkeList(matnr);
    }

   /**
    * @Author cy
    * @Description 获取物料评估类型视图数据
    * @Return
    * @Date 2022/6/8 11:27
    */
    private List<It_MbewVO> queryMbewList() {
        String matnr = matnrTextField.getText();
        return scService.queryMbewList(matnr);
    }

  /**
   * @Author cy
   * @Description 获取物料计量单位视图数据
   * * @param null
   * @Return
   * @Date 2022/6/8 16:59
   */
    private List<It_MarmVO> queryMarmList() {
        String matnr = matnrTextField.getText();
        return scService.queryMarmList(matnr);
    }

  /**
   * @Author cy
   * @Description 获取物料库存相关视图数据
   * * @param null
   * @Return
   * @Date 2022/6/8 16:59
   */
    private List<It_MardVO> queryMardList() {
        String matnr = matnrTextField.getText();
        return scService.queryMardList(matnr);
    }
    /**
     * @Author cy
     * @Description 获取物料工厂视图数据
     * * @param null
     * @Return
     * @Date 2022/6/8 17:54
     */
    private List<It_MarcVO> queryMarcList() {
        String matnr = matnrTextField.getText();
        return scService.queryMarcList(matnr);
    }

    /**
     * @Author cy
     * @Description 设置销售页签表格
     * * @param null
     * @Return
     * @Date 2022/6/8 11:40
     */
    public void setSaleTable(){
        ObservableList<It_MvkeVO> items = saleTableView.getItems();
        items.clear();
        items.addAll(queryMvkeList());

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
    public void setCostTable(){
        ObservableList<It_MbewVO> items = costTableView.getItems();
        items.clear();
        items.addAll(queryMbewList());

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
    public void setUnitTable(){
        ObservableList<It_MarmVO> items = unitTableView.getItems();
        items.clear();
        items.addAll(queryMarmList());

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
    public void setStockTable(){
        ObservableList<It_MardVO> items = stockTableView.getItems();
        items.clear();
        items.addAll(queryMardList());

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
    public void setFactoryTable(){
        ObservableList<It_MarcVO> items = factoryTableView.getItems();
        items.clear();
        items.addAll(queryMarcList());

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


    //关闭窗口
    public void closeView(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }


    @FXML
    public void searchTable(ActionEvent actionEvent) {
        String tabName = tabPane.getSelectionModel().getSelectedItem().getText();//获取页签名称
        if("销售".equals(tabName)){
            setSaleTable();
        }else if("成本".equals(tabName)){
            setCostTable();
        }else if("单位".equals(tabName)){
            setUnitTable();
        }else if("库存".equals(tabName)){
            setStockTable();
        }else if("工厂".equals(tabName)){
            setFactoryTable();
        }else{

        }
    }
}
