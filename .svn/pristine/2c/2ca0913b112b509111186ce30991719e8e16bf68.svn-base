package com.ztesoft.mobile.v2.util;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

/**
 * zip压缩工具类
 * @author heisonyee
 *
 */
public final class ZipUtils {

	private static final String BASE_DIR = "";

	// 符号"/"用来作为目录标识判断符
	private static final String PATH = "/";
	private static final int BUFFER = 1024;

	private static final String EXT = ".zip";
	
	/**
	 * 归档
	 * 
	 * @param srcPath
	 * @param destPath
	 * @throws Exception
	 */
	public static void archive(String srcPath, String destPath)
			throws Exception {
		File srcFile = new File(srcPath);
		archive(srcFile, destPath);
	}

	/**
	 * 归档
	 * 
	 * @param srcFile
	 *            源路径
	 * @param destPath
	 *            目标路径
	 * @throws Exception
	 */
	public static void archive(File srcFile, File destFile) throws Exception {
		ZipArchiveOutputStream zaos = new ZipArchiveOutputStream(
				new FileOutputStream(destFile));
		archive(srcFile, zaos, BASE_DIR);
		zaos.flush();
		zaos.close();
	}

	/**
	 * 归档
	 * 
	 * @param srcFile
	 * @throws Exception
	 */
	public static void archive(File srcFile) throws Exception {
		String name = srcFile.getName();
		String basePath = srcFile.getParent();
		String destPath = basePath + name + EXT;
		archive(srcFile, destPath);
	}

	/**
	 * 归档文件
	 * 
	 * @param srcFile
	 * @param destPath
	 * @throws Exception
	 */
	public static void archive(File srcFile, String destPath) throws Exception {
		archive(srcFile, new File(destPath));
	}

	/**
	 * 归档
	 * 
	 * @param srcPath
	 * @throws Exception
	 */
	public static void archive(String srcPath) throws Exception {
		File srcFile = new File(srcPath);
		archive(srcFile);
	}

	/**
	 * 归档
	 * 
	 * @param srcFile
	 *            源路径
	 * @param taos
	 *            TarArchiveOutputStream
	 * @param basePath
	 *            归档包内相对路径
	 * @throws Exception
	 */
	private static void archive(File srcFile, ZipArchiveOutputStream zaos,
			String basePath) throws Exception {
		if (srcFile.isDirectory()) {
			archiveDir(srcFile, zaos, basePath);
		} else {
			archiveFile(srcFile, zaos, basePath);
		}
	}

	/**
	 * 目录归档
	 * 
	 * @param dir
	 * @param taos
	 *            TarArchiveOutputStream
	 * @param basePath
	 * @throws Exception
	 */
	private static void archiveDir(File dir, ZipArchiveOutputStream zaos,
			String basePath) throws Exception {
		File[] files = dir.listFiles();
		if (files.length < 1) {
			ZipArchiveEntry entry = new ZipArchiveEntry(basePath
					+ dir.getName() + PATH);
			
			zaos.putArchiveEntry(entry);
			zaos.closeArchiveEntry();
		}
		for (File file : files) {
			// 递归归档
			archive(file, zaos, basePath + dir.getName() + PATH);
		}
	}

	/**
	 * 数据归档
	 * 
	 * @param data
	 *            待归档数据
	 * @param path
	 *            归档数据的当前路径
	 * @param name
	 *            归档文件名
	 * @param taos
	 *            ZipArchiveOutputStream
	 * @throws Exception
	 */
	private static void archiveFile(File file, ZipArchiveOutputStream zaos,
			String dir) throws Exception {

		/**
		 * 归档内文件名定义
		 * 
		 * <pre>
		 * 如果有多级目录，那么这里就需要给出包含目录的文件名 
		 * 如果用WinRAR打开归档包，中文名将显示为乱码
		 * </pre>
		 */
		ZipArchiveEntry entry = new ZipArchiveEntry(dir + file.getName());

		entry.setSize(file.length());

		zaos.putArchiveEntry(entry);

		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(
				file));
		int count;
		byte data[] = new byte[BUFFER];
		while ((count = bis.read(data, 0, BUFFER)) != -1) {
			zaos.write(data, 0, count);
		}

		bis.close();

		zaos.closeArchiveEntry();
	}

	/**
	 * 解归档
	 * 
	 * @param srcFile
	 * @throws Exception
	 */
	public static void dearchive(File srcFile) throws Exception {
		String basePath = srcFile.getParent();
		dearchive(srcFile, basePath);
	}

	/**
	 * 解归档
	 * 
	 * @param srcFile
	 * @param destFile
	 * @throws Exception
	 */
	public static void dearchive(File srcFile, File destFile) throws Exception {
		ZipArchiveInputStream zais = new ZipArchiveInputStream(new FileInputStream(srcFile));
		dearchive(destFile, zais);
		zais.close();
	}

	/**
	 * 解归档
	 * 
	 * @param srcFile
	 * @param destPath
	 * @throws Exception
	 */
	public static void dearchive(File srcFile, String destPath)
			throws Exception {
		dearchive(srcFile, new File(destPath));
	}

	/**
	 * 文件 解归档
	 * 
	 * @param destFile
	 *            目标文件
	 * @param tais
	 *            ZipInputStream
	 * @throws Exception
	 */
	private static void dearchive(File destFile, ZipArchiveInputStream zais)
			throws Exception {

		ZipArchiveEntry entry = null;
		while ((entry = zais.getNextZipEntry()) != null) {
			// 文件
			String dir = destFile.getPath() + File.separator + entry.getName();
			File dirFile = new File(dir);
			// 文件检查
			fileProber(dirFile);

			if (entry.isDirectory()) {
				dirFile.mkdirs();
			} else {
				dearchiveFile(dirFile, zais);
			}
		}
	}

	/**
	 * 文件 解归档
	 * 
	 * @param srcPath
	 *            源文件路径
	 * 
	 * @throws Exception
	 */
	public static void dearchive(String srcPath) throws Exception {
		File srcFile = new File(srcPath);
		dearchive(srcFile);
	}

	/**
	 * 文件 解归档
	 * 
	 * @param srcPath
	 *            源文件路径
	 * @param destPath
	 *            目标文件路径
	 * @throws Exception
	 */
	public static void dearchive(String srcPath, String destPath)
			throws Exception {
		File srcFile = new File(srcPath);
		dearchive(srcFile, destPath);
	}

	/**
	 * 文件解归档
	 * 
	 * @param destFile
	 *            目标文件
	 * @param tais
	 *            TarArchiveInputStream
	 * @throws Exception
	 */
	private static void dearchiveFile(File destFile, ZipArchiveInputStream zais)
			throws Exception {
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile));
		int count;
		byte data[] = new byte[BUFFER];
		while ((count = zais.read(data, 0, BUFFER)) != -1) {
			bos.write(data, 0, count);
		}
		bos.close();
	}

	/**
	 * 文件探针
	 * 
	 * <pre>
	 * 当父目录不存在时，创建目录！
	 * </pre>
	 * 
	 * @param dirFile
	 */
	private static void fileProber(File dirFile) {
		File parentFile = dirFile.getParentFile();
		if (!parentFile.exists()) {
			// 递归寻找上级目录
			fileProber(parentFile);
			parentFile.mkdir();
		}
	}

}