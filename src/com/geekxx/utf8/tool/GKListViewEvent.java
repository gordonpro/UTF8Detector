package com.geekxx.utf8.tool;

import java.util.Date;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionModel;

public class GKListViewEvent implements EventHandler<Event> {
	
	/**
	 * 记录最后一次点击的Item，用于决定是否是双击
	 */
	int lastPressIndex = -1;
	
	/**
	 * 记录最后一次点击的时间，如果超过500ms 就不算是双击
	 */
	long lastPressDate;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void handle(Event event) {
		// TODO Auto-generated method stub
		
		//如果时间间隔大于了0.5秒，不响应
		long now = new Date().getTime();
		ListView<String> list = (ListView<String>) event.getSource();
		SelectionModel<String> sm = list.getSelectionModel();

		if (lastPressDate+500 < now) {
			lastPressDate = now;
			lastPressIndex = sm.getSelectedIndex();
			return;
		}
		
		if (lastPressIndex == sm.getSelectedIndex()) {
			String fileAbsPath = list.getSelectionModel().getSelectedItem();
			// 打开所在的路径
			try {
//				Runtime.getRuntime().exec("explorer " + path);
				CommonUtil.editInNotepad(fileAbsPath);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		lastPressIndex = sm.getSelectedIndex();
	}

}
