package com.example.gamified_learning_app.data;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
		public Card(Map<String, Object> map) {
			this((String) map.get("term"), (String) map.get("definition"));
		}
	}
	
	public static class Event {
		public Date date;
		public double recallRate;
		public double duration;
		
		public Event(Date date, double recallRate, double duration) {
			this.date = date;
			this.recallRate = recallRate;
			this.duration = duration;
		}
		public Event(Map<String,Object> map) {
			this((Date) map.get("date"), (Double) map.get("recallRate"), (Double) map.get("duration"));
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
		if (this.history == null)
			this.history = new ArrayList<Event>();
		if (this.cards == null)
			this.cards = new ArrayList<Card>();
	}
	
	public CardSet(DocumentSnapshot snapshot) {
		this(snapshot.getString("owner"), snapshot.getString("name"),
			snapshot.getString("description"));
		if (snapshot.get("history") != null) {
			for (Map<String,Object> objectMap : (List<Map<String,Object>>) snapshot.get("history"))
				this.history.add(new Event(objectMap));
		}
		if (snapshot.get("cards") != null) {
			for (Map<String,Object> objectMap : (List<Map<String,Object>>) snapshot.get("cards"))
				this.cards.add(new Card(objectMap));
		}
	}
	
	public String getIdentifier() {
		return owner + "_" + name;
	}
}
