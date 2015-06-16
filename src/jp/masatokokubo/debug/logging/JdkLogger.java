/*
	JdkLogger.java

	Created on 2014/10/11.
	(C) Masato Kokubo
*/

package jp.masatokokubo.debug.logging;

import java.util.logging.Level;

/**
	java.util.logging.Logger を使用したログ出力実装です。
	@since 1.0.0
	@author Masato Kokubo
*/
public class JdkLogger implements Logger {
	// ログ
	private java.util.logging.Logger logger;

	/**
		指定の名前で Logger を構築します。
		@param name 名前
	*/
	public JdkLogger(String name) {
		logger = java.util.logging.Logger.getLogger(name);
	}

	/**
		trace レベルでメッセージをログ出力します。
		@param message メッセージ
	*/
	public void trace(String message) {
		logger.log(Level.FINEST, message);
	}

	/**
		trace レベルのログ処理が有効かどうかを返します。
		@return ログ処理が有効な場合 true、そうでない場合は false
	*/
	public boolean isTraceEnabled() {
		return logger.isLoggable(Level.FINEST);
	}
}
