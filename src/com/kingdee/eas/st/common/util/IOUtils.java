/*
 * @(#)IOUtils.java
 *
 * 金蝶国际软件集团有限公司版权所有 
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
 * 描述: 提供IO相关的公共方法.
 * 
 * 使用样例:
 * 
 * Properties props = new Properties(); //读属性文件
 * props.load(IOUtils.inputStreamInstance(
 * "com/kingdee/eas/st/weigh/client/WieighBillUserSession.properties"));
 * 
 * //修改属性文件值 props.setProperty("006","this is a apple.");
 * 
 * //写属性文件 OutputStream ops = IOUtils.outputStreamInstance(
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
	 * 描述：按CLASSPATH相对路径获取URL.
	 * 
	 * @param relativePath
	 *            CLASSPATH相对路径串
	 * @return URL
	 * @author:daij 创建时间：2006-12-19
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
	 * 描述：按CLASSPATH相对路径获取资源路径串.
	 * 
	 * @param relativePath
	 *            CLASSPATH相对路径串
	 * @return String
	 * @author:daij 创建时间：2006-12-19
	 *              <p>
	 */
	public static String resourcePath(String relativePath) {
		URL url = resourceURL(relativePath);
		return (isExistsPath(url)) ? url.getPath() : null;
	}

	/**
	 * 
	 * 描述：按CLASSPATH相对路径判定路径是否存在.
	 * 
	 * @param relativePath
	 *            CLASSPATH相对路径串
	 * @return boolean
	 * @author:daij 创建时间：2006-12-19
	 *              <p>
	 */
	public static boolean isExistsPath(String relativePath) {
		return isExistsPath(resourceURL(relativePath));
	}

	/**
	 * 
	 * 描述：按URL判定路径是否存在.
	 * 
	 * @param url
	 *            URL串
	 * @return boolean
	 * @author:daij 创建时间：2006-12-19
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
	 * 描述：按CLASSPATH相对路径构建InputStream
	 * 
	 * @param relativePath
	 *            CLASSPATH相对路径串
	 * @return InputStream
	 * @author:daij 创建时间：2006-12-19
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
	 * 描述：按File构建InputStream
	 * 
	 * @param file
	 *            文件
	 * @return InputStream
	 * @throws MalformedURLException
	 * @throws IOException
	 * @author:daij 创建时间：2007-3-5
	 *              <p>
	 */
	public static InputStream inputStreamInstance(File file)
			throws MalformedURLException, IOException {
		return (isExistsFile(file) && isExistsPath(file.toURL())) ? file
				.toURL().openStream() : null;
	}

	/**
	 * 
	 * 描述：按CLASSPATH相对路径构建OutputStream 注意: 1.在jar包中获取到的url.path 形如:
	 * file:/E:/Jcode/EP/jar/d_io.jar!/io/BOTPProcessor.properties
	 * 是无法直接构造出OutputStream进行写入的,提示: java.io.FileNotFoundException:
	 * file:W:\eas\Client
	 * \client\lib\client\eas\st_weigh-client.jar!\com\kingdee\
	 * eas\st\weigh\client\WeighSession.properties (文件名、目录名或卷标语法不正确。)
	 * 
	 * 2.要能够构造出OutputStream,拿到非空的URL串不够,必须确保File.exists == true.
	 * 
	 * @param relativePath
	 *            CLASSPATH相对路径串
	 * @return OutputStream
	 * @throws FileNotFoundException
	 * @author:daij 创建时间：2006-12-19
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
	 * 描述：按文件构建OutputStream
	 * 
	 * @param file
	 *            文件
	 * @return OutputStream
	 * @throws FileNotFoundException
	 * @author:daij 创建时间：2007-3-5
	 *              <p>
	 */
	public static OutputStream outputStreamInstance(File file)
			throws FileNotFoundException {
		return (isExistsFile(file)) ? new FileOutputStream(file) : null;
	}

	/**
	 * 
	 * 描述：判断文件是否确实存在
	 * 
	 * @param file
	 *            文件
	 * @return boolean
	 * @author:daij 创建时间：2007-3-5
	 *              <p>
	 */
	public static boolean isExistsFile(File file) {
		return (STQMUtils.isNotNull(file) && file.exists());
	}

	/**
	 * 
	 * 描述：在用户应用的物理目录创建指定扩展名文件.
	 * 
	 * @param fileName
	 *            文件名(包含扩展名)
	 * @return File
	 * @throws IOException
	 * @author:daij 创建时间：2007-3-5
	 *              <p>
	 */
	public static File fileForUserDir(String fileName) throws IOException {
		return fileForSpecifyDir(System.getProperty("user.dir"), fileName);
	}

	/**
	 * 
	 * 描述：在$EAS_HOME$/client下创建指定扩展名文件.
	 * 
	 * @param fileName
	 *            文件名(包含扩展名)
	 * @return File
	 * @throws IOException
	 * @author:daij 创建时间：2007-3-6
	 *              <p>
	 */
	public static File fileForEASClient(String fileName) throws IOException {
		// EAS应用的Home目录.
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
	 * 描述：按指定目录创建文件.
	 * 
	 * @param dir
	 *            指定目录
	 * @param fileName
	 *            指定文件名(包含扩展名)
	 * @return File
	 * @throws IOException
	 * @author:daij 创建时间：2007-3-6
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
						// 构造文件
						StringBuffer f = new StringBuffer();
						f.append(dir).append(File.separator).append(fileName);
						// 文件不存在就创建指定文件
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
