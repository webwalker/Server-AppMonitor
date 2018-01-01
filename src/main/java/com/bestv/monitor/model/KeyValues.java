package com.bestv.monitor.model;

/**
 * @author xu.jian
 * 
 */
public class KeyValues {
	private String key;
	private String value;

	public KeyValues() {
	}

	public KeyValues(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
