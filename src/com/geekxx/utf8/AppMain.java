package com.geekxx.utf8;

import java.net.URL;

import com.geekxx.utf8.view.AppRootView;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
		
		//初始化所有控件
		AppMem.getInstance().init(scene);
		primaryStage.show();
	}

}
