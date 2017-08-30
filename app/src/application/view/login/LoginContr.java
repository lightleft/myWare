package application.view.login;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.Main;
import application.cache.AppContext;
import application.util.IAlert7;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

@SuppressWarnings("all")
public class LoginContr implements Initializable {
	private Logger log = LoggerFactory.getLogger(LoginContr.class);
	@FXML
	private Button fileSelectorBtn;
	@FXML
	private Button submitBtn;
	@FXML
	private TextField fileNameShowField;

	public void fileSelected(ActionEvent event) {
		final FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("请选择文件");
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("DB鏂囦欢", "*.db"));
		File file = fileChooser.showOpenDialog(Main.LOGIN_STAGE);
		if (file != null) {
			fileNameShowField.setText(file.getAbsolutePath());
		}
	}

	public void submit(ActionEvent event) {
		String fileName = fileNameShowField.getText();
		File file = new File(fileName);
		if (file.exists() && file.isFile()) {
			Main.LOGIN_STAGE.hide();
			try {
				Stage stage = new Stage();
				Main.TABS_STAGE = stage;
				Parent root = FXMLLoader.load(getClass().getResource("../tabs/TabsScene.fxml"));
				Scene scene = new Scene(root, 718, 460);
				scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
				stage.setScene(scene);
				stage.setResizable(false);
				stage.show();
			} catch (Exception e) {
				log.error(e.getMessage());
			}
		} else {
			IAlert7.showMessageDialog(Main.LOGIN_STAGE, "提示信息", "文件不存在");
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
}
