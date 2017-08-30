package application.view.searchFilter;

import java.net.URL;
import java.util.ResourceBundle;

import application.cache.TableUtil;
import application.model.Trem;
import application.view.tabs.TabsContr;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;

public class SearchFilter implements Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String tableName = TabsContr.tableName;
		String[] chNames = TableUtil.getTableByName(tableName).getColumnChNames();
		ObservableList<String> options = FXCollections.observableArrayList(chNames);
		colSelectBox.setItems(options);
	}

	@FXML
	private ComboBox<String> colSelectBox;
	@FXML
	private TableView<Trem> colTable;

	public void boxChange(ActionEvent event) {
		
	}

}
