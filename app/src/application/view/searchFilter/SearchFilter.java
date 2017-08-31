package application.view.searchFilter;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import application.model.Trem;
import application.util.Lists;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

@SuppressWarnings("all")
public class SearchFilter implements Initializable {
	private static ObservableList<Trem> allData = FXCollections.observableArrayList();
	public static Trem selectedTrem;

	public static void clearTable() {
		allData.clear();
	}

	public static void addData(Trem trem) {
		allData.add(trem);
	}

	public static List<Trem> getDataAll() {
		return Lists.asList(allData);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		colTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory("colModel"));
		colTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory("value"));
		colTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory("rela"));
		colTable.setItems(allData);
	}

	@FXML
	private TableView<Trem> colTable;
	@FXML
	private Button add;
	@FXML
	private Button clear;
	@FXML
	private Button delete;

	public void add(ActionEvent event) throws IOException {
		Stage stage = new Stage();
		Main.UPDATE_STAGE = stage;
		Parent root = FXMLLoader.load(getClass().getResource("../addUpdate/AddUpdateScene.fxml"));
		Scene scene = new Scene(root, 265, 188);
		scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	public void delete(ActionEvent event) {
		int index = colTable.getSelectionModel().getSelectedIndex();
		allData.remove(index);
	}

	public void clear(ActionEvent event) {
		allData.clear();
	}

}
