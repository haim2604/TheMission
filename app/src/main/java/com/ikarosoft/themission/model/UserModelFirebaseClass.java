package com.ikarosoft.themission.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class UserModelFirebaseClass {
    // Access a Cloud Firestore instance from your Activity
    List<User> data;
    public void getAllUser(UserModelClass.GetAllUserListener listener) {
        data = new LinkedList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
               //filter if we want .whereEqualTo("capital", true)
                .get()
                 .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc: task.getResult()) {
                                User ut = doc.toObject(User.class);
                                data.add(ut);
                                Log.d("TAG", doc.getId() + " => " + doc.getData());

                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                        listener.onComplete(data);
                    }
                });



    }

    public void addUser(User user, UserModelClass.AddUserListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
// Create a new user with a first and last name
//        Map<String, Object> data = new HashMap<>();
//        data.put("first", "Ada");
//        data.put("last", "Lovelace");
//        data.put("born", 1815);

// Add a new document with a generated ID
        db.collection("users")
                .document(user.getPhone())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "user add to firebase......");
                        listener.onComplete();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error adding document", e);
                        listener.onComplete();

                    }
                });

    }

    public void getUserByPhone(String phone, UserModelClass.GetUserListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(phone).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                User user = null;
                if(task.isSuccessful()){
                    DocumentSnapshot doc = task.getResult();
                    if( doc != null){
                        user =task.getResult().toObject(User.class);
                    }
                }
                listener.onComplete(user);

            }
        });
    }

    public void deleteUser(User user, UserModelClass.DeleteListener listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users").document(user.getPhone())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        listener.onComplete();

                    }
                });

    }
}
