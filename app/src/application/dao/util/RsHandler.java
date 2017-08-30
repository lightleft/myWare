package application.dao.util;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RsHandler<T> {
	public T handler(ResultSet rs)throws SQLException;
}
