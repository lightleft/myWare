package cn.bhl.xlsp.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
/**
 * 处理excel文件
 * @ClassName: PoiUtils 
 * @author chenj
 * @date: 2017年6月12日 下午1:24:34 
 * @version 1.0
 */
public final class PoiUtils {
	/**
	 * 复制表
	 * 
	 * @param sourceSheet
	 *            源
	 * @param targetSheet
	 *            目标
	 * @param styleFlag
	 *            是否复制样式标识
	 * @param ramark
	 *            是否复制注释标识,不可用
	 */
	public static final void copySheet(Sheet sourceSheet, Sheet targetSheet,
			Workbook targetWb, boolean styleFlag, boolean ramark) {
		if (sourceSheet == null || targetSheet == null || targetWb == null) {
			throw new IllegalArgumentException(
					"调用PoiUtil.copySheet()方法时,targetSheet、sourceSheet、targetWb为空,故抛出该异常!");
		}
		Map<String, CellStyle> styleMap = (styleFlag) ? new HashMap<String, CellStyle>()
				: null;
		// 复制源表中的行
		int maxColumnNum = 0;
		for (int i = sourceSheet.getFirstRowNum(); i <= sourceSheet
				.getLastRowNum(); i++) {
			Row sourceRow = sourceSheet.getRow(i);
			Row targetRow = targetSheet.createRow(i);

			if (sourceRow != null) {
				copyRow(sourceRow, targetRow, targetWb, styleMap);
				if (sourceRow.getLastCellNum() > maxColumnNum) {
					maxColumnNum = sourceRow.getLastCellNum();
				}
			}
		}

		// 复制合并单元格
		mergerRegion(sourceSheet, targetSheet);
		// 设置目标sheet的列宽
		for (int i = 0; i < maxColumnNum; i++) {
			targetSheet.setColumnWidth(i, sourceSheet.getColumnWidth(i));
		}

	}

	/**
	 * 复制表
	 * 
	 * @param sourceSheet
	 *            源
	 * @param targetSheet
	 *            目标
	 */
	public static final void copySheet(Sheet sourceSheet, Sheet targetSheet,
			Workbook targetWb) {
		copySheet(sourceSheet, targetSheet, targetWb, true, false);
	}

	/**
	 * 复制行
	 * 
	 * @param sourceRow
	 *            源
	 * @param targetRow
	 *            目标
	 */
	private static final void copyRow(Row sourceRow, Row targetRow,
			Workbook targetWb, Map<String, CellStyle> styleMap) {
		// 设置行高
		targetRow.setHeight(sourceRow.getHeight());

		for (int i = sourceRow.getFirstCellNum(); i <= sourceRow
				.getLastCellNum(); i++) {
			Cell sourceCell = sourceRow.getCell(i);
			Cell targetCell = targetRow.getCell(i);
			if (sourceCell != null) {
				if (targetCell == null) {
					targetCell = targetRow.createCell(i);
				}
				copyCell(sourceCell, targetCell, targetWb, styleMap);
			}
		}
	}

	/**
	 * 复制合并单元格
	 * 
	 * @param sourceSheet
	 *            源表
	 * @param targetSheet
	 *            目标表
	 */
	private static void mergerRegion(Sheet sourceSheet, Sheet targetSheet) {
		for (int i = 0; i < sourceSheet.getNumMergedRegions(); i++) {
			CellRangeAddress oldRange = sourceSheet.getMergedRegion(i);
			CellRangeAddress newRange = new CellRangeAddress(
					oldRange.getFirstRow(), oldRange.getLastRow(),
					oldRange.getFirstColumn(), oldRange.getLastColumn());
			targetSheet.addMergedRegion(newRange);
		}
	}

	/**
	 * 复制单元格
	 * 
	 * @param sourceCell
	 *            源
	 * @param targetCell
	 *            目标
	 * @param targetWb
	 *            目标工作簿
	 * @param styleMap
	 *            样式map
	 */
	@SuppressWarnings("all")
	private static final void copyCell(Cell sourceCell, Cell targetCell,
			Workbook targetWb, Map<String, CellStyle> styleMap) {

		// 处理单元格内容
		switch (sourceCell.getCellType()) {
		case Cell.CELL_TYPE_STRING: {
			targetCell.setCellValue(sourceCell.getStringCellValue());
			break;
		}
		case Cell.CELL_TYPE_NUMERIC: {
			targetCell.setCellValue(sourceCell.getNumericCellValue());
			break;
		}
		case Cell.CELL_TYPE_BLANK: {
			targetCell.setCellType(HSSFCell.CELL_TYPE_BLANK);
			break;
		}
		case Cell.CELL_TYPE_BOOLEAN: {
			targetCell.setCellValue(sourceCell.getBooleanCellValue());
			break;
		}
		case Cell.CELL_TYPE_ERROR: {
			targetCell.setCellErrorValue(sourceCell.getErrorCellValue());
			break;
		}
		case Cell.CELL_TYPE_FORMULA: {
			targetCell.setCellFormula(sourceCell.getCellFormula());
			break;
		}
		default: {
			break;
		}
		}
		// 处理单元格样式
		if (styleMap != null) {
			String stHashCode = "" + sourceCell.getCellStyle().hashCode();
			CellStyle targetCellStyle = styleMap.get(stHashCode);
			if (targetCellStyle == null) {
				targetCellStyle = targetWb.createCellStyle();
				targetCellStyle.cloneStyleFrom(sourceCell.getCellStyle());
				styleMap.put(stHashCode, targetCellStyle);
			}
			targetCell.setCellStyle(targetCellStyle);

		}

	}
}
