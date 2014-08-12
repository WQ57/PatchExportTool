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
 * �ļ����������� .
 * 
 * @author qingwu
 * 
 */
public class FileUtils {

	static LogUtils log = new LogUtils(FileUtils.class);

	/**
	 * �����ļ�.
	 * 
	 * @param sourceFile
	 *            Դ�ļ���ַ
	 * @param targetFile
	 *            Ŀ���ļ���ַ
	 * @return
	 */
	public static boolean copyFile(String sourceFile, String targetFile) {
		log.info(sourceFile + " --> " + targetFile);
		return copyFile(new File(sourceFile), new File(targetFile));
	}

	/**
	 * �����ļ�.
	 * 
	 * @param sourceFile
	 *            Դ�ļ�
	 * @param targetFile
	 *            Ŀ���ļ�
	 * @return
	 */
	public static boolean copyFile(File sourceFile, File targetFile) {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			// ����Ŀ¼
			File parent = targetFile.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
			}

			// �½��ļ����������������л���
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

			// �½��ļ���������������л���
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

			// ��������
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// ˢ�´˻���������
			outBuff.flush();
		} catch (FileNotFoundException e) {
			log.err(e.getMessage());
			return false;
		} catch (IOException e) {
			log.err(e.getMessage());
			return false;
		} finally {
			// �ر���
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
	 * �����ļ�(ָ������).
	 * 
	 * @param srcFileName
	 *            Դ�ļ���ַ
	 * @param destFileName
	 *            Ŀ���ļ���ַ
	 * @param srcCoding
	 *            Դ�ļ�����
	 * @param destCoding
	 *            Ŀ���ļ�����
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
	 * ���ֽ�Ϊ��λ��ȡ�ļ��������ڶ��������ļ�����ͼƬ��������Ӱ����ļ�.
	 * 
	 * @param fileName
	 *            �ļ���ַ
	 */
	public static List<Integer> readFileByBytes(String fileName) {
		List<Integer> list = new ArrayList<Integer>();
		File file = new File(fileName);
		InputStream in = null;
		try {
			// ����ļ��������򴴽�һ��
			if (!file.exists()) {
				file.createNewFile();
			}

			System.out.println("���ֽ�Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���ֽڣ�");
			// һ�ζ�һ���ֽ�
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
	 * ���ַ�Ϊ��λ��ȡ�ļ��������ڶ��ı������ֵ����͵��ļ�.
	 * 
	 * @param fileName
	 *            �ļ���ַ
	 */
	public static String readFileByChars(String fileName) {
		Reader reader = null;
		String str = "";
		try {
			// System.out.println("���ַ�Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�����ֽڣ�");
			// һ�ζ�����ַ�
			char[] tempchars = new char[30];
			int charread = 0;
			reader = new InputStreamReader(new FileInputStream(fileName));
			// �������ַ����ַ������У�charreadΪһ�ζ�ȡ�ַ���
			while ((charread = reader.read(tempchars)) != -1) {
				// ͬ�����ε�\r����ʾ
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
	 * ����Ϊ��λ��ȡ�ļ��������ڶ������еĸ�ʽ���ļ�.
	 * 
	 * @param fileName
	 *            �ļ���ַ
	 */
	public static List<String> readFileByLines(String fileName) {
		List<String> list = new ArrayList<String>();
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			// ����ļ��������򴴽�һ��
			if (!file.exists()) {
				file.createNewFile();
			}

			// System.out.println("����Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���У�");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// һ�ζ���һ�У�ֱ������nullΪ�ļ�����
			while ((tempString = reader.readLine()) != null) {
				// ��ʾ�к�
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
	 * ����Ϊ��λ��ȡ�ļ��������ڶ������еĸ�ʽ���ļ�.
	 * 
	 * @param fileName
	 *            �ļ���ַ
	 */
	public static String readFile(String fileName) {
		String FileContent = ""; // �ļ��ܳ��Ļ�����ʹ��StringBuffer
		try {
			FileInputStream fis = new FileInputStream(fileName);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			while ((line = br.readLine()) != null) {
				FileContent += line;
				FileContent += "\r\n"; // ���ϻ��з�
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return FileContent;
	}

	/**
	 * д���ļ�(ԭ�����ݱ�����).
	 * 
	 * @param fileName
	 *            �ļ���ַ
	 * @param content
	 *            д������
	 * @return
	 */
	public static boolean writeFile(String fileName, String content) {
		File file = new File(fileName);
		FileOutputStream out = null;
		PrintStream p = null;
		try {
			// ����ļ��������򴴽�һ��
			// ����Ŀ¼
			File parent = file.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
			}

			if (!file.exists()) {
				file.createNewFile();
			}
			// д��Ϣ
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
