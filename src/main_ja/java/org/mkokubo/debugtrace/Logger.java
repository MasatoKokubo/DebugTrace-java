/*
	Logger.java

	Created on 2014/10/11.
	(C) Masato Kokubo
*/

package org.mkokubo.debugtrace;

/**
	Logger クラスのインターフェースです。
	@since 1.0.0
	@author 小久保 雅人
*/
@FunctionalInterface
public interface Logger {
	/**
		ログレベルを設定します。
		@param logLevelStr ログレベルの文字列
	*/
	default void setLevel(String logLevelStr) {}

	/**
		ログ出力が有効かどうかを返します。
		@return ログ出力が有効なら true、そうでなければ false
	*/
	default boolean isEnabled() {return false;}

	/**
		メッセージをログに出力します。
		@param message メッセージ
	*/
	void log(String message);
}
