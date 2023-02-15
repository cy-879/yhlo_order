package com.yhlo.oa.util;


import com.yhlo.oa.entity.GeneralOrderDetailVO;
import com.yhlo.oa.entity.T001wVO;
import com.yhlo.oa.service.PublicDataService;
import com.yhlo.oa.service.iml.PublicDataServiceImpl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author cy
 * @Description 日期明细组件
 * @Return
 * @Date 2022/9/23 10:57
 */

/*public class EditCellDatePicker<S,String> extends TableCell<S, java.lang.String> {*/
public class EditCellDatePicker extends TableCell<GeneralOrderDetailVO, String> {

    private DatePicker datePicker;
    private String ofPattern;//日期格式类型样板串


    public EditCellDatePicker(String pattern) {
        ofPattern = pattern;
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createComboBoxField();
            setText(null);
            datePicker.setEditable(true);
            setGraphic(datePicker);

        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText(getItem());
        setGraphic(null);
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (empty) {
            setText(null);
            setGraphic(null);
        } else {
            if (isEditing()) {
                if (datePicker != null) {
                    datePicker.setValue( LocalDate.parse(getString(), DateTimeFormatter.ofPattern(ofPattern)));
                }
                setText(null);
                setGraphic(datePicker);
            } else {
                setText(getString());
                setGraphic(null);
            }

        }
    }

    private void createComboBoxField() {
        datePicker = new DatePicker();
        datePicker.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);

        //离焦事件
        datePicker.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                //数据改变
                if (oldValue == true && newValue == false) {//表示失去焦点
                    LocalDate value = datePicker.getValue();
                    if("".equals(value) || null == value){
                        commitEdit("");
                        datePicker.setPromptText("该字段为必填项");
                        datePicker.setStyle("-fx-text-box-border: red ; -fx-background-color: red; ");

                    }else{
                        commitEdit(value.toString());
                    }
                }
            }
        });
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }

}