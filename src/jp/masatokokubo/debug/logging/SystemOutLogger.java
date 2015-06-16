/*
	SystemOutLogger.java

	Created on 2014/10/12.
	(C) Masato Kokubo
*/

package jp.masatokokubo.debug.logging;

/**
	System.err に出力するログ出力実装です。
	@since 1.0.0
	@author Masato Kokubo
*/
public class SystemOutLogger implements Logger {
	/**
		指定の名前で Logger を構築します。
		@param name 名前
	*/
	public SystemOutLogger(String name) {
	}

	/**
		trace レベルでメッセージをログ出力します。
		@param message メッセージ
	*/
	public void trace(String message) {
		System.out.println(message);
	}

	/**
		trace レベルのログ処理が有効かどうかを返します。
		@return ログ処理が有効な場合 true、そうでない場合は false
	*/
	public boolean isTraceEnabled() {
		return true;
	}
}
