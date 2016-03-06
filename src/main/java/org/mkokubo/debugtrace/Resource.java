/*
	Resource.java

	Copyright (c) 2015 Masato Kokubo
*/
package org.mkokubo.debugtrace;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.function.Function;

/**
	Uses this class when gets resources.

	@since 1.0.0

	@author Masato Kokubo
*/
public class Resource {
	private ResourceBundle enResourceBundle;
	/**
		Returns the english ResourceBundle.

		@return the english ResourceBundle
	*/
	public ResourceBundle enResourceBundle() {return enResourceBundle;}

	private ResourceBundle defaultResourceBundle;
	/**
		Returns the default locale ResourceBundle.

		@return the default locale ResourceBundle
	*/
	public ResourceBundle defaultResourceBundle() {return defaultResourceBundle;}

	private ResourceBundle userResourceBundle;
	/**
		Returns the user defined ResourceBundle.

		@return the user defined ResourceBundle
	*/
	public ResourceBundle userResourceBundle() {return userResourceBundle;}

	private Function<String, String> filter;
	/**
		Returns the filter function.

		@return the filter functions
	*/
	public Function<String, String> filter() {return filter;}

	/**
		Construct a Resource.

		@param baseClass the base class of a ResourceBundle
	*/
	public Resource(Class<?> baseClass) {
		this(baseClass.getName(), null);
	}

	/**
		Construct a Resource.

		@param baseClass the base class of a ResourceBundle

		@param filter a filter function
	*/
	public Resource(Class<?> baseClass, Function<String, String> filter) {
		this(baseClass.getName(), filter);
	}

	/**
		Construct a Resource.

		@param baseName the base name of a ResourceBundle
	*/
	public Resource(String baseName) {
		this(baseName, null);
	}

	/**
		Construct a Resource.

		@param baseName the base name of a ResourceBundle
		@param filter a filter function
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
		Gets and returns a string resource property value.

		@param key the key of a string resource property

		@return the value of the string resource property

		@throws MissingResourceException if the key dose not found
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
		Gets and returns an int resource property value.

		@param key the key of an int resource property

		@return the value of the int resource property

		@throws MissingResourceException if the key dose not found
		@throws NumberFormatException if the resource property value can not convert to int
	*/
	public int getInt(String key) {
		return Integer.parseInt(getString(key));
	}
}

