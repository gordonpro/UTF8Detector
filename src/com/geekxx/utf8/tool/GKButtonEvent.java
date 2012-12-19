package com.geekxx.utf8.tool;

import java.io.File;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.stage.DirectoryChooser;

import com.geekxx.utf8.AppMem;

public class GKButtonEvent implements EventHandler<ActionEvent>{
	
	AppMem mem = AppMem.getInstance();
	
	
	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		EventTarget target = event.getTarget();
		if (target == mem.bt_ChooseDir) {
			handleChooseDir();
		}
		else if (target == mem.bt_Convert2Utf8){
			
		}
		else if(target == mem.bt_DetectNonUtf8){
			listNonUTF8();
		}
		else if(target == mem.bt_ListFilteredFiles){
			listFilteredFiles();
		}
	}
	
	
	/**
	 * 内部策略，弹出选择目录对话框
	 */
	private void handleChooseDir(){
		DirectoryChooser dirChooser = new DirectoryChooser();
		File path = dirChooser.showDialog(null);
		mem.txtf_DirPath.setText(path.getAbsolutePath());
	}
	
	/**
	 * 列出过滤之后的文件
	 */
	private void listFilteredFiles(){
		//先清空上一次的缓存
		mem.filteredFiles.clear();
		//得到要过滤的条件
		String ct = mem.txtf_Filter.getText().trim();
		String[] subs = ct.split(",");
		//得到指定的路径
		String path = mem.txtf_DirPath.getText().trim();
		
		List<File> files = CommonUtil.getFilesWithPath(new File(path), subs);
		ObservableList<String> filePathes = FXCollections.observableArrayList();
		for (File file : files) {
			filePathes.add(file.getAbsolutePath());
		}
		mem.list_Result.setItems(filePathes);
	}
	
	/**
	 * 列出带BOM的UTF8文件
	 */
	private void listNonUTF8(){
		//先列出文件
		listFilteredFiles();
		ObservableList<String> nonUtf8files = FXCollections.observableArrayList();
		for (File file : mem.filteredFiles) {
			//如果不是UTF8格式，就加入集合
			if(!CommonUtil.detectUTF8(file)){
				nonUtf8files.add(file.getAbsolutePath());
			}
		}
		mem.list_Result.setItems(nonUtf8files);
	}
	
	private void handleCoverUtf8(){
		
	}
	
}
