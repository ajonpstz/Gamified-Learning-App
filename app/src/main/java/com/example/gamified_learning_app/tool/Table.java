package com.example.gamified_learning_app.tool;

abstract public class Table {
	String TABLE_NAME;
	String[] FIELD_NAME, FIELD_TYPE;
	
	String onCreate() {
		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE " + TABLE_NAME + " (");
		for (int i = 0; i < FIELD_NAME.length; i++) {
			sb.append(FIELD_NAME[i] + " " + FIELD_TYPE[i]);
			if (i != FIELD_NAME.length-1)
				sb.append(", ");
		};
		sb.append(");");
		return sb.toString();
	};
}
