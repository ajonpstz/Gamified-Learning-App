package com.example.gamified_learning_app.data;

import com.google.firebase.firestore.DocumentSnapshot;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class CardSet {
	static final public double FAILURE_RATE = .5;
	static final public double SAVING_RATE = .75;
	static final public double Ebbinghaus_const = 1.84, Ebbinghaus_exp = 1.25;
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
	
	public void add(Card card) {
		this.cards.add(card);
	}
	
	public void removeCard(String term) {
		for (int i = 0; i < cards.size(); i++)
			if (cards.get(i).term.equals(term)) {
				cards.remove(i);
				break;
			}
	}
	
	public void add(Event event) {
		this.history.add(event);
	}
	
	public Date nextScheduled() {
		if (history.isEmpty())
			return Calendar.getInstance().getTime();
		else {
			int j = history.size()-1;
			while (history.get(j).recallRate >= FAILURE_RATE)
				j--;
			double t = Math.pow((1-SAVING_RATE)*Ebbinghaus_const/FAILURE_RATE,
				Math.log(10)/Math.log(Ebbinghaus_exp)) * Math.pow(2,history.size()-1-j);
			Date res = history.get(history.size()-1).date;
			LocalDateTime.from(res.toInstant()).plusDays(
				(long) Math.ceil(t / 1440)
			);
			return res;
		}
	}
	
	public String getIdentifier() {
		return owner + "_" + name;
	}
}
