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
 * 公共类，很多常用的方法封装
 * @author Gordon
 */
public class CommonUtil {
	
	private static AppMem mem = AppMem.getInstance();
	
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
						mem.filteredFiles.add(file);
					}
				}
			}
		}
		
		return mem.filteredFiles;
	}

	/**
	 * 检测文件是否是UTF8 只能检查出带BOM的UTF8
	 * @param allFile 要检查的文件集合
	 * @return 如果是UTF8就返回true
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
				System.out.println(file.getAbsolutePath()+"不是utf8");
				return false;
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			JOptionPane.showConfirmDialog(null, file.getAbsolutePath()+"不存在");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
