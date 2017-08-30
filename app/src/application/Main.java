package application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.cache.AppRegister;
import application.cache.AppContext;
import application.cache.Cache;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	public static Stage LOGIN_STAGE;
	public static Stage TABS_STAGE;
	private Logger log = LoggerFactory.getLogger(Main.class);

	@Override
	public void start(Stage primaryStage) {
		Main.TABS_STAGE = primaryStage;
		try {
			Parent root = FXMLLoader.load(getClass().getResource("./view/tabs/TabsScene.fxml"));
			// Scene scene = new Scene(root, 341, 116);
			Scene scene = new Scene(root, 718, 460);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	public static void main(String[] args) {
		initApp();
		initCache();
		launch(args);
	}

	public static void initApp() {
		AppContext.init();
		AppRegister.init();
	}

	public static void initCache() {
		Cache.init();
	}
}
