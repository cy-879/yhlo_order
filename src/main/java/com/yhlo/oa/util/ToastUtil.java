package com.yhlo.oa.util;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Timer;
import java.util.TimerTask;

/*
 *@author unclezs.com
 *@date 2019.07.06 12:46
 */
public class ToastUtil {
    private static Stage stage = new Stage();
    private static Label label = new Label();
    static {
        stage.initStyle(StageStyle.TRANSPARENT);// 舞台透明
    }

    // 默认3秒
    public static void toast(String msg) {
        toast(msg, 3000);
    }

    /**
     * 指定时间消失
     *
     * @param msg
     * @param time
     */
    public static void toast(String msg, int time) {
        label.setText(msg);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> stage.close());
            }
        };
        init(msg);
        Timer timer = new Timer();
        timer.schedule(task, time);
        stage.show();
    }

    // 设置消息
    private static void init(String msg) {
        Label label = new Label(msg);// 默认信息
        label.setStyle("-fx-background: rgba(56,56,56,0.7);-fx-border-radius: 15;-fx-background-radius: 15");// label透明,圆角
        label.setTextFill(Color.rgb(225, 255, 226));// 消息字体颜色
        label.setPrefHeight(50);
        label.setPadding(new Insets(15));
        label.setAlignment(Pos.CENTER);// 居中
        label.setFont(new Font(16));// 字体大小
        Scene scene = new Scene(label);
        scene.setFill(null);// 场景透明
        stage.setScene(scene);
    }

    /**
     * @Author cy
     * @Description    // 指定时间消失
     * * @param null
     * @Return
     * @Date 2022/7/20 11:38
     */
    public static void customizeToast(String msg, int time,Double x,Double y) {
        label.setText(msg);
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> stage.close());
            }
        };
        customizeInit(msg,x,y);
        Timer timer = new Timer();
        timer.schedule(task, time);
        stage.show();
    }

    /**
     * @Author cy
     * @Description 设置指定位置的消息显示
     * * @param null
     * @Return 
     * @Date 2022/7/20 11:39
     */
    private static void customizeInit(String msg ,Double x,Double y) {

        Label label = new Label(msg);// 默认信息
        label.setStyle("-fx-background: rgb(102,95,95);-fx-border-radius: 15;-fx-background-radius: 15");// label透明,圆角
        label.setTextFill(Color.rgb(225, 255, 226));// 消息字体颜色
        label.setPrefHeight(30);
        label.setPadding(new Insets(15));
        label.setAlignment(Pos.CENTER);// 居中
        label.setFont(new Font(15));// 字体大小
        Scene scene = new Scene(label);
        scene.setFill(null);// 场景透明
        stage.setX(x);
        stage.setY(y);
        stage.setScene(scene);
    }
}

