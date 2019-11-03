package com.example.gamified_learning_app.data;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MemorizationList extends Task {
	static final public double ALPHA_FOR_EXP_AVERRAGING = .5;
	static final public double FAILURE_RATE = .5;
	
	static class Card {
		String  front,
				back;
		double success;
		int frequency;
	}
	List<Card> cards;
	
	/*  Using exponential averaging, we can calculate the expected duration
		of a certain task. The idea is very simple:
		
		predicted[0] = actual[0]
		predicted[i] = alpha * actual[i-1] + (1 - alpha) * predicted[i-1]
		
		result is in minutes
	 */
	public int computeExpectedDuration() {
		double expected = 0;
		for (Event event : this.events) {
			if (expected == 0) {
				expected = event.duration;
			} else {
				expected = expected * event.duration + (1 - ALPHA_FOR_EXP_AVERRAGING) * expected;
			}
		}
		return (int) (Math.ceil(expected));
	}
	
	public Date computeExpectedDate() {
		if (events.isEmpty()) {
			return Calendar.getInstance().getTime();
		} else {
			int j = -1;
			for (int i = 0; i < events.size(); i++) {
				if (events.get(i).success < FAILURE_RATE) {
					j = i;
				}
			}
			Date ret = events.get(events.size()-1).datePerformed;
			LocalDateTime.from(ret.toInstant()).plusDays(
				(long) (1.5 * Math.pow(2, (events.size()-1-j)))
			);
			return ret;
		}
	}
}
