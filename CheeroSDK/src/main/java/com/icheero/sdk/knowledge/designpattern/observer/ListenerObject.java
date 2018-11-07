package com.icheero.sdk.knowledge.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

public class ListenerObject
{
	private List<IListener> listeners = new ArrayList<IListener>();
	
	public void addObserver(IListener listener)
	{
		listeners.add(listener);
	}
	
	public void removeObserver(IListener listener)
	{
		listeners.remove(listener);
	}
	
	public void notifyObservers()
	{
		if (listeners != null && listeners.size() > 0)
		{
			for (int i = 0, j = listeners.size(); i < j; i++)
			{
				listeners.get(i).notifyMe();
			}
		}
	}
}
