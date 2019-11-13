package com.example.gamified_learning_app.tool;



public final class UserTable extends Table {
	UserTable() {
		this.TABLE_NAME = "users";
		this.FIELD_NAME = new String[]{
			"email",
			"username",
			"description",
			"dateJoined"
		};
		this.FIELD_TYPE = new String[]{
			"TEXT PRIMARY KEY",
			"TEXT NOT NULL UNIQUE",
			"TEXT",
			"TEXT"
		};
	}
}
