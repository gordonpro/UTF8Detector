package com.geekxx.utf8.tool;

import com.geekxx.utf8.AppMem;
import com.geekxx.utf8.view.PopupView;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventTarget;



/**
 * 
 * @author Gordon
 *
 */
public class GKMenuEvent implements EventHandler<ActionEvent> {
	
	AppMem mem;
	
	/**
	 * µØ¥∞ ”Õº∂‘œÛ
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
			popupView = new PopupView();
			popupView.show(mem.bt_ChooseDir.getScene().getWindow());
		}
		else if(target == mem.mi_About){
			if(popupView!=null){
				System.out.println(popupView.root.getLayoutBounds().getMaxX());
			}
		}
	}

}
