/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.tfkj.framework.core.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.tfkj.business.web.constants.Constants;
import com.tfkj.framework.core.utils.Reflections;
import com.tfkj.framework.core.utils.excel.annotation.ExcelField;
import com.tfkj.framework.core.utils.excel.annotation.ExcelFields;
import com.tfkj.framework.system.utils.ConstantsUtil;
import com.tfkj.framework.system.utils.DictUtils;

/**
 * 导入Excel文件（支持“XLS”和“XLSX”格式）
 *
 * @author ThinkGem
 * @version 2013-03-10
 */
public class ImportExcel {

	private static Logger log = LoggerFactory.getLogger(ImportExcel.class);

	/**
	 * 工作薄对象
	 */
	private Workbook wb;

	/**
	 * 工作表对象
	 */
	private Sheet sheet;

	/**
	 * 标题行号
	 */
	private int headerNum;

	/**
	 * 构造函数
	 *
	 * @param path
	 *            导入文件，读取第一个工作表
	 * @param headerNum
	 *            标题行号，数据行号=标题行号+1
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public ImportExcel(String fileName, int headerNum) throws InvalidFormatException, IOException {
		this(new File(fileName), headerNum);
	}

	/**
	 * 构造函数
	 *
	 * @param path
	 *            导入文件对象，读取第一个工作表
	 * @param headerNum
	 *            标题行号，数据行号=标题行号+1
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public ImportExcel(File file, int headerNum) throws InvalidFormatException, IOException {
		this(file, headerNum, 0);
	}

	/**
	 * 构造函数
	 *
	 * @param path
	 *            导入文件
	 * @param headerNum
	 *            标题行号，数据行号=标题行号+1
	 * @param sheetIndex
	 *            工作表编号
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public ImportExcel(String fileName, int headerNum, int sheetIndex) throws InvalidFormatException, IOException {
		this(new File(fileName), headerNum, sheetIndex);
	}

	/**
	 * 构造函数
	 *
	 * @param path
	 *            导入文件对象
	 * @param headerNum
	 *            标题行号，数据行号=标题行号+1
	 * @param sheetIndex
	 *            工作表编号
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public ImportExcel(File file, int headerNum, int sheetIndex) throws InvalidFormatException, IOException {
		this(file.getName(), new FileInputStream(file), headerNum, sheetIndex);
	}

	/**
	 * 构造函数
	 *
	 * @param file
	 *            导入文件对象
	 * @param headerNum
	 *            标题行号，数据行号=标题行号+1
	 * @param sheetIndex
	 *            工作表编号
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public ImportExcel(MultipartFile multipartFile, int headerNum, int sheetIndex) throws InvalidFormatException, IOException {
		this(multipartFile.getOriginalFilename(), multipartFile.getInputStream(), headerNum, sheetIndex);
	}

	/**
	 * 构造函数
	 *
	 * @param path
	 *            导入文件对象
	 * @param headerNum
	 *            标题行号，数据行号=标题行号+1
	 * @param sheetIndex
	 *            工作表编号
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public ImportExcel(String fileName, InputStream is, int headerNum, int sheetIndex) throws InvalidFormatException, IOException {
		if (StringUtils.isBlank(fileName)) {
			throw new RuntimeException("导入文档为空!");
		} else if (fileName.toLowerCase().endsWith("xls")) {
			this.wb = new HSSFWorkbook(is);
		} else if (fileName.toLowerCase().endsWith("xlsx")) {
			this.wb = new XSSFWorkbook(is);
		} else {
			throw new RuntimeException("文档格式不正确!");
		}
		if (this.wb.getNumberOfSheets() < sheetIndex) {
			throw new RuntimeException("文档中没有工作表!");
		}
		this.sheet = this.wb.getSheetAt(sheetIndex);
		this.headerNum = headerNum;
		log.debug("Initialize success.");
	}

	/**
	 * 获取行对象
	 *
	 * @param rownum
	 * @return
	 */
	public Row getRow(int rownum) {

		return this.sheet.getRow(rownum);
	}

	/**
	 * 获取数据行号
	 *
	 * @return
	 */
	public int getDataRowNum() {

		return headerNum + 1;
	}

	/**
	 * 获取最后一个数据行号
	 *
	 * @return
	 */
	public int getLastDataRowNum() {

		return this.sheet.getLastRowNum();
	}

	/**
	 * 获取最后一个列号
	 *
	 * @return
	 */
	public int getLastCellNum() {

		return this.getRow(headerNum).getLastCellNum();
	}

	/**
	 * 获取单元格值
	 *
	 * @param row
	 *            获取的行
	 * @param column
	 *            获取单元格列号
	 * @return 单元格值
	 */
	public Object getCellValue(Row row, int column) {

		Object val = "";
		try {
			Cell cell = row.getCell(column);
			if (cell != null) {
				if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					val = cell.getNumericCellValue();
				} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					val = cell.getStringCellValue();
				} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
					val = cell.getCellFormula();
				} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
					val = cell.getBooleanCellValue();
				} else if (cell.getCellType() == Cell.CELL_TYPE_ERROR) {
					val = cell.getErrorCellValue();
				}
			}
		} catch ( Exception e ) {
			return val;
		}
		return val;
	}

	/**
	 * 获取导入数据列表
	 *
	 * @param cls
	 *            导入对象类型
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public <E> List<E> getDataList(Class<E> cls) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException {

		return getDataList(cls, null, null);
	}

	/**
	 * 获取导入数据列表
	 *
	 * @param cls
	 *            导入对象类型
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public <E> List<E> getDataList(Class<E> cls, String exportListType) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException {

		return getDataList(cls, exportListType, null);
	}

	/**
	 * 获取导入数据列表
	 *
	 * @param cls
	 *            导入对象类型
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public <E> List<E> getDataList(Class<E> cls, String exportListType, String uuid) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException {

		List<Object[]> annotationList = Lists.newArrayList();
		// 获取注解的字段
		Field[] fs = cls.getDeclaredFields();
		for (Field f : fs) {
			ExcelField ef = f.getAnnotation(ExcelField.class);
			if (ef != null && (ef.type() == 0 || ef.type() == 2)) {
				annotationList.add(new Object[] { ef, f });
			}
			ExcelFields efs = f.getAnnotation(ExcelFields.class);
			if (efs != null && efs.value().length > 0) {
				for (ExcelField e : efs.value()) {
					if (ef != null && (e.type() == 0 || e.type() == 2)) {
						annotationList.add(new Object[] { e, f });
					}
				}
			}
		}
		// 获取注解的方法
		Method[] ms = cls.getDeclaredMethods();
		for (Method m : ms) {
			ExcelField ef = m.getAnnotation(ExcelField.class);
			if (ef != null && (ef.type() == 0 || ef.type() == 2)) {
				annotationList.add(new Object[] { ef, m });
			}
			ExcelFields efs = m.getAnnotation(ExcelFields.class);
			if (efs != null && efs.value().length > 0) {
				for (ExcelField e : efs.value()) {
					if (e != null && (e.type() == 0 || e.type() == 2)) {
						annotationList.add(new Object[] { e, m });
					}
				}
			}
		}
		// 排序
		Collections.sort(annotationList, new Comparator<Object[]>() {

			@Override
			public int compare(Object[] o1, Object[] o2) {

				return new Integer(((ExcelField) o1[0]).sort()).compareTo(new Integer(((ExcelField) o2[0]).sort()));
			};
		});
		// 获取Excel中的数据
		List<E> dataList = Lists.newArrayList();
		// 逐行读取
		for (int i = this.getDataRowNum(); i <= this.getLastDataRowNum(); i++) {
			E e = cls.newInstance();
			int column = 0;
			Row row = this.getRow(i);
			StringBuilder sb = new StringBuilder();
			// 导出系统版本号
			String exportSysVersion = (String) this.getCellValue(row, 0);
			// 逐个注解读取
			for (Object[] os : annotationList) {
				// 导出文件的系统版本号与字段添加时所在版本号比较
				String versionAddField = ((ExcelField) os[0]).versionAddField();
				if (exportSysVersion.startsWith("V")) {
					int compareVersion = ConstantsUtil.compareVersion(exportSysVersion, versionAddField);
					// 如果导入系统的实体类字段的版本号比字段添加时所在版本号大则导入,小则不导入
					if (compareVersion < 0) {
						continue;
					}
				} else {
					exportSysVersion = Constants.INIT_WEB_VERSION;
					continue;
				}
				// 获取导出列表类型
				String fieldExportListType = ((ExcelField) os[0]).exportListType();
				boolean isExist = false;
				if (StringUtils.isBlank(fieldExportListType)) {
					isExist = true;
				}
				if (StringUtils.isNotBlank(fieldExportListType)) {
					String[] exportListTypes = fieldExportListType.split(",");
					for (int j = 0; j < exportListTypes.length; j++) {
						if (exportListType.equals(exportListTypes[j])) {
							isExist = true;
						}
					}
				}
				if (!isExist) {
					continue;
				}
				Object val = this.getCellValue(row, column++);
				if (val != null) {
					ExcelField ef = (ExcelField) os[0];
					// 码值转换
					if (StringUtils.isNotBlank(ef.dictType())) {
						val = DictUtils.getDictValue(val.toString().trim(), ef.dictType(), Constants.YW_DICT_KONG);
					}
					// 获取对象属性的类型
					Class<?> valType = Class.class;
					// 如果注解中有value属性
					if (StringUtils.isNotBlank(ef.value())) {
						String[] valArray = StringUtils.split(ef.value(), ".");
						for (int j = 0; j < valArray.length; j++) {
							String getterMethodName = "get" + StringUtils.capitalize(valArray[j]);
							if (j == 0) {
								valType = cls.getMethod(getterMethodName).getReturnType();
							} else {
								valType = valType.getMethod(getterMethodName).getReturnType();
							}
						}
					} else if (os[1] instanceof Field) {
						valType = ((Field) os[1]).getType();
					} else if (os[1] instanceof Method) {
						Method method = ((Method) os[1]);
						if ("get".equals(method.getName().substring(0, 3))) {
							valType = method.getReturnType();
						} else if ("set".equals(method.getName().substring(0, 3))) {
							valType = ((Method) os[1]).getParameterTypes()[0];
						}
					}
					// 转换对象属性类型
					try {
						if (valType == String.class) {
							String s = String.valueOf(val.toString());
							if (StringUtils.endsWith(s, ".0")) {
								val = StringUtils.substringBefore(s, ".0");
							} else {
								val = String.valueOf(val.toString());
							}
							if (uuid != null) {
								val = StringUtils.replace((String) val, "tempFiles", "tempFiles" + "/" + uuid);
							}
						} else if (valType == Integer.class) {
							val = Double.valueOf(val.toString()).intValue();
						} else if (valType == Long.class) {
							val = Double.valueOf(val.toString()).longValue();
						} else if (valType == Double.class) {
							val = Double.valueOf(val.toString());
						} else if (valType == Float.class) {
							val = Float.valueOf(val.toString());
						} else if (valType == Date.class) {
							val = DateUtil.getJavaDate((Double) val);
						} else {
							// 如果注解中配置了fieldType属性
							if (ef.fieldType() != Class.class) {
								val = ef.fieldType().getMethod("getValue", String.class).invoke(null, val.toString());
							}
							// 如果注解中没有配置fieldType属性
							else {
								String className = this.getClass().getName().replaceAll(this.getClass().getSimpleName(), "fieldtype." + valType.getSimpleName() + "Type");
								val = Class.forName(className).getMethod("getValue", String.class).invoke(null, val.toString());
							}
						}
					} catch ( Exception ex ) {
						log.info("Get cell value [" + i + "," + column + "] error: " + ex.toString());
						val = null;
					}
					// 往对象中赋值
					if (StringUtils.isNotBlank(ef.value())) {
						Reflections.invokeSetter(e, ef.value(), val);
					} else if (os[1] instanceof Field) {
						Reflections.invokeSetter(e, ((Field) os[1]).getName(), val);
					} else if (os[1] instanceof Method) {
						String mthodName = ((Method) os[1]).getName();
						if ("get".equals(mthodName.substring(0, 3))) {
							mthodName = "set" + StringUtils.substringAfter(mthodName, "get");
						}
						Reflections.invokeMethod(e, mthodName, new Class[] { valType }, new Object[] { val });
					}
				}
				sb.append(val + ", ");
			}
			dataList.add(e);
			log.debug("Read success: [" + i + "] " + sb.toString());
		}
		return dataList;
	}
	// /**
	// * 导入测试
	// */
	// public static void main(String[] args) throws Throwable {
	//
	// ImportExcel ei = new ImportExcel("target/export.xlsx", 1);
	//
	// for (int i = ei.getDataRowNum(); i < ei.getLastDataRowNum(); i++) {
	// Row row = ei.getRow(i);
	// for (int j = 0; j < ei.getLastCellNum(); j++) {
	// Object val = ei.getCellValue(row, j);
	// System.out.print(val+", ");
	// }
	// System.out.print("\n");
	// }
	//
	// }
}
