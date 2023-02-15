package com.yhlo.oa.controller;

import com.yhlo.oa.fx.FxmlView;
import com.yhlo.oa.fx.SpringFXMLLoader;
import com.yhlo.oa.util.CommonUtil;
import com.yhlo.oa.util.SpringBeanUtil;
import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

/**
 * @create: 2022-04-11 11:15
 * @description:
 **/
@Slf4j
@FXMLController
public class FrameWorkController implements Initializable {
    public Pane main;
    public Pane normal;
    public BorderPane roles;
    public ScrollPane body;
    public TabPane tabPane;

    public static Map<String, Tab> openTabs = new HashMap<>();
    private String userName;//当前登录人姓名
    private String loginName;//当前登录人工号
    private String userType;//当前登录人用户类型

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Preferences userPreferences = Preferences.userRoot();
            userName = userPreferences.get("userName","");
            userName = userPreferences.get("loginName","");
            userType = userPreferences.get("userType","");

            homePage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void homePage() throws Exception {
        log.info("进入主页");
        //updateBody(FxmlView.MAIN);
        addTab(FxmlView.MAIN,"主页");
    }

    public void generalOrder() throws Exception {
        log.info("一般订单创建");
        addTab(FxmlView.VIEW_GENERALORDER,"一般订单创建");
    }

    public void generalOrderApproval() throws Exception {
        log.info("进入一般订单审核");
        addTab(FxmlView.VIEW_GENERALORDERAPPROVAL,"一般订单审核");
    }

    public void roles() throws Exception {
        log.info("进入多角订单");
        addTab(FxmlView.VIEW_ROLES,"多角订单");
    }

    public void config() throws Exception {
        log.info("进入系统配置同步");
        if(userName.equals("系统管理员") || userType.equals("00") ){
            addTab(FxmlView.VIEW_CONFIG,"系统配置同步");
        }else{
            addTab(FxmlView.VIEW_UNAUTH,"出错了！！！");
        }
    }

    public void DataSyn() throws Exception {
        log.info("进入主数据置同步");
        if(userName.equals("系统管理员") || userType.equals("00") ){
            addTab(FxmlView.VIEW_DATASYN,"主数据置同步");
        }else{
            addTab(FxmlView.VIEW_UNAUTH,"出错了！！！");
        }
    }

    public void goWebview(ActionEvent actionEvent) throws Exception {
        log.info("进入网页视图");
        addTab(FxmlView.VIEW_WEBVIEW,"网页视图");
    }

    public void productData(ActionEvent actionEvent) throws Exception {
        log.info("进入物料基本信息界面");
        addTab(FxmlView.VIEW_MATERIAMASTER,"物料主数据");
    }

    public void customerData(ActionEvent actionEvent) throws Exception {
        log.info("进入客户基本信息界面");
        addTab(FxmlView.VIEW_CUSTOMERDATA,"客户主数据");
    }

    public void rebatePolicy(ActionEvent actionEvent) throws Exception {
        log.info("进入返利政策界面");
        addTab(FxmlView.VIEW_REBATEPOLICY,"返利政策");
    }

    public void rebatePolicySearch(ActionEvent actionEvent) throws Exception {
        log.info("进入返利政策列表界面");
        addTab(FxmlView.VIEW_REBATEPOLICYSEARCH,"返利政策列表");
    }

    public void rebatePolicyApproval(ActionEvent actionEvent) throws Exception {
        log.info("进入返利政策待审批列表界面");
        addTab(FxmlView.VIEW_REBATEPOLICYAPPROVAL,"返利政策待审批列表");
    }

    public void userManagement(ActionEvent actionEvent) throws Exception{
        log.info("进入用户管理界面");
        if(userName.equals("系统管理员") || userType.equals("00") ){
            addTab(FxmlView.VIEW_USERMANAGEMENT,"用户管理");
        }else{
            addTab(FxmlView.VIEW_UNAUTH,"出错了！！！");
        }

    }



    private void updateBody(FxmlView view) {
        try {
           // Parent parent = SpringBeanUtil.getBean(SpringFXMLLoader.class).load(view.fxml());
            Parent parent = FXMLLoader.load(getClass().getResource(view.fxml()));
            body.setContent(parent);
        } catch (IOException e) {
            log.error(String.valueOf(e));
        }
    }




    /**
     * @Author cy
     * @Description 添加Tab页
     *  @param view 视图地址
     *  @param tabName tab 名称
     * @Date 2022/6/17 9:12
     */
    public void addTab(FxmlView view,String tabName) throws Exception {

        try {
            if(openTabs.containsKey(view.fxml()) && view.fxml().equals("/views/unauth.fxml")){//当选择的tab已经打开过，就定位到已打开的，不打开新的tab
                tabPane.getSelectionModel().select(openTabs.get(view.fxml()));
            }else{
                Parent root = FXMLLoader.load(getClass().getResource(view.fxml()));
                Tab tab = new Tab(tabName,root);
                tabPane.getTabs().add(tab);
                openTabs.put(view.fxml(), tab);
                tabPane.getSelectionModel().select(openTabs.get(view.fxml()));
                if("主页".equals(tabName)){
                    tab.setClosable(false);//不允许关闭首页
                }
                tab.setOnClosed(e -> openTabs.remove(view.fxml()));
            }
        } catch (IOException e) {
            log.error(String.valueOf(e));
        }
    }



    /**
     * @Author cy
     * @Description 复制窗口
     * * @param null
     * @Return
     * @Date 2022/7/12 16:01
     */
    public void OpenNewWindow(ActionEvent actionEvent) throws IOException {
        Stage mainStage = new Stage();
        mainStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/views/FrameWork.fxml")), 1000, 600));
        mainStage.getScene().getStylesheets().add("/css/bootstrapfx.css");
        mainStage.getIcons().add(CommonUtil.getLogo());
        mainStage.setTitle("订单系统");
        //mainStage.setResizable(false);
        mainStage.show();
    }

    //退出系统
    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }



}


