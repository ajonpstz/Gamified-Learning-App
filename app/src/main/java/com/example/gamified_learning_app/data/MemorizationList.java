package com.example.gamified_learning_app.data;

import java.util.Date;
import java.util.List;

public class MemorizationList extends Task {
	static class Card {
		String  front,
				back;
		float success;
		int frequency;
	}
	List<Card> cards;
	public int computeExpectedDuration() {
		return 0;
	}
	
	protected Date computeExpectedDate() {
		return new Date();
	}
}
