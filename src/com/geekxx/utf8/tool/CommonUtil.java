package com.geekxx.utf8.tool;

import info.monitorenter.cpdetector.io.ASCIIDetector;
import info.monitorenter.cpdetector.io.ByteOrderMarkDetector;
import info.monitorenter.cpdetector.io.CodepageDetectorProxy;
import info.monitorenter.cpdetector.io.JChardetFacade;
import info.monitorenter.cpdetector.io.ParsingDetector;
import info.monitorenter.cpdetector.io.UnicodeDetector;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.Properties;

import javax.swing.JOptionPane;

import com.geekxx.utf8.AppMem;


/**
 * 公共类，很多常用的方法封装
 * @author Gordon
 */
public class CommonUtil {
	
	
	/**
	 * 第三方的一个文件编码检测器
	 */
	private static CodepageDetectorProxy detector = CodepageDetectorProxy.getInstance();
	
	//  初始化检测器, 等于是增强检测器
	static{
		detector.add(new ByteOrderMarkDetector()); 
	    // The first instance delegated to tries to detect the meta charset attribut in html pages.
	    detector.add(new ParsingDetector(true)); // be verbose about parsing.
	    // This one does the tricks of exclusion and frequency detection, if first implementation is 
	    // unsuccessful:
	    detector.add(JChardetFacade.getInstance()); // Another singleton.
	    detector.add(ASCIIDetector.getInstance()); // Fallbac
	    detector.add(UnicodeDetector.getInstance());
	}
	
	private static AppMem mem = AppMem.getInstance();
	
	/**
	 * 从指定目录中获取所有的文件，包括子目录中，无限递归
	 * @param path 指定的路径(目录)的File形式
	 * @param filter 过滤条件
	 * @return 返回一个集合，内容是符合条件的文件
	 */
	public static void filterFilesWithPath(File path,String[] filter){
		
		//如果文件不存在，返回
		if (!path.exists()) {
			return;
		}
		//列出当前文件夹的所有内容
		File[] temps = path.listFiles();
		//遍历
		for (File file : temps) {
			//如果文件是个目录，那么递归
			if(file.isDirectory()){
				filterFilesWithPath(file,filter);
			}
			//如果是文件，那么过滤条件
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
		
	}

	/**
	 * 检测文件是否是UTF8 只能检查出带BOM的UTF8
	 * @param allFile 要检查的文件集合
	 * @return 如果是UTF8就返回true
	 *
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
	
	
	@Deprecated
	public static void authorize(){
		while(true){
			String input = JOptionPane.showInputDialog("你是？");
			if ("henry".equals(input)) {
				break;
			}
		}
	}
	
	
	/**
	 * 检测文件编码，新的方法。加入了几个牛逼的lib实现了检测无BOM的UTF-8
	 * @param file 被检测文件
	 * @return 返回字符编码
	 */
	public static Charset detectCharset(File file){
		Charset charset = null;
		try {
			charset = detector.detectCodepage(file.toURI().toURL());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return charset;
	}
	
	/**
	 * 自动转码成UTF-8，这是个实验性的功能，暂时仅能支持ASCII和GBK**之类的编码
	 * 转成UTF-8，其他的日本韩国等字符没有试验过
	 * @param file 要转码的文件
	 * @param charset 文件的原始格式
	 * @throws IOException 转码过程中发生的IO异常
	 * 
	 */
	public static final void autoConvert(File file) throws IOException{
		
		//  **先读取文件内容成字符串，一定要用原始编码格式 解读**
		byte[] buffer = new byte[1024];
		ByteArrayOutputStream bao = new ByteArrayOutputStream();
		InputStream in = new FileInputStream(file);
		int len = 0;
		while((len=in.read(buffer))!=-1){
			bao.write(buffer, 0, len);
		}
		//  文件字符串内容
		String body = new String(bao.toByteArray(), Charset.forName("GB2312"));
		//  关闭文件流 后面才能写
		in.close();
		bao.close();
		
		//  **写入转码好的内容覆盖到原文件**
		OutputStream out = new FileOutputStream(file);
		out.write(body.getBytes(Charset.forName("UTF-8")));
		out.close();
	}
	
	
}
