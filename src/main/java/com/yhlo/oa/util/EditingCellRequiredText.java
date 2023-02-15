package com.yhlo.oa.util;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;

/**
 * @Author cy
 * @Description 明细必填文本
 * @Date 2022/9/1 17:08
 */
public class EditingCellRequiredText<S,String> extends TableCell<S, String> {

    private TextField textField;

    public EditingCellRequiredText() {
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createTextField();
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }
    }

    @Override
    public void cancelEdit() {

        if("".equals(textField.getText()) || null == textField.getText()){
            return;
        }
        super.cancelEdit();
        setText(getItem()==null?"":getItem().toString());
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
                if (textField != null) {
                    textField.setText((java.lang.String) getString());
                }
                setText(null);
                setGraphic(textField);
            } else {
                setText((java.lang.String) getString());
                setGraphic(null);
            }
        }
    }

    private void createTextField() {
        textField = new TextField((java.lang.String) getString());
        textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);

        //离焦事件
        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

                //数据改变
                if (oldValue == true && newValue == false) {//表示失去焦点
                    java.lang.String value = textField.getText();
                    if("".equals(value) || null == value){
                        textField.setPromptText("该字段为必填项");
                        textField.setStyle("-fx-text-box-border: red ;");
                        return;
                    }else{
                        commitEdit((String) value);
                    }
                }
            }
        });
    }

    private String getString() {
        return (String) (getItem() == null ? "" : getItem().toString());
    }
}