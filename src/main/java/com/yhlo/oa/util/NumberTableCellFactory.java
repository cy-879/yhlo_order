package com.yhlo.oa.util;

import javafx.beans.NamedArg;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

/**
 * @author cy
 * @ClassName: 明细表序号工具
 * @Description:
 * @date 2022/8/3110:22
 */
public class NumberTableCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

    private final int startNumber;

    public NumberTableCellFactory(@NamedArg("startNumber") int startNumber) {
        this.startNumber = startNumber;
    }

    public NumberTableCellFactory() {
        this(1);
    }

    public static class NumberTableCell<S, T> extends TableCell<S, T> {

        private final int startNumber;

        public NumberTableCell(int startNumber) {
            this.startNumber = startNumber;
        }



        @Override
        public void updateItem(T item, boolean empty) {
            super.updateItem(item, empty);

            this.setText(null);
            this.setGraphic(null);

            if (!empty) {
                int rowIndex = this.getIndex() + 10;
                setText(String.valueOf(rowIndex));
            }
        }

    }

    @Override
    public TableCell<S, T> call(TableColumn<S, T> param) {
        return new NumberTableCell<>(startNumber);
    }

    public static <T> TableColumn<T, Void> createNumberColumn(TableColumn column, int startNumber) {
        //TableColumn<T, Void> column = new TableColumn<>(text);
        column.setSortable(false);
        column.setEditable(false);
        column.setCellFactory(new NumberTableCellFactory<>(startNumber));
        return column;
    }
}
