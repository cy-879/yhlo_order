package com.yhlo.oa.controller;

import com.yhlo.oa.entity.SysUser;
import com.yhlo.oa.service.SysUserService;
import com.yhlo.oa.service.iml.SysUserServiceImpl;
import com.yhlo.oa.util.CommonUtil;
import com.yhlo.oa.util.ResultUtil;
import com.yhlo.oa.util.SpringBeanUtil;
import com.yhlo.oa.util.StringUtils;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @create: 2022-03-31 17:45
 * @description: 登录
 **/
@Slf4j
@Component
public class LoginController implements Initializable {
    public TextField textUserAccount;
    public PasswordField textPassword;
    private SysUserService sysUserService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sysUserService = SpringBeanUtil.getBean(SysUserServiceImpl.class);
    }

    public void login() throws IOException {
        String password = textPassword.getText();
        String userAccount = textUserAccount.getText();

        // 用户名或密码为空 错误
        if (StringUtils.isEmpty(userAccount) || StringUtils.isEmpty(password)) {
            ResultUtil.getWarringResult("用户名或密码为空,请检查！");
            return;
        }

        // 先用于测试
        if ("admin".equals(userAccount) && "admin".equals(password)) {
            skipMain();
            return;
        }

//        UsernamePasswordToken token = new UsernamePasswordToken(userAccount, password, false);
//        Subject subject = SecurityUtils.getSubject();
//        try
//        {
//            subject.login(token);
//
//        }
//        catch (AuthenticationException e)
//        {
//            String msg = "用户或密码错误";
//            if (StringUtils.isNotEmpty(e.getMessage()))
//            {
//                msg = e.getMessage();
//            }
//            ResultUtil.getWarringResult(msg);
//        }

         //查询用户信息
        SysUser user = sysUserService.selectUserByLoginName(userAccount);
        if (null == user) {
            ResultUtil.getWarringResult("用户不存在！");
            return;
        }

        String encryptPassword = encryptPassword(user.getLoginName(),password,user.getSalt());

        if (!encryptPassword.equals(user.getPassword())) {
            ResultUtil.getWarringResult("密码不正确！");
            return;
        }

        skipMain();
    }

    /***
     * 跳转至主页面
     * @throws IOException
     */
    private void skipMain() throws IOException {
        Stage mainStage = new Stage();
        mainStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/views/FrameWork.fxml")), 1000, 600));
        mainStage.getScene().getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        mainStage.getIcons().add(CommonUtil.getLogo());
        mainStage.setTitle("订单系统");
        //mainStage.setResizable(false);
        mainStage.show();

        Window window = textUserAccount.getScene().getWindow();
        if (window instanceof Stage) {
            ((Stage) window).close();
        }
    }

    public String encryptPassword(String loginName, String password, String salt)
    {
        return new Md5Hash(loginName + password + salt).toHex();
    }
}
