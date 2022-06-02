package com.yhlo.oa.config;

import com.yhlo.oa.fx.SpringFXMLLoader;
import com.yhlo.oa.util.ExceptionWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.io.StringWriter;
import java.util.ResourceBundle;

/**
* <b>ClassName</b>: AppJavaConfig <br/>
*
* <b>Description</b>: AppJavaConfig <br/>
*
* <b>Date</b>: Apr 22, 2019 1:13:50 PM <br/>
*
* @author pdai
* @version Apr 22, 2019
*
*/
@Configuration
public class AppJavaConfig {

    @Autowired
    SpringFXMLLoader springFXMLLoader;

    /**
     * Useful when dumping stack trace to a string for logging.
     * @return ExceptionWriter contains logging utility methods
     */
    @Bean
    @Scope("prototype")
    public ExceptionWriter exceptionWriter() {
        return new ExceptionWriter(new StringWriter());
    }

    @Bean
    public ResourceBundle resourceBundle() {
        return ResourceBundle.getBundle("Bundle");
    }

//    @Bean
//    @Lazy(value = true) //Stage only created after Spring context bootstap
//    public StageManager stageManager(Stage stage, GNDecorator decorator) throws IOException {
//        return new StageManager(springFXMLLoader, stage, decorator);
//    }

}
