package application.util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.Window;

public final class IAlert {
	public static final boolean alert_confirmDialog(String p_header, String p_message, Window window) {
		Alert _alert = new Alert(Alert.AlertType.CONFIRMATION, p_message, new ButtonType("取消", ButtonBar.ButtonData.NO),
				new ButtonType("确定", ButtonBar.ButtonData.YES));
		_alert.setTitle("确认");
		_alert.setHeaderText(p_header);
		_alert.initOwner(window);
		Optional<ButtonType> _buttonType = _alert.showAndWait();
		if (_buttonType.get().getButtonData().equals(ButtonBar.ButtonData.YES)) {
			return true;
		} else {
			return false;
		}
	}

	public static final void alert_informationDialog(String p_header, String p_message, Window window) {
		Alert _alert = new Alert(Alert.AlertType.INFORMATION);
		_alert.setTitle("提示信息");
		_alert.setHeaderText(p_header);
		_alert.setContentText(p_message);
		_alert.initOwner(window);
		_alert.show();
	}
}
