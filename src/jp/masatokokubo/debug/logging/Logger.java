/*
	Logger.java

	Created on 2014/10/11.
	(C) Masato Kokubo
*/

package jp.masatokokubo.debug.logging;

/**
	ログ出力を行うインタフェースです。
	@since 1.0.0
	@author Masato Kokubo
*/
public interface Logger {
	/**
		trace レベルでメッセージをログ出力します。
		@param message メッセージ
	*/
	void trace(String message);

	/**
		trace レベルのログ処理が有効かどうかを返します。
		@return ログ処理が有効な場合 true、そうでない場合は false
	*/
	boolean isTraceEnabled();
}
