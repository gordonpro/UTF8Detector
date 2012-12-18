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
 * 全局的变量存放,本类只放引用
 * @author Gordon
 */
public class AppMem {
	
	
	public Parent root;
	
	/**
	 * 选择文件夹的按钮
	 */
	public Button bt_ChooseDir;
	
	/**
	 * 列出被过滤之后的文件列表
	 */
	public Button bt_ListFilteredFiles;
	/**
	 *  检测非UTF8文件
	 */
	public Button bt_DetectNonUtf8;
	/**
	 * 转成UTF8按钮
	 */
	public Button bt_Convert2Utf8;
	
	
	/**
	 * TextField文件路径
	 */
	public TextField txtf_DirPath;
	
	/**
	 * TextField过滤后缀
	 */
	public TextField txtf_Filter;
	
	/**
	 * 返回结果的ListView
	 */
	public ListView<String> list_Result;
	
	
	/**
	 * 按钮事件委托
	 */
	public ButtonEvent buttonEvent;
	
	/**
	 * 过滤结果装在里面
	 */
	public List<File> filteredFiles = null;
	
	/**
	 * 非UTF8文件装在里面
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
	 * 初始化控件的绑定事件
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
