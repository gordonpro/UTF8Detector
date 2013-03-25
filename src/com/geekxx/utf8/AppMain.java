package com.geekxx.utf8;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import com.geekxx.utf8.tool.CommonUtil;

public class AppMain extends Application{
	
	
	public static void main(String[] args) {
		
		launch(args);
	}
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		
		URL url = AppMain.class.getResource("/com/geekxx/utf8/src/utf8.fxml");
		Parent root = FXMLLoader.load(url);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		
		//设置应用程序图标
		String iconURL = AppMain.class.getResource("/com/geekxx/utf8/src/icon.png").toExternalForm();
		primaryStage.getIcons().add(new Image(iconURL));
		primaryStage.setTitle("UTF8检测工具");
		//初始化所有控件
		AppMem.getInstance().init(scene);
		primaryStage.show();
		//检测插件Notepad++
		CommonUtil.detectNotepad();
		
	}
	
}
