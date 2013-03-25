package com.geekxx.utf8;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

import com.geekxx.utf8.tool.GKButtonEvent;
import com.geekxx.utf8.tool.GKMenuEvent;
import com.geekxx.utf8.tool.GKListViewEvent;
import com.geekxx.utf8.view.PopupView;


/**
 * 全局的变量存放,本类只放引用
 * @author Gordon
 */
public class AppMem {
	
	
	/**
	 * 在Properties中的名字
	 */
	public static final String PROPERTY_NAME = "plugin";
	
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
	 * 选择文件夹的按钮
	 */
	public Button bt_ConvertAll;
	
	/**
	 * TextField文件路径
	 */
	public TextField txtf_DirPath;
	
	/**
	 * TextField过滤后缀
	 */
	public TextField txtf_Filter;
	
	/**
	 * 一个Label，上面显示一些提示信息
	 */
	public Label lb_Message;
	
	/**
	 * 返回结果的ListView
	 */
	public ListView<String> list_Result;
	
	
	/**
	 * 文件绝对路径和对应的Charset映射，用于自动转码时，找到该文件的检测过的Charset
	 */
	public HashMap<String, Charset> charsetMap = new HashMap<>();
	
	
	/**
	 * 对话框布局<单实例>
	 */
	public PopupView popup;
	
	/**
	 * 提示贴
	 */
	public Tooltip tooltip ;
	
	/**
	 * 按钮事件委托
	 */
	public GKButtonEvent buttonEvent;
	
	/**
	 * 按钮事件委托
	 */
	public GKMenuEvent menuEvent;
	
	/**
	 * 过滤结果装在里面
	 */
	public List<File> filteredFiles = new ArrayList<File>();
	
	/**
	 * 非UTF8文件装在里面
	 */
	public List<File> nonUtf8Files = null;
	
	/**
	 * 菜单[配置Notepad++]
	 */
	public MenuItem mi_NotePad;
	
	/**
	 * 菜单【关于】
	 */
	public MenuItem mi_About;
	
	
	/**
	 * Notepad++的路径
	 */
	public String notepadPath;
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 初始化操作，把所有的控件和事件都初始化，并且绑定
	 * @param s
	 */
	@SuppressWarnings("unchecked")
	public void init(Scene s){
		bt_ChooseDir = (Button) s.lookup("#main_bt_ChooseDir");
		bt_ListFilteredFiles = (Button) s.lookup("#main_bt_ListFilteredFiles");
		bt_DetectNonUtf8 = (Button) s.lookup("#main_bt_DetectNonUTF8");
		bt_ConvertAll = (Button) s.lookup("#main_bt_AllConvertUTF8");
		txtf_DirPath = (TextField) s.lookup("#main_txtf_ShowDir");
		txtf_Filter = (TextField) s.lookup("#main_txtf_Filter");
		list_Result = (ListView<String>) s.lookup("#main_list");
		//初始化菜单
		MenuBar menubar = (MenuBar) s.lookup("#main_menubar");
		Menu menu0 = menubar.getMenus().get(0);//第一组
		Menu menu1 = menubar.getMenus().get(1);//第2组
		mi_NotePad = menu0.getItems().get(0);
		mi_About = menu1.getItems().get(0);
		popup = PopupView.getInstance();
		lb_Message = (Label) s.lookup("#label_message");
		tooltip = new Tooltip();
		
		bindEvent();
		
		filteredFiles = new ArrayList<>();
		nonUtf8Files = new ArrayList<>();
	}
	
	/**
	 * 初始化控件的绑定事件
	 */
	private void bindEvent(){
		buttonEvent = new GKButtonEvent();
		menuEvent = new GKMenuEvent();
		bt_ChooseDir.setOnAction(buttonEvent);
		bt_ListFilteredFiles.setOnAction(buttonEvent);
		bt_DetectNonUtf8.setOnAction(buttonEvent);
		bt_ConvertAll.setOnAction(buttonEvent);
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
