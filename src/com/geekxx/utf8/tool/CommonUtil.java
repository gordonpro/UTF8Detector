package com.geekxx.utf8.tool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * �����࣬�ܶೣ�õķ�����װ
 * @author Gordon
 */
public class CommonUtil {
	
	/**
	 * �����������ļ�
	 */
	private static final List<File> filteredFiles = new ArrayList<File>();
	
	/**
	 * �����������ļ��ľ���·��
	 * @deprecated ����Ҫ�����ֻ��Ҫ���±�����ȡ��2012��12��18�� 13:53:45
	 */
	@Deprecated
	private static final List<String> filteredFilesName = new ArrayList<>();
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
						filteredFiles.add(file);
						filteredFilesName.add(file.getName());
					}
				}
			}
		}
		
		return filteredFiles;
	}
}
