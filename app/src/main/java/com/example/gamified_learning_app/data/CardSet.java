package com.example.gamified_learning_app.data;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CardSet {
	public String owner;
	public String name;
	public String description;
	
	public static class Card {
		public String term;
		public String definition;
		
		public Card(String term, String definition) {
			this.term = term;
			this.definition = definition;
		}
	}
	
	public static class Event {
		public Date date;
		public double recallRate;
		public double duration;
		
		Event(Date date, double recallRate, double duration) {
			this.date = date;
			this.recallRate = recallRate;
			this.duration = duration;
		}
	}
	
	public List<Event> history;
	public List<Card> cards;
	
	public CardSet(String owner, String name, String description) {
		this(owner, name, description, new ArrayList<Event>(), new ArrayList<Card>());
	}
	
	public CardSet(String owner, String name, String description, List<Event> history, List<Card> cards) {
		this.owner = owner;
		this.name = name;
		this.description = description;
		this.history = history;
		this.cards = cards;
	}
	
	public CardSet(DocumentSnapshot snapshot) {
		this(snapshot.getString("owner"), snapshot.getString("name"),
			snapshot.getString("description"));
		if (snapshot.get("history") != null)
			this.history.addAll((List<Event>) snapshot.get("history"));
		if (snapshot.get("cards") != null)
			this.cards.addAll((List<Card>) snapshot.get("cards"));
	}
	
	public String getIdentifier() {
		return owner + "_" + name;
	}
}
