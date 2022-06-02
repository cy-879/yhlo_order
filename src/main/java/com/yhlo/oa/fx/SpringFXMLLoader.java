package com.yhlo.oa.fx;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ResourceBundle;

/**
 * <b>ClassName</b>: SpringFXMLLoader <br/>
 *
 * <b>Description</b>: Will load the FXML hierarchy as specified in the load
 * method and register Spring as the FXML Controller Factory. Allows Spring and
 * Java FX to coexist once the Spring Application context has been
 * bootstrapped.<br/>
 *
 * <b>Date</b>: Apr 22, 2019 1:11:58 PM <br/>
 *
 * @author pdai
 * @version Apr 22, 2019
 *
 */
@Component
public class SpringFXMLLoader {
	private final ApplicationContext context;
    private final ResourceBundle resourceBundle;

	@Autowired
	public SpringFXMLLoader(ApplicationContext context, ResourceBundle resourceBundle) {
		this.context = context;
        this.resourceBundle = resourceBundle;
    }

    /**
     * 加载path页面
     * @param fxmlPath
     * @return
     * @throws IOException
     */
	public Parent load(String fxmlPath) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setControllerFactory(context::getBean); // Spring now FXML Controller Factory
		loader.setLocation(getClass().getResource(fxmlPath));
		return loader.load();
	}


    /**
     * 新打开一个页面
     * @param view
     */
    public void showPopWindow(final FxmlView view)  {
        Parent viewRootNodeHierarchy = null;
        try {
            viewRootNodeHierarchy = load(view.fxml());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = prepareScene(viewRootNodeHierarchy);
        Stage primaryStage = new Stage();
        primaryStage.getIcons().add(new Image(String.valueOf(this.getClass().getClassLoader().getResource("img/logo.png"))));
        primaryStage.setTitle(view.title());
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.centerOnScreen();
        try {
            primaryStage.show();
        } catch (Exception exception) {
        }
    }

    private Scene prepareScene(Parent rootnode) {
        Scene scene = new Scene(rootnode);
        scene.setRoot(rootnode);
        return scene;
    }
}
