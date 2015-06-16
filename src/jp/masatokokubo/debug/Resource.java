/*
	Resource.java

	Created on 2014/10/19.
	(C) Masato Kokubo
*/
package jp.masatokokubo.debug;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.function.Function;

/**
	リソースを取得する際に使用します。
	@since 1.0.0
	@author Masato Kokubo
*/
public class Resource {
	/** 英語デフォルトのリソースバンドル */
	private ResourceBundle enResourceBundle;
	public ResourceBundle enResourceBundle() {return enResourceBundle;}

	/** デフォルトロケールのリソースバンドル */
	private ResourceBundle defaultResourceBundle;
	public ResourceBundle defaultResourceBundle() {return defaultResourceBundle;}

	/** ユーザー定義のリソースバンドル */
	private ResourceBundle userResourceBundle;
	public ResourceBundle userResourceBundle() {return userResourceBundle;}

	public static interface Function<T, R> {
		R apply(T t);
	}

	// フィルター
	private Function<String, String> filter;
	public Function<String, String> filter() {return filter;}

	/**
		Resource を構築します。
		@param baseClass リソースバンドルのベースクラス
	*/
	public Resource(Class<?> baseClass) {
		this(baseClass.getName(), null);
	}

	/**
		Resource を構築します。
		@param baseClass リソースバンドルのベースクラス
		@param fitler フィルター関数
	*/
	public Resource(Class<?> baseClass, Function<String, String> filter) {
		this(baseClass.getName(), filter);
	}

	/**
		Resource を構築します。
		@param baseName リソースバンドルのベース名
	*/
	public Resource(String baseName) {
		this(baseName, null);
	}

	/**
		Resource を構築します。
		@param baseName リソースバンドルのベース名
		@param fitler フィルター関数
	*/
	public Resource(String baseName, Function<String, String> filter) {
		this.filter = filter;

		try {
			enResourceBundle = ResourceBundle.getBundle(baseName, Locale.ENGLISH);
		}
		catch (Exception e) {
		}

		try {
			defaultResourceBundle = ResourceBundle.getBundle(baseName, Locale.getDefault());
		}
		catch (Exception e) {
		}

		try {
			String userBaseName = baseName.substring(baseName.lastIndexOf('.') + 1);
			userResourceBundle = ResourceBundle.getBundle(userBaseName);
		}
		catch (Exception e) {
		}
	}

	/**
		文字列のリソース値を取得して返します。
		@param key プロパティのキー
		@return リソース値
		@throws MissingResourceException 指定されたキーが見つからない場合
	*/
	public String getString(String key) {
		String string = null;

		MissingResourceException e = null;

		if (userResourceBundle != null) {
			try {
				string = userResourceBundle.getString(key);
			}
			catch (MissingResourceException e2) {
				e = e2;
			}
		}

		if (string == null && defaultResourceBundle != null) {
			try {
				string = defaultResourceBundle.getString(key);
				e = null;
			}
			catch (MissingResourceException e2) {
				e = e2;
			}
		}

		if (string == null && enResourceBundle != null) {
			try {
				string = enResourceBundle.getString(key);
				e = null;
			}
			catch (MissingResourceException e2) {
				e = e2;
			}
		}
		if (e != null)
			throw e;

		if (filter != null)
			string = filter.apply(string);

		return string;
	}

	/**
		int のリソース値を取得して返します。
		見つからない場合は、
		@param key プロパティのキー
		@return リソース値
		@throws MissingResourceException 指定されたキーが見つからない場合
		@throws NumberFormatException リソース値が int に変換できない場合
	*/
	public int getInt(String key) {
		return Integer.parseInt(getString(key));
	}
}

