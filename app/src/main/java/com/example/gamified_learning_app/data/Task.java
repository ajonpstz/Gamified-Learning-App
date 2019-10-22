package com.example.gamified_learning_app.data;

import java.util.Date;
import java.util.List;

public class Task {
	public String  owner,
			title,
			description;
	public Date    dateCreated,
			dateUpdated,
			nextScheduled;
	
	// History
	static class Event {
		Date    datePerformed;
		float   success;
		boolean attempted;
		int     duration;
	}
	public List<Event> events;
	
	int computeExpectedDuration() {
		return 0;
	}
}
