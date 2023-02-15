package com.yhlo.oa.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;


/**
 * @create: 2022-04-11 15:05
 * @description: 公共类
 **/
public class CommonUtil {

    public static Image getLogo() {
        return new Image("/img/logo.png");
    }

    /**
     * 获取图片
     * @param url 图片路径
     * @return
     */
    public static Image getImage(String url){
        return new Image(url);
    }

    /***
     *错误弹出框
     * @param headerText 头信息
     * @param content 提示内容
     */
    public static void _alertError(String headerText,String content){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("温馨提示");
        alert.setHeaderText(headerText);
        alert.setContentText(content);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(CommonUtil.getLogo());
        alert.showAndWait();
    }


    /***
     *消息提醒弹出框
     * @param content 提示内容
     */
    public static void _alertInformation(String content){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("温馨提示");
        alert.setHeaderText("");
        alert.setContentText(content);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(CommonUtil.getLogo());
        alert.showAndWait();
    }


    /**
     * @Author cy
     * @Description 提示语句
     * @param headerText 提示语句
     * @param errorContent 错误信息
     * @Return
     * @Date 2022/10/21 16:40
     */
    public static void _alertErrorMessage(String headerText,String errorContent){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("异常提示");
        alert.setContentText(headerText);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(CommonUtil.getLogo());

        StringWriter sw = new StringWriter();
        sw.append(errorContent);

        PrintWriter pw = new PrintWriter(sw);
        String exceptionText = sw.toString();
        Label label = new Label("异常信息：");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);//不能编辑
        textArea.setWrapText(true);//换行
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // 在对话框窗格中设置可扩展异常。
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }


    /**
     *异常提示弹出框
     * @param content 提示语句
     * @param ex 异常
     */
    public static void _alertException(String content, Exception ex){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("异常提示");
        alert.setContentText(content);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(CommonUtil.getLogo());

        //Exception ex = new FileNotFoundException("Could not find file blabla.txt");

        // 创建可扩展异常
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();

        Label label = new Label("异常信息：");

        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        // 在对话框窗格中设置可扩展异常。
        alert.getDialogPane().setExpandableContent(expContent);

        alert.showAndWait();
    }


    /**
     * 确定框
     * @return
     */
    public static String _alertConfirm(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("温馨提示");
        alert.setHeaderText("");
        alert.setContentText("你确定要继续执行该操作吗？");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(CommonUtil.getLogo());

        String rs = "";
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            rs  = "Ok";
        } else {
             rs  = "close";
        }
        return rs;
    }

}
