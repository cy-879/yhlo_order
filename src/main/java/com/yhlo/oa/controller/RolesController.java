package com.yhlo.oa.controller;

import com.yhlo.oa.entity.OrderVO;
import com.yhlo.oa.entity.RolesOrderVO;
import com.yhlo.oa.service.NormalOrderService;
import com.yhlo.oa.service.RolesOrderService;
import com.yhlo.oa.service.iml.NormalOrderServiceImpl;
import com.yhlo.oa.service.iml.RolesOrderServiceImpl;
import com.yhlo.oa.util.CommonUtil;
import com.yhlo.oa.util.SpringBeanUtil;
import com.yhlo.oa.util.StageManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

@Slf4j
@Component
public class RolesController implements Initializable {


    private final static String[] ROWS = {"orderNo", "status", "createBy", "createTime"};


    @FXML
    private TextField saleOrderNo;

    @FXML
    private TextField consignee;

    @FXML
    private TextField coupon;

    @FXML
    private TextField deliveryParty;

    @FXML
    private TextField freight;

    @FXML
    private TextField priceType;

    @FXML
    private TableView<RolesOrderVO> rolesOrderList;

    @FXML
    private TextField contractOrderNo;

    @FXML
    private TextField salesOrganization;

    @FXML
    private TextField remarks;

    @FXML
    private TextField totalTaxInclusiveAmount;

    private RolesOrderService rolesOrderService;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("进入多角订单查询界面");

        rolesOrderService = SpringBeanUtil.getBean(RolesOrderServiceImpl.class);
    }

    public List<RolesOrderVO> getDataList() {
        return rolesOrderService.queryOrderList();
    }


    public void loadTable(ActionEvent actionEvent) {
        System.err.println("点击了查询");

    }

    public void toAdd(ActionEvent actionEvent) {
        // 打开新的场景需要UI更新线程执行
        Platform.runLater(()->{
            Stage saveDiary = StageManager.getStage("saveRoles");
            // 每次创建场景前，判断该场景是否被创建过，创建过直接显示场景即可，无需多次创建，但是需要清除上次输入的数据
            if(Objects.isNull(saveDiary)) {
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/addRoles.fxml"));
                    Parent pane = (Parent)fxmlLoader.load();
                    Scene scene = new Scene(pane);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.getScene().getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
                    stage.setTitle("添加多角订单数据");
                    stage.resizableProperty().setValue(Boolean.FALSE);//禁用最大化
                    //设置窗口不可拉伸
                    stage.setResizable(false);
                    stage.getIcons().add(CommonUtil.getLogo());
                    stage.show();
                    // 存放Scene
                    StageManager.put("saveRoles", stage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                saveDiary.show();
            }
        });
    }
}
