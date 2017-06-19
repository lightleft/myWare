package cn.bhl.xlsp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import cn.bhl.xlsp.utils.PoiUtils;

/**
 * Unit test for simple App.
 */
public class AppTest {
	@Test
	public void fun() {
		try {
			InputStream is = new FileInputStream("tem/tem-电子档案接收检验登记表.xlsx");
			Workbook source = new XSSFWorkbook(is);
			Sheet sourceSheet = source.getSheetAt(0);
			Workbook targetWb = new XSSFWorkbook();
			Sheet targetSheet = targetWb.createSheet();
			PoiUtils.copySheet(sourceSheet, targetSheet, targetWb);
			OutputStream os = new FileOutputStream("D:\\test.xlsx");
			targetWb.write(os);
			os.close();
			source.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
