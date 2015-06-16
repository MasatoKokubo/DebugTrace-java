/*
	Log4j2LoggerFactory.java

	Created on 2014/10/13.
	(C) Masato Kokubo
*/

package jp.masatokokubo.debug.logging;

/**
	Log4j2Logger のインスタンスを生成します。
	@since 1.0.0
	@author Masato Kokubo
*/
public class Log4j2LoggerFactory implements LoggerFactory {
	/**
		指定の名前のロガーを返します。
		@param name 名前
		@return Logger
	*/
	public Logger getLogger(String name) {
		return new Log4j2Logger(name);
	}
}
