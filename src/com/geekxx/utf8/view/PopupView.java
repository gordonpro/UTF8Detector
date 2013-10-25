package com.geekxx.utf8.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Popup;
import javafx.stage.Window;

import com.geekxx.utf8.AppMain;
import com.geekxx.utf8.AppMem;
import com.geekxx.utf8.tool.CommonUtil;

public class PopupView extends Popup{
	
	/**
	 * 对话框的根布局
	 */
	public Parent root;
	
	/**
	 * 屏幕宽高
	 */
	int sw,sh;
	
	/**
	 * 对话框上面的3个按钮 【确定】【取消】【定位NotePad++插件】
	 */
	public Button bt_Positive,bt_Negative,bt_LocationNotepad;
	
	/**
	 * 显示Nodepad++的定位路径
	 */
	public TextField txtf_NotepadPath;
	
	
	
	
	private PopupView() {
		// TODO Auto-generated constructor stub
		URL url = AppMain.class.getResource("/com/geekxx/utf8/src/popup.fxml");
		//获取屏幕宽高，为了定位到中间
		Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
		sw = dm.width;
		sh = dm.height;
		try {
			root = FXMLLoader.load(url);
			getContent().add(root);
			initNode();//自己的方法，初始化控件
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化一些控件，和绑定他们的事件
	 */
	private void initNode(){
		txtf_NotepadPath = (TextField) root.lookup("#popup_txtf_notepadpath");
		bt_LocationNotepad = (Button) root.lookup("#popup_bt_notepad");
		bt_Negative =  (Button) root.lookup("#popup_bt_negative");
		bt_Positive =  (Button) root.lookup("#popup_bt_positive");
		
		//绑定事件
		bt_LocationNotepad.setOnAction(btnEvent);
		bt_Negative.setOnAction(btnEvent);
		bt_Positive.setOnAction(btnEvent);
		
	}
	
	@Override
	public void show(Window owner) {
		// TODO Auto-generated method stub
		
		//为了固定到中间，先隐藏显示，再计算，再显示
		root.setVisible(false);
		super.show(owner);
		Bounds bounds = root.getLayoutBounds();
		int rw = (int) bounds.getWidth();
		int rh = (int) bounds.getHeight();
		setX(sw/2-rw/2);
		setY(sh/2 -rh/2);
		setY(sh/2 -rh/2);
		root.setVisible(true);
	}
	
	
	private static PopupView self = null;
	
	public static PopupView getInstance(){
		if (self==null) {
			self = new PopupView();
		}
		return self;
		
	}
	
	/**
	 * 内部事件处理器
	 */
	private EventHandler<ActionEvent> btnEvent = new EventHandler<ActionEvent>() {
		
		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub
			EventTarget target = event.getTarget();
			if (target==bt_LocationNotepad) {
				FileChooser chooser = new FileChooser();
				//设置文件选择过滤器
				FileChooser.ExtensionFilter filter = new ExtensionFilter("notepad++", "notepad++.exe");
				chooser.getExtensionFilters().add(filter);
				File file = chooser.showOpenDialog(root.getScene().getWindow());
				if(file!=null){
					txtf_NotepadPath.setText(file.getAbsolutePath());
				}
			}
			else if(target==bt_Negative){
				hide();
			}
			else if(target==bt_Positive){
				//保存配置文件
				CommonUtil.storeProperty();
				//再次检测
				File f = new File(txtf_NotepadPath.getText());
				if (f.exists()) {
					AppMem.getInstance().lb_Message.setText("配置成功");
				}
				CommonUtil.detectNotepad();
				hide();
			}
		}
	};
}
