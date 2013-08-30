package com.simiansoft.falcon;

public enum LoggingStatus {
	Stopped (0),
	Active (1),
	Paused (2),
	ClickedStart (3),
	ClickedStop (4);
	
	private int value;
	
	private LoggingStatus(int value) {
		this.value = value;
	}
	
	public int toInt() {
		return this.value;
	}
}
