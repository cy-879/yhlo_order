package com.yhlo.oa.util;


import com.alibaba.fastjson.JSONObject;
import com.yhlo.oa.entity.*;
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
 * @Description  通过传参加载 下拉组件
 * * @param null
 * @Return 
 * @Date 2022/8/2 8:53
 */
public class EditCellParmaComboBox<S, String> extends TableCell<S, String> {

    private ComboBox comboBox;
    private String showName;//显示字段
    private String componentName;//组件名称
    private String param;//查询参数

    @Autowired
    private PublicDataService pdService;

    public EditCellParmaComboBox(String pa,String name, String zujmc) {

        pdService = SpringBeanUtil.getBean(PublicDataServiceImpl.class);

        param = pa;
        showName = name;
        componentName = zujmc;
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createComboBoxField();
            setText(null);
            comboBox.setEditable(true);
            setGraphic(comboBox);
            initParmaComboBox(comboBox,showName,param,componentName);
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
                setText((java.lang.String) getItem());
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



    /***
     * 初始化下拉控件
     */
    public void initParmaComboBox(ComboBox cob, String text,String param,String componentName){

        if("订单单位".equals(componentName)){
            List<It_MarmVO> itemList = pdService.queryOrderUnitList((java.lang.String) param,"");

            List<Map> mpList = new ArrayList<Map>();
            if(null != itemList && itemList.size()>0){
                for (It_MarmVO item :itemList ) {
                    Map m = new HashMap<>();
                    m.put("matnr",item.getMatnr());
                    m.put("meinh",item.getMeinh());
                    m.put("umrez",item.getUmrez());
                    m.put("umren",item.getUmren());
                    mpList.add(m);
                }
            }

            cob.setPromptText("请输入单位搜索");
            cob.setPlaceholder(new Label("空值"));
            AutoCompleteComboBoxListener.getComboBox(cob, FXCollections.observableList(mpList), (java.lang.String) text);
        }else if("仓位".equals(componentName)){

            List<T001lVO> itemList = pdService.queryPositionList((java.lang.String) param);

            List<Map> mpList = new ArrayList<Map>();
            if(null != itemList && itemList.size()>0){
                for (T001lVO item :itemList ) {
                    Map m = new HashMap<>();
                    m.put("werks",item.getWerks());
                    m.put("lgort",item.getLgort());
                    m.put("lgobe",item.getLgobe());
                    mpList.add(m);
                }
            }

            cob.setPromptText("请输入仓位搜索");
            cob.setPlaceholder(new Label("空值"));
            AutoCompleteComboBoxListener.getComboBox(cob, FXCollections.observableList(mpList), (java.lang.String) text);
        }else if("多角贸易".equals(componentName)){

            if(null == param || "|".equals(param) && param.toString().length()<=0){
                return;
            }

            java.lang.String kunnr = param.toString().substring(0,param.toString().indexOf("|"));
            java.lang.String vkorg = param.toString().substring(param.toString().indexOf("|")+1,param.toString().length());
            List<It_Ztsd_002VO> itemList = pdService.queryMultiAngleTrade(kunnr, vkorg);

            List<Map> mpList = new ArrayList<Map>();
            if(null != itemList && itemList.size()>0){
                for (It_Ztsd_002VO item :itemList ) {
                    Map m = new HashMap<>();
                    m.put("kunnr",item.getKunnr());
                    m.put("vkorg",item.getVkorg());
                    m.put("knumh",item.getKnumh());
                    m.put("name1",item.getName1());
                    m.put("c_default",item.getC_default());
                    m.put("name2",item.getName2());
                    m.put("datab",item.getDatab());
                    m.put("datbi",item.getDatbi());
                    m.put("loevm_ko",item.getLoevm_ko());
                    mpList.add(m);
                }
            }

            cob.setPromptText("请输入多角贸易代码搜索");
            cob.setPlaceholder(new Label("空值"));
            AutoCompleteComboBoxListener.getComboBox(cob, FXCollections.observableList(mpList), (java.lang.String) text);
        }

    }
}