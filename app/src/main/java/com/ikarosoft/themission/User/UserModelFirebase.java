package com.ikarosoft.themission.User;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ikarosoft.themission.ListenerVoid;
import com.ikarosoft.themission.MyListener;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;


public class UserModelFirebase {
    List<User> data;


    public void getAllUser(MyListener <List<User>> listener) {
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

    public void addUser(User user, ListenerVoid listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("project")
                .document()
                .set(user.toMap())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "task add to firebase......");
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

 /*   public void getUserByPhone(String phone, UserModel.GetUserListener listener) {
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
    }*/

    public void deleteUser(User user, UserModel.DeleteListener listener) {
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


    public  void uploadImage(Bitmap bitmap,String name,final UserModel.UploadImageListener listener){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference imageRef = storage.getReference().child("images").child(name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte [] data = baos.toByteArray();
        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onComplate(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloadUrl= uri;
                        listener.onComplate(downloadUrl.toString());
                    }
                });



            }
        });



    }
}
