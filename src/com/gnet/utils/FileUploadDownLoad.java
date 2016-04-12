package com.gnet.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileUploadDownLoad {
	
	public void doDownload(HttpServletRequest request, HttpServletResponse response,String filePath) throws IOException {
		System.out.println("17输入参数filePath:"+filePath);
		
		String fileName = "";
		if(filePath.startsWith("\"") && filePath.endsWith("\"")){
			filePath=filePath.substring(1, filePath.length()-1);
			fileName = filePath.substring(filePath.lastIndexOf(File.separator)+1,filePath.length());
		}else {
			fileName = filePath.substring(filePath.lastIndexOf(File.separator)+1);
		}
		File file = new File(filePath);
		InputStream is = new BufferedInputStream(new FileInputStream(file));
		//将输入流里面的数据转存至输出流
		byte[] b = new byte[is.available()];
		//将输入流里面的数据写入至b,并关闭
		is.read(b);
		is.close();
		// 清空response
		response.reset();
		//设置响应头信息
		response.setContentType("application/octet-stream");
		response.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("utf-8"), "ISO-8859-1"));
		response.addHeader("Content-Length", "" + file.length());
		//获取下载的输出流
		OutputStream os = new BufferedOutputStream(response.getOutputStream());
		os.write(b);
		//清空管道并关闭
		os.flush();
		os.close();
	}
}
