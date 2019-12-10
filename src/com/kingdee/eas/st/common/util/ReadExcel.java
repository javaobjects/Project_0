package com.kingdee.eas.st.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * excel read class only for excel 2003(except 2007,2010)
 * 
 * @author KG
 * 
 */
public class ReadExcel {
	/**
	 * load excel output the format is list(String[])
	 * 
	 * @param path
	 * @param sheetName
	 */
	public static List excelLoad(String path, String sheetName) {
		InputStream is = null;
		Workbook wb = null;
		ArrayList list = new ArrayList();
		int lastRowNum = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			is = new FileInputStream(new File(path));
			// ��������������Workbook����
			// wb = WorkbookFactory.create(is);
			wb = new HSSFWorkbook(is);
			Sheet sheet = wb.getSheet(sheetName);
			if (sheet == null)
				sheet = wb.getSheetAt(0);

			if (sheet != null && sheet.getRow(0) != null) {
				// �õ��������
				lastRowNum = sheet.getRow(0).getLastCellNum();
			}
			int rowNums = sheet.getLastRowNum();
			for (int k = 0; k <= rowNums; k++) {
				String contents[] = new String[lastRowNum + 1];
				String temp = null;
				if (sheet.getRow(k) != null) {
					for (int i = 0; i < lastRowNum; i++) {
						Cell cell = sheet.getRow(k).getCell((short) i);
						if (cell == null || "".equals(cell)) {
							temp = null;
						} else {
							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_STRING:
								// ��ȡString
								temp = cell.getRichStringCellValue() + "";
								break;
							// �հ�
							case Cell.CELL_TYPE_BLANK:
								// ��ȡString
								temp = null;
								break;
							case Cell.CELL_TYPE_NUMERIC:
								// �ȿ��Ƿ������ڸ�ʽ
								if (DateUtil.isCellDateFormatted(cell)) {
									// ��ȡ���ڸ�ʽ
									temp = sdf.format(cell.getDateCellValue());
								} else {
									// ��ȡ����
									temp = String.valueOf((long) cell
											.getNumericCellValue());
								}
								break;
							case Cell.CELL_TYPE_BOOLEAN:
								// �õ�Boolean����ķ���
								temp = cell.getBooleanCellValue() + "";
								break;
							case Cell.CELL_TYPE_FORMULA:
								// ��ȡ��ʽ
								try {
									temp = String.valueOf(cell
											.getNumericCellValue());
								} catch (IllegalStateException e) {
									temp = String.valueOf(cell
											.getRichStringCellValue());
								}
								break;
							default:
								break;
							}
						}
						contents[i] = temp;
					}
					// ����к�
					contents[lastRowNum] = sheet.getRow(k).getRowNum() + 1 + "";
					list.add(contents);
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * read the excel data to the list output the format is list(list)
	 * 
	 * @param path
	 * @param sheetName
	 * @return
	 */
	public static List excelLoadReturnListInList(String path, String sheetName) {
		InputStream is = null;
		Workbook wb = null;
		ArrayList list = new ArrayList();
		int lastRowNum = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			is = new FileInputStream(new File(path));
			// ��������������Workbook����
			// wb = WorkbookFactory.create(is);
			wb = new HSSFWorkbook(is);
			Sheet sheet = wb.getSheet(sheetName);
			if (sheet == null)
				sheet = wb.getSheetAt(0);

			if (sheet != null && sheet.getRow(0) != null) {
				// �õ��������
				lastRowNum = sheet.getRow(0).getLastCellNum();

			}
			// int rowNums = sheet.getPhysicalNumberOfRows();
			int rowNums = sheet.getLastRowNum();
			for (int k = 0; k <= rowNums; k++) {
				// String contents[] = new String[lastRowNum + 1];
				// if(rowNums d > rowNumProp) break;
				ArrayList ctxList = new ArrayList();
				String temp = null;
				if (sheet.getRow(k) != null) {
					for (int i = 0; i < lastRowNum; i++) {
						Cell cell = sheet.getRow(k).getCell(i);
						if (cell == null || "".equals(cell)) {
							temp = null;
						} else {
							switch (cell.getCellType()) {
							case Cell.CELL_TYPE_STRING:
								// ��ȡString
								temp = cell.getRichStringCellValue() + "";
								break;
							// �հ�
							case Cell.CELL_TYPE_BLANK:
								// ��ȡString
								temp = null;
								break;
							case Cell.CELL_TYPE_NUMERIC:
								// �ȿ��Ƿ������ڸ�ʽ
								if (DateUtil.isCellDateFormatted(cell)) {
									// ��ȡ���ڸ�ʽ
									temp = sdf.format(cell.getDateCellValue());
								} else {
									// ��ȡ����
									temp = getRightStr(cell
											.getNumericCellValue()
											+ "");
								}
								break;
							case Cell.CELL_TYPE_BOOLEAN:
								// �õ�Boolean����ķ���
								temp = cell.getBooleanCellValue() + "";
								break;
							case Cell.CELL_TYPE_FORMULA:
								// ��ȡ��ʽ
								try {
									temp = String.valueOf(cell
											.getNumericCellValue());
								} catch (IllegalStateException e) {
									temp = String.valueOf(cell
											.getRichStringCellValue());
								}
								break;
							default:
								break;
							}
						}
						// contents[i] = temp;
						ctxList.add(temp == null ? null : temp.trim());
					}
					// ����к�
					String rowNumber = sheet.getRow(k).getRowNum() + 1 + "";
					ctxList.add(rowNumber);
					list.add(ctxList);
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}

	/**
	 * ���������Զ���0����
	 * 
	 * @param sNum
	 * @return
	 */
	private static String getRightStr(String sNum) {
		DecimalFormat decimalFormat = new DecimalFormat("#.000000");
		String resultStr = decimalFormat.format(new Double(sNum));
		if (resultStr.matches("^[-+]?\\d+\\.[0]+$")) {
			resultStr = resultStr.substring(0, resultStr.indexOf("."));
		}
		return resultStr;
	}

	public static void main(String[] args) throws Exception {
		// List list = excelLoad("c://modelģ��.xlsx","������ģ��");
		// for(int i = 0; i < list.size(); i++){
		// for(int j = 0; j < list.get(i).length; j++){
		// System.out.print(list.get(i)[j] + " ");
		// }
		// System.out.println("");
		// }
	}
}