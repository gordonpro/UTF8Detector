package com.geekxx.utf8.tool;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventTarget;

import com.geekxx.utf8.AppMem;
import com.geekxx.utf8.view.PopupView;



/**
 * 
 * @author Gordon
 *
 */
public class GKMenuEvent implements EventHandler<ActionEvent> {
	
	AppMem mem;
	
	/**
	 * 弹窗视图对象
	 */
	PopupView popupView;
	
	public GKMenuEvent() {
		// TODO Auto-generated constructor stub
		this.mem = AppMem.getInstance();
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		EventTarget target = event.getTarget();
		if (target == mem.mi_NotePad ) {
			popupView = PopupView.getInstance();
			popupView.show(mem.bt_ChooseDir.getScene().getWindow());
		}
		else if(target == mem.mi_About){
			if(popupView!=null){
				System.out.println(popupView.root.getLayoutBounds().getMaxX());
			}
		}
	}

}
