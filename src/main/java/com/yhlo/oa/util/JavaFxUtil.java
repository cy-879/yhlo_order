package com.yhlo.oa.util;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/**
 * @author cy
 * @ClassName: JavaFxUtil
 * @Description:
 * @date 2022/7/1917:32
 */
public class JavaFxUtil {

    /**
     * @Author cy
     * @Description 限定文本只能填写小数2位
     * * @param null
     * @Return
     * @Date 2022/7/19 17:29
     */
    public static void getNumberText(Pattern decimalPattern, TextField textField) {
        UnaryOperator<TextFormatter.Change> filter = c -> {
            if (decimalPattern.matcher(c.getControlNewText()).matches()) {
                return c;
            } else {
                return null ;
            }
        };

        TextFormatter<Double> formatter = new TextFormatter<>(filter);
        textField.setTextFormatter(formatter);
    }

    /**
     * @Author cy
     * @Description 设置自定格式的日期选择框
     * * @param null
     * @Return
     * @Date 2022/7/19 17:50
     */
    public static void setFormatDateField(String pattern, DatePicker dateField) {
        StringConverter converter = new StringConverter<LocalDate>() {//创建一个字符串格式转换器
            DateTimeFormatter dateFormatter =
                    DateTimeFormatter.ofPattern(pattern);//根据字符串样板样例模式创建一个日期格式化器
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return dateFormatter.format(date);//利用格式化足迹将日期格式化成一个字符串
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, dateFormatter);//利用格式化足迹将一个日期字符串格式化成一个本地日期
                } else {
                    return null;
                }
            }
        };

        dateField.setConverter(converter);//给日期选择控件绑定一个格式转换器组件
    }



    /**
     * @Author cy
     * @Description 设置文本框必填
     * * @param null
     * @Return
     * @Date 2022/7/22 14:07
     */
    public static void setTextFieldRequired(TextField textField) {

        //离焦事件
        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                //数据改变
                if (oldValue == true && newValue == false) {//表示失去焦点
                    String value = textField.getText();
                    if("".equals(value)){
                        textField.setPromptText("该字段为必填项");
                        textField.setStyle("-fx-text-box-border: red ;");
                    }else{
                        textField.setStyle("");
                    }
                }
            }
        });
    }

    /**
     * @Author cy
     * @Description 设置日期选择框必填
     * * @param null
     * @Return
     * @Date 2022/7/22 16:57
     */
    public static void setDatePickerFieldRequired(DatePicker field) {

        //离焦事件
        field.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                //数据改变
                if (oldValue == true && newValue == false) {//表示失去焦点
                    LocalDate value = field.getValue();
                    if("".equals(value) || null == value){
                        field.setPromptText("该字段为必填项");
                        field.setStyle("-fx-text-box-border: red ; -fx-background-color: red; ");
                    }else{
                        field.setStyle("");
                    }
                }
            }
        });
    }


    /**
     * @Author cy
     * @Description 设置下拉列表必填
     * * @param null
     * @Return
     * @Date 2022/7/25 14:37
     */
    public static void setComboBoxFieldRequired(ComboBox field) {

        //离焦事件
        field.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                //数据改变
                if (oldValue == true && newValue == false) {//表示失去焦点
                    Object value = field.getValue();
                    if("".equals(value) || null == value){
                        field.setPromptText("该字段为必填项");
                        field.setStyle("-fx-border-color: red;");
                    }else{
                        field.setStyle("");
                    }
                }
            }
        });
    }
}
