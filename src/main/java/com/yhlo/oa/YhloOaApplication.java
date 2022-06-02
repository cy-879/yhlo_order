package com.yhlo.oa;

import com.yhlo.oa.util.CommonUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

import static com.sun.javafx.application.LauncherImpl.launchApplication;


@EnableScheduling //开启定时任务
@MapperScan(basePackages = {"com.yhlo.oa.mapper"})
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}, scanBasePackages = {"com.yhlo.oa"})
public class YhloOaApplication extends Application {

    public static void main(String[] args) {
        // 先加载spring
        SpringApplication.run(YhloOaApplication.class, args);
        // 再启动主页面
        //launch(args);
        launchApplication(YhloOaApplication.class, FxAppPreloader.class, args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.getScene().getStylesheets().add("org/kordamp/bootstrapfx/bootstrapfx.css");
        primaryStage.setTitle("订单管理系统");
        primaryStage.resizableProperty().setValue(Boolean.FALSE);//禁用最大化
        primaryStage.setScene(scene);
        //设置窗口不可拉伸
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(CommonUtil.getLogo());
        primaryStage.show();
    }


    @Override
    public void stop() throws Exception {
        // Platform.exit();
        // springContext.stop();
        System.exit(0);
    }
}
