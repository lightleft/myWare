package cn.bhl.xlsp.parse;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cn.bhl.xlsp.utils.UUID;
import cn.bhl.xlsp.utils.XlsUtils;

@SuppressWarnings("all")
public class SQLParse {
	private List<String> sqls;
	private Map<String, String> params;
	private Map<String, String[]> someParams;
	private String type = "1";
	private ParseService parseService;

	public SQLParse(ParseService parseService) {
		this.parseService = parseService;
		sqls = new ArrayList<>();
	}

	private void getSqls(Sheet sheet, List<String> sqls) {
		boolean sqlFlag = false;
		for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			Cell cell = row.getCell(0);
			if (cell != null) {
				String value = cell.getStringCellValue();
				if (value == null || value.trim().isEmpty()) {
					continue;
				} else {
					value = value.trim();
					if (value.toLowerCase().startsWith("start")) {
						String[] s = value.split(":");
						if (s.length > 1) {
							String _t = s[1].trim();
							if (!_t.isEmpty()) {
								type = _t;
							}
						}
						continue;
					} else if (value.equalsIgnoreCase("sqls")) {
						sqlFlag = true;
					} else if (value.equalsIgnoreCase("esql")) {
						sqlFlag = false;
					} else if (value.toLowerCase().startsWith("end")) {
						break;
					} else {
						if (sqlFlag) {
							sqls.add(value);
						}
					}
				}
			}
		}
	}

	private void getGenData(Map<String, String> p) {
		params = new HashMap<>();
		for (String sql : this.sqls) {
			Map<String, String> map = parseService.runGen(sql, p);
			if (map != null)
				this.params.putAll(map);
		}
	}

	private void getSomeData(Map<String, String> p) {
		someParams = new HashMap<>();
		for (String sql : this.sqls) {
			Map<String, String[]> map = parseService.runEach(sql, p);
			if (map != null)
				this.someParams.putAll(map);

		}
	}

	public String fill(String sourceFilePath, String targetDirPath, Map<String, String> p) {
		StringBuilder targetName = new StringBuilder();
		Workbook source = null;
		Workbook target = null;
		InputStream in = null;
		try {
			if (sourceFilePath.endsWith(XlsUtils.XLS)) {
				targetName.append(UUID.generateShortUuid()).append(XlsUtils.XLS);
				in = new FileInputStream(sourceFilePath);
				source = new HSSFWorkbook(in);
				target = new HSSFWorkbook();
			} else if (sourceFilePath.endsWith(XlsUtils.XLSX)) {
				targetName.append(UUID.generateShortUuid()).append(XlsUtils.XLSX);
				in = new FileInputStream(sourceFilePath);
				source = new XSSFWorkbook(in);
				target = new XSSFWorkbook();
			} else {
				throw new RuntimeException("Template error");
			}
			String fileName = targetName.toString();
			getSqls(source.getSheetAt(1), this.sqls);
			switch (type) {
			case "1": {
				getGenData(p);
				String filePath = null;
				if (targetDirPath.endsWith("/") || targetDirPath.endsWith("\\")) {
					filePath = targetDirPath + fileName;
				} else {
					filePath = targetDirPath + "/" + fileName;
				}
				XlsUtils.generalFill(source.getSheetAt(0), target, this.params, filePath);
				break;
			}
			case "2": {
				getSomeData(p);
				String filePath = null;
				if (targetDirPath.endsWith("/") || targetDirPath.endsWith("\\")) {
					filePath = targetDirPath + fileName;
				} else {
					filePath = targetDirPath + "/" + fileName;
				}
				XlsUtils.eachFill(source.getSheetAt(0), target, this.someParams, filePath);
				break;
			}
			default: {
				break;
			}
			}
			return fileName;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {

			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
