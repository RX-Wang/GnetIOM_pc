package com.gnet.utils;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

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
 * <td width="20%">描述信息</td><td width="80%" colspan="2">gzip数据压缩与解压缩</td>
 * </tr>
 * </tr>
 * </table>
 */
public abstract class GZipUtils {

	/*
	 * 数据缓冲
	 */
	public static final int BUFFER = 1024;
	
	/*
	 *要替换的扩展名 
	 */
	public static final String EXT = ".gz";

	/**
	 * 描述信息:数据压缩
	 * 
	 * @param data
	 * 				压缩文件的字节
	 * 
	 * @return
	 * 				压缩后的字节
	 * @throws Exception
	 */
	public static byte[] compress(byte[] data) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		// 压缩
		compress(bais, baos);

		byte[] output = baos.toByteArray();

		baos.flush();
		baos.close();

		bais.close();

		return output;
	}

	/**
	 * 描述信息:文件压缩
	 * 
	 * @param file
	 * 				压缩文件
	 * 
	 * @throws Exception
	 */
	public static void compress(File file) throws Exception {
		compress(file, true);
	}

	/**
	 * 描述信息:文件压缩
	 * 
	 * @param file
	 * 				压缩文件
	 * @param delete
	 *            是否删除原始文件
	 *            
	 * @throws Exception
	 */
	public static void compress(File file, boolean delete) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		FileOutputStream fos = new FileOutputStream(file.getPath() + EXT);
		compress(fis, fos);
		fis.close();
		fos.flush();
		fos.close();

		if (delete) {
			file.delete();
		}
	}

	/**
	 * 描述信息:数据压缩
	 * 
	 * @param is
	 * 				文件的输入流
	 * @param os
	 * 				文件的输出流
	 * 
	 * @throws Exception
	 */
	public static void compress(InputStream is, OutputStream os) throws Exception {
		GZIPOutputStream gos = new GZIPOutputStream(os);
		int count;
		byte data[] = new byte[BUFFER];
		while ((count = is.read(data, 0, BUFFER)) != -1) {
			gos.write(data, 0, count);
		}
		gos.finish();
		gos.flush();
		gos.close();
	}

	/**
	 * 描述信息:文件压缩
	 * 
	 * @param path
	 * 				压缩文件的路径
	 * 
	 * @throws Exception
	 */
	public static void compress(String path) throws Exception {
		compress(path, true);
	}

	/**
	 * 描述信息:文件压缩
	 * 
	 * @param path
	 * 			压缩文件的路径
	 * @param delete
	 *            是否删除原始文件
	 *            
	 * @throws Exception
	 */
	public static void compress(String path, boolean delete) throws Exception {
		File file = new File(path);
		compress(file, delete);
	}

	/**
	 * 描述信息:数据解压缩
	 * 
	 * @param data
	 * 				压缩文件的字节
	 * 
	 * @return
	 * 				解压后的字节
	 * 
	 * @throws Exception
	 */
	public static byte[] decompress(byte[] data) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(data);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 解压缩
		decompress(bais, baos);
		data = baos.toByteArray();
		baos.flush();
		baos.close();
		bais.close();
		return data;
	}

	/**
	 * 描述信息:文件解压缩
	 * 
	 * @param file
	 * 				解压缩的文件
	 * 
	 * @throws Exception
	 */
	public static void decompress(File file) throws Exception {
		decompress(file, true);
	}

	/**
	 * 描述信息:文件解压缩
	 * 
	 * @param file
	 * 				解压缩的文件
	 * @param delete
	 *            是否删除原始文件
	 *            
	 * @throws Exception
	 */
	public static void decompress(File file, boolean delete) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		FileOutputStream fos = new FileOutputStream(file.getPath().replace(EXT, ""));
		decompress(fis, fos);
		fis.close();
		fos.flush();
		fos.close();

		if (delete) {
			file.delete();
		}
	}

	/**
	 * 描述信息:数据解压缩
	 * 
	 * @param is
	 * 				文件的输入流
	 * @param os
	 * 				文件的输出流
	 * 
	 * @throws Exception
	 */
	public static void decompress(InputStream is, OutputStream os)
			throws Exception {

		GZIPInputStream gis = new GZIPInputStream(is);

		int count;
		byte data[] = new byte[BUFFER];
		while ((count = gis.read(data, 0, BUFFER)) != -1) {
			os.write(data, 0, count);
		}

		gis.close();
	}

	/**
	 * 描述信息:文件解压缩
	 * 
	 * @param path
	 * 				解压缩的文件路径
	 * @throws Exception
	 */
	public static void decompress(String path) throws Exception {
		decompress(path, true);
	}

	/**
	 * 描述信息:文件解压缩
	 * 
	 * @param path
	 * 				解压缩的文件路径
	 * @param delete
	 *				是否删除原始文件
	 *
	 * @throws Exception
	 */
	public static void decompress(String path, boolean delete) throws Exception {
		File file = new File(path);
		decompress(file, delete);
	}

	public static void main(String[] args) throws Exception {
		String inputStr = "第三方撒旦噶斯12346546943212312311，31，。31，31.3，1.3码。32，12，。12/1214546321312332，3131，23132，1.，31.3，321321211212，12.11212121‘；、21蒂芬给定风格asdfsadfasdfsadfsadfsdafsadfs456358943693459834-564353452345sdasdfasdf";
		
		System.err.println("原文:\t" + inputStr);

		byte[] input = inputStr.getBytes();
		System.err.println("长度:\t" + input.length);

		byte[] data = GZipUtils.compress(input);
		System.err.println("压缩后:\t");
		System.err.println("长度:\t" + data.length);

		byte[] output = GZipUtils.decompress(data);
		String outputStr = new String(output);
		System.err.println("解压缩后:\t" + outputStr);
		System.err.println("长度:\t" + output.length);
	}
}
