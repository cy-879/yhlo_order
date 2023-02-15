package com.yhlo.oa.util;

import com.yhlo.oa.entity.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cy
 * @ClassName: 下拉搜索框
 * @Description:
 * @date 2022/5/613:48
 */
public class AutoCompleteComboBoxListener<T> implements EventHandler<KeyEvent> {
    private ComboBox comboBox;
    private StringBuilder sb;
    private ObservableList<T> data;
    private boolean moveCaretToPos = false;
    private int caretPos;

    public AutoCompleteComboBoxListener(ComboBox comboBox) {
        this.comboBox = comboBox;
        sb = new StringBuilder();
        data = comboBox.getItems();

        this.comboBox.setEditable(true);
        this.comboBox.setOnKeyPressed(new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent t) {
                comboBox.hide();
            }
        });


        this.comboBox.setOnKeyReleased(AutoCompleteComboBoxListener.this);
    }

    @Override
    public void handle(KeyEvent event) {

        if (event.getCode() == KeyCode.UP) {
            caretPos = -1;
            moveCaret(comboBox.getEditor().getText().length());
            return;
        } else if (event.getCode() == KeyCode.DOWN) {
            if (!comboBox.isShowing()) {
                comboBox.show();
            }
            caretPos = -1;
            moveCaret(comboBox.getEditor().getText().length());
            return;
        } else if (event.getCode() == KeyCode.BACK_SPACE) {
            moveCaretToPos = true;
            caretPos = comboBox.getEditor().getCaretPosition();
        } else if (event.getCode() == KeyCode.DELETE) {
            moveCaretToPos = true;
            caretPos = comboBox.getEditor().getCaretPosition();
        }

        if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
                || event.isControlDown() || event.getCode() == KeyCode.HOME
                || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
            return;
        }

        ObservableList list = FXCollections.observableArrayList();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).toString().toUpperCase().contains(
                    AutoCompleteComboBoxListener.this.comboBox
                            .getEditor().getText().toUpperCase())) {
                list.add(data.get(i));
            }
        }
        String t = comboBox.getEditor().getText();

        comboBox.setItems(list);

        comboBox.getEditor().setText(t);

        if (!moveCaretToPos) {
            caretPos = -1;
        }
        moveCaret(t.length());
        if (!list.isEmpty()) {
            comboBox.show();
        }
    }

    private void moveCaret(int textLength) {
        if (caretPos == -1) {
            comboBox.getEditor().positionCaret(textLength);
        } else {
            comboBox.getEditor().positionCaret(caretPos);
        }
        moveCaretToPos = false;
    }

    /**
     * 查询下拉框
     * @param comboBox 组件
     * @param list 数据
     * @param showName 要显示的名字
     */
    public static void getComboBox(ComboBox comboBox, ObservableList<Map> list, String showName){


        comboBox.getItems().addAll(list);
        comboBox.converterProperty().set(new StringConverter<Map>() {
            Map temp = new HashMap();
            @Override
            public String toString(Map object) {
                if(null != object){
                    temp = object;
                    return String.valueOf(object.get(showName));
                }
              return null;
            }
            @Override
            public Map fromString(String string) {
                if("".equals(string) || !string.equals(temp.get(showName))){
                    return null;
                }
                return temp;
            }
        });
        new AutoCompleteComboBoxListener<>(comboBox);
    }


}
