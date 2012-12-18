package com.geekxx.utf8.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import com.geekxx.utf8.AppMem;


/**
 * �����࣬�ܶೣ�õķ�����װ
 * @author Gordon
 */
public class CommonUtil {
	
	private static AppMem mem = AppMem.getInstance();
	
	/**
	 * ��ָ��Ŀ¼�л�ȡ���е��ļ���������Ŀ¼�У����޵ݹ�
	 * @param path ָ����·��(Ŀ¼)��File��ʽ
	 * @return ����һ�����ϣ������Ƿ����������ļ�
	 */
	public static List<File> getFilesWithPath(File path,String[] filter){
		//����ļ������ڣ�����
		if (!path.exists()) {
			return null;
		}
		//�г���ǰ�ļ��е���������
		File[] temps = path.listFiles();
		//����
		for (File file : temps) {
			//����ļ��Ǹ�Ŀ¼����ô�ݹ�
			if(file.isDirectory()){
				getFilesWithPath(file,filter);
			}
			//������ļ�����ô����
			else{
				String name = file.getName();
				//�õ���׺��
				int last = name.lastIndexOf(".");
				String suffix = name.substring(last+1, name.length());
				
				//�����������ݣ����������ļ�������
				for (String f : filter) {
					if(f.equals(suffix)){
						mem.filteredFiles.add(file);
					}
				}
			}
		}
		
		return mem.filteredFiles;
	}

	/**
	 * ����ļ��Ƿ���UTF8 ֻ�ܼ�����BOM��UTF8
	 * @param allFile Ҫ�����ļ�����
	 * @return �����UTF8�ͷ���true
	 */
	public static boolean  detectUTF8(File file){
		try {
			InputStream in = new FileInputStream(file);
			byte[] header = new byte[3];
			in.read(header);
			in.close();
			if(header[0] == -17 && header[1]== -69 && header[2]==-65){
				return true;
			}
			else{
				System.out.println(file.getAbsolutePath()+"����utf8");
				return false;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showConfirmDialog(null, file.getAbsolutePath()+"������");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
