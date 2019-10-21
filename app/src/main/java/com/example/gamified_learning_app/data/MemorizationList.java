package com.example.gamified_learning_app.data;

import java.util.List;

public class MemorizationList extends Task {
	static class Card {
		String  front,
				back;
	}
	List<Card> cards;
}
