/*
	DebugTrace.java

	Created on 2014/10/11.
	(C) Masato Kokubo
*/
package org.mkokubo.debugtrace;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

/**
	デバッグ用のユーティリティクラスです。<br>
	DebugTrace.enter および DebugTrace.leave メソッドをデバッグするメソッドの開始時と終了時にコールしてください。
	プログラムの実行トレースが出力されます。
	then outputs execution trace of the program.
	@since 1.0.0
	@author 小久保 雅人
*/
public class DebugTrace {
	/**
		トレースが有効かどうかを返します。
		@return トレースが有効の場合 true、そうでなければ false
	*/
	public static boolean isEnabled() {return false;}

	/**
		リフレクション対象のクラスを追加します。
		@param targetClass リフレクション対象のクラス
	*/
	public static void addReflectionTarget(Class<?> targetClass) {}

	/**
		値を表示しないプロパティを追加します。の
		@param targetClass プロパティの対象クラス
		@param propertyNames 対象のプロパティ名配列
		@since 1.5.0
	*/
	public static void addNonPrintProperties(Class<?> targetClass, String... propertyNames) {}

	/**
		このメソッドをデバッグするメソッドの開始時にコールしてください。
	*/
	public static void enter() {}

	/**
		このメソッドをデバッグするメソッドの終了時にコールしてください。
	*/
	public static void leave() {}

	/**
		指定のメッセージをログに出力します。
		@param message メッセージ (null 可)
	*/
	public static void print(String message) {}

	/**
		指定のメッセージをログに出力します。
		@param messageSupplier メッセージサプライア (null 不可)
	*/
	public static void print(Supplier<String> messageSupplier) {}

	/**
		名前と boolean 値ををログに出力します。
		@param name 名前 (null 可)
		@param value boolean 値
	*/
	public static void print(String name, boolean value) {}

	/**
		名前と char 値ををログに出力します。
		@param name 名前 (null 可)
		@param value char 値
	*/
	public static void print(String name, char value) {}

	/**
		名前と byte 値ををログに出力します。
		@param name 名前 (null 可)
		@param value byte 値
	*/
	public static void print(String name, byte value) {}

	/**
		名前と short 値ををログに出力します。
		@param name 名前 (null 可)
		@param value short 値
	*/
	public static void print(String name, short value) {}

	/**
		名前と int 値ををログに出力します。
		@param name 名前 (null 可)
		@param value int 値
	*/
	public static void print(String name, int value) {}

	/**
		名前と long 値ををログに出力します。
		@param name 名前 (null 可)
		@param value long 値
	*/
	public static void print(String name, long value) {}

	/**
		名前と float 値ををログに出力します。
		@param name 名前 (null 可)
		@param value float 値
	*/
	public static void print(String name, float value) {}

	/**
		名前と double 値ををログに出力します。
		@param name 名前 (null 可)
		@param value double 値
	*/
	public static void print(String name, double value) {}

	/**
		名前と値ををログに出力します。
		@param name 名前 (null 可)
		@param value 値 (null 可)
	*/
	public static void print(String name, Object value) {}

	/**
		名前と値ををログに出力します。
		@param <T> 値の型
		@param name 名前 (null 可)
		@param valueSupplier メッセージサプライア (null 不可)
	*/
	public static <T> void print(String name, Supplier<T> valueSupplier) {}

	/**
		名前と boolean 値ををログに出力します。
		@param name 名前 (null 可)
		@param valueSupplier boolean サプライア (null 不可)
	*/
	public static void print(String name, BooleanSupplier valueSupplier) {}

	/**
		名前と int 値ををログに出力します。
		@param name 名前 (null 可)
		@param valueSupplier int サプライア (null 不可)
	*/
	public static void print(String name, IntSupplier valueSupplier) {}

	/**
		名前と long 値ををログに出力します。
		@param name 名前 (null 可)
		@param valueSupplier long サプライア (null 不可)
	*/
	public static void print(String name, LongSupplier valueSupplier) {}

	/**
		名前と double 値ををログに出力します。
		@param name 名前 (null 可)
		@param valueSupplier double サプライア (null 不可)
	*/
	public static void print(String name, DoubleSupplier valueSupplier) {}
}
