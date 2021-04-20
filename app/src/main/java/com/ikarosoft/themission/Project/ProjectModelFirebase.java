package com.ikarosoft.themission.Project;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ikarosoft.themission.ListenerVoid;
import com.ikarosoft.themission.MyListener;
import com.ikarosoft.themission.Task.MyTask;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class ProjectModelFirebase {
    // Access a Cloud Firestore instance from your Activity
    List<MyProject> data;

    public void getAllProject(String myPhone,long lastUpdated, MyListener<List<MyProject>> listener) {
        //TODO fix filter
        data = new LinkedList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Timestamp ts = new Timestamp(lastUpdated, 0);


        db.collection("project")
             //  .whereGreaterThanOrEqualTo("lastUpdated", ts)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                MyProject pt = new MyProject();
                                pt.fromMap(doc.getData());
                                if (pt.getUsersPhone().contains(myPhone)){
                                    data.add(pt);
                                    Log.d("TAGBACK", doc.getId() + " => " + doc.getData());
                                }

                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                        listener.onComplete(data);
                    }
                });


    }

    public void addProject(MyProject myProject, ListenerVoid listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("project")
                .document()
                .set(myProject.toMap())
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

    public void getProjectByPhone(String phone, MyListener<MyProject> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("project").document(phone).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        MyProject myProject = null;
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            if (doc != null) {
                                myProject = new MyProject();
                                myProject.fromMap(task.getResult().getData());
                                //  task.getResult().toObject(MyTask.class);
                            }
                        }
                        listener.onComplete(myProject);

                    }
                });
    }

    public void deleteProject(MyProject myProject, ListenerVoid listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("project").document(myProject.getNumProj())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        listener.onComplete();

                    }
                });

    }


    public void uploadImage(Bitmap bitmap, String name, final MyListener<String> listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference imageRef = storage.getReference().child("images_project").child(name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
        UploadTask uploadTask = imageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                listener.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Uri downloadUrl = uri;
                        listener.onComplete(downloadUrl.toString());
                    }
                });


            }
        });


    }
}
