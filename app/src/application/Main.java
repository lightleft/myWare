package application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		Main.LOGIN_STAGE = primaryStage;
		try {
			Parent root = FXMLLoader.load(getClass().getResource("./view/login/LoginScene.fxml"));
			Scene scene = new Scene(root, 341, 116);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			log.error(e.getMessage());
		}

	}

	public static void main(String[] args) {
		launch(args);
	}

}
