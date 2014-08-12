package com.wq.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件操作工具类 .
 * 
 * @author qingwu
 * 
 */
public class FileUtils {

	static LogUtils log = new LogUtils(FileUtils.class);

	/**
	 * 复制文件.
	 * 
	 * @param sourceFile
	 *            源文件地址
	 * @param targetFile
	 *            目标文件地址
	 * @return
	 */
	public static boolean copyFile(String sourceFile, String targetFile) {
		log.info(sourceFile + " --> " + targetFile);
		return copyFile(new File(sourceFile), new File(targetFile));
	}

	/**
	 * 复制文件.
	 * 
	 * @param sourceFile
	 *            源文件
	 * @param targetFile
	 *            目标文件
	 * @return
	 */
	public static boolean copyFile(File sourceFile, File targetFile) {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			// 复制目录
			File parent = targetFile.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
			}

			// 新建文件输入流并对它进行缓冲
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

			// 新建文件输出流并对它进行缓冲
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
		} catch (FileNotFoundException e) {
			log.err(e.getMessage());
			return false;
		} catch (IOException e) {
			log.err(e.getMessage());
			return false;
		} finally {
			// 关闭流
			try {
				if (inBuff != null)
					inBuff.close();
				if (outBuff != null)
					outBuff.close();
			} catch (IOException e) {
				log.err(e.getMessage());
				return false;
			}
		}
		return true;
	}

	/**
	 * 复制文件(指定编码).
	 * 
	 * @param srcFileName
	 *            源文件地址
	 * @param destFileName
	 *            目标文件地址
	 * @param srcCoding
	 *            源文件编码
	 * @param destCoding
	 *            目标文件编码
	 * @throws IOException
	 */
	public static void copyFile(File srcFileName, File destFileName,
			String srcCoding, String destCoding) throws IOException {
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(
					srcFileName), srcCoding));
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(destFileName), destCoding));
			char[] cbuf = new char[1024 * 5];
			int len = cbuf.length;
			int off = 0;
			int ret = 0;
			while ((ret = br.read(cbuf, off, len)) > 0) {
				off += ret;
				len -= ret;
			}
			bw.write(cbuf, 0, off);
			bw.flush();
		} finally {
			if (br != null)
				br.close();
			if (bw != null)
				bw.close();
		}
	}

	/**
	 * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件.
	 * 
	 * @param fileName
	 *            文件地址
	 */
	public static List<Integer> readFileByBytes(String fileName) {
		List<Integer> list = new ArrayList<Integer>();
		File file = new File(fileName);
		InputStream in = null;
		try {
			// 如果文件不存在则创建一个
			if (!file.exists()) {
				file.createNewFile();
			}

			System.out.println("以字节为单位读取文件内容，一次读一个字节：");
			// 一次读一个字节
			in = new FileInputStream(file);
			int tempbyte;
			while ((tempbyte = in.read()) != -1) {
				System.out.write(tempbyte);
				list.add(tempbyte);
			}
			in.close();
		} catch (IOException e) {
			log.err(e.getMessage());
			return null;
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				log.err(e.getMessage());
				return null;
			}
		}
		return list;
	}

	/**
	 * 以字符为单位读取文件，常用于读文本，数字等类型的文件.
	 * 
	 * @param fileName
	 *            文件地址
	 */
	public static String readFileByChars(String fileName) {
		Reader reader = null;
		String str = "";
		try {
			// System.out.println("以字符为单位读取文件内容，一次读多个字节：");
			// 一次读多个字符
			char[] tempchars = new char[30];
			int charread = 0;
			reader = new InputStreamReader(new FileInputStream(fileName));
			// 读入多个字符到字符数组中，charread为一次读取字符数
			while ((charread = reader.read(tempchars)) != -1) {
				// 同样屏蔽掉\r不显示
				if ((charread == tempchars.length)
						&& (tempchars[tempchars.length - 1] != '\r')) {
					System.out.print(tempchars);
				} else {
					for (int i = 0; i < charread; i++) {
						if (tempchars[i] == '\r') {
							continue;
						} else {
							System.out.print(tempchars[i]);
							str += tempchars[i];
						}
					}
				}
			}

		} catch (Exception e1) {
			e1.printStackTrace();
			log.err(e1.getMessage());
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					log.err(e1.getMessage());
				}
			}
		}
		return str;
	}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件.
	 * 
	 * @param fileName
	 *            文件地址
	 */
	public static List<String> readFileByLines(String fileName) {
		List<String> list = new ArrayList<String>();
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			// 如果文件不存在则创建一个
			if (!file.exists()) {
				file.createNewFile();
			}

			// System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				// System.out.println(tempString);
				list.add(tempString);
			}
			reader.close();
		} catch (IOException e) {
			log.err(e.getMessage());
			return null;
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
					log.err(e1.getMessage());
					return null;
				}
			}
		}
		return list;
	}

	/**
	 * 以行为单位读取文件，常用于读面向行的格式化文件.
	 * 
	 * @param fileName
	 *            文件地址
	 */
	public static String readFile(String fileName) {
		String FileContent = ""; // 文件很长的话建议使用StringBuffer
		try {
			FileInputStream fis = new FileInputStream(fileName);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				FileContent += line;
				FileContent += "\r\n"; // 补上换行符
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return FileContent;
	}

	/**
	 * 写入文件(原有内容被覆盖).
	 * 
	 * @param fileName
	 *            文件地址
	 * @param content
	 *            写入内容
	 * @return
	 */
	public static boolean writeFile(String fileName, String content) {
		File file = new File(fileName);
		FileOutputStream out = null;
		PrintStream p = null;
		try {
			// 如果文件不存在则创建一个
			// 复制目录
			File parent = file.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
			}

			if (!file.exists()) {
				file.createNewFile();
			}
			// 写信息
			out = new FileOutputStream(fileName);
			p = new PrintStream(out);
			p.write(content.getBytes());
			p.flush();
		} catch (FileNotFoundException e) {
			log.err(e.getMessage());
			return false;
		} catch (IOException e) {
			log.err(e.getMessage());
			return false;
		} finally {
			if (p != null) {
				p.close();
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					log.err(e.getMessage());
					return false;
				}
			}
		}
		return true;
	}

}
