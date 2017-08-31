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
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
	public static Stage LOGIN_STAGE;
	public static Stage TABS_STAGE;
	public static Stage SEARCH_STAGE;
	public static Stage UPDATE_STAGE;
	private static Logger log = LoggerFactory.getLogger(Main.class);

	@Override
	public void start(Stage primaryStage) {
		Main.TABS_STAGE = primaryStage;
		try {
			Parent root = FXMLLoader.load(getClass().getResource("./view/tabs/TabsScene.fxml"));
			// Scene scene = new Scene(root, 341, 116);
			Scene scene = new Scene(root, 718, 460);
			scene.getStylesheets().add(getClass().getResource("./application.css").toExternalForm());
			primaryStage.getIcons().add(new Image("/application/util/res/info.png"));
			primaryStage.setTitle("±¦ºùÂ«x 1.0");
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
		log.info("System config load OK! show Stage!");
		launch(args);
	}

	/**
	 * ÈÝÆ÷³õÊ¼»¯
	 */
	public static void initApp() {
		AppContext.init();
		AppRegister.init();
	}

	/**
	 * »º´æ³õÊ¼»¯
	 */
	public static void initCache() {
		Cache.init();
	}
}
