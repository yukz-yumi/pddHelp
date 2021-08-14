package com.yukz.daodaoping.common.observer;

import java.util.Observable;

/**
 * 通知事件
 * @author micezhao
 *
 */
public class RealSubject extends Observable {

	public void makeChanged(Object obj) {
		setChanged();
		notifyObservers(obj);
	}
	
}
