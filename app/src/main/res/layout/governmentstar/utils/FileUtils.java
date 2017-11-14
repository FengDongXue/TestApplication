package com.lanwei.governmentstar.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.FileProvider;
import android.util.Log;

public class FileUtils {
	
	// 复制文件
	public static void copyFile(File sourceFile, File targetFile) throws IOException {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			outBuff.flush();
		} finally {
			if (inBuff != null)
				inBuff.close();
			if (outBuff != null)
				outBuff.close();
		}
	}
	
	    /**
		 * 写文本文件 在Android系统中，文件保存在 /data/data/PACKAGE_NAME/files 目录下
		 * 
		 * @param context
		 */
		public static void write(Context context, String fileName, String content) {
			if (content == null)
				content = "";

			try {
				FileOutputStream fos = context.openFileOutput(fileName,
						Context.MODE_PRIVATE);
				fos.write(content.getBytes());

				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		/**
		 * 读取文本文件
		 * 
		 * @param context
		 * @param fileName
		 * @return
		 */
		public static String read(Context context, String fileName) {
			try {
				FileInputStream in = context.openFileInput(fileName);
				return readInStream(in);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return "";
		}

		public static String readInStream(InputStream inStream) {
			try {
				ByteArrayOutputStream outStream = new ByteArrayOutputStream();
				byte[] buffer = new byte[512];
				int length = -1;
				while ((length = inStream.read(buffer)) != -1) {
					outStream.write(buffer, 0, length);
				}

				outStream.close();
				inStream.close();
				return outStream.toString();
			} catch (IOException e) {
			}
			return null;
		}

		public static File createFile(String folderPath, String fileName) {
			File destDir = new File(folderPath);
			if (!destDir.exists()) {
				destDir.mkdirs();
			}
			return new File(folderPath, fileName + fileName);
		}

		/**
		 * 向手机写文件
		 * 
		 * @param buffer
		 * @param folder
		 * @param fileName
		 * @return
		 */
		public static boolean writeFile(byte[] buffer, String folder,
				String fileName) {
			boolean writeSucc = false;
			File fileDir = new File(folder.substring(0,
					folder.lastIndexOf(File.separator)));
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			File file = new File(folder + fileName);
			FileOutputStream out = null;
			try {
				out = new FileOutputStream(file);
				out.write(buffer);
				writeSucc = true;
			} catch (Exception e) {
				e.printStackTrace();
				writeSucc = false;
			} finally {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			return writeSucc;
		}

		/**
		 * 根据文件绝对路径获取文件名
		 * 
		 * @param filePath
		 * @return
		 */
		public static String getFileName(String filePath) {
			if (StringUtils.isEmpty(filePath))
				return "";
			return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
		}

		/**
		 * 根据文件的绝对路径获取文件名但不包含扩展名
		 * 
		 * @param filePath
		 * @return
		 */
		public static String getFileNameNoFormat(String filePath) {
			if (StringUtils.isEmpty(filePath)) {
				return "";
			}
			int point = filePath.lastIndexOf('.');
			return filePath.substring(filePath.lastIndexOf(File.separator) + 1,
					point);
		}

		/**
		 * 获取文件扩展名
		 * 
		 * @param fileName
		 * @return
		 */
		public static String getFileFormat(String fileName) {
			if (StringUtils.isEmpty(fileName))
				return "";

			int point = fileName.lastIndexOf('.');
			return fileName.substring(point + 1);
		}

		/**
		 * 获取文件大小
		 * 
		 * @param filePath
		 * @return
		 */
		public static long getFileSize(String filePath) {
			long size = 0;

			File file = new File(filePath);
			if (file != null && file.exists()) {
				size = file.length();
			}
			return size;
		}

		/**
		 * 获取文件大小
		 * 
		 * @param size
		 *            字节
		 * @return
		 */
		public static String getFileSize(long size) {
			if (size <= 0)
				return "(0.0M)";
			java.text.DecimalFormat df = new java.text.DecimalFormat("##.##");
			float temp = (float) size / 1024;
			if (temp >= 1024) {
				return "("+df.format(temp / 1024) + "M)";
			} else {
				return "("+df.format(temp) + "K)";
			}
		}

		/**
		 * 转换文件大小
		 * 
		 * @param fileS
		 * @return B/KB/MB/GB
		 */
		public static String formatFileSize(long fileS) {
			java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
			String fileSizeString = "";
			if (fileS < 1024) {
				fileSizeString = df.format((double) fileS) + "B";
			} else if (fileS < 1048576) {
				fileSizeString = df.format((double) fileS / 1024) + "KB";
			} else if (fileS < 1073741824) {
				fileSizeString = df.format((double) fileS / 1048576) + "MB";
			} else {
				fileSizeString = df.format((double) fileS / 1073741824) + "G";
			}
			return fileSizeString;
		}

		/**
		 * 获取目录文件大小
		 * 
		 * @param dir
		 * @return
		 */
		public static long getDirSize(File dir) {
			if (dir == null) {
				return 0;
			}
			if (!dir.isDirectory()) {
				return 0;
			}
			long dirSize = 0;
			File[] files = dir.listFiles();
			for (File file : files) {
				if (file.isFile()) {
					dirSize += file.length();
				} else if (file.isDirectory()) {
					dirSize += file.length();
					dirSize += getDirSize(file); // 递归调用继续统计
				}
			}
			return dirSize;
		}

		/**
		 * 获取目录文件个数
		 * 
		 * @param dir
		 * @return
		 */
		public long getFileList(File dir) {
			long count = 0;
			File[] files = dir.listFiles();
			count = files.length;
			for (File file : files) {
				if (file.isDirectory()) {
					count = count + getFileList(file);// 递归
					count--;
				}
			}
			return count;
		}

		public static byte[] toBytes(InputStream in) throws IOException {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int ch;
			while ((ch = in.read()) != -1) {
				out.write(ch);
			}
			byte buffer[] = out.toByteArray();
			out.close();
			return buffer;
		}

		/**
		 * 检查文件是否存在
		 * 
		 * @param name
		 * @return
		 */
		public static boolean checkFileExists(String name) {
			boolean status;
			if (!name.equals("")) {
				File path = Environment.getExternalStorageDirectory();
				File newPath = new File(path.toString() + "/Download/" + name);
				status = newPath.exists();
			} else {
				status = false;
			}
			return status;
		}

		/**
		 * 检查路径是否存在
		 * 
		 * @param path
		 * @return
		 */
		public static boolean checkFilePathExists(String path) {
			return new File(path).exists();
		}

		/**
		 * 计算SD卡的剩余空间
		 * 
		 * @return 返回-1，说明没有安装sd卡
		 */
		@SuppressWarnings("deprecation")
		public static long getFreeDiskSpace() {
			String status = Environment.getExternalStorageState();
			long freeSpace = 0;
			if (status.equals(Environment.MEDIA_MOUNTED)) {
				try {
					File path = Environment.getExternalStorageDirectory();
					StatFs stat = new StatFs(path.getPath());
					long blockSize = stat.getBlockSize();
					long availableBlocks = stat.getAvailableBlocks();
					freeSpace = availableBlocks * blockSize / 1024;
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				return -1;
			}
			return (freeSpace);
		}

		/**
		 * 新建目录
		 * 
		 * @param directoryName
		 * @return
		 */
		public static boolean createDirectory(String directoryName) {
			boolean status;
			if (!directoryName.equals("")) {
				File path = Environment.getExternalStorageDirectory();
				File newPath = new File(path.toString() + directoryName);
				status = newPath.mkdir();
				status = true;
			} else
				status = false;
			return status;
		}

		/**
		 * 检查是否安装SD卡
		 * 
		 * @return
		 */
		public static boolean checkSaveLocationExists() {
			String sDCardStatus = Environment.getExternalStorageState();
			boolean status;
			if (sDCardStatus.equals(Environment.MEDIA_MOUNTED)) {
				status = true;
			} else
				status = false;
			return status;
		}
		
		/**
		 * 检查是否安装外置的SD卡
		 * 
		 * @return
		 */
		public static boolean checkExternalSDExists() {
			
			Map<String, String> evn = System.getenv();
			return evn.containsKey("SECONDARY_STORAGE");
		}

		/**
		 * 删除目录(包括：目录里的所有文件)
		 * 
		 * @param fileName
		 * @return
		 */
		public static boolean deleteDirectory(String fileName) {
			boolean status;
			SecurityManager checker = new SecurityManager();
			
			if (!fileName.equals("")) {

				File path = Environment.getExternalStorageDirectory();
				File newPath = new File(path.toString() + fileName);
				checker.checkDelete(newPath.toString());
				if (newPath.isDirectory()) {
					String[] listfile = newPath.list();
					// delete all files within the specified directory and then
					// delete the directory
					try {
						for (int i = 0; i < listfile.length; i++) {
							File deletedFile = new File(newPath.toString() + "/"
									+ listfile[i].toString());
							deletedFile.delete();
						}
						newPath.delete();
//						Log.d("DirectoryManager deleteDirectory", fileName);
						status = true;
					} catch (Exception e) {
						e.printStackTrace();
						status = false;
					}

				} else
					status = false;
			} else
				status = false;
			return status;
		}

		/**
		 * 删除文件
		 * 
		 * @param fileName
		 * @return
		 */
		public static boolean deleteFile(String fileName) {
			boolean status;
			SecurityManager checker = new SecurityManager();

			if (!fileName.equals("")) {

				File newPath = new File(fileName);
				checker.checkDelete(newPath.toString());
				if (newPath.isFile()) {
					try {
//						Log.i("DirectoryManager deleteFile", fileName);
						newPath.delete();
						status = true;
					} catch (SecurityException se) {
						se.printStackTrace();
						status = false;
					}
				} else
					status = false;
			} else
				status = false;
			return status;
		}

		/**
		 * 删除空目录
		 * 
		 * 返回 0代表成功 ,1 代表没有删除权限, 2代表不是空目录,3 代表未知错误
		 * 
		 * @return
		 */
		public static int deleteBlankPath(String path) {
			File f = new File(path);
			if (!f.canWrite()) {
				return 1;
			}
			if (f.list() != null && f.list().length > 0) {
				return 2;
			}
			if (f.delete()) {
				return 0;
			}
			return 3;
		}

		/**
		 * 重命名
		 * 
		 * @param oldName
		 * @param newName
		 * @return
		 */
		public static boolean reNamePath(String oldName, String newName) {
			File f = new File(oldName);
			return f.renameTo(new File(newName));
		}

		/**
		 * 删除文件
		 * 
		 * @param filePath
		 */
		public static boolean deleteFileWithPath(String filePath) {
			SecurityManager checker = new SecurityManager();
			File f = new File(filePath);
			checker.checkDelete(filePath);
			if (f.isFile()) {
//				Log.i("DirectoryManager deleteFile", filePath);
				f.delete();
				return true;
			}
			return false;
		}
		
		/**
		 * 清空一个文件夹
		 * @param filePath
		 */
		public static void clearFileWithPath(String filePath) {
			List<File> files = FileUtils.listPathFiles(filePath);
			if (files.isEmpty()) {
				return;
			}
			for (File f : files) {
				if (f.isDirectory()) {
					clearFileWithPath(f.getAbsolutePath());
				} else {
					f.delete();
				}
			}
		}
		
		/**
		 * 清空一个文件夹，如果传入的directory是个文件，将不做处理 
		 * @param directory
		 */
		public static void deleteFilesByDirectory(File directory) {
			if (directory != null && directory.exists() && directory.isDirectory()) {
				for (File child : directory.listFiles()) {
					if (child.isDirectory()) {
						deleteFilesByDirectory(child);
					} 
					child.delete();
				}
			}
		}

		/**
		 * 获取SD卡的根目录
		 * 
		 * @return
		 */
		public static String getSDRoot() {
			
			return Environment.getExternalStorageDirectory().getAbsolutePath();
		}
		
		/**
		 * 获取手机外置SD卡的根目录
		 * 
		 * @return
		 */
		public static String getExternalSDRoot() {
			
			Map<String, String> evn = System.getenv();
			
			return evn.get("SECONDARY_STORAGE");
		}

		/**
		 * 列出root目录下所有子目录
		 * 
		 * @param root
		 * @return 绝对路径
		 */
		public static List<String> listPath(String root) {
			List<String> allDir = new ArrayList<String>();
			SecurityManager checker = new SecurityManager();
			File path = new File(root);
			checker.checkRead(root);
			// 过滤掉以.开始的文件夹
			if (path.isDirectory()) {
				for (File f : path.listFiles()) {
					if (f.isDirectory() && !f.getName().startsWith(".")) {
						allDir.add(f.getAbsolutePath());
					}
				}
			}
			return allDir;
		}
		
		/**
		 * 获取一个文件夹下的所有文件
		 * @param root
		 * @return
		 */
		public static List<File> listPathFiles(String root) {
			List<File> allDir = new ArrayList<File>();
			SecurityManager checker = new SecurityManager();
			File path = new File(root);
			checker.checkRead(root);
			File[] files = path.listFiles();
			for (File f : files) {
				if (f.isFile())
					allDir.add(f);
				else 
					listPath(f.getAbsolutePath());
			}
			return allDir;
		}

		public enum PathStatus {
			SUCCESS, EXITS, ERROR
		}

		/**
		 * 创建目录
		 * 
		 * @param newPath
		 */
		public static PathStatus createPath(String newPath) {
			File path = new File(newPath);
			if (path.exists()) {
				return PathStatus.EXITS;
			}
			if (path.mkdir()) {
				return PathStatus.SUCCESS;
			} else {
				return PathStatus.ERROR;
			}
		}

		/**
		 * 截取路径名
		 * 
		 * @return
		 */
		public static String getPathName(String absolutePath) {
			int start = absolutePath.lastIndexOf(File.separator) + 1;
			int end = absolutePath.length();
			return absolutePath.substring(start, end);
		}
		
		/**
		 * 获取应用程序缓存文件夹下的指定目录
		 * @param context
		 * @param dir
		 * @return
		 */
		public static String getAppCache(Context context, String dir) {
			String savePath = context.getCacheDir().getAbsolutePath() + "/" + dir + "/";
			File savedir = new File(savePath);
			if (!savedir.exists()) {
				savedir.mkdirs();
			}
			savedir = null;
			return savePath;
		}

	

	// 复制文件夹
	public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
		(new File(targetDir)).mkdirs();
		File[] file = (new File(sourceDir)).listFiles();
		for (int i = 0; i < file.length; i++) {
			if (file[i].isFile()) {
				File sourceFile = file[i];
				File targetFile = new File( new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
				copyFile(sourceFile, targetFile);
			}
			if (file[i].isDirectory()) {
				String dir1 = sourceDir + "/" + file[i].getName();
				String dir2 = targetDir + "/" + file[i].getName();
				copyDirectiory(dir1, dir2);
			}
		}
	}

	/**
	 * 删除文件夹
	 */
	public static void del(String filepath) throws IOException {
		File f = new File(filepath);// 定义文件路径
		if (f.exists() && f.isDirectory()) {// 判断是文件还是目录
			if (f.listFiles().length == 0) {// 若目录下没有文件则直接删除
				f.delete();
			} else {// 若有则把文件放进数组，并判断是否有下级目录
				File delFile[] = f.listFiles();
				int i = f.listFiles().length;
				for (int j = 0; j < i; j++) {
					if (delFile[j].isDirectory()) {
						del(delFile[j].getAbsolutePath());// 递归调用del方法并取得子目录路径
					}
					delFile[j].delete();// 删除文件
				}
			}
		}
	}
	
	public static File getStorageDir(Context paramContext) {
		File storageDir = Environment.getExternalStorageDirectory();
		if (storageDir.exists()) {
			return storageDir;
		}
		storageDir = paramContext.getFilesDir();
		return storageDir;
	}
	
	public static  String getRealPathFromURI(Uri contentUri,Context mContext) {
	    String[] proj = { MediaStore.Images.Media.DATA };
	    CursorLoader loader = new CursorLoader(mContext, contentUri, proj, null, null, null);
	    Cursor cursor = loader.loadInBackground();
	    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	    cursor.moveToFirst();
	    return cursor.getString(column_index);
	}


	/**
	 * 打开文件
	 * @param filePath
	 * @param context
	 */
	public static void openFile(String filePath, Context context) {
		Intent intent = new Intent("android.intent.action.VIEW");
		File file = new File(filePath);
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
			Uri contentUri = FileProvider.getUriForFile(context, "com.lanwei.governmentstar", file);
			String type = getMIMEType(file);
			intent.setDataAndType(contentUri, type);   //设置intent的data和Type属性。
			intent.addCategory("android.intent.category.DEFAULT");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			try {
				context.startActivity(intent);
			} catch (Exception e) {
				e.printStackTrace();
//				BasicUtils.showToast("没有能打开的相关软件", 1000);
			}

		} else {
			String type = getMIMEType(file);
			intent.setDataAndType(Uri.fromFile(file), type);
			intent.addCategory("android.intent.category.DEFAULT");
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
			try {
				context.startActivity(intent);
			} catch (Exception e) {
				e.printStackTrace();
//				BasicUtils.showToast("没有能打开的相关软件", 1000);
			}
		}
	}


	private static String getMIMEType(File file) {

		String type = "*/*";
		String fName = file.getName();
		//获取后缀名前的分隔符"."在fName中的位置。
		int dotIndex = fName.lastIndexOf(".");
		if (dotIndex < 0) {
			return type;
		}
        /* 获取文件的后缀名*/
		String end = fName.substring(dotIndex, fName.length()).toLowerCase();
		if (end == "") return type;
		//在MIME和文件类型的匹配表中找到对应的MIME类型。
		for (int i = 0; i < MIME_MapTable.length; i++) {

			if (end.equals(MIME_MapTable[i][0]))
				type = MIME_MapTable[i][1];
		}
		return type;
	}

	private static String[][] MIME_MapTable = {
			//{后缀名，MIME类型}
			{".3gp", "video/3gpp"},
			{".apk", "application/vnd.android.package-archive"},
			{".asf", "video/x-ms-asf"},
			{".avi", "video/x-msvideo"},
			{".bin", "application/octet-stream"},
			{".bmp", "image/bmp"},
			{".c", "text/plain"},
			{".class", "application/octet-stream"},
			{".conf", "text/plain"},
			{".cpp", "text/plain"},
			{".doc", "application/msword"},
			{".docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
			{".xls", "application/vnd.ms-excel"},
			{".xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
			{".exe", "application/octet-stream"},
			{".gif", "image/gif"},
			{".gtar", "application/x-gtar"},
			{".gz", "application/x-gzip"},
			{".h", "text/plain"},
			{".htm", "text/html"},
			{".html", "text/html"},
			{".jar", "application/java-archive"},
			{".java", "text/plain"},
			{".jpeg", "image/jpeg"},
			{".jpg", "image/jpeg"},
			{".js", "application/x-javascript"},
			{".log", "text/plain"},
			{".m3u", "audio/x-mpegurl"},
			{".m4a", "audio/mp4a-latm"},
			{".m4b", "audio/mp4a-latm"},
			{".m4p", "audio/mp4a-latm"},
			{".m4u", "video/vnd.mpegurl"},
			{".m4v", "video/x-m4v"},
			{".mov", "video/quicktime"},
			{".mp2", "audio/x-mpeg"},
			{".mp3", "audio/x-mpeg"},
			{".mp4", "video/mp4"},
			{".mpc", "application/vnd.mpohun.certificate"},
			{".mpe", "video/mpeg"},
			{".mpeg", "video/mpeg"},
			{".mpg", "video/mpeg"},
			{".mpg4", "video/mp4"},
			{".mpga", "audio/mpeg"},
			{".msg", "application/vnd.ms-outlook"},
			{".ogg", "audio/ogg"},
			{".pdf", "application/pdf"},
			{".png", "image/png"},
			{".pps", "application/vnd.ms-powerpoint"},
			{".ppt", "application/vnd.ms-powerpoint"},
			{".pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"},
			{".prop", "text/plain"},
			{".rc", "text/plain"},
			{".rmvb", "audio/x-pn-realaudio"},
			{".rtf", "application/rtf"},
			{".sh", "text/plain"},
			{".tar", "application/x-tar"},
			{".tgz", "application/x-compressed"},
			{".txt", "text/plain"},
			{".wav", "audio/x-wav"},
			{".wma", "audio/x-ms-wma"},
			{".wmv", "audio/x-ms-wmv"},
			{".wps", "application/vnd.ms-works"},
			{".xml", "text/plain"},
			{".z", "application/x-compress"},
			{".zip", "application/x-zip-compressed"},
			{"", "*/*"}
	};
}
