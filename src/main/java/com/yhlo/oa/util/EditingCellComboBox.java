package com.yhlo.oa.util;


import com.alibaba.fastjson.JSON;
import com.mysql.cj.xdevapi.JsonString;
import com.yhlo.oa.controller.RebatePolicyController;
import com.yhlo.oa.entity.RebatePolicyDetailVO;
import com.yhlo.oa.entity.T023tVO;
import com.yhlo.oa.service.PublicDataService;
import com.yhlo.oa.service.RebatePolicyService;
import com.yhlo.oa.service.iml.PublicDataServiceImpl;
import com.yhlo.oa.service.iml.RebatePolicyServiceImpl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author cy
 * @Description 返利政策《物料组》下拉列表的编辑控件
 * * @param null
 * @Return 
 * @Date 2022/8/2 8:53
 */
public class EditingCellComboBox extends TableCell<RebatePolicyDetailVO, String> {

    private ComboBox comboBox;


    @Autowired
    private PublicDataService pdService;

    public EditingCellComboBox() {
        pdService = SpringBeanUtil.getBean(PublicDataServiceImpl.class);

    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createComboBoxField();
            setText(null);
            comboBox.setEditable(true);
            setGraphic(comboBox);
            initMaterialGroupComboBox(comboBox,"wgbez60");
        }
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();

        setText((String) getItem());
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
                setText(getString());
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
                    RebatePolicyController.mp = m;
                    commitEdit((String) m.get("wgbez60"));
                }else{
                    commitEdit("");
                }
            }
        });
        /*comboBox.focusedProperty().addListener(
                (ObservableValue<? extends Boolean> arg0,
                 Boolean arg1, Boolean arg2) -> {
                    if (!arg2) {
                        if(null != comboBox.getValue()){
                            Map mp = (Map) comboBox.getValue();
                            commitEdit((String) mp.get("wgbez60"));
                        }else{
                            commitEdit("");
                        }

                    }
                });*/
    }

    private String getString() {
        return getItem() == null ? "" : getItem().toString();
    }

    /***
     * 初始化物料组下拉控件
     */
    public void initMaterialGroupComboBox(ComboBox cob,String text){

        List<T023tVO> itemList = pdService.queryMaterialGroupList();

        List<Map> mpList = new ArrayList<Map>();
        if(null != itemList && itemList.size()>0){
            for (T023tVO item :itemList ) {
                Map m = new HashMap<>();
                m.put("spras",item.getSpras());
                m.put("matkl",item.getMatkl());
                m.put("wgbez60",item.getWgbez60());
                mpList.add(m);
            }
        }

        cob.setPromptText("请输入物料组搜索");
        cob.setPlaceholder(new Label("空值"));
        AutoCompleteComboBoxListener.getComboBox(cob, FXCollections.observableList(mpList),text);

    }
}