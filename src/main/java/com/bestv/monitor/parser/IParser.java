package com.bestv.monitor.parser;

/**
 * @author xujian
 * 
 */
public interface IParser<T, V> {

	public T parse(V data);
}
