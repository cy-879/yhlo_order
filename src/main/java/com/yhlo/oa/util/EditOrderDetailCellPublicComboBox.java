package com.yhlo.oa.util;


import com.alibaba.fastjson.JSONObject;
import com.yhlo.oa.controller.GeneralOrderController;
import com.yhlo.oa.controller.GeneralOrderUpdateController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;

import java.util.Map;

/**
 * @Author cy
 * @Description 订单明细各下拉组件编辑设置
 * * @param null
 * @Return 
 * @Date 2022/8/2 8:53
 */
public class EditOrderDetailCellPublicComboBox<S,String> extends TableCell<S, String> {

    private ComboBox comboBox;
    private String showName;//显示字段
    private String componentName;//组件名称
    private String vkorg;//销售组织
    private String operationPageType;//订单操作页面类型（订单新增页面/修改页面）


    public EditOrderDetailCellPublicComboBox(String title,String zujmc,String xiaoszz,String pageType) {
        showName = title;
        componentName = zujmc;
        vkorg = xiaoszz;
        operationPageType = pageType;
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createComboBoxField();
            setText(null);
            comboBox.setEditable(true);
            setGraphic(comboBox);
            if("addPage".equals(operationPageType)){//新增页面
                GeneralOrderController.initOrderDetailComboBox(comboBox, (java.lang.String) showName,(java.lang.String) componentName,(java.lang.String)vkorg);
            }else if("updatePage".equals(operationPageType)){//订单修改页面
                GeneralOrderUpdateController.initOrderDetailComboBox(comboBox, (java.lang.String) showName,(java.lang.String) componentName,(java.lang.String)vkorg);
            }

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
                if (comboBox != null) {
                    comboBox.setValue(getString());
                }
                setText(null);
                setGraphic(comboBox);
            } else {
                setText((java.lang.String) getString());
                setGraphic(null);
            }

        }
    }

    private void createComboBoxField() {
        comboBox = new ComboBox();
        comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
        comboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                if(null != newValue) {
                    Map m = (Map) newValue;
                    JSONObject json = new JSONObject(m);
                    commitEdit((String) json.toString());
                }else{
                    commitEdit(null);
                }
            }
        });

    }

    private String getString() {
        return (String) (getItem() == null ? "" : getItem().toString());
    }


}