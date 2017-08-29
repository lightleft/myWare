package application.view.tabs;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;

@SuppressWarnings("all")
public class TabsContr implements Initializable {
	// private Logger log = LoggerFactory.getLogger(TabsContr.class);
	@FXML
	private Tab dataPaneTab; // 数据显示主页
	@FXML
	private Tab configPaneTab; // 配置显示主页
	@FXML
	private MenuItem relaDeleteBtn; // 配置删除按钮
	@FXML
	private MenuItem relaUpdateBtn;// 配置修改按钮
	@FXML
	private MenuItem relaAddBtn;// 配置新增按钮
	@FXML
	private TableView<?> relaTable;// 配置页面table
	@FXML
	private TableView<Map<String, String>> dataTable;// 数据页面table
	@FXML
	private Label showPageInfoLabel;// 数据页面显示table信息
	@FXML
	private ComboBox tableComboBox; // 表选择下拉框

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		List<String> colNames = new ArrayList<String>() {
			{
				add("id");
				add("name");
				add("age");
			}
		};
		TableColumn[] ts = new TableColumn[colNames.size()];
		for (int i = 0; i < colNames.size(); i++) {
			String colName = colNames.get(i);
			TableColumn<Map<String, String>, String> firstDataColumn = new TableColumn<>(colName);
			firstDataColumn.setCellValueFactory(new MapValueFactory(colName));
			firstDataColumn.setMinWidth(130);
			firstDataColumn.setMaxWidth(300);
			ts[i] = firstDataColumn;
		}
		dataTable.setEditable(true);
		dataTable.getSelectionModel().setCellSelectionEnabled(true);
		dataTable.getColumns().setAll(ts);
		ObservableList<Map<String, String>> items = generateDataInMap();
		dataTable.setItems(items);
		showPageInfoLabel.setText("共" + items.size() + "记录");
	}

	private ObservableList<Map<String, String>> generateDataInMap() {
		int max = 10;
		ObservableList<Map<String, String>> allData = FXCollections.observableArrayList();
		for (int i = 1; i < max; i++) {
			Map<String, String> dataRow = new HashMap<>();

			String value1 = "id" + i;
			String value2 = "name" + i;
			String value3 = "age" + i;
			dataRow.put("id", value1);
			dataRow.put("name", value2);
			dataRow.put("age", value3);
			allData.add(dataRow);
		}
		return allData;
	}

	/**
	 * 关系配置页面 新增按钮
	 * 
	 * @param event
	 */
	public void add(ActionEvent event) {
	}

	/**
	 * 关系配置页面 修改按钮
	 * 
	 * @param event
	 */
	public void update(ActionEvent event) {
	}

	/**
	 * 关系配置页面 删除按钮
	 * 
	 * @param event
	 */
	public void delete(ActionEvent event) {
	}
}
