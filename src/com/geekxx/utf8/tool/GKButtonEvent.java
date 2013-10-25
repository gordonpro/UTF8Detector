package com.geekxx.utf8.tool;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Set;

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
		else if(target == mem.bt_ConvertAll){
			convertAll();
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
		//先清空上一次的缓存
		mem.filteredFiles.clear();
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
		//  清空Charset映射, 因为新列出的要新重建
		mem.charsetMap.clear();
		ObservableList<String> nonUtf8files = FXCollections.observableArrayList();
		for (File file : mem.filteredFiles) {
			//  如果不是UTF8格式，就加入集合
			Charset charset = CommonUtil.detectCharset(file);
			//  如果Charset为null 或者不为"UTF-8" 都要加入修改列表 
			if(charset==null || !"UTF-8".equals(charset.name())){
				String path = file.getAbsolutePath();
				//  映射字符集
				mem.charsetMap.put(path, charset);
				nonUtf8files.add(path);
			}
		}
		mem.list_Result.setItems(nonUtf8files);
		if(nonUtf8files.size()>0){
			mem.bt_ConvertAll.setDisable(false);
		}
	}
	
	/**
	 * 把列表中的文件全转成UTF-8
	 */
	private void convertAll(){
		
		
		Set<String> keys = mem.charsetMap.keySet();
		for (String key : keys) {
			Charset charset = mem.charsetMap.get(key);
			if(charset!=null){
				try {
					CommonUtil.autoConvert(new File(key));
					System.out.println(key+"  ：  "+charset.name()+"转码成:UTF-8");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		mem.bt_ConvertAll.setDisable(true);
		//  转码完毕之后 再次检测非UTF8文件
		listNonUTF8();
	}
	
}
