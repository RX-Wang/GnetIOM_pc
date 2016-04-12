package com.gnet.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.AndFileFilter;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.NotFileFilter;
import org.apache.commons.io.filefilter.SuffixFileFilter;

/**
 * <table width="100%" border="1px">
 * <tr>
 * <td width="20%">作者</td><td width="80%" colspan="2">sam</td>
 * </tr>
 * <tr>
 * <td width="20%">版本</td><td width="80%" colspan="2">v1.0</td>
 * </tr>
 * <tr>
 * <td width="20%">创建日期</td><td width="80%" colspan="2">2013-06-24</td>
 * </tr>
 * <tr>
 * <td width="100%" colspan="3">修订记录:</td>
 * <tr>
 * <td width="20%">修改日期</td><td width="20%">修改人</td><td width="60%">修改记录</td>
 * </tr>
 * <tr>
 * <td width="20%">-------</td><td width="20%">-------</td><td width="60%">--------------</td>
 * </tr>
 * <tr>
 * <td width="20%">描述信息</td><td width="80%" colspan="2">对文件的操作方法</td>
 * </tr>
 * </tr>
 * </table>
 */
public class FileUtil {

	private static String encoding = "UTF-8";
	/**
	 * 复制文件
	 * 
	 * @param psResFilePath
	 * 				源文件路径
	 * 
	 * @param psDistFolder
	 * 				目标文件路径
	 */
	public static void copyFile(String psResFilePath, String psDistFolder)
			throws IOException {
		File _fResFile = new File(psResFilePath);
		File _fDistFile = new File(psDistFolder);
		if (_fResFile.isDirectory()) {
			FileUtils.copyDirectoryToDirectory(_fResFile, _fDistFile);
		} else if (_fDistFile.isFile()) {
			FileUtils.copyFileToDirectory(_fResFile, _fDistFile, true);
		}
	}

	/**
	 * 对文件进行写入操作
	 * 
	 * @param pFile
	 * 				操作的文件
	 * 
	 * @param psData
	 * 				写入的数据
	 */
	public static void writeFile(File pFile, String psData){
		try {
			FileUtils.writeStringToFile(pFile, psData, encoding, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除文件
	 * 
	 * @param psTargetPath
	 * 				要删除文件的路径
	 */
	public static void deleteFile(String psTargetPath) throws IOException {
		File targetFile = new File(psTargetPath);
		if (targetFile.isDirectory()) {
			FileUtils.deleteDirectory(targetFile);
		} else if (targetFile.isFile()) {
			targetFile.delete();
		}
	}

	/**
	 * 剪切文件/文件夹
	 * 
	 * @param psResFilePath
	 * 				源文件路径
	 * 
	 * @param psDistFolder
	 * 				目标文件路径
	 */
	public static void moveFile(String psResFilePath, String psDistFolder)
			throws IOException {
		File _fResFile = new File(psResFilePath);
		File _fDistFile = new File(psDistFolder);
		if (_fResFile.isDirectory()) {
			FileUtils.moveDirectoryToDirectory(_fResFile, _fDistFile, true);
		} else if (_fResFile.isFile()) {
			FileUtils.moveFileToDirectory(_fResFile, _fDistFile, true);
		}
	}

	/**
	 * 对文件重命名
	 * 
	 * @param psResFilePath
	 * 				文件路径
	 * @param psNewFileName
	 * 				新文件名
	 */
	public static boolean renameFile(String psResFilePath, String psNewFileName) {
		String _sNewFilePath = StringUtil.formatPath(getParentPath(psResFilePath) + "/" + psNewFileName);
		File _fResFile = new File(psResFilePath);
		File _fNewFile = new File(_sNewFilePath);
		return _fResFile.renameTo(_fNewFile);
	}

	/**
	 * 活得文件的大小
	 * 
	 * @param psDistFilePath
	 * 				文件路径
	 * 
	 * @return
	 * 				文件的大小
	 */
	public static long genFileSize(String psDistFilePath) {
		File _fDistFile = new File(psDistFilePath);
		if (_fDistFile.isFile()) {
			return _fDistFile.length();
		} else if (_fDistFile.isDirectory()) {
			return FileUtils.sizeOfDirectory(_fDistFile);
		}
		return -1L;
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param psFilePath
	 * 				文件路径
	 * 
	 * @return
	 * 				文件是否存在
	 */
	public static boolean isExist(String psFilePath) {
		return new File(psFilePath).exists();
	}

	/**
	 * 查找指定后缀的文件
	 * 
	 * @param psFolder
	 * 				要查找的文件夹
	 * @param psSuffix
	 * 				需要匹配的后缀名
	 * 
	 * @return
	 * 				文件名的数组集合
	 */
	public static String[] listFilebySuffix(String psFolder, String psSuffix) {
		IOFileFilter _fileSuffix = new SuffixFileFilter(psSuffix);
		IOFileFilter _fileNot = new NotFileFilter(
				DirectoryFileFilter.INSTANCE);
		FilenameFilter _fFilenameFilter = new AndFileFilter(_fileSuffix,
				_fileNot);
		return new File(psFolder).list(_fFilenameFilter);
	}

	/**
	 * 对文件进行二进制字符的写入
	 * 
	 * @param psRes
	 * 				二进制字符
	 * 
	 * @param psFilePath
	 * 				要操作的文件
	 * 
	 * @return
	 * 				操作是否成功
	 */
	public static boolean string2File(String psRes, String psFilePath) {
		boolean flag = true;
		BufferedReader bufferedReader = null;
		BufferedWriter bufferedWriter = null;
		try {
			File distFile = new File(psFilePath);
			if (!distFile.getParentFile().exists())
				distFile.getParentFile().mkdirs();
			bufferedReader = new BufferedReader(new StringReader(psRes));
			bufferedWriter = new BufferedWriter(new FileWriter(distFile));
			char buf[] = new char[1024]; // 字符缓冲区
			int len;
			while ((len = bufferedReader.read(buf)) != -1) {
				bufferedWriter.write(buf, 0, len);
			}
			bufferedWriter.flush();
			bufferedReader.close();
			bufferedWriter.close();
		} catch (IOException e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	/**
	 * 将字节写入文件
	 * 
	 * @param pFile
	 * 				要操作的文件
	 * @param pbData
	 * 				要写入的数据
	 * @param pblAppend
	 * 				是否为追加操作
	 * @return	操作是否成功
	 */
	public static boolean byte2File(File pFile, byte[] pbData, boolean pblAppend){
		try {
			FileUtils.writeByteArrayToFile(pFile, pbData, pblAppend);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 查找输入文件夹的父文件夹
	 * 
	 * @param psPath
	 * 				查找的文件夹
	 * @return
	 * 				查找文件夹的父文件夹
	 */
	public static String getParentPath(String psPath) {
		return new File(psPath).getParent();
	}

	/**
	 * 逐行读取文件
	 * 
	 * @param pFile
	 * 				要读取的文件
	 * @return
	 * 				返回文件的全文字符
	 */
	public static String readFile(File pFile){
		try {
			return FileUtils.readFileToString(pFile, encoding);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 逐行读取文件
	 * 
	 * @param pFile
	 * 				要读取的文件
	 * @return
	 * 				通过行形成的集合
	 */
	public static List<String> readLines(File pFile){
		try {
			return FileUtils.readLines(pFile, encoding);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
