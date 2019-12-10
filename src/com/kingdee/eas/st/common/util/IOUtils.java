/*
 * @(#)IOUtils.java
 *
 * �����������������޹�˾��Ȩ���� 
 */
package com.kingdee.eas.st.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import com.kingdee.eas.basedata.mm.qm.utils.STQMUtils;
import com.kingdee.util.StringUtils;

/**
 * ����: �ṩIO��صĹ�������.
 * 
 * ʹ������:
 * 
 * Properties props = new Properties(); //�������ļ�
 * props.load(IOUtils.inputStreamInstance(
 * "com/kingdee/eas/st/weigh/client/WieighBillUserSession.properties"));
 * 
 * //�޸������ļ�ֵ props.setProperty("006","this is a apple.");
 * 
 * //д�����ļ� OutputStream ops = IOUtils.outputStreamInstance(
 * "com/kingdee/eas/st/weigh/client/WieighBillUserSession.properties");
 * props.store(ops,null); ops.close();
 * 
 * props.load(IOUtils.inputStreamInstance(
 * "com/kingdee/eas/st/weigh/client/WieighBillUserSession.properties"));
 * 
 * 
 * @author daij date:2006-12-19
 *         <p>
 * @version EAS5.2.0
 */
public abstract class IOUtils {

	/**
	 * 
	 * ��������CLASSPATH���·����ȡURL.
	 * 
	 * @param relativePath
	 *            CLASSPATH���·����
	 * @return URL
	 * @author:daij ����ʱ�䣺2006-12-19
	 *              <p>
	 */
	public static URL resourceURL(String relativePath) {
		URL url = null;
		if (!StringUtils.isEmpty(relativePath)) {
			url = IOUtils.class.getClassLoader().getResource(relativePath);
		}
		return url;
	}

	/**
	 * 
	 * ��������CLASSPATH���·����ȡ��Դ·����.
	 * 
	 * @param relativePath
	 *            CLASSPATH���·����
	 * @return String
	 * @author:daij ����ʱ�䣺2006-12-19
	 *              <p>
	 */
	public static String resourcePath(String relativePath) {
		URL url = resourceURL(relativePath);
		return (isExistsPath(url)) ? url.getPath() : null;
	}

	/**
	 * 
	 * ��������CLASSPATH���·���ж�·���Ƿ����.
	 * 
	 * @param relativePath
	 *            CLASSPATH���·����
	 * @return boolean
	 * @author:daij ����ʱ�䣺2006-12-19
	 *              <p>
	 */
	public static boolean isExistsPath(String relativePath) {
		return isExistsPath(resourceURL(relativePath));
	}

	/**
	 * 
	 * ��������URL�ж�·���Ƿ����.
	 * 
	 * @param url
	 *            URL��
	 * @return boolean
	 * @author:daij ����ʱ�䣺2006-12-19
	 *              <p>
	 */
	public static boolean isExistsPath(URL url) {
		String path = null;
		if (STQMUtils.isNotNull(url)) {
			path = url.getPath();
		}
		return (!StringUtils.isEmpty(path));
	}

	/**
	 * 
	 * ��������CLASSPATH���·������InputStream
	 * 
	 * @param relativePath
	 *            CLASSPATH���·����
	 * @return InputStream
	 * @author:daij ����ʱ�䣺2006-12-19
	 *              <p>
	 */
	public static InputStream inputStreamInstance(String relativePath) {
		InputStream ips = null;
		if (isExistsPath(relativePath)) {
			ips = IOUtils.class.getClassLoader().getResourceAsStream(
					relativePath);
		}
		return ips;
	}

	/**
	 * 
	 * ��������File����InputStream
	 * 
	 * @param file
	 *            �ļ�
	 * @return InputStream
	 * @throws MalformedURLException
	 * @throws IOException
	 * @author:daij ����ʱ�䣺2007-3-5
	 *              <p>
	 */
	public static InputStream inputStreamInstance(File file)
			throws MalformedURLException, IOException {
		return (isExistsFile(file) && isExistsPath(file.toURL())) ? file
				.toURL().openStream() : null;
	}

	/**
	 * 
	 * ��������CLASSPATH���·������OutputStream ע��: 1.��jar���л�ȡ����url.path ����:
	 * file:/E:/Jcode/EP/jar/d_io.jar!/io/BOTPProcessor.properties
	 * ���޷�ֱ�ӹ����OutputStream����д���,��ʾ: java.io.FileNotFoundException:
	 * file:W:\eas\Client
	 * \client\lib\client\eas\st_weigh-client.jar!\com\kingdee\
	 * eas\st\weigh\client\WeighSession.properties (�ļ�����Ŀ¼�������﷨����ȷ��)
	 * 
	 * 2.Ҫ�ܹ������OutputStream,�õ��ǿյ�URL������,����ȷ��File.exists == true.
	 * 
	 * @param relativePath
	 *            CLASSPATH���·����
	 * @return OutputStream
	 * @throws FileNotFoundException
	 * @author:daij ����ʱ�䣺2006-12-19
	 *              <p>
	 */
	public static OutputStream outputStreamInstance(String relativePath)
			throws FileNotFoundException {
		String path = resourcePath(relativePath);
		return (STQMUtils.isNull(path)) ? null : outputStreamInstance(new File(
				path));
	}

	/**
	 * 
	 * ���������ļ�����OutputStream
	 * 
	 * @param file
	 *            �ļ�
	 * @return OutputStream
	 * @throws FileNotFoundException
	 * @author:daij ����ʱ�䣺2007-3-5
	 *              <p>
	 */
	public static OutputStream outputStreamInstance(File file)
			throws FileNotFoundException {
		return (isExistsFile(file)) ? new FileOutputStream(file) : null;
	}

	/**
	 * 
	 * �������ж��ļ��Ƿ�ȷʵ����
	 * 
	 * @param file
	 *            �ļ�
	 * @return boolean
	 * @author:daij ����ʱ�䣺2007-3-5
	 *              <p>
	 */
	public static boolean isExistsFile(File file) {
		return (STQMUtils.isNotNull(file) && file.exists());
	}

	/**
	 * 
	 * ���������û�Ӧ�õ�����Ŀ¼����ָ����չ���ļ�.
	 * 
	 * @param fileName
	 *            �ļ���(������չ��)
	 * @return File
	 * @throws IOException
	 * @author:daij ����ʱ�䣺2007-3-5
	 *              <p>
	 */
	public static File fileForUserDir(String fileName) throws IOException {
		return fileForSpecifyDir(System.getProperty("user.dir"), fileName);
	}

	/**
	 * 
	 * ��������$EAS_HOME$/client�´���ָ����չ���ļ�.
	 * 
	 * @param fileName
	 *            �ļ���(������չ��)
	 * @return File
	 * @throws IOException
	 * @author:daij ����ʱ�䣺2007-3-6
	 *              <p>
	 */
	public static File fileForEASClient(String fileName) throws IOException {
		// EASӦ�õ�HomeĿ¼.
		String easHomeString = System.getProperty("EAS_HOME");
		if (StringUtils.isEmpty(easHomeString)) {
			easHomeString = "C:\\kingdee\\eas";
		}
		File file = null;
		StringBuffer dir = new StringBuffer();
		dir.append(easHomeString).append(File.separator).append("client");
		File dirF = new File(dir.toString());
		if (dirF.exists()) {
			file = fileForSpecifyDir(dir.toString(), fileName);
		}
		return file;
	}

	/**
	 * 
	 * ��������ָ��Ŀ¼�����ļ�.
	 * 
	 * @param dir
	 *            ָ��Ŀ¼
	 * @param fileName
	 *            ָ���ļ���(������չ��)
	 * @return File
	 * @throws IOException
	 * @author:daij ����ʱ�䣺2007-3-6
	 *              <p>
	 */
	public static File fileForSpecifyDir(String dir, String fileName)
			throws IOException {
		boolean isRealFile = true;

		File file = null;
		if (!StringUtils.isEmpty(dir)) {
			File tempFile = new File(dir);

			if (STQMUtils.isNotNull(tempFile)) {
				if (tempFile.isDirectory() && isExistsFile(tempFile)) {
					if (!StringUtils.isEmpty(fileName)) {
						// �����ļ�
						StringBuffer f = new StringBuffer();
						f.append(dir).append(File.separator).append(fileName);
						// �ļ������ھʹ���ָ���ļ�
						if (!StringUtils.isEmpty(f.toString())) {
							file = new File(f.toString());

							if (!isExistsFile(file)) {
								isRealFile = file.createNewFile();
							}
						}
					}
				} else if (tempFile.isFile()) {
					file = tempFile;
					if (!isExistsFile(tempFile)) {
						isRealFile = tempFile.createNewFile();
					}
				}
			}
		}
		return (isRealFile) ? file : null;
	}
}
