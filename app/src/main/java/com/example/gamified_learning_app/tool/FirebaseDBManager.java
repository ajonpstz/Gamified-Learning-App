package com.example.gamified_learning_app.tool;

import com.example.gamified_learning_app.data.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;
import java.util.function.Function;

// EVEYTHING IS AN ASYNCRONOUS MESS :(
public class FirebaseDBManager {
	private static FirebaseFirestore db = FirebaseFirestore.getInstance();
	
	public static void createUser(User user, Function<User,Void> onDelivery, Function<User,Void> onFail) {
		DocumentReference userDetails = db.collection("users").document(user.username);
		userDetails.set(user).addOnSuccessListener(task-> {
			if (onDelivery != null)
				onDelivery.apply(null);
		}).addOnFailureListener(task->{
			if (onFail != null)
				onFail.apply(null);
		});
	}
	
	public static void getUser(String username, Function<User,Void> onDelivery,
	                           Function<Void,Void> onFail) {
		DocumentReference userDetails = db.collection("users").document(username);
		userDetails.get().addOnCompleteListener(task->{
			if (task.isSuccessful()) {
				DocumentSnapshot snapshot = task.getResult();
				if (onDelivery != null)
					onDelivery.apply(new User(snapshot.getString("email"),
						snapshot.getString("username"),
						snapshot.getString("description")));
			} else if (onFail != null)
				onFail.apply(null);
		});
	}
	
	public static void updateUser(User user, Function<User,Void> onDelivery,
	                              Function<User,Void> onFail) {
		DocumentReference userDetails = db.collection("users").document(user.username);
		db.runTransaction(transaction -> {
			DocumentSnapshot snapshot = transaction.get(userDetails);
			transaction.update(userDetails, new ObjectMapper().convertValue(user, Map.class));
			if (onDelivery != null)
				onDelivery.apply(user);
			return null;
		}).addOnFailureListener(e -> {
			if (onFail != null)
				onFail.apply(user);
		});
	}
}
