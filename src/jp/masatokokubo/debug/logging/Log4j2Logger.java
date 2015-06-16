/*
	Log4j2Logger.java

	Created on 2014/10/13.
	(C) Masato Kokubo
*/

package jp.masatokokubo.debug.logging;

import org.apache.logging.log4j.LogManager;

/**
	Log4J2 を使用したログ出力実装です。
	@since 1.0.0
	@author Masato Kokubo
*/
public class Log4j2Logger implements Logger {
	// ログ
	private org.apache.logging.log4j.Logger logger;

	/**
		指定の名前で Logger を構築します。
		@param name 名前
	*/
	public Log4j2Logger(String name) {
		logger = LogManager.getLogger(name);
	}

	/**
		trace レベルでメッセージをログ出力します。
		@param message メッセージ
	*/
	public void trace(String message) {
		logger.trace(message);
	}

	/**
		trace レベルのログ処理が有効かどうかを返します。
		@return ログ処理が有効な場合 true、そうでない場合は false
	*/
	public boolean isTraceEnabled() {
		return logger.isTraceEnabled();
	}
}
