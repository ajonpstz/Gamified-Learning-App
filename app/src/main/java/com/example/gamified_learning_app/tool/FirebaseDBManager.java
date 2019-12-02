package com.example.gamified_learning_app.tool;

import com.example.gamified_learning_app.data.CardSet;
import com.example.gamified_learning_app.data.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

// EVEYTHING IS AN ASYNCRONOUS MESS :(
public class FirebaseDBManager {
	private static FirebaseFirestore db = FirebaseFirestore.getInstance();
	
	
	/*
	
	All of the functions work as described
	
	onDelivery -> function that will be called upon the success of the operation
	onFail -> function that will be called upon the failure of the operation
	 */
	
	/*
	===============================================================================================
	
											USERS
	
	===============================================================================================
	 */
	
	public static void createUser(User user, Function<User,Void> onDelivery, Function<String,Void> onFail) {
		DocumentReference userDetails = db.collection("users").document(user.username);
		db.runTransaction(transaction -> {
			DocumentSnapshot snapshot = transaction.get(userDetails);
			if (!snapshot.exists())
				transaction.set(userDetails, new ObjectMapper().convertValue(user, Map.class));
			return null;
		}).addOnSuccessListener(aVoid->{
			if (onDelivery != null) onDelivery.apply(user);
		}).addOnFailureListener(e -> {
			if (onFail != null)
				onFail.apply(e.getLocalizedMessage());
		});
	}
	
	public static void getUser(String username, Function<User,Void> onDelivery,
	                           Function<String,Void> onFail) {
		DocumentReference userDetails = db.collection("users").document(username);
		userDetails.get().addOnSuccessListener(snapshot->{
			if (snapshot.exists()) {
				if (onDelivery != null) onDelivery.apply(new User(snapshot));
			} else if (onFail != null) onDelivery.apply(null);
		}).addOnFailureListener(e->{
			if (onFail != null) onFail.apply(e.getLocalizedMessage());
		});
	}
	
	public static void updateUser(User user, Function<User,Void> onDelivery,
	                              Function<String,Void> onFail) {
		DocumentReference userDetails = db.collection("users").document(user.username);
		userDetails.set(user).addOnSuccessListener(task-> {
			if (onDelivery != null) onDelivery.apply(user);
		}).addOnFailureListener(e->{
			if (onFail != null) onFail.apply(e.getLocalizedMessage());
		});
	}
	
	public static void deleteUser(User user, Function<User,Void> onDelivery,
	                              Function<String,Void> onFail) {
		DocumentReference userDetails = db.collection("users").document(user.username);
		userDetails.delete().addOnSuccessListener(aVoid -> {
			if (onDelivery != null)
				onDelivery.apply(user);
		}).addOnFailureListener(e->{
			if (onFail != null)
				onFail.apply(e.getLocalizedMessage());
		});
	}
	
	/*
	===============================================================================================
	
										CARD SETS
	
	===============================================================================================
	 */
	
	public static void createCardSet(CardSet deck, Function<CardSet,Void> onDelivery,
	                                 Function<String,Void> onFail) {
		// Identifier
		DocumentReference cardDetails = db.collection("cards").document(
			deck.getIdentifier());
		db.runTransaction(transaction -> {
			DocumentSnapshot snapshot = transaction.get(cardDetails);
			if (snapshot.exists()) {
				onFail.apply("A card deck of the same identifier already exist");
			} else
				transaction.set(cardDetails, new ObjectMapper().convertValue(deck, Map.class));
			return null;
		}).addOnSuccessListener(aVoid->{
			if (onDelivery != null)
				onDelivery.apply(deck);
		}).addOnFailureListener(e->{
			if (onFail != null)
				onFail.apply(e.getLocalizedMessage());
		});
	}
	
	public static void getCardSet(String owner, String setName,
	                              Function<CardSet, Void> onDelivery, Function<String,Void> onFail) {
		DocumentReference cardDetails = db.collection("cards")
			.document(new CardSet(owner, setName, "").getIdentifier());
		cardDetails.get().addOnSuccessListener(snapshot -> {
			if (onDelivery != null)
				onDelivery.apply(new CardSet(snapshot));
		}).addOnFailureListener(e->{
			if (onFail != null) onFail.apply(e.getLocalizedMessage());
		});
	}
	
	public static void updateCardSet(CardSet deck, Function<CardSet,Void> onDelivery,
	                              Function<String,Void> onFail) {
		DocumentReference cardDetails = db.collection("cards")
			.document(deck.getIdentifier());
		cardDetails.set(deck).addOnSuccessListener(task-> {
			if (onDelivery != null) onDelivery.apply(deck);
		}).addOnFailureListener(e->{
			if (onFail != null) onFail.apply(e.getLocalizedMessage());
		});
	}
	
	public static void deleteCardSet(CardSet deck, Function<CardSet,Void> onDelivery,
	                              Function<String,Void> onFail) {
		DocumentReference cardDetails = db.collection("cards").document(deck.getIdentifier());
		cardDetails.delete().addOnSuccessListener(aVoid -> {
			if (onDelivery != null) onDelivery.apply(deck);
		}).addOnFailureListener(e->{
			if (onFail != null) onFail.apply(e.getLocalizedMessage());
		});
	}
	
	/*
		Get card sets owned by a user
	 */
	public static void getCardSet(String owner, Function<List<CardSet>, Void> onDelivery, Function<String, Void> onFail) {
		db.collection("cards").whereEqualTo("owner", owner).get().addOnSuccessListener(queryDocumentSnapshots -> {
			List<CardSet> cardSets = new ArrayList<CardSet>();
			for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {
				cardSets.add(new CardSet(snapshot));
			}
			if (onDelivery != null) onDelivery.apply(cardSets);
		}).addOnFailureListener(e->{
			if (onFail != null) onFail.apply(e.getLocalizedMessage());
		});
	}
}
