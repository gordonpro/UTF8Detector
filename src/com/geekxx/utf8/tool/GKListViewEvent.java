package com.geekxx.utf8.tool;

import java.io.IOException;
import java.util.Date;

import com.sun.javafx.scene.control.skin.ListCellSkin;
import com.sun.javafx.scene.control.skin.ListViewSkin;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionModel;

public class GKListViewEvent implements EventHandler<Event> {
	
	/**
	 * ��¼���һ�ε����Item�����ھ����Ƿ���˫��
	 */
	int lastPressIndex = -1;
	
	/**
	 * ��¼���һ�ε����ʱ�䣬�������500ms �Ͳ�����˫��
	 */
	long lastPressDate;
	
	
	@Override
	public void handle(Event event) {
		// TODO Auto-generated method stub
		
		//���ʱ����������0.5�룬����Ӧ
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
			String path = fileAbsPath.substring(0,
					fileAbsPath.lastIndexOf("\\") + 1);
			// �����ڵ�·��
			try {
				Runtime.getRuntime().exec("explorer " + path);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		lastPressIndex = sm.getSelectedIndex();
	}

}
