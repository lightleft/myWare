package application.view.addUpdate;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import application.model.ColumnModel;
import application.model.TableModel;
import application.model.Trem;
import application.view.searchFilter.SearchFilter;
import application.view.tabs.TabsContr;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class UpdateContr implements Initializable {
	@FXML
	private Button submit;
	@FXML
	private ComboBox<ColumnModel> col;
	@FXML
	private ComboBox<Trem.Rela> rela;
	@FXML
	private TextField filterVal;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ObservableList<Trem.Rela> relas = FXCollections.observableArrayList(Trem.Rela.values());
		rela.setItems(relas);
		TableModel table = TabsContr.table;
		ObservableList<ColumnModel> cols = FXCollections.observableArrayList(table.getColumn());
		col.setItems(cols);
	}

	public void submit(ActionEvent event) {
		ColumnModel colo = col.getSelectionModel().getSelectedItem();
		Trem.Rela relao = rela.getSelectionModel().getSelectedItem();
		String filter = filterVal.getText();
		if (colo == null || relao == null || filter == null || filter.trim().isEmpty()) {
			return;
		}
		Trem e = new Trem(filter, relao, colo);
		SearchFilter.addData(e);
		Main.UPDATE_STAGE.close();
	}
}
