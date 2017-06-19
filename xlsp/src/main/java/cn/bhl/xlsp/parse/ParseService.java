package cn.bhl.xlsp.parse;

import java.util.Map;

public interface ParseService {
	Map<String, String> runGen(String sql, Map<String, String> p);
	Map<String,String[]> runEach(String sql,Map<String,String> p);
}
