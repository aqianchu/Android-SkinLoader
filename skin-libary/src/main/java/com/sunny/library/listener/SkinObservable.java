package com.sunny.library.listener;

public interface SkinObservable {
	void attach(SkinObserver observer);

	void detach(SkinObserver observer);

	void notifySkinUpdate();
}
