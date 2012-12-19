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
	
	
	
	
	/**
	 * ���Notepad++ �Ƿ�����
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
				//������óɹ�������Notepad++�Ѿ������ڣ�ҲҪ��������
				if (!notepadEXE.exists()) {
					mem.lb_Message.setText("Notepad++���λ��ʧЧ������������");
					mem.lb_Message.setStyle("-fx-text-fill:red");
				}
				mem.popup.txtf_NotepadPath.setText(notepadEXE.getAbsolutePath());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			//�����ڣ���Ҫ��������,��������
			mem.lb_Message.setText("��⵽Notepad++δ���ã�Ч�����ܶ�");
			mem.lb_Message.setStyle("-fx-text-fill:red");
		}
	}
	
	/**
	 * �洢ini��Ϣ��notepad++��·��
	 */
	public static void storeProperty(){
		//�Ȼ�ȡ�û�ѡ��
		String notepadPath = mem.popup.txtf_NotepadPath.getText();
		Properties p = new Properties();
		p.put(AppMem.PROPERTY_NAME, notepadPath);
		
		///�洢·��
		//��ȡϵͳ��ʱ·��
		String sysTmpDir = System.getProperty("java.io.tmpdir");
		//�����ļ�
		File iniFile = new File(sysTmpDir+File.separator+"utf8tool.ini");
		//���
		try {
			OutputStream out = new FileOutputStream(iniFile);
			p.store(out, "UTF8ת�빤��---Geekxx.com");
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * ��notepad++���ļ�
	 * @param filepath �ļ���·��
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
	 * ��notepad++���ļ�
	 * @param file �ļ�
	 */
	public static void editInNotepad(File file){
		editInNotepad(file.getAbsolutePath());
	}
}
