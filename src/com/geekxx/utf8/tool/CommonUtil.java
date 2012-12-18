package com.geekxx.utf8.tool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * 公共类，很多常用的方法封装
 * @author Gordon
 */
public class CommonUtil {
	
	/**
	 * 符合条件的文件
	 */
	private static final List<File> filteredFiles = new ArrayList<File>();
	
	/**
	 * 符合条件的文件的绝对路径
	 * @deprecated 不需要这个，只需要重新遍历获取。2012年12月18日 13:53:45
	 */
	@Deprecated
	private static final List<String> filteredFilesName = new ArrayList<>();
	/**
	 * 从指定目录中获取所有的文件，包括子目录中，无限递归
	 * @param path 指定的路径(目录)的File形式
	 * @return 返回一个集合，内容是符合条件的文件
	 */
	public static List<File> getFilesWithPath(File path,String[] filter){
		//如果文件不存在，返回
		if (!path.exists()) {
			return null;
		}
		//列出当前文件夹的所有内容
		File[] temps = path.listFiles();
		//遍历
		for (File file : temps) {
			//如果文件是个目录，那么递归
			if(file.isDirectory()){
				getFilesWithPath(file,filter);
			}
			//如果是文件，那么过滤
			else{
				String name = file.getName();
				//得到后缀名
				int last = name.lastIndexOf(".");
				String suffix = name.substring(last+1, name.length());
				
				//遍历过滤内容，符合条件的加入容器
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
