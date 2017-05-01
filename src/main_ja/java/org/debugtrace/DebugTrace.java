// DebugTrace.java
// (C) 2015 Masato Kokubo
package org.debugtrace;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import java.util.function.LongSupplier;
import java.util.function.Supplier;

/**
 * デバッグ用のユーティリティクラスです。<br>
 * DebugTrace.enter および DebugTrace.leave メソッドをデバッグするメソッドの開始時と終了時にコールしてください。
 * プログラムの実行トレースが出力されます。
 *
 * @since 1.0.0
 * @author Masato Kokubo
 */
public class DebugTrace {
	/**
	 * トレースが有効かどうかを返します。
	 *
	 * @return トレースが有効の場合 true、そうでなければ false
	 */
	public static boolean isEnabled() {return false;}

	/**
	 * このメソッドをデバッグするメソッドの開始時にコールしてください。
	 */
	public static void enter() {
	}

	/**
	 * このメソッドをデバッグするメソッドの終了時にコールしてください。
	 */
	public static void leave() {
	}

	/**
	 * 指定のメッセージをログに出力します。
	 *
	 * @param message メッセージ (null 可)
	 */
	public static void print(String message) {
	}

	/**
	 * 指定のメッセージをログに出力します。
	 *
	 * @param messageSupplier メッセージサプライア
	 */
	public static void print(Supplier<String> messageSupplier) {
	}

	/**
	 * 名前と boolean 値ををログに出力します。
	 *
	 * @param name 名前
	 * @param value boolean 値
	 */
	public static void print(String name, boolean value) {
	}

	/**
	 * 名前と char 値ををログに出力します。
	 *
	 * @param name 名前
	 * @param value char 値
	 */
	public static void print(String name, char value) {
	}

	/**
	 * 名前と byte 値ををログに出力します。
	 *
	 * @param name 名前
	 * @param value byte 値
	 */
	public static void print(String name, byte value) {
	}

	/**
	 * 名前と byte 値ををログに出力します。
	 *
	 * @param mapName 値に対応する定数名を得るためのマップの名前 (null 可)
	 * @param name 名前
	 * @param value byte 値
	 *
	 * @since 2.4.0
	 */
	public static void print(String mapName, String name, byte value) {
	}

	/**
	 * 名前と short 値ををログに出力します。
	 *
	 * @param name 名前
	 * @param value short 値
	 */
	public static void print(String name, short value) {
	}

	/**
	 * 名前と short 値ををログに出力します。
	 *
	 * @param mapName 値に対応する定数名を得るためのマップの名前 (null 可)
	 * @param name 名前
	 * @param value short 値
	 *
	 * @since 2.4.0
	 */
	public static void print(String mapName, String name, short value) {
	}

	/**
	 * 名前と int 値ををログに出力します。
	 *
	 * @param name 名前
	 * @param value int 値
	 */
	public static void print(String name, int value) {
	}

	/**
	 * 名前と int 値ををログに出力します。
	 *
	 * @param mapName 値に対応する定数名を得るためのマップの名前 (null 可)
	 * @param name 名前
	 * @param value int 値
	 *
	 * @since 2.4.0
	 */
	public static void print(String mapName, String name, int value) {
	}

	/**
	 * 名前と long 値ををログに出力します。
	 *
	 * @param name 名前
	 * @param value long 値
	 */
	public static void print(String name, long value) {
	}

	/**
	 * 名前と long 値ををログに出力します。
	 *
	 * @param mapName 値に対応する定数名を得るためのマップの名前 (null 可)
	 * @param name 名前
	 * @param value long 値
	 *
	 * @since 2.4.0
	 */
	public static void print(String mapName, String name, long value) {
	}

	/**
	 * 名前と float 値ををログに出力します。
	 *
	 * @param name 名前
	 * @param value float 値
	 */
	public static void print(String name, float value) {
	}

	/**
	 * 名前と double 値ををログに出力します。
	 *
	 * @param name 名前
	 * @param value double 値
	 */
	public static void print(String name, double value) {
	}

	/**
	 * 名前と値ををログに出力します。
	 *
	 * @param name 名前
	 * @param value 値 (null 可)
	 */
	public static void print(String name, Object value) {
	}

	/**
	 * 名前と値ををログに出力します。
	 *
	 * @param mapName 値に対応する定数名を得るためのマップの名前 (null 可)
	 * @param name 名前
	 * @param value 値 (null 可)
	 *
	 * @since 2.4.0
	 */
	public static void print(String mapName, String name, Object value) {
	}

	/**
	 * 名前と boolean 値ををログに出力します。
	 *
	 * @param name 名前
	 * @param valueSupplier boolean サプライア
	 */
	public static void print(String name, BooleanSupplier valueSupplier) {
	}

	/**
	 * 名前と int 値ををログに出力します。
	 *
	 * @param name 名前
	 * @param valueSupplier int サプライア
	 */
	public static void print(String name, IntSupplier valueSupplier) {
	}

	/**
	 * 名前と int 値ををログに出力します。
	 *
	 * @param mapName 値に対応する定数名を得るためのマップの名前 (null 可)
	 * @param name 名前
	 * @param valueSupplier int サプライア
	 *
	 * @since 2.4.0
	 */
	public static void print(String mapName, String name, IntSupplier valueSupplier) {
	}

	/**
	 * 名前と long 値ををログに出力します。
	 *
	 * @param name 名前
	 * @param valueSupplier long サプライア
	 */
	public static void print(String name, LongSupplier valueSupplier) {
	}

	/**
	 * 名前と long 値ををログに出力します。
	 *
	 * @param mapName 値に対応する定数名を得るためのマップの名前 (null 可)
	 * @param name 名前
	 * @param valueSupplier long サプライア
	 *
	 * @since 2.4.0
	 */
	public static void print(String mapName, String name, LongSupplier valueSupplier) {
	}

	/**
	 * 名前と double 値ををログに出力します。
	 *
	 * @param name 名前
	 * @param valueSupplier double サプライア
	 */
	public static void print(String name, DoubleSupplier valueSupplier) {
	}

	/**
	 * 名前と値ををログに出力します。
	 *
	 * @param <T> 値の型
	 * @param name 名前
	 * @param valueSupplier メッセージサプライア
	*/
	public static <T> void print(String name, Supplier<T> valueSupplier) {
	}

	/**
	 * 名前と値ををログに出力します。
	 *
	 * @param <T> 値の型
	 * @param mapName 値に対応する定数名を得るためのマップの名前 (null 可)
	 * @param name 名前
	 * @param valueSupplier メッセージサプライア
	 *
	 * @since 2.4.0
	*/
	public static <T> void print(String mapName, String name, Supplier<T> valueSupplier) {
	}

	/**
	 * 最後にログに出力した文字列を返します。
	 *
	 * @return 最後にログに出力した文字列
	 *
	 * @since 2.4.0
	 */
	public static String getLastLog() {
		return null;
	}
}
