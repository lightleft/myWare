package cn.bhl.xlsp.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 复制excel 并完成填充
 * 
 * @ClassName: XlsUtils
 * @Description TODO
 * @author chenj
 * @date: 2017年6月12日 下午1:26:20
 * @version 1.0
 */
@SuppressWarnings("all")
public final class XlsUtils {

	private static String PREFIX = "#{%";
	private static String SUFFIX = "%}";
	public static final String XLS = ".xls";
	public static final String XLSX = ".xlsx";

	public static void generalFill(Sheet source, Workbook target, Map<String, String> data, String filePath) {
		Sheet newSheet = target.createSheet();
		PoiUtils.copySheet(source, newSheet, target);
		_fillByMap(newSheet, data);
		createFile(target, filePath);
	}

	/**
	 * 档案填充定制方法，不通用
	 * 
	 * @author chenj
	 * @param sourceFilePath
	 * @param targetDirPath
	 * @param index
	 * @param data
	 * @return String 文件名
	 * @date 2017年6月12日 下午8:25:35
	 */
	public static String eachFill(String sourceFilePath, String targetDirPath, int index,
			List<Map<String, String[]>> data, int size) {
		StringBuilder targetName = new StringBuilder();
		Workbook source = null;
		Workbook target = null;
		InputStream in = null;
		try {
			if (sourceFilePath.endsWith(XLS)) {
				targetName.append(UUID.generateShortUuid()).append(XLS);
				in = new FileInputStream(sourceFilePath);
				source = new HSSFWorkbook(in);
				target = new HSSFWorkbook();
			} else if (sourceFilePath.endsWith(XLSX)) {
				targetName.append(UUID.generateShortUuid()).append(XLSX);
				in = new FileInputStream(sourceFilePath);
				source = new XSSFWorkbook(in);
				target = new XSSFWorkbook();
			} else {
				throw new RuntimeException("Template error");
			}
			if (data != null && data.size() > 0) {
				for (int i = 0; i < size; i++) {
					Sheet newSheet = target.createSheet();
					PoiUtils.copySheet(source.getSheetAt(index), newSheet, target);
					if (data.size() > i) {
						_fillByList(newSheet, data.get(i));
					} else {
						_fillByList(newSheet, null);
					}
				}
			} else {
				Sheet newSheet = target.createSheet();
				PoiUtils.copySheet(source.getSheetAt(index), newSheet, target);
				_fillByList(newSheet, null);
			}
			String fileName = targetName.toString();
			if (targetDirPath.endsWith("/") || targetDirPath.endsWith("\\")) {
				createFile(target, targetDirPath + fileName);
			} else {
				createFile(target, targetDirPath + "/" + fileName);
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

	private static void createFile(Workbook target, String filePath) {
		BufferedOutputStream bos = null;
		try {
			File file = new File(filePath);
			int i = file.getAbsolutePath().lastIndexOf(file.getName());
			File drks = new File(file.getAbsolutePath().substring(0, i));
			if (!drks.exists() || !drks.isDirectory()) {
				drks.mkdirs();
			}
			bos = new BufferedOutputStream(new FileOutputStream(file));
			target.write(bos);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			try {
				if (bos != null) {
					bos.close();
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private static void _fillByList(Sheet sheet, Map<String, String[]> data) {
		if (sheet == null) {
			return;
		}
		if (data == null || data.size() < 1) {
			data = new HashMap<>();
		}
		for (int i = sheet.getFirstRowNum(); i < sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
				Cell cell = row.getCell(j);
				if (cell != null) {
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING: {
						String value = cell.getStringCellValue();
						if (value.contains(PREFIX) && value.contains(SUFFIX)) {
							int si = 0;
							int pi = 0;
							while (si >= 0 && pi >= 0) {
								int pindex = value.indexOf(PREFIX, pi);
								int sindex = value.indexOf(SUFFIX, si);
								pi = pindex;
								si = sindex;
								if (pindex < sindex) {
									String content = value.substring(pindex + PREFIX.length(), sindex);
									String param = null;
									int index = 1;
									String contentPre = content;
									if (content.contains(".")) {
										int x = content.lastIndexOf(".");
										contentPre = content.substring(0, x);
										index = Integer.parseInt(content.substring(x + 1));
									}
									String[] params = data.get(contentPre);
									if (params != null && params.length > 0 && params.length >= index) {
										param = params[index - 1];
									} else {
										param = " ";
									}
									value = value.replace(PREFIX + content + SUFFIX, param.toString());

								} else {
									break;
								}
							}
							cell.setCellValue(value);
						}
					}
					default: {
						break;
					}
					}

				}
			}
		}
	}

	private static void _fillByMap(Sheet sheet, Map<String, String> data) {
		for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
				Cell cell = row.getCell(j);
				if (cell != null) {
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_STRING: {
						String value = cell.getStringCellValue();
						if (value.contains(PREFIX) && value.contains(SUFFIX)) {
							int pindex = value.indexOf(PREFIX);
							int sindex = value.indexOf(SUFFIX);
							while (pindex > -1 && sindex > -1) {
								String content = value.substring(pindex + PREFIX.length(), sindex);
								String param = null;
								param = data.get(content) == null ? " — " : data.get(content);
								value = value.replace(PREFIX + content + SUFFIX, param.toString());
								pindex = value.indexOf(PREFIX, pindex);
								sindex = value.indexOf(SUFFIX, sindex);
							}
							cell.setCellValue(value);
						}
						break;
					}
					default: {
						break;
					}
					}

				}
			}
		}
	}

}
