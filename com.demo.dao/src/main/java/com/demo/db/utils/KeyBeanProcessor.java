package com.demo.db.utils;

import java.beans.PropertyDescriptor;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;

import org.apache.commons.dbutils.BeanProcessor;

public class KeyBeanProcessor extends BeanProcessor {

	private final String keyColumnName;

	private final String generateKeyName;

	private static final String GENERATE_KEY_NAME = "GENERATED_KEY";

	private static final String KEY_COLUMN_NAME = "id";

	/**
	 * Default constructor.
	 */
	public KeyBeanProcessor() {
		super();
		this.keyColumnName = KEY_COLUMN_NAME;
		this.generateKeyName = GENERATE_KEY_NAME;
	}

	public KeyBeanProcessor(String generateKeyName) {
		super();
		this.keyColumnName = KEY_COLUMN_NAME;
		this.generateKeyName = generateKeyName;
	}

	public KeyBeanProcessor(String keyColumnName, String generateKeyName) {
		super();
		this.keyColumnName = keyColumnName;
		if (null == generateKeyName || "".equals(generateKeyName)) {
			this.generateKeyName = GENERATE_KEY_NAME;
		} else {
			this.generateKeyName = generateKeyName;
		}
	}

	@Override
	protected int[] mapColumnsToProperties(final ResultSetMetaData rsmd, final PropertyDescriptor[] props) throws SQLException {

		final int cols = rsmd.getColumnCount();
		final int[] columnToProperty = new int[cols + 1];
		Arrays.fill(columnToProperty, PROPERTY_NOT_FOUND);

		for (int col = 1; col <= cols; col++) {
			String columnName = rsmd.getColumnLabel(col);

			if (null == columnName || 0 == columnName.length()) {
				columnName = rsmd.getColumnName(col);
			}

			if (columnName.equalsIgnoreCase(this.generateKeyName)) {
				columnName = keyColumnName;
			}

			final String generousColumnName = columnName.replace("_", "");

			for (int i = 0; i < props.length; i++) {
				final String propName = props[i].getName();

				// see if either the column name, or the generous one matches
				if (columnName.equalsIgnoreCase(propName) || generousColumnName.equalsIgnoreCase(propName)) {
					columnToProperty[col] = i;
					break;
				}
			}
		}

		return columnToProperty;
	}
}
