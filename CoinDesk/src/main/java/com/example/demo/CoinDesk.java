package com.example.demo;

import java.util.Map;

public class CoinDesk {
	
	private Map<String, Double> bpi;
	private String disclaimer;
	private time time;
	
	
	
	public Map<String, Double> getBpi() {
		return bpi;
	}
	public void setBpi(Map<String, Double> bpi) {
		this.bpi = bpi;
	}
	public String getDisclaimer() {
		return disclaimer;
	}
	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
	}
	public time getTime() {
		return time;
	}
	public void setTime(time time) {
		this.time = time;
	}
	
	
	

}
