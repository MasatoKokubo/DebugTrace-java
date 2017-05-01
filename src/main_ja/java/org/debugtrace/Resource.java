// Resource.java
// (C) 2015 Masato Kokubo

package org.debugtrace;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * リソースを取得する際に使用するクラスです。
 *
 * @since 1.0.0
 * @author Masato Kokubo
 */
public class Resource {
	/**
	 * デフォルトロケールの ResourceBundle を返します。
	 *
	 * @return デフォルトロケールの ResourceBundle
	 */
	public ResourceBundle defaultResourceBundle() {
		return null;
	}

	/**
	 * ユーザー定義の ResourceBundle を返します。
	 *
	 * @return ユーザー定義の ResourceBundle
	 */
	public ResourceBundle userResourceBundle() {
		return null;
	}

	/**
	 * フィルタ関数を返します。
	 *
	 * @return フィルタ関数
	 */
	public Function<String, String> filter() {
		return null;
	}

	/**
	 * Resource を構築します。
	 *
	 * @param baseClass ResourceBundle のベースクラス
	 */
	public Resource(Class<?> baseClass) {
	}

	/**
	 * Resource を構築します。
	 *
	 * @param baseName ResourceBundle のベース名
	 */
	public Resource(String baseName) {
	}

	/**
	 * リソース・プロパティの値を返します。
	 * 
	 * @param <V> 値の型
	 * @param propertyKey リソース・プロパティのキー
	 * @param valueConverter 文字列を戻値の型に変換する関数
	 * @return リソース・プロパティの値
	 *
	 * @throws MissingResourceException プロパティが見つからない場合
	 *
	 * @since 2.4.0
	 */
	public <V> V getValue(String propertyKey, Function<String, V> valueConverter) {
		return null;
	}

	/**
	 * リソース・プロパティの値を返します。
	 * 
	 * @param <V> 値の型
	 * @param propertyKey リソース・プロパティのキー
	 * @param valueConverter 文字列を返す値の型に変換する関数
	 * @param defaultValue デフォルト値
	 * @return リソース・プロパティの値 (プロパティ・ファイルに見つからない場合は、デフォルト値)
	 *
	 * @since 2.4.0
	 */
	public <V> V getValue(String propertyKey, Function<String, V> valueConverter, V defaultValue) {
		return null;
	}

	/**
	 * リソース・プロパティの文字列値を返します。
	 *
	 * @param propertyKey リソース・プロパティのキー
	 * @return リソース・プロパティの文字列値
	 *
	 * @throws MissingResourceException プロパティが見つからない場合
	 */
	public String getString(String propertyKey) {
		return null;
	}

	/**
	 * リソース・プロパティの文字列値を返します。
	 *
	 * @param propertyKey リソース・プロパティのキー
	 * @param defaultValue デフォルト値
	 * @return リソース・プロパティの文字列値 (プロパティ・ファイルに見つからない場合は、デフォルト値)
	 *
	 * @since 2.3.0
	 */
	public String getString(String propertyKey, String defaultValue) {
		return null;
	}

	/**
	 * リソース・プロパティのint値を返します。
	 *
	 * @param propertyKey リソース・プロパティのキー
	 * @return リソース・プロパティの int値
	 *
	 * @throws MissingResourceException プロパティが見つからない場合
	 * @throws NumberFormatException リソース・プロパティ値が int に変換できない場合
	 */
	public int getInt(String propertyKey) {
		return 0;
	}

	/**
	 * リソース・プロパティのint値を返します。
	 *
	 * @param propertyKey リソース・プロパティのキー
	 * @param defaultValue デフォルト値
	 * @return リソース・プロパティのint値 (プロパティ・ファイルに見つからない場合は、デフォルト値)
	 *
	 * @throws MissingResourceException プロパティが見つからない場合
	 * @throws NumberFormatException リソース・プロパティ値が int に変換できない場合
	 *
	 * @since 2.3.0
	 */
	public int getInt(String propertyKey, int defaultValue) {
		return 0;
	}

	/**
	 * リソース・プロパティの値から作成したリストを返します。見つからない場合は、空のリストを返します。
	 *
	 * @param <E> リストの要素の型
	 * @param propertyKey リソース・プロパティのキー
	 * @param valueConverter 文字列を要素の型に変換する関数
	 * @return 作成されたリスト (または空のリスト)
	 *
	 * @since 2.4.0
	 */
	public <E> List<E> getList(String propertyKey, Function<String, E> valueConverter) {
		return null;
	}

	/**
	 * リソース・プロパティの値から作成した文字列リストを返します。見つからない場合は、空のリストを返します。
	 *
	 * @param propertyKey リソース・プロパティのキー
	 * @return 作成された文字列リスト (または空のリスト)
	 *
	 * @since 2.4.0
	 */
	public List<String> getStringList(String propertyKey) {
		return null;
	}
	/**
	 * リソース・プロパティの値から作成したマップを返します。見つからない場合は、空のマップを返します。
	 *
	 * @param <K> マップのキーの型
	 * @param <V> マップの値の型
	 * @param propertyKey リソース・プロパティのキー
	 * @param keyConverter 文字列をマップのキーの型に変換する関数
	 * @param valueConverter 文字列をマップの値の型に変換する関数
	 * @return 作成されたマップ (または空のマップ)
	 *
	 * @since 2.4.0
	 */
	public <K, V> Map<K, V> getMap(String propertyKey, Function<String, K> keyConverter, Function<String, V> valueConverter) {
		return null;
	}

	/**
	 * リソース・プロパティの値から作成したマップ (キー: Integer, 値: String) を返します。
	 * 見つからない場合は、空のマップを返します。
	 *
	 * @param propertyKey リソース・プロパティのキー
	 * @return 作成されたマップ (または空のマップ)
	 *
	 * @since 2.4.0
	 */
	public Map<Integer, String> getIntegerKeyMap(String propertyKey) {
		return null;
	}

	/**
	 * リソース・プロパティの値から作成したマップ (キー: String, 値: String)を返します。
	 * 見つからない場合は、空のマップを返します。
	 *
	 * @param propertyKey リソース・プロパティのキー
	 * @return 作成されたマップ (または空のマップ)
	 *
	 * @since 2.4.0
	 */
	public Map<String, String> getStringKeyMap(String propertyKey) {
		return null;
	}
}

