/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.framework.core.utils.excel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.synth.Region;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tfkj.framework.core.utils.Encodes;
import com.tfkj.framework.core.utils.FileUtils;
import com.tfkj.framework.core.utils.Reflections;
import com.tfkj.framework.core.utils.excel.annotation.ExcelField;
import com.tfkj.framework.core.utils.excel.annotation.ExcelFields;
import com.tfkj.framework.system.utils.DictUtils;

/**
 * 导出Excel文件（导出“XLSX”格式，支持大数据量导出 @see org.apache.poi.ss.SpreadsheetVersion）
 *
 * @author ThinkGem
 * @version 2013-04-21
 */
public class ExportExcel2003 {

	private static Logger log = LoggerFactory.getLogger(ExportExcel2003.class);

	/**
	 * 工作薄对象
	 */
	private HSSFWorkbook wb;

	/**
	 * 工作表对象
	 */
	private Sheet sheet;

	/**
	 * 样式列表
	 */
	private Map<String, CellStyle> styles;

	/**
	 * 当前行号
	 */
	private int rownum;

	/**
	 * 注解列表（Object[]{ ExcelField, Field/Method }）
	 */
	List<Object[]> annotationList = Lists.newArrayList();

	/**
	 * 构造函数
	 *
	 * @param title
	 *            表格标题，传“空值”，表示无标题
	 * @param cls
	 *            实体对象，通过annotation.ExportField获取标题
	 */
	public ExportExcel2003(String title, Class<?> cls) {
		this(title, cls, 1);
	}

	/**
	 * 构造函数
	 *
	 * @param title
	 *            表格标题，传“空值”，表示无标题
	 * @param exportListType
	 *            列表类型
	 * @param cls
	 *            实体对象，通过annotation.ExportField获取标题
	 */
	public ExportExcel2003(String title, String exportListType, Class<?> cls) {
		this(title, exportListType, cls, 1);
	}

	public ExportExcel2003(String title, String exportListType, Class<?> cls, int type) {
		// 获取注解的字段
		Field[] fs = cls.getDeclaredFields();
		for (Field f : fs) {
			ExcelField ef = f.getAnnotation(ExcelField.class);
			if (ef != null && (ef.type() == 0 || ef.type() == type)) {
				annotationList.add(new Object[] { ef, f });
			}
			ExcelFields efs = f.getAnnotation(ExcelFields.class);
			if (efs != null && efs.value().length > 0) {
				for (ExcelField e : efs.value()) {
					if (e != null && (e.type() == 0 || e.type() == type)) {
						annotationList.add(new Object[] { e, f });
					}
				}
			}
		}
		// 获取注解的方法
		Method[] ms = cls.getDeclaredMethods();
		for (Method m : ms) {
			ExcelField ef = m.getAnnotation(ExcelField.class);
			if (ef != null && (ef.type() == 0 || ef.type() == type)) {
				annotationList.add(new Object[] { ef, m });
			}
			ExcelFields efs = m.getAnnotation(ExcelFields.class);
			if (efs != null && efs.value().length > 0) {
				for (ExcelField e : efs.value()) {
					if (e != null && (e.type() == 0 || e.type() == type)) {
						annotationList.add(new Object[] { e, m });
					}
				}
			}
		}
		// 注解排序
		Collections.sort(annotationList, new Comparator<Object[]>() {

			@Override
			public int compare(Object[] o1, Object[] o2) {

				return new Integer(((ExcelField) o1[0]).sort()).compareTo(new Integer(((ExcelField) o2[0]).sort()));
			};
		});
		// Initialize
		List<String> headerList = Lists.newArrayList();
		for (Object[] os : annotationList) {
			String t = ((ExcelField) os[0]).title();
			String fieldExportListType = ((ExcelField) os[0]).exportListType();
			boolean isExist = false;
			if (StringUtils.isBlank(fieldExportListType)) {
				isExist = true;
			}
			if (StringUtils.isNotBlank(fieldExportListType)) {
				String[] exportListTypes = fieldExportListType.split(",");
				for (int i = 0; i < exportListTypes.length; i++) {
					if (exportListType.equals(exportListTypes[i])) {
						isExist = true;
					}
				}
			}
			// 如果是导出，则去掉注释
			if (type == 1) {
				String[] ss = StringUtils.split(t, "**", 2);
				if (ss.length == 2) {
					t = ss[0];
				}
			}
			if (isExist) {
				headerList.add(t);
			}
		}
		initialize(title, headerList);
	}

	/**
	 * 构造函数
	 *
	 * @param title
	 *            表格标题，传“空值”，表示无标题
	 * @param cls
	 *            实体对象，通过annotation.ExportField获取标题
	 * @param type
	 *            导出类型（1:导出数据；2：导出模板）
	 * @param
	 *            导入分组
	 */
	public ExportExcel2003(String title, Class<?> cls, int type) {
		// Get annotation field
		Field[] fs = cls.getDeclaredFields();
		for (Field f : fs) {
			ExcelField ef = f.getAnnotation(ExcelField.class);
			if (ef != null && (ef.type() == 0 || ef.type() == type)) {
				annotationList.add(new Object[] { ef, f });
			}
		}
		// Get annotation method
		Method[] ms = cls.getDeclaredMethods();
		for (Method m : ms) {
			ExcelField ef = m.getAnnotation(ExcelField.class);
			if (ef != null && (ef.type() == 0 || ef.type() == type)) {
				annotationList.add(new Object[] { ef, m });
			}
		}
		// Field sorting
		Collections.sort(annotationList, new Comparator<Object[]>() {

			@Override
			public int compare(Object[] o1, Object[] o2) {

				return new Integer(((ExcelField) o1[0]).sort()).compareTo(new Integer(((ExcelField) o2[0]).sort()));
			};
		});
		// Initialize
		List<String> headerList = Lists.newArrayList();
		for (Object[] os : annotationList) {
			String t = ((ExcelField) os[0]).title();
			// 如果是导出，则去掉注释
			if (type == 1) {
				String[] ss = StringUtils.split(t, "**", 2);
				if (ss.length == 2) {
					t = ss[0];
				}
			}
			headerList.add(t);
		}
		initialize(title, headerList);
	}

	/**
	 * 构造函数
	 *
	 * @param title
	 *            表格标题，传“空值”，表示无标题
	 * @param headers
	 *            表头数组
	 */
	public ExportExcel2003(String title, String[] headers) {
		initialize(title, Lists.newArrayList(headers));
	}

	/**
	 * 构造函数
	 *
	 * @param title
	 *            表格标题，传“空值”，表示无标题
	 * @param headerList
	 *            表头列表
	 */
	public ExportExcel2003(String title, List<String> headerList) {
		initialize(title, headerList);
	}

	/**
	 * 初始化函数
	 *
	 * @param title
	 *            表格标题，传“空值”，表示无标题
	 * @param headerList
	 *            表头列表
	 */
	private void initialize(String title, List<String> headerList) {

		this.wb = new HSSFWorkbook();
		this.sheet = wb.createSheet("Export");
		this.styles = createStyles(wb);
		// Create title
		if (StringUtils.isNotBlank(title)) {
			Row titleRow = sheet.createRow(rownum++);
			titleRow.setHeightInPoints(30);
			Cell titleCell = titleRow.createCell(0);
			titleCell.setCellStyle(styles.get("title"));
			titleCell.setCellValue(title);
			sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), titleRow.getRowNum(), headerList.size() ));
		}
		// Create header
		if (headerList == null) {
			throw new RuntimeException("headerList not null!");
		}
		Row headerRow = sheet.createRow(rownum++);
		headerRow.setHeightInPoints(16);
		/**标题序号*/
		Cell cellSort = headerRow.createCell(0);
		cellSort.setCellStyle(styles.get("header"));
		cellSort.setCellValue("序号");
		/**标题序号*/
		for (int i = 0; i < headerList.size(); i++) {
			Cell cell = headerRow.createCell(i+1);
			cell.setCellStyle(styles.get("header"));
			String[] ss = StringUtils.split(headerList.get(i), "**", 2);
			if (ss.length == 2) {
				cell.setCellValue(ss[0]);
				Comment comment = this.sheet.createDrawingPatriarch().createCellComment(new XSSFClientAnchor(0, 0, 0, 0, (short) 3, 3, (short) 5, 6));
				comment.setString(new XSSFRichTextString(ss[1]));
				cell.setCellComment(comment);
			} else {
				cell.setCellValue(headerList.get(i));
			}
			sheet.autoSizeColumn(i);
		}
		for (int i = 0; i < headerList.size(); i++) {
			int colWidth = sheet.getColumnWidth(i) * 2;
			sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);
		}
		log.debug("Initialize success.");
	}

	/**
	 * 创建表格样式
	 *
	 * @param wb
	 *            工作薄对象
	 * @return 样式列表
	 */
	private Map<String, CellStyle> createStyles(Workbook wb) {

		Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
		CellStyle style = wb.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		Font titleFont = wb.createFont();
		titleFont.setFontName("Arial");
		titleFont.setFontHeightInPoints((short) 16);
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		style.setFont(titleFont);
		styles.put("title", style);
		style = wb.createCellStyle();
		style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
		Font dataFont = wb.createFont();
		dataFont.setFontName("Arial");
		dataFont.setFontHeightInPoints((short) 10);
		style.setFont(dataFont);
		styles.put("data", style);
		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_LEFT);
		styles.put("data1", style);
		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_CENTER);
		styles.put("data2", style);
		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		styles.put("data3", style);
		style = wb.createCellStyle();
		style.cloneStyleFrom(styles.get("data"));
		// style.setWrapText(true);
		style.setAlignment(CellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		Font headerFont = wb.createFont();
		headerFont.setFontName("Arial");
		headerFont.setFontHeightInPoints((short) 10);
		headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerFont.setColor(IndexedColors.WHITE.getIndex());
		style.setFont(headerFont);
		styles.put("header", style);
		return styles;
	}

	/**
	 * 添加一行
	 *
	 * @return 行对象
	 */
	public Row addRow() {

		return sheet.createRow(rownum++);
	}

	/**
	 * 添加一个单元格
	 *
	 * @param row
	 *            添加的行
	 * @param column
	 *            添加列号
	 * @param val
	 *            添加值
	 * @return 单元格对象
	 */
	public Cell addCell(Row row, int column, Object val) {

		return this.addCell(row, column, val, 2, Class.class);
	}

	/**
	 * 添加一个单元格
	 *
	 * @param row
	 *            添加的行
	 * @param column
	 *            添加列号
	 * @param val
	 *            添加值
	 * @param align
	 *            对齐方式（1：靠左；2：居中；3：靠右）
	 * @return 单元格对象
	 */
	public Cell addCell(Row row, int column, Object val, int align, Class<?> fieldType) {

		Cell cell = row.createCell(column);
		CellStyle style = styles.get("data" + (align >= 1 && align <= 3 ? align : ""));
		try {
			if (val == null) {
				cell.setCellValue("");
			} else if (val instanceof String) {
				cell.setCellValue( jsonEscape((String)val));
			} else if (val instanceof Integer) {
				cell.setCellValue(((Integer) val).toString());
			} else if (val instanceof Long) {
				cell.setCellValue((Long) val);
			} else if (val instanceof Double) {
				cell.setCellValue((Double) val);
			} else if (val instanceof Float) {
				cell.setCellValue((Float) val);
			} else if (val instanceof Date) {
				DataFormat format = wb.createDataFormat();
				style.setDataFormat(format.getFormat("yyyy-MM-dd"));
				cell.setCellValue((Date) val);
			} else {
				if (fieldType != Class.class) {
					cell.setCellValue((String) fieldType.getMethod("setValue", Object.class).invoke(null, val));
				} else {
					cell.setCellValue((String) Class.forName(this.getClass().getName().replaceAll(this.getClass().getSimpleName(), "fieldtype." + val.getClass().getSimpleName() + "Type"))
					        .getMethod("setValue", Object.class).invoke(null, val));
				}
			}
		} catch ( Exception ex ) {
			log.info("Set cell value [" + row.getRowNum() + "," + column + "] error: " + ex.toString());
			cell.setCellValue(val.toString());
		}
		cell.setCellStyle(style);
		return cell;
	}


	/**
	 * @Title: jsonEscape
	 * @Description: 将json串中的特殊字符转义
	 * @param @param jsonStr
	 * @param @return 参数
	 * @return String 返回类型
	 * @throws
	 */
	public  String jsonEscape(String jsonStr) {

		if (StringUtils.isBlank(jsonStr)) {
			return "";
		}
		jsonStr = jsonStr.replace("&gt;", ">");
		jsonStr = jsonStr.replace("&lt;", "<");
		jsonStr = jsonStr.replace("&nbsp;", " ");
		jsonStr = jsonStr.replace("&quot;", "\"");
		jsonStr = jsonStr.replace("&ldquo;", "“");
		jsonStr = jsonStr.replace("&rdquo;", "”");
		jsonStr = jsonStr.replace("&#39;", "\'");
		jsonStr = jsonStr.replace("\\\\", "\\");// 对斜线的转义
		jsonStr = jsonStr.replace("\\n", "\n");
		jsonStr = jsonStr.replace("\\r", "\r");
		jsonStr = jsonStr.replace("%3B", ";");
		jsonStr = jsonStr.replace("%2C", ",");
		return jsonStr;
	}

	/**
	 * 添加数据（通过annotation.ExportField添加数据）
	 *
	 * @return list 数据列表
	 */
	public <E> ExportExcel2003 setDataList(List<E> list) {

		return setDataList(list, null);
	}
	/**
	 * 合并单元格
	 * @param data  原始数据（在服务端完成排序）
	 * @param fieldName 合并属性名称所在位置
	   @param flagName  标记属性名称
	 * @param colspan   合并列
	 * @param target    目标表格对象
	 * @return
	 */
	public <E> void mergeCells(List<E> data, Integer[] fieldName, String flagName) {
		//声明一个map计算相同属性值在data对象出现的次数和
		Map<String, Integer> sortMap = Maps.newLinkedHashMap();
		for ( E prop : data) {
				String key = String.valueOf(Reflections.invokeGetter(prop, flagName));
				if (sortMap.containsKey(key)) {
					sortMap.put(key, (sortMap.get(key)* 1 + 1));
				} else {
					sortMap.put(key, 1);
				}
		}
		//合并单元格
		Integer index = 2;
		//序号
		Integer sort = 1;
		//循环行
		for ( String prop : sortMap.keySet()) {
			Integer count = sortMap.get(prop) * 1;
			//循环列
			for (Integer integer : fieldName) {
				CellRangeAddress region = new CellRangeAddress(index, // first row
						index+count-1, // last row
						integer, // first column
				        integer // last column
				);
				sheet.addMergedRegion(region);
			}
			this.addCell(sheet.getRow(index), 0, sort);
			index += count;
			sort++;
		}

	}
	/**
	 * @Title: setDataList
	 * @Description: 根据合并单元格数据
	 * @param @param list 数据
	 * @param @param exportListType 导出列表类型
	 * @param @return 参数
	 * @return ExportExcel2003 返回类型
	 * @throws
	 */
	public <E> ExportExcel2003 setMulDataList(List<E> list, String exportListType,Integer[] fieldName, String flagName) {
		for (E e : list) {
			int colunm = 1;
			Row row = this.addRow();
			StringBuilder sb = new StringBuilder();
			for (Object[] os : annotationList) {
				String fieldExportListType = ((ExcelField) os[0]).exportListType();
				// 注解的属性是否需要导出
				boolean isExist = false;
				if (StringUtils.isBlank(fieldExportListType)) {
					isExist = true;
				}
				if (StringUtils.isNotBlank(fieldExportListType)) {
					String[] exportListTypes = fieldExportListType.split(",");
					for (int i = 0; i < exportListTypes.length; i++) {
						if (exportListType != null && exportListType.equals(exportListTypes[i])) {
							isExist = true;
						}
					}
				}
				ExcelField ef = (ExcelField) os[0];
				Object val = null;
				// 从对象中读取值
				try {
					if (isExist) {
						// 如果注解中有value属性
						if (StringUtils.isNotBlank(ef.value())) {
							val = Reflections.invokeGetter(e, ef.value());
						}
						// 如果注解没有value属性
						else {
							if (os[1] instanceof Field) {
								val = Reflections.invokeGetter(e, ((Field) os[1]).getName());
							} else if (os[1] instanceof Method) {
								val = Reflections.invokeMethod(e, ((Method) os[1]).getName(), new Class[] {}, new Object[] {});
							}
						}
						// 如果有dictType属性
						if (StringUtils.isNotBlank(ef.dictType())) {
							val = DictUtils.getDictLabel(val == null ? "" : val.toString(), ef.dictType(), "");
						}
					}
				} catch ( Exception ex ) {
					// Failure to ignore
					log.info(ex.toString());
					val = "";
				}
				if (isExist) {
					// 如果有filePath属性
					if (val instanceof String && ef.isFilePath() == 1 && val != null && !"".equals(val)) {
						val = StringUtils.replace((String) val, "copyFiles", "tempFiles");
					}
					// 将属性放入excel单元格
					this.addCell(row, colunm++, val, ef.align(), ef.fieldType());
					sb.append(val + ", ");
				}
			}
			log.debug("Write success: [" + row.getRowNum() + "] " + sb.toString());
		}
		mergeCells(list,fieldName,flagName);
		return this;
	}
	/**
	 * @Title: setDataList
	 * @Description: 根据导出列表类型筛选数据
	 * @param @param list 数据
	 * @param @param exportListType 导出列表类型
	 * @param @return 参数
	 * @return ExportExcel2003 返回类型
	 * @throws
	 */
	public <E> ExportExcel2003 setDataList(List<E> list, String exportListType) {
		for (E e : list) {
			int colunm = 0;
			Row row = this.addRow();
			StringBuilder sb = new StringBuilder();
			for (Object[] os : annotationList) {
				String fieldExportListType = ((ExcelField) os[0]).exportListType();
				// 注解的属性是否需要导出
				boolean isExist = false;
				if (StringUtils.isBlank(fieldExportListType)) {
					isExist = true;
				}
				if (StringUtils.isNotBlank(fieldExportListType)) {
					String[] exportListTypes = fieldExportListType.split(",");
					for (int i = 0; i < exportListTypes.length; i++) {
						if (exportListType != null && exportListType.equals(exportListTypes[i])) {
							isExist = true;
						}
					}
				}
				ExcelField ef = (ExcelField) os[0];
				Object val = null;
				// 从对象中读取值
				try {
					if (isExist) {
						// 如果注解中有value属性
						if (StringUtils.isNotBlank(ef.value())) {
							val = Reflections.invokeGetter(e, ef.value());
						}
						// 如果注解没有value属性
						else {
							if (os[1] instanceof Field) {
								val = Reflections.invokeGetter(e, ((Field) os[1]).getName());
							} else if (os[1] instanceof Method) {
								val = Reflections.invokeMethod(e, ((Method) os[1]).getName(), new Class[] {}, new Object[] {});
							}
						}
						// 如果有dictType属性
						if (StringUtils.isNotBlank(ef.dictType())) {
							val = DictUtils.getDictLabel(val == null ? "" : val.toString(), ef.dictType(), "");
						}
					}
				} catch ( Exception ex ) {
					// Failure to ignore
					log.info(ex.toString());
					val = "";
				}
				if (isExist) {
					// 如果有filePath属性
					if (val instanceof String && ef.isFilePath() == 1 && val != null && !"".equals(val)) {
						val = StringUtils.replace((String) val, "copyFiles", "tempFiles");
					}
					// 将属性放入excel单元格
					this.addCell(row, colunm++, val, ef.align(), ef.fieldType());
					sb.append(val + ", ");
				}
			}
			log.debug("Write success: [" + row.getRowNum() + "] " + sb.toString());
		}
		return this;
	}

	/**
	 * 输出数据流
	 *
	 * @param os
	 *            输出数据流
	 */
	public ExportExcel2003 write(OutputStream os) throws IOException {

		wb.write(os);
		return this;
	}

	/**
	 * 输出到客户端
	 *
	 * @param fileName
	 *            输出文件名
	 */
	public ExportExcel2003 write(HttpServletResponse response, String fileName) throws IOException {

		response.reset();
		response.setContentType("application/octet-stream; charset=utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + Encodes.urlEncode(fileName));
		write(response.getOutputStream());
		return this;
	}

	/**
	 * 输出到文件
	 *
	 * @param
	 *            输出文件名
	 */
	public ExportExcel2003 writeFile(String name) throws FileNotFoundException, IOException {

		FileUtils.createFile(name);
		File file = new File(name);
		FileOutputStream os = new FileOutputStream(file);
		this.write(os);
		return this;
	}
	// /**
	// * 导出测试
	// */
	// public static void main(String[] args) throws Throwable {
	//
	// List<String> headerList = Lists.newArrayList();
	// for (int i = 1; i <= 10; i++) {
	// headerList.add("表头"+i);
	// }
	//
	// List<String> dataRowList = Lists.newArrayList();
	// for (int i = 1; i <= headerList.size(); i++) {
	// dataRowList.add("数据"+i);
	// }
	//
	// List<List<String>> dataList = Lists.newArrayList();
	// for (int i = 1; i <=1000000; i++) {
	// dataList.add(dataRowList);
	// }
	//
	// ExportExcel2003 ee = new ExportExcel("表格标题", headerList);
	//
	// for (int i = 0; i < dataList.size(); i++) {
	// Row row = ee.addRow();
	// for (int j = 0; j < dataList.get(i).size(); j++) {
	// ee.addCell(row, j, dataList.get(i).get(j));
	// }
	// }
	//
	// ee.writeFile("target/export.xlsx");
	//
	// ee.dispose();
	//
	// log.debug("Export success.");
	//
	// }
}