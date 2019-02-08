package com.example.desk.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtils {

	/**
	 * �����ļ���·��
	 */
	public static String SDPATH = Environment.getExternalStorageDirectory()
			+ "/TEST_PY/";

	/**
	 * ��ͼƬѹ�����浽�ļ���
	 * 
	 * @param bm
	 * @param picName
	 */
	public static void saveBitmap(Bitmap bm, String picName) {
		try {

			// ���û���ļ��оʹ���һ�������ļ���
			if (!isFileExist("")) {
				File tempf = createSDDir("");
			}
			File f = new File(SDPATH, picName + ".JPEG");
			// ������ļ�������ͬ�����ļ�������ɾ����ԭ�ļ�
			if (f.exists()) {
				f.delete();
			}
			FileOutputStream out = new FileOutputStream(f);
			bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����ѹ�� ������Bitmap
	 * 
	 * @param image
	 *            Ҫѹ����ͼƬ
	 * @return ѹ�����ͼƬ
	 */
	private Bitmap compressImage(Bitmap image) {

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// ����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baos��
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // ѭ���ж����ѹ����ͼƬ�Ƿ����100kb,���ڼ���ѹ��
			baos.reset();// ����baos�����baos
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// ����ѹ��options%����ѹ��������ݴ�ŵ�baos��
			options -= 10;// ÿ�ζ�����10
		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// ��ѹ���������baos��ŵ�ByteArrayInputStream��
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// ��ByteArrayInputStream��������ͼƬ
		return bitmap;
	}

	/**
	 * ����ѹ��
	 * 
	 * @param bitmap
	 * @param picName
	 */
	public static void compressImageByQuality(final Bitmap bitmap,
			String picName) {
		// ���û���ļ��оʹ���һ�������ļ���
		if (!isFileExist("")) {
			try {
				File tempf = createSDDir("");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		File f = new File(SDPATH, picName + ".JPEG");
		// ������ļ�������ͬ�����ļ�������ɾ����ԭ�ļ�
		if (f.exists()) {
			f.delete();
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int options = 100;
		// ����ѹ������������100��ʾ��ѹ������ѹ��������ݴ�ŵ�baos��
		bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
		// ѭ���ж����ѹ����ͼƬ�Ƿ����200kb,���ڼ���ѹ��
		while (baos.toByteArray().length / 1024 > 500) {
			// ����baos������һ�ε�д�븲��֮ǰ������
			baos.reset();
			// ͼƬ����ÿ�μ���5
			options -= 5;
			// ���ͼƬ����С��10����ͼƬ������ѹ������Сֵ
			if (options < 0)
				options = 0;
			// ��ѹ�����ͼƬ���浽baos��
			bitmap.compress(Bitmap.CompressFormat.JPEG, options, baos);
			// ���ͼƬ�������ѽ�������򣬲��ٽ���ѹ��
			if (options == 0)
				break;
		}
		// ��ѹ�����ͼƬ����ı�����ָ��·����
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(new File(SDPATH, picName + ".JPEG"));
			fos.write(baos.toByteArray());
			fos.flush();
			fos.close();
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

	/**
	 * �����ļ���
	 * 
	 * @param dirName
	 *            �ļ�������
	 * @return �ļ���·��
	 * @throws IOException
	 */
	public static File createSDDir(String dirName) throws IOException {
		File dir = new File(SDPATH + dirName);
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {

			System.out.println("createSDDir:" + dir.getAbsolutePath());
			System.out.println("createSDDir:" + dir.mkdir());
		}
		return dir;
	}

	/**
	 * �жϸ��ļ��Ƿ���һ����׼�ļ�
	 * 
	 * @param fileName
	 *            �жϵ��ļ�·��
	 * @return �жϽ��
	 */
	public static boolean isFileExist(String fileName) {
		File file = new File(SDPATH + fileName);
		file.isFile();
		return file.exists();
	}

	/**
	 * ɾ��ָ���ļ�
	 * 
	 * @param fileName
	 */
	public static void delFile(String fileName) {
		File file = new File(SDPATH + fileName);
		if (file.isFile()) {
			file.delete();
		}
		file.exists();
	}

	/**
	 * ɾ��ָ���ļ�
	 * @param file
	 */
	public static void deleteFile(File file) {
		if (file.exists()) { // �ж��ļ��Ƿ����
			if (file.isFile()) { // �ж��Ƿ����ļ�
				file.delete(); // delete()���� ��Ӧ��֪�� ��ɾ������˼;
			} else if (file.isDirectory()) { // �����������һ��Ŀ¼
				File files[] = file.listFiles(); // ����Ŀ¼�����е��ļ� files[];
				for (int i = 0; i < files.length; i++) { // ����Ŀ¼�����е��ļ�
					deleteFile(files[i]); // ��ÿ���ļ� ������������е���
				}
			}
			file.delete();
		} else {
			Log.i("TAG", "�ļ������ڣ�");
		}
	}

	/**
	 * ɾ��ָ���ļ����е������ļ�
	 */
	public static void deleteDir() {
		File dir = new File(SDPATH);
		if (dir == null || !dir.exists() || !dir.isDirectory())
			return;

		for (File file : dir.listFiles()) {
			if (file.isFile())
				file.delete();
			else if (file.isDirectory())
				deleteDir();
		}
		dir.delete();
	}

	/**
	 * �ж��Ƿ���ڸ��ļ�
	 * 
	 * @param path
	 *            �ļ�·��
	 * @return
	 */
	public static boolean fileIsExists(String path) {
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {

			return false;
		}
		return true;
	}

}
