package com.geekxx.utf8.view;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import com.geekxx.utf8.AppMem;
import com.geekxx.utf8.tool.ButtonEvent;

public class AppRootView extends Scene{
	
	
	/**
	 * ����Node ���ǿؼ�
	 */
	Parent root;
	
	/**
	 * 
	 */
	AppMem mem ;
	
	/**
	 * ��ť�¼�ί����
	 */
	ButtonEvent buttonEvent;
	
	public AppRootView(Parent root) {
		super(root);
		// TODO Auto-generated constructor stub
		this.root = root;
		mem = AppMem.getInstance();
		buttonEvent = new ButtonEvent();
		mem.bt_ChooseDir.setOnAction(buttonEvent);
	}
	
}
