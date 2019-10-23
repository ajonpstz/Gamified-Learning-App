package com.example.gamified_learning_app.data;

import java.util.Date;
import java.util.List;

public abstract class Task {
	public String  owner,
					title,
					description;
	public Date     dateCreated,
					dateUpdated,
					nextScheduled,
					expectedDate;
	public float    priority;
	
	// History
	static class Event {
		Date    datePerformed;
		float   success;
		boolean attempted;
		int     duration;
	}
	public List<Event> events;
	
	public abstract int computeExpectedDuration();
	protected abstract Date computeExpectedDate();
	
}
