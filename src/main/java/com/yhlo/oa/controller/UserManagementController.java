package com.yhlo.oa.controller;

import com.yhlo.oa.entity.*;
import com.yhlo.oa.entity.SysUser;
import com.yhlo.oa.service.SysUserService;
import com.yhlo.oa.service.iml.SysUserServiceImpl;
import com.yhlo.oa.util.*;
import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

/**
 * @author cy
 * @ClassName: 用户管理界面
 * @Description:
 * @date 2022/11/1715:59
 */
@Slf4j
@FXMLController
public class UserManagementController implements Initializable {

    private final static String[] ROWS = {
            "userId", "loginName", "userName", "userType","status"};

    @FXML
    public TableView<SysUser> tableView;
    @FXML
    public BorderPane root;
    @FXML
    public TextField userIdAssembly;
    @FXML
    public TextField userNameAssembly;
    @FXML
    public TextField loginNameAssembly;
    @FXML
    public TextField passwordAssembly;
    @FXML
    public RadioButton enableAssembly;
    @FXML
    public RadioButton disableAssembly;
    @FXML
    public RadioButton businessUsersAssembly;
    @FXML
    public RadioButton financialUsersAssembly;
    @FXML
    public RadioButton systemAdminAssembly;
    @FXML
    public Button addBt;
    @FXML
    public Button updateBt;
    @FXML
    public Button emptyBt;
    @FXML
    public Button searchTableBt;

    @Autowired
    private SysUserService sysUserService;

    private String user_Name;//当前登录人姓名
    private String login_Name;//当前登录人工号


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        log.info("进入用户管理");

        Preferences userPreferences = Preferences.userRoot();
        user_Name = userPreferences.get("userName","");
        login_Name = userPreferences.get("loginName","");

        sysUserService = SpringBeanUtil.getBean(SysUserServiceImpl.class);

        ImageView addImage = new ImageView( CommonUtil.getImage("/img/icon/save.png"));
        addBt.setGraphic(addImage);

        ImageView updateImage = new ImageView( CommonUtil.getImage("/img/icon/edit.png"));
        updateBt.setGraphic(updateImage);

        ImageView emptyImage = new ImageView( CommonUtil.getImage("/img/icon/empty.png"));
        emptyBt.setGraphic(emptyImage);

        ImageView searchTableImage = new ImageView( CommonUtil.getImage("/img/icon/search.png"));
        searchTableBt.setGraphic(searchTableImage);


        JavaFxUtil.setTextFieldRequired(userNameAssembly);
        JavaFxUtil.setTextFieldRequired(loginNameAssembly);
        JavaFxUtil.setTextFieldRequired(passwordAssembly);


        // 单击选中行
        tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (Objects.nonNull(newValue)) {

                String userName = newValue.getUserName();
                String loginName = newValue.getLoginName();
                String userType = newValue.getUserType();
                String userStatus = newValue.getStatus();
                Long userId = newValue.getUserId();
                userNameAssembly.setText(userName);
                loginNameAssembly.setText(loginName);
                userIdAssembly.setText(String.valueOf(userId));

                if("00".equals(userType)){//系统管理员
                    systemAdminAssembly.setSelected(true);
                }else if("01".equals(userType)){//商务用户
                    businessUsersAssembly.setSelected(true);
                }else if("02".equals(userType)){//财务用户
                    financialUsersAssembly.setSelected(true);
                }

                if("0".equals(userStatus)){//正常
                    enableAssembly.setSelected(true);
                }else if("1".equals(userStatus)){//停用
                    disableAssembly.setSelected(true);
                }

            }

        });

    }

    /**
     * @Author cy
     * @Description 添加用户
     * @Return
     * @Date 2022/12/7 10:47
     */
    public void toAddUser(ActionEvent actionEvent) {

        String salt = "yhlo";//盐加密
        String userName = userNameAssembly.getText();
        String loginName = loginNameAssembly.getText();
        String password = passwordAssembly.getText();
        String userStatu = "";
        String userType = "";
        boolean enableBo = enableAssembly.isSelected();
        boolean disableBo = disableAssembly.isSelected();
        boolean businessUsersBo = businessUsersAssembly.isSelected();
        boolean financialUsersBo = financialUsersAssembly.isSelected();
        boolean systemAdminBo = systemAdminAssembly.isSelected();

        if("".equals(userName)){
            userNameAssembly.setPromptText("该字段为必填项");
            userNameAssembly.setStyle("-fx-text-box-border: red ;");
            return;
        }

        if("".equals(loginName)){
            loginNameAssembly.setPromptText("该字段为必填项");
            loginNameAssembly.setStyle("-fx-text-box-border: red ;");
            return;
        }

        if("".equals(password)){
            passwordAssembly.setPromptText("该字段为必填项");
            passwordAssembly.setStyle("-fx-text-box-border: red ;");
            return;
        }

        if(!enableBo && !disableBo){
            CommonUtil._alertInformation("用户状态为必填项，请选择！！！");
            return;
        }

        if(enableBo){
            userStatu = "0";
        }else{
            userStatu = "1";
        }

        if(!businessUsersBo && !financialUsersBo && !systemAdminBo){
            CommonUtil._alertInformation("用户类型为必填项，请选择！！！");
            return;
        }

        if(businessUsersBo){//商务用户
            userType = "01";
        }else if(financialUsersBo){//财务用户
            userType = "02";
        }else if(systemAdminBo){//系统管理员
            userType = "00";
        }else{//默认是商务用户
            userType = "01";
        }

        password = encryptPassword(loginName,password,salt);

        SysUser us = new SysUser();
        us.setLoginName(loginName);
        us.setUserName(userName);
        us.setPassword(password);
        us.setStatus(userStatu);
        us.setUserType(userType);
        us.setSalt(salt);
        us.setCreateBy(user_Name+"|"+login_Name);

        List<SysUser> usList = sysUserService.checkUserExists(loginName);
        if(null != usList && usList.size()>0){
            CommonUtil._alertErrorMessage("出错拉！！！","该工号已经存在");
            return;
        }
        String rs = sysUserService.addYhloUser(us);
        if(rs.indexOf("error")!=-1){
            CommonUtil._alertErrorMessage("出错拉！！！","新增用户失败："+rs);
        }else{
            ToastUtil.customizeToast("新增用户成功",2000,500.00,300.00);
            loadTable();
            userIdAssembly.setText("");
            userNameAssembly.setText("");
            loginNameAssembly.setText("");
            passwordAssembly.setText("");
            enableAssembly.setSelected(false);
            disableAssembly.setSelected(false);
            businessUsersAssembly.setSelected(false);
            financialUsersAssembly.setSelected(false);
            systemAdminAssembly.setSelected(false);
        }


    }


    /**
     * @Author cy
     * @Description 修改用户
     * @Return
     * @Date 2022/12/7 10:47
     */
    public void toEditUser(ActionEvent actionEvent) {
        String salt = "yhlo";//盐加密
        Long userId = Convert.toLong(userIdAssembly.getText());
        String userName = userNameAssembly.getText();
        String loginName = loginNameAssembly.getText();
        String password = passwordAssembly.getText();
        String userStatu = "";
        String userType = "";
        boolean enableBo = enableAssembly.isSelected();
        boolean disableBo = disableAssembly.isSelected();
        boolean businessUsersBo = businessUsersAssembly.isSelected();
        boolean financialUsersBo = financialUsersAssembly.isSelected();
        boolean systemAdminBo = systemAdminAssembly.isSelected();

        if("".equals(userName)){
            userNameAssembly.setPromptText("该字段为必填项");
            userNameAssembly.setStyle("-fx-text-box-border: red ;");
            return;
        }

        if("".equals(loginName)){
            loginNameAssembly.setPromptText("该字段为必填项");
            loginNameAssembly.setStyle("-fx-text-box-border: red ;");
            return;
        }

        if("".equals(password)){
            passwordAssembly.setPromptText("该字段为必填项");
            passwordAssembly.setStyle("-fx-text-box-border: red ;");
            return;
        }

        if(!enableBo && !disableBo){
            CommonUtil._alertInformation("用户状态为必填项，请选择！！！");
            return;
        }

        if(enableBo){
            userStatu = "0";
        }else{
            userStatu = "1";
        }

        if(!businessUsersBo && !financialUsersBo && !systemAdminBo){
            CommonUtil._alertInformation("用户类型为必填项，请选择！！！");
            return;
        }

        if(businessUsersBo){//商务用户
            userType = "01";
        }else if(financialUsersBo){//财务用户
            userType = "02";
        }else if(systemAdminBo){//系统管理员
            userType = "00";
        }else{//默认是商务用户
            userType = "01";
        }

        password = encryptPassword(loginName,password,salt);

        SysUser us = new SysUser();

        us.setLoginName(loginName);
        us.setUserName(userName);
        us.setPassword(password);
        us.setStatus(userStatu);
        us.setUserType(userType);
        us.setSalt(salt);
        us.setUserId(userId);
        us.setUpdateBy(user_Name+"|"+login_Name);

        if(null == userId || "".equals(userId)){
            CommonUtil._alertInformation("请在左边列表种选择要修改的用户！！！");
            return;
        }


        String rs = sysUserService.updateYhloUser(us);
        if(rs.indexOf("error")!=-1){
            CommonUtil._alertErrorMessage("出错拉！！！","修改用户失败："+rs);
        }else{
            ToastUtil.customizeToast("修改用户成功",2000,500.00,300.00);
            loadTable();
            userIdAssembly.setText("");
            userNameAssembly.setText("");
            loginNameAssembly.setText("");
            passwordAssembly.setText("");
            enableAssembly.setSelected(false);
            disableAssembly.setSelected(false);
            businessUsersAssembly.setSelected(false);
            financialUsersAssembly.setSelected(false);
            systemAdminAssembly.setSelected(false);
        }

    }


    /**
     * @Author cy
     * @Description 清空数据
     * @Return
     * @Date 2022/12/14 16:42
     */
    public void toEmpty(ActionEvent actionEvent) {
        userIdAssembly.setText("");
        userNameAssembly.setText("");
        loginNameAssembly.setText("");
        passwordAssembly.setText("");
        enableAssembly.setSelected(false);
        disableAssembly.setSelected(false);
        businessUsersAssembly.setSelected(false);
        financialUsersAssembly.setSelected(false);
        systemAdminAssembly.setSelected(false);
    }

    /**
     * @Author cy
     * @Description 查询列表
     * @Return
     * @Date 2022/12/7 10:48
     */
    public void loadTable() {

        ObservableList<SysUser> items = tableView.getItems();
        items.clear();
        items.addAll(queryUserList());

        ObservableList<TableColumn<SysUser, ?>> columns = tableView.getColumns();
        columns.clear();
        List<KeyList> keyList = DataTypeWrapper.getKeyList(SysUser.class);
        for (int i = 0; i < ROWS.length; i++) {
            String key = ROWS[i];
            Optional<KeyList> keys = keyList.stream().filter(e-> e.getKey().equals(key)).findFirst();
            TableColumn<SysUser, Object> column = new TableColumn();
            column.setText(keys.get().getLabel());
            column.setCellValueFactory(new PropertyValueFactory(key));
            column.setPrefWidth(150);
            columns.add(column);
        }
    }


    /**
     * @Author cy
     * @Description 取用户列表
     * @Return
     * @Date 2022/12/14 14:34
     */
    private List<SysUser>queryUserList(){
        return sysUserService.getUserList();
    }

    /**
     * @Author cy
     * @Description 加密
     * @Return
     * @Date 2022/12/14 9:52
     */
    public String encryptPassword(String loginName, String password, String salt)
    {
        return new Md5Hash(loginName + password + salt).toHex();
    }

}
