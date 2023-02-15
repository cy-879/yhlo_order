package com.yhlo.oa.util;


import com.alibaba.fastjson.JSONObject;
import com.yhlo.oa.controller.ShowMaterialDetailsModelController;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;

/**
 * @Author cy
 * @Description  物料下拉组件
 * * @param null
 * @Return 
 * @Date 2022/8/2 8:53
 */
public class CreateOrderDetailComboBoxModel<S,String> extends TableCell<S, String> {

    private ComboBox comboBox;
    private String modelType;//模型类别（控件名称）


    public CreateOrderDetailComboBoxModel(String type) {
        modelType = type;
    }

    @Override
    public void startEdit() {
        if (!isEmpty()) {
            super.startEdit();
            createComboBoxField();
            setText(null);
            comboBox.setEditable(true);
            setGraphic(comboBox);
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

    public void searchModelDataDetail(String param) {

        if("物料".equals(modelType)){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/ShowMaterialDetailsModel.fxml"));
                Parent pane = (Parent)fxmlLoader.load();
                Scene scene = new Scene(pane);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.getScene().getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
                stage.setTitle("数据详情");
                stage.resizableProperty().setValue(Boolean.FALSE);//禁用最大化
                //设置窗口不可拉伸
                stage.setResizable(false);
                stage.getIcons().add(CommonUtil.getLogo());
                ShowMaterialDetailsModelController controller = fxmlLoader.getController();
                controller.setMainController(this);//传输父对象至子对象中
                controller.getDataListModel((java.lang.String) param);
                stage.initModality(Modality.APPLICATION_MODAL);//遮盖窗口
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    private void createComboBoxField() {
        comboBox = new ComboBox();
        comboBox.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);

        comboBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                searchModelDataDetail((String) comboBox.getEditor().getText());
            }
        });
    }

    private String getString() {
        return (String) (getItem() == null ? "" : getItem().toString());
    }


    //为父对象字段赋值
    public void setComboBoxValue(Map mp){
        if(null == mp){
            commitEdit(null);
            return;
        }
        JSONObject json = new JSONObject(mp);
        commitEdit((String) json.toString());
    }


}