package com.yhlo.oa.util;


import com.alibaba.fastjson.JSONObject;
import com.yhlo.oa.controller.GeneralOrderController;
import com.yhlo.oa.entity.GeneralOrderDetailVO;
import com.yhlo.oa.entity.It_MaraVO;
import com.yhlo.oa.service.PublicDataService;
import com.yhlo.oa.service.iml.PublicDataServiceImpl;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author cy
 * @Description  物料下拉组件
 * * @param null
 * @Return 
 * @Date 2022/8/2 8:53
 */
public class EditCellMaterialComboBox extends TableCell<GeneralOrderDetailVO, String> {

    private ComboBox comboBox;
    private String vkorg;//销售组织

    @Autowired
    private PublicDataService pdService;

    public EditCellMaterialComboBox(String xiaoszz) {
        pdService = SpringBeanUtil.getBean(PublicDataServiceImpl.class);
        vkorg = xiaoszz;
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createComboBoxField();
            setText(null);
            comboBox.setEditable(true);
            setGraphic(comboBox);
            initMaterialComboBox(comboBox,"maktx");
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
                    JSONObject json = new JSONObject(m);
                    commitEdit(json.toString());
                }else{
                    commitEdit(null);
                }
            }
        });

         /* comboBox.focusedProperty().addListener(
                (ObservableValue<? extends Boolean> arg0,
                 Boolean arg1, Boolean arg2) -> {
                    if (!arg2) {
                        if(null != comboBox.getValue()){
                            Map mp = (Map) comboBox.getValue();
                            System.err.println("factMap=="+mp);
                            GeneralOrderController.factMap = mp;
                            commitEdit((String) mp.get("name1"));
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
     * 初始化物料下拉控件
     */
    public void initMaterialComboBox(ComboBox cob, String text){
        List<It_MaraVO> itemList = pdService.queryMaterialList("",vkorg);

        List<Map> mpList = new ArrayList<Map>();
        if(null != itemList && itemList.size()>0){
            for (It_MaraVO item :itemList ) {
                Map m = new HashMap<>();
                m.put("matnr",item.getMatnr());
                m.put("maktx",item.getMaktx());
                m.put("mtart",item.getMtart());
                m.put("matkl",item.getMatkl());
                m.put("raube",item.getRaube());
                m.put("bismt",item.getBismt());
                m.put("spart",item.getSpart());
                m.put("prdha",item.getPrdha());
                m.put("meins",item.getMeins());
                m.put("zggxh",item.getZggxh());
                m.put("zcus01",item.getZcus01());
                m.put("zcus11",item.getZcus11());
                m.put("zcus12",item.getZcus12());
                mpList.add(m);
            }
        }

        cob.setPromptText("请输入物料搜索");
        cob.setPlaceholder(new Label("空值"));
        AutoCompleteComboBoxListener.getComboBox(cob, FXCollections.observableList(mpList),text);

    }
}