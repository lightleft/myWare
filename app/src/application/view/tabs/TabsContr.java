package application.view.tabs;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import application.Main;
import application.biz.ArchiveService;
import application.cache.AppRegister;
import application.cache.TableUtil;
import application.model.ColumnModel;
import application.model.PageBean;
import application.model.TableModel;
import application.util.StringUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

@SuppressWarnings("all")
public class TabsContr implements Initializable {
	private final int pageSize = 20;
	private Logger log = LoggerFactory.getLogger(TabsContr.class);
	@FXML
	private Tab dataPaneTab;
	@FXML
	private Tab configPaneTab;
	@FXML
	private MenuItem relaDeleteBtn;
	@FXML
	private MenuItem relaUpdateBtn;
	@FXML
	private MenuItem relaAddBtn;
	@FXML
	private TableView<?> relaTable;
	@FXML
	private TableView<Map<String, Object>> dataTable;
	@FXML
	private Label showPageInfoLabel;
	@FXML
	private ComboBox<String> tableComboBox;
	@FXML
	private Button preBtn;
	@FXML
	private Button nextBtn;
	@FXML
	private Button searchFilterBtn;
	@FXML
	private Button exportIndexBtn;

	private ArchiveService archiveService = AppRegister.getBean("archiveService", ArchiveService.class);
	private int index;
	public static String tableName;

	/**
	 * 初始化
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String[] tables = TableUtil.getChNames();
		ObservableList<String> options = FXCollections.observableArrayList(tables);
		tableComboBox.setItems(options);
	}

	/*-------------------事件方法区--------------------*/
	/**
	 * 
	 * @param event
	 */
	public void add(ActionEvent event) {
	}

	/**
	 * 
	 * @param event
	 */
	public void update(ActionEvent event) {
	}

	/**
	 * 
	 * @param event
	 */
	public void delete(ActionEvent event) {
	}

	/**
	 * 上一页
	 * 
	 * @param event
	 */
	public void prePage(ActionEvent event) {
		this.index -= 1;
		PageBean pageBean = getSearchData();
		refreshDataTable(pageBean);
	}

	/**
	 * 下一页
	 * 
	 * @param event
	 */
	public void nextPage(ActionEvent event) {
		this.index += 1;
		PageBean pageBean = getSearchData();
		refreshDataTable(pageBean);
	}

	public void tableComboChange(ActionEvent event) {
		this.index = 1;
		PageBean pageBean = getSearchData();
		refreshDataTable(pageBean);
	}

	public void searchFilter(ActionEvent event) throws IOException {
		if (TabsContr.tableName == null)
			return;
		Stage stage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("../searchFilter/SearchFilter.fxml"));
		Scene scene = new Scene(root, 300, 438);
		scene.getStylesheets().add(getClass().getResource("/application/application.css").toExternalForm());
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}

	public void exportIndex(ActionEvent event) {

	}

	/*--------------------------私有方法区--------------------------*/
	/**
	 * 获取table表头
	 * 
	 * @param cols
	 * @return
	 */

	private PageBean getSearchData() {
		Object o = tableComboBox.getSelectionModel().getSelectedItem();
		String tableName = StringUtil.toString(o);
		TabsContr.tableName = tableName;
		TableModel table = TableUtil.getTableByName(tableName);
		PageBean pageBean = new PageBean(table);
		pageBean.setIndex(index);
		pageBean = archiveService.getArch(pageBean);
		return pageBean;
	}

	private TableColumn[] getTableCol(List<ColumnModel> cols) {
		TableColumn[] ts = new TableColumn[cols.size()];
		for (int i = 0; i < cols.size(); i++) {
			String colChName = cols.get(i).getColChName();
			String colEnName = cols.get(i).getColEnName();
			TableColumn<Map<String, Object>, String> firstDataColumn = new TableColumn<>(colChName);
			firstDataColumn.setCellValueFactory(new MapValueFactory(colEnName));
			firstDataColumn.setMinWidth(50);
			firstDataColumn.setPrefWidth(80);
			firstDataColumn.setMaxWidth(200);
			ts[i] = firstDataColumn;
		}
		return ts;
	}

	private ObservableList<Map<String, Object>> generateDataInMap(List<ColumnModel> cols,
			List<Map<String, Object>> data) {
		ObservableList<Map<String, Object>> allData = FXCollections.observableArrayList();
		for (int i = 0; i < data.size(); i++) {
			allData.add(data.get(i));
		}
		return allData;
	}

	private void refreshDataTable(PageBean pageBean) {
		List<ColumnModel> cols = pageBean.getTable().getColumn();
		dataTable.getColumns().setAll(getTableCol(cols));
		ObservableList<Map<String, Object>> items = generateDataInMap(cols, pageBean.getData());
		dataTable.setItems(items);
		String msg = "共" + pageBean.getPageNumbers() + "页," + pageBean.getTotal() + "条记录;" + "当前第" + pageBean.getIndex()
				+ "页," + pageBean.getNowPageCount() + "条记录";
		preBtn.setDisable(!pageBean.hasPerPage());
		nextBtn.setDisable(!(pageBean.hasNextPage()));
		showPageInfoLabel.setText(msg);
	}
}
