/**
 * $ XLSExport.java v1.0 Sep 18, 2007
 * $copyright 2007 Recency, Inc. All Rights Reserved.
 * Description:
 */
package com.ztesoft.eoms.common.helper;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

/** */
/**
 * 生成导出Excel文件对象
 *
 * @author Rodney Chen
 *
 */
public class XLSExport {

	// 设置cell编码解决中文高位字节截断
	private static short XLS_ENCODING = HSSFWorkbook.ENCODING_UTF_16;

	// 定制日期格式
	private static String DATE_FORMAT = "m/d/yy h:mm"; // "m/d/yy h:mm"

	// 定制浮点数格式
	private static String NUMBER_FORMAT = "#,##0.00";

	private String xlsFileName;

	private HSSFWorkbook workbook;

	private HSSFCellStyle cstyleTitle;

	private HSSFSheet sheet;

	private HSSFRow row;

	/**
	 * @param cstyleTitle
	 *            the cstyleTitle to set
	 */
	public void setCstyleTitle(HSSFCellStyle cstyleTitle) {
		this.cstyleTitle = cstyleTitle;
	}

	/** */
	/**
	 * 初始化Excel
	 *
	 * @param fileName
	 *  导出文件名
	 */
	public XLSExport(String fileName) {
		this.xlsFileName = fileName;
		this.workbook = new HSSFWorkbook();
		this.sheet = workbook.createSheet();
		this.sheet.setDefaultColumnWidth((short) 20);
		this.cstyleTitle = workbook.createCellStyle();
		this.cstyleTitle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中
        this.cstyleTitle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中
	}

	/** */
	/**
	 * 导出Excel文件
	 *
	 * @throws XLSException
	 */
	public void exportXLS() throws Exception {
		try {
			FileOutputStream fOut = new FileOutputStream(xlsFileName);
			workbook.write(fOut);
			fOut.flush();
			fOut.close();
		} catch (FileNotFoundException e) {
			throw new Exception(" 生成导出Excel文件出错! ", e);
		} catch (IOException e) {
			throw new Exception(" 写入Excel文件出错! ", e);
		}

	}

	/** */
	/**
	 * 增加一行
	 *
	 * @param index
	 * 行号
	 */
	public void createRow(int index) {
		this.row = this.sheet.createRow(index);
	}

	/** */
	/**
	 * 设置单元格
	 *
	 * @param index
	 *            列号
	 * @param value
	 *            单元格填充值
	 */
	public void setCell(int index, String value) {
		HSSFCell cell = this.row.createCell((short) index);
		cell.setCellType(HSSFCell.CELL_TYPE_STRING);
		cell.setEncoding(XLS_ENCODING);
		cell.setCellValue(value);
		cell.setCellStyle(this.cstyleTitle);
	}

	/** */
	/**
	 * 设置单元格
	 *
	 * @param index
	 *  列号
	 * @param value
	 *  单元格填充值
	 */
	public void setCell(int index, Calendar value) {
		HSSFCell cell = this.row.createCell((short) index);
		cell.setEncoding(XLS_ENCODING);
		cell.setCellValue(value.getTime());
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat(DATE_FORMAT)); // 设置cell样式为定制的日期格式
		cell.setCellStyle(cellStyle); // 设置该cell日期的显示格式


	}
	/** */
	/**
	 * 设置单元格
	 *
	 * @param index
	 * 列号
	 * @param value
	 * 单元格填充值
	 */
	public void setCell(int index, Date value, String dateFormat) {
		HSSFCell cell = this.row.createCell((short) index);
		cell.setEncoding(XLS_ENCODING);
		cell.setCellValue(value);
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat(dateFormat)); // 设置cell样式为定制的日期格式
		cell.setCellStyle(cellStyle); // 设置该cell日期的显示格式

	}	

	/** */
	/**
	 * 设置单元格
	 *
	 * @param index
	 * 列号
	 * @param value
	 * 单元格填充值
	 */
	public void setCell(int index, int value) {
		HSSFCell cell = this.row.createCell((short) index);
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
		cell.setCellStyle(this.cstyleTitle);
	}


	/**
	 * 设置单元格
	 *
	 * @param index
	 * 列号
	 * @param value
	 * 单元格填充值
	 */
	public void setCell(int index, double value) {
		HSSFCell cell = this.row.createCell((short) index);
		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
		cell.setCellValue(value);
		HSSFCellStyle cellStyle = workbook.createCellStyle(); // 建立新的cell样式
		HSSFDataFormat format = workbook.createDataFormat();
		cellStyle.setDataFormat(format.getFormat(NUMBER_FORMAT)); // 设置cell样式为定制的浮点数格式
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中
        cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//上下居中
		cell.setCellStyle(cellStyle); // 设置该cell浮点数的显示格式
	}
	/**
	 * 合并
	 * @param startrow
	 * @param endrow
	 * @param colnum
	 */
	public void addMergedRegion(int startrow,int startcol, int endrow, int endcol) {
		this.sheet.addMergedRegion(new Region(startrow, (short) startcol, endrow,
				(short) endcol));
	}
}
