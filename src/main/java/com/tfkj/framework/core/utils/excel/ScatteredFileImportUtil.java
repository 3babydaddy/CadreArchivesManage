package com.tfkj.framework.core.utils.excel;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import com.tfkj.business.scattereds.entity.TblHandOverFiles;
import com.tfkj.business.scattereds.entity.TblScatteredFiles;
import com.tfkj.framework.core.utils.StringUtils;

public class ScatteredFileImportUtil {
	
	public TblScatteredFiles getExcelInfo(String fileName, MultipartFile file){
    	
    	TblScatteredFiles tblScatteredFiles = new TblScatteredFiles();
        //初始化输入流
        InputStream is = null;  
        try{
        	//根据新建的文件实例化输入流
           is = file.getInputStream();
           
           //根据文件名判断文件是2003版本还是2007版本
           boolean isExcel2003 = true; 
           if(WDWUtil.isExcel2007(fileName)){
               isExcel2003 = false;  
           }
           //根据excel里面的内容读取客户信息
           Workbook wb = null;
           //当excel是2003时
           if(isExcel2003){
               wb = new HSSFWorkbook(is); 
           }
           else{//当excel是2007时
               wb = new XSSFWorkbook(is); 
           }
           
           //读取Excel里面客户的信息
           tblScatteredFiles = this.readExcelValue(wb);
           is.close();
       }catch(Exception e){
           e.printStackTrace();
       } finally{
           if(is !=null)
           {
               try{
                   is.close();
               }catch(IOException e){
                   is = null;    
                   e.printStackTrace();  
               }
           }
       }
       return tblScatteredFiles;
    }
    
    private TblScatteredFiles readExcelValue(Workbook wb)throws Exception{ 
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	TblScatteredFiles info = new TblScatteredFiles();
    	//得到第一个shell  
        Sheet sheet=wb.getSheetAt(0);
         
        //得到Excel的行数
         int totalRows = sheet.getPhysicalNumberOfRows();
         int totalCells = 0;
        //得到Excel的列数(前提是有行数)
         if(totalRows >= 3 && sheet.getRow(3) != null){
        	 totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
         }
         
         //特别处理第二行和最后一行
         String handOverUnitName = sheet.getRow(1).getCell(1).getStringCellValue();
         String handOverDate = sheet.getRow(1).getCell(3).getStringCellValue();
         handOverDate = handOverDate.replace("年", "-").replace("月", "-").replace("日", "").replace(" ", ""); 
         
         String operator = sheet.getRow(totalRows-1).getCell(2).getStringCellValue();
         String recipient = sheet.getRow(totalRows-1).getCell(4).getStringCellValue();
         
         info.setHandOverUnitName(handOverUnitName);
         info.setHandOverDate(sdf.parse(handOverDate));
         info.setOperator(operator);
         info.setRecipient(recipient);
         
         List<TblHandOverFiles> handOverFilesList = new ArrayList<TblHandOverFiles>();
         TblHandOverFiles handOverFiles;            
        //循环Excel行数,从第二行开始。标题不入库
         for(int r = 3;r < (totalRows-1);r++){
             Row row = sheet.getRow(r);
             if (row == null) continue;
             handOverFiles = new TblHandOverFiles();
             
             //循环Excel的列
             for(int c = 1; c < totalCells; c++){    
                 Cell cell = row.getCell(c);
                 if (null != cell){
                     if(c==0){//第一列不读
                     }else if(c==1){
                    	 handOverFiles.setName(cell.getStringCellValue());//名称
                     }else if(c==2){
                    	 handOverFiles.setDuty(cell.getStringCellValue());//职务
                     }else if(c==3){
                    	 handOverFiles.setFilesNames(cell.getStringCellValue());//材料名称
                     }else if(c==4 && cell.getCellType() == 0){
                    	 handOverFiles.setOriginalNo((long)cell.getNumericCellValue());//份数
                     }
                 }
             }
             //添加人员信息
             if(StringUtils.isNotBlank(handOverFiles.getName())){
            	 handOverFilesList.add(handOverFiles); 
             }
         }
         info.setTblHandOverFilesList(handOverFilesList);
         return info;
    }
	
}
