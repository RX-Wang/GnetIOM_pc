package com.gnet.utils;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExicelUtil {
	/**
	 * @param f				：用来存放生成的.xls文件的文件夹
	 * @param fileName		:生成的 .xls文件的名字
	 * @param head			：表头数据
	 * @param list			：表单元格
	 * @param request
	 * @return
	 */
	public static String createExcel(String f,String fileName,String[] head, List<String[]> list,HttpServletRequest request) {
		String[] ret = new String[2];
		String path = "attachFiles" + File.separator + f + File.separator;
        /**得到Excel保存目录的真实路径**/    
        String fileNamePath = request.getSession().getServletContext().getRealPath(path);
		String fullFileName = fileName + ".xls";
		newFolder(fileNamePath);
		int rowNum = 0;
		try {
			fileNamePath = fileNamePath + File.separator  + fullFileName;
			// 打开文件
			WritableWorkbook book = Workbook.createWorkbook(new File(fileNamePath));
			// 得到类别
			WritableSheet sheet = book.createSheet(fileName, 0);
			if (!(head.length == 0 || head == null)) {// 表头数据
				for (int i = 0; i < head.length; i++) {
					Label label = new Label(i, 0, head[i].toString());
					sheet.addCell(label);
				}
				rowNum++;
			}
			if (!(list.size() == 0 || list == null)) {
				for (int cell = 0; cell < list.size(); cell++) {
					String[] str = list.get(cell);
					for (int j = 0; j < str.length; j++) {
						if (str[j] == null) {
							str[j] = "";
						}
						Label labelStrList = new Label(j, rowNum, str[j].toString());
						sheet.addCell(labelStrList);
					}
					rowNum++;
				}
			}
			book.write();
			book.close();
			ret[0] = "Y";
			ret[1] = fileNamePath + fullFileName;
			System.out.println("55:"+fileNamePath);
		} catch (Exception e) {
			ret[0] = "N";
			ret[1] = "数据生成失败！错误提示：" + e;
		}
		return fileNamePath;
	}
	/**
	 * 新建目录
	 * 
	 * @param folderPath
	 *            String 如 c:/fqf
	 * @return boolean
	 */
	public static void newFolder(String folderPath) {
		try {
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			if (!myFilePath.exists()) {
				myFilePath.mkdirs();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
