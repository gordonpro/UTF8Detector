package com.geekxx.utf8;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

import com.geekxx.utf8.tool.GKButtonEvent;
import com.geekxx.utf8.tool.GKMenuEvent;
import com.geekxx.utf8.tool.GKListViewEvent;


/**
 * ȫ�ֵı������,����ֻ������
 * @author Gordon
 */
public class AppMem {
	
	
	
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
	public GKButtonEvent buttonEvent;
	
	/**
	 * ��ť�¼�ί��
	 */
	public GKMenuEvent menuEvent;
	
	/**
	 * ���˽��װ������
	 */
	public List<File> filteredFiles = null;
	
	/**
	 * ��UTF8�ļ�װ������
	 */
	public List<File> nonUtf8Files = null;
	
	/**
	 * �˵�[����Notepad++]
	 */
	public MenuItem mi_NotePad;
	
	/**
	 * �˵������ڡ�
	 */
	public MenuItem mi_About;
	
	/**
	 * ��ʼ�������������еĿؼ����¼�����ʼ�������Ұ�
	 * @param s
	 */
	@SuppressWarnings("unchecked")
	public void init(Scene s){
		bt_ChooseDir = (Button) s.lookup("#main_bt_ChooseDir");
		bt_ListFilteredFiles = (Button) s.lookup("#main_bt_ListFilteredFiles");
		bt_DetectNonUtf8 = (Button) s.lookup("#main_bt_DetectNonUTF8");
		bt_Convert2Utf8 = (Button) s.lookup("#main_bt_Convert2UTF8");
		txtf_DirPath = (TextField) s.lookup("#main_txtf_ShowDir");
		txtf_Filter = (TextField) s.lookup("#main_txtf_Filter");
		list_Result = (ListView<String>) s.lookup("#main_list");
		//��ʼ���˵�
		MenuBar menubar = (MenuBar) s.lookup("#main_menubar");
		Menu menu0 = menubar.getMenus().get(0);//��һ��
		Menu menu1 = menubar.getMenus().get(1);//��2��
		mi_NotePad = menu0.getItems().get(0);
		mi_About = menu1.getItems().get(0);
		bindEvent();
		
		filteredFiles = new ArrayList<>();
		nonUtf8Files = new ArrayList<>();
	}
	
	/**
	 * ��ʼ���ؼ��İ��¼�
	 */
	private void bindEvent(){
		buttonEvent = new GKButtonEvent();
		menuEvent = new GKMenuEvent();
		bt_ChooseDir.setOnAction(buttonEvent);
		bt_ListFilteredFiles.setOnAction(buttonEvent);
		bt_DetectNonUtf8.setOnAction(buttonEvent);
		list_Result.setOnMouseClicked(new GKListViewEvent());
		
		mi_NotePad.setOnAction(menuEvent);
		mi_About.setOnAction(menuEvent);
		
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
