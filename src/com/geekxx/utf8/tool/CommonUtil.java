package com.geekxx.utf8.tool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

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
	/**
	 * @param file
	 * @return
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
	
	
	
	
	/**
	 * 检测Notepad++ 是否被配置
	 */
	public static void detectNotepad(){
		String sysTmpDir = System.getProperty("java.io.tmpdir");
		File iniFile = new File(sysTmpDir+File.separator+"utf8tool.ini");
		if(iniFile.exists()){
			Properties p = new Properties();
			try {
				InputStream in = new FileInputStream(iniFile);
				p.load(in);
				AppMem.getInstance().notepadPath = p.getProperty(AppMem.PROPERTY_NAME);
				in.close();
				File notepadEXE = new File(AppMem.getInstance().notepadPath);
				//如果配置成功，不过Notepad++已经不存在，也要重新配置
				if (!notepadEXE.exists()) {
					mem.lb_Message.setText("Notepad++插件位置失效，请重新配置");
					mem.lb_Message.setStyle("-fx-text-fill:red");
				}
				mem.popup.txtf_NotepadPath.setText(notepadEXE.getAbsolutePath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			//不存在，需要重新配置,给出警告
			mem.lb_Message.setText("检测到Notepad++未配置，效果会差很多");
			mem.lb_Message.setStyle("-fx-text-fill:red");
		}
	}
	
	/**
	 * 存储ini信息，notepad++的路径
	 */
	public static void storeProperty(){
		//先获取用户选择
		String notepadPath = mem.popup.txtf_NotepadPath.getText();
		Properties p = new Properties();
		p.put(AppMem.PROPERTY_NAME, notepadPath);
		
		///存储路径
		//获取系统临时路径
		String sysTmpDir = System.getProperty("java.io.tmpdir");
		//建立文件
		File iniFile = new File(sysTmpDir+File.separator+"utf8tool.ini");
		//存放
		try {
			OutputStream out = new FileOutputStream(iniFile);
			p.store(out, "UTF8转码工具---Geekxx.com");
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 用notepad++打开文件
	 * @param filepath 文件的路径
	 */
	public static void editInNotepad(String filepath){
		
		String cmd = mem.notepadPath+" "+filepath;
		
		try {
			Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 用notepad++打开文件
	 * @param file 文件
	 */
	public static void editInNotepad(File file){
		editInNotepad(file.getAbsolutePath());
	}
}
