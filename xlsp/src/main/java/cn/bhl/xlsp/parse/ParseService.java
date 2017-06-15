package cn.bhl.xlsp.parse;

import java.util.Map;

public interface ParseService {
	void run(String sql, Map<String, String> p, Map<String, String> params);
}
