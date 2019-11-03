package com.example.gamified_learning_app.data;

import java.util.Date;
import java.util.List;

public abstract class Task {
	public String  owner,
					title,
					description;
	public Date     dateCreated,
					dateUpdated,
					dateScheduled;
	public double    priority;
	
	// History
	static protected class Event {
		Date    datePerformed;
		double   success;
		boolean attempted;
		int     duration;
	}
	protected List<Event> events;
	
	public abstract int computeExpectedDuration();
	public abstract Date computeExpectedDate();
	
}
