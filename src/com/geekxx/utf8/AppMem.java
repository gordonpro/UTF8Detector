package com.geekxx.utf8;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import com.geekxx.utf8.tool.ButtonEvent;
import com.geekxx.utf8.tool.ListViewEvent;


/**
 * ȫ�ֵı������,����ֻ������
 * @author Gordon
 */
public class AppMem {
	
	
	public Parent root;
	
	/**
	 * ѡ���ļ��еİ�ť
	 */
	public Button bt_ChooseDir;
	
	/**
	 * �г�������֮����ļ��б�
	 */
	public Button bt_ListFilteredFiles;
	/**
	 *  ����UTF8�ļ�
	 */
	public Button bt_DetectNonUtf8;
	/**
	 * ת��UTF8��ť
	 */
	public Button bt_Convert2Utf8;
	
	
	/**
	 * TextField�ļ�·��
	 */
	public TextField txtf_DirPath;
	
	/**
	 * TextField���˺�׺
	 */
	public TextField txtf_Filter;
	
	/**
	 * ���ؽ����ListView
	 */
	public ListView<String> list_Result;
	
	
	/**
	 * ��ť�¼�ί��
	 */
	public ButtonEvent buttonEvent;
	
	/**
	 * ���˽��װ������
	 */
	public List<File> filteredFiles = null;
	
	/**
	 * ��UTF8�ļ�װ������
	 */
	public List<File> nonUtf8Files = null;
	
	
	public void init(Scene s){
		bt_ChooseDir = (Button) s.lookup("#main_bt_ChooseDir");
		bt_ListFilteredFiles = (Button) s.lookup("#main_bt_ListFilteredFiles");
		bt_DetectNonUtf8 = (Button) s.lookup("#main_bt_DetectNonUTF8");
		bt_Convert2Utf8 = (Button) s.lookup("#main_bt_Convert2UTF8");
		txtf_DirPath = (TextField) s.lookup("#main_txtf_ShowDir");
		txtf_Filter = (TextField) s.lookup("#main_txtf_Filter");
		list_Result = (ListView<String>) s.lookup("#main_list");
		bindEvent();
		
		filteredFiles = new ArrayList<>();
		nonUtf8Files = new ArrayList<>();
	}
	
	/**
	 * ��ʼ���ؼ��İ��¼�
	 */
	private void bindEvent(){
		buttonEvent = new ButtonEvent();
		bt_ChooseDir.setOnAction(buttonEvent);
		bt_ListFilteredFiles.setOnAction(buttonEvent);
		bt_DetectNonUtf8.setOnAction(buttonEvent);
		list_Result.setOnMouseClicked(new ListViewEvent());
	}
	
	
	
	public static AppMem mem = new AppMem();
	
	private AppMem(){
		
	}
	
	public static AppMem getInstance(){
		if (mem==null) {
			return new AppMem();
		}
		return mem;
	}
}
