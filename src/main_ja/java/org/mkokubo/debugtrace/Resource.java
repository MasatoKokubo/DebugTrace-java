/*
	Resource.java

	Created on 2014/10/19.
	(C) Masato Kokubo
*/
package org.mkokubo.debugtrace;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.function.Function;

/**
	リソースを取得する際に使用するクラスです。
	@since 1.0.0
	@author 小久保 雅人
*/
public class Resource {
	/**
		英語の ResourceBundle を返します。
		@return 英語の ResourceBundle
	*/
	public ResourceBundle enResourceBundle() {return null;}

	/**
		デフォルトロケールの ResourceBundle を返します。
		@return デフォルトロケールの ResourceBundle
	*/
	public ResourceBundle defaultResourceBundle() {return null;}

	/**
		ユーザー定義の ResourceBundle を返します。
		@return ユーザー定義の ResourceBundle
	*/
	public ResourceBundle userResourceBundle() {return null;}

	/**
		フィルタ関数を返します。
		@return フィルタ関数
	*/
	public Function<String, String> filter() {return null;}

	/**
		Resource を構築します。
		@param baseClass ResourceBundle のベースクラス
	*/
	public Resource(Class<?> baseClass) {}

	/**
		Resource を構築します。
		@param baseClass ResourceBundle のベースクラス
		@param filter フィルタ関数
	*/
	public Resource(Class<?> baseClass, Function<String, String> filter) {}

	/**
		Resource を構築します。
		@param baseName ResourceBundle のベース名
	*/
	public Resource(String baseName) {}

	/**
		Resource を構築します。
		@param baseName ResourceBundle のベース名
		@param filter フィルタ関数
	*/
	public Resource(String baseName, Function<String, String> filter) {}

	/**
		文字列リソースのプロパティ値を取得して返します。
		@param key 文字列リソースのキー
		@return 文字列リソースのプロパティ値
		@throws MissingResourceException キーが見つからない場合
	*/
	public String getString(String key) {return null;}

	/**
		int リソースのプロパティ値を取得して返します。
		@param key int リソースのキー
		@return int リソースのプロパティ値
		@throws MissingResourceException キーが見つからない場合
		@throws NumberFormatException リソースのプロパティ値が int に変換できない場合
	*/
	public int getInt(String key) {return 0;}
}

