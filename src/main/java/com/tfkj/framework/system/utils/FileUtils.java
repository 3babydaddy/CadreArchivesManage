package com.tfkj.framework.system.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileUtils {

	public static void download(String fileName, File file, HttpServletRequest request, HttpServletResponse response) {

		PrintWriter out = null;
		FileInputStream in = null;
		try {
			if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {  
        	    fileName = URLEncoder.encode(fileName, "UTF-8");  
        	} else {  
        		fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");  
        	}
			response.reset();
			response.setContentType("APPLICATION/OCTET-STREAM");
			response.setHeader("Content-Length", file.length() + "");
			response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
			
			in = new FileInputStream(file);
			out = response.getWriter();
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
