package com.yhlo.oa.controller;

import com.yhlo.oa.fx.FxmlView;
import com.yhlo.oa.fx.SpringFXMLLoader;
import com.yhlo.oa.util.SpringBeanUtil;
import de.felixroske.jfxsupport.FXMLController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        homePage();
    }

    public void homePage() {
        log.info("进入主页");
        updateBody(FxmlView.MAIN);
//        main.setVisible(true);
//        normal.setVisible(false);
//        roles.setVisible(false);
    }

    public void normal() {
        log.info("进入一般订单");
        updateBody(FxmlView.VIEW_NORMAL);
//        main.setVisible(false);
//        normal.setVisible(true);
//        roles.setVisible(false);
    }

    public void roles() {
        log.info("进入多角订单");
        updateBody(FxmlView.VIEW_ROLES);
//        main.setVisible(false);
//        normal.setVisible(false);
//        roles.setVisible(true);
    }

    public void config() {
        log.info("进入系统配置同步");
        updateBody(FxmlView.VIEW_CONFIG);
    }

    public void DataSyn() {
        log.info("进入主数据置同步");
        updateBody(FxmlView.VIEW_DATASYN);
    }

    public void goWebview(ActionEvent actionEvent) {
        log.info("进入网页视图");
        updateBody(FxmlView.VIEW_WEBVIEW);
    }

    public void productData(ActionEvent actionEvent) {
        log.info("进入物料基本信息界面");
        updateBody(FxmlView.VIEW_MATERIAMASTER);
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

    //退出系统
    public void exit(ActionEvent actionEvent) {
        Platform.exit();
    }



}


