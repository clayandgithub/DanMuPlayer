package com.danmuplayer.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public class IOUtil {

	/**
	 * 把以一种编码格式的输入流转化为另一种编码格式的输出流
	 * 
	 * @return
	 */
	public static void fromIsToOsByCode(InputStream is, OutputStream os,
			String incode, String outcode) {
		BufferedReader reader = null;
		BufferedWriter writer = null;
		try {
			reader = new BufferedReader(new InputStreamReader(is, incode));
			writer = new BufferedWriter(new OutputStreamWriter(os, outcode));
			String line;
			while ((line = reader.readLine()) != null) {
				writer.write(line + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 以指定的格式来读取输入流
	 */
	public static String readStrByCode(InputStream is, String code) {
		StringBuilder builder = new StringBuilder();
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new InputStreamReader(is, code));
			String line;
			while ((line = reader.readLine()) != null) {
				builder.append(line + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return builder.toString();
	}

	/**
	 * 输入InputStream流，返回字符串文字。
	 * 
	 * @param is
	 * @return
	 */
	public static String fromIputStreamToString(InputStream is) {
		if (is == null)
			return null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int i = -1;
		try {
			while ((i = is.read()) != -1) {
				baos.write(i);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return baos.toString();
	}

	/**
	 * 输入InputStream流和文件地址，返回成功与否。
	 * 
	 * @param is
	 * @return
	 */
	public static boolean fromIputStreamToFile(InputStream is,
			String outfilepath) {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;

		try {
			// 新建文件输入流并对它进行缓冲
			inBuff = new BufferedInputStream(is);

			// 新建文件输出流并对它进行缓冲
			outBuff = new BufferedOutputStream(
					new FileOutputStream(outfilepath));

			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				// 关闭流
				if (inBuff != null)

					inBuff.close();
				if (outBuff != null)
					outBuff.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return true;
	}

	/**
	 * 输入文件地址，返回inputStream流。
	 * 
	 * @param is
	 * @return
	 */
	public static InputStream fromFileToIputStream(String infilepath) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(infilepath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fis;
	}

	public static InputStream fromStringToIputStream(String s) {
		if (s != null && !s.equals("")) {
			try {

				ByteArrayInputStream stringInputStream = new ByteArrayInputStream(
						s.getBytes());
				return stringInputStream;
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * 把输入流转化成UTF-8格式的输入流
	 * 
	 * @param in
	 * @param charset
	 * @return
	 * @throws Exception
	 */
	// public static InputStream fromInputStreamToInputStreamInCharset(
	// InputStream in, String charset) throws Exception {
	// StringBuilder builder=new StringBuilder();
	// byte[] buffer = new byte[2048];
	// int len = -1;
	// while ((len = in.read(buffer)) != -1) {
	// builder.append(EncodingUtils.getString(buffer, 0, len, "UTF-8"));
	// }
	// return IOUtil.fromStringToIputStream(builder.toString());
	// }

	public static InputStream getInputStreamFromUrl(String urlstr) {
		try {
			InputStream is = null;
			HttpURLConnection conn = null;
			System.out.println("urlstr:" + urlstr);
			URL url = new URL(urlstr);
			conn = (HttpURLConnection) url.openConnection();
			if (conn.getResponseCode() == 200) {
				is = conn.getInputStream();
				return is;
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return null;
	}

	public static void writerStrByCode(FileOutputStream os, String outcode,
			String str) {
		BufferedWriter writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(os, outcode));
			writer.write(str);
			writer.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}