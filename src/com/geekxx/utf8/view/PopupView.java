package com.geekxx.utf8.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.stage.Popup;
import javafx.stage.Window;

import com.geekxx.utf8.AppMain;

public class PopupView extends Popup{
	
	
	public Parent root;
	
	/**
	 * 屏幕宽高
	 */
	int sw,sh;
	
	public PopupView() {
		// TODO Auto-generated constructor stub
		URL url = AppMain.class.getResource("/com/geekxx/utf8/src/popup.fxml");
		//获取屏幕宽高，为了定位到中间
		Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
		sw = dm.width;
		sh = dm.height;
		try {
			root = FXMLLoader.load(url);
			getContent().add(root);
			setX(0);
			setY(0);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		System.out.println(sw);
		System.out.println(sh);
		setX(sw/2-rw/2);
		setY(sh/2 -rh/2);
		root.setVisible(true);
	}
	
}
