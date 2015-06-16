/*
	Log4jLogger.java

	Created on 2014/10/11.
	(C) Masato Kokubo
*/

package jp.masatokokubo.debug.logging;

/**
	Log4J を使用したログ出力実装です。
	@since 1.0.0
	@author Masato Kokubo
*/
public class Log4jLogger implements Logger {
	// ログ
	private org.apache.log4j.Logger logger;

	/**
		指定の名前で Logger を構築します。
		@param name 名前
	*/
	public Log4jLogger(String name) {
		logger = org.apache.log4j.Logger.getLogger(name);
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
