package com.yhlo.oa.util;


import com.yhlo.oa.entity.RebatePolicyDetailVO;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;

/**
 * EditingCell
 *
 * @author xiongchao
 * @date 2019/8/12 9:47
 */
public class EditingCell<S,String> extends TableCell<S, String> {

    private TextField textField;

    public EditingCell() {
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
        super.cancelEdit();

        setText((java.lang.String) getItem());
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
        textField.focusedProperty().addListener(
                (ObservableValue<? extends Boolean> arg0,
                 Boolean arg1, Boolean arg2) -> {
                    if (!arg2) {
                        commitEdit((String) textField.getText());
                    }
                });
    }

    private String getString() {
        return (String) (getItem() == null ? "" : getItem().toString());
    }
}