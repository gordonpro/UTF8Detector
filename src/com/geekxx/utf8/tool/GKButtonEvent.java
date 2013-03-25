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
		else if(target == mem.bt_DetectNonUtf8){
			listNonUTF8();
			//加入tips
			mem.list_Result.setTooltip(null);
			mem.list_Result.setTooltip(mem.tooltip);
			mem.tooltip.setText("双击打开");
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
		if(path!=null){
			mem.txtf_DirPath.setText(path.getAbsolutePath());
		}
	}
	
	/**
	 * 列出过滤之后的文件
	 */
	private void listFilteredFiles(){
		
		//得到要过滤的条件
		String ct = mem.txtf_Filter.getText().trim();
		String[] subs = ct.split(",");
		//得到指定的路径
		String path = mem.txtf_DirPath.getText().trim();
		//  得到符合要求的文件结合，准备做遍历转码了
		CommonUtil.filterFilesWithPath(new File(path), subs);
		ObservableList<String> filePathes = FXCollections.observableArrayList();
		for (File file : mem.filteredFiles) {
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
	
	
}
