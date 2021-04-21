package com.ikarosoft.themission.Task;

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
import com.ikarosoft.themission.Project.MyProject;
import com.ikarosoft.themission.User.User;
import com.ikarosoft.themission.User.UserModel;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;


public class TaskModelFirebase {
    // Access a Cloud Firestore instance from your Activity
    List<MyTask> data;

    public void getAllTask(String myProject, long lastUpdated, MyListener<List<MyTask>> listener) {
        //TODO fix filter
        data = new LinkedList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Timestamp ts = new Timestamp(lastUpdated, 0);

        db.collection("task")
                .whereGreaterThanOrEqualTo("lastUpdated", ts)
                .whereEqualTo("numberProject", myProject)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                MyTask tt = new MyTask();
                                tt.fromMap(doc.getData());
//                                MyTask tt = doc.toObject(MyTask.class);
                                data.add(tt);
                                Log.d("TAGBACK", doc.getId() + " => " + doc.getData());

                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                        listener.onComplete(data);
                    }
                });


    }

    public void addTask(MyTask myTask, ListenerVoid listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("task")
                .document(myTask.getNumberTask())
                .set(myTask.toMap())
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

    public void getTaskByPhone(String phone, MyListener<MyTask> listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("task").document(phone).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        MyTask myTask = null;
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            if (doc != null) {
                                myTask = new MyTask();
                                myTask.fromMap(task.getResult().getData());
                                //  task.getResult().toObject(MyTask.class);
                            }
                        }
                        listener.onComplete(myTask);

                    }
                });
    }

    public void deleteTask(MyTask myTask, ListenerVoid listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("task").document(myTask.getNumberTask())
                .update("isDeleted", true)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        listener.onComplete();

                    }
                });
    }

    public void updateTask(MyTask myTask, ListenerVoid listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("task").document(myTask.getNumberTask())
                .update("progress", myTask.getProgress()
                        , "note", myTask.getNote()
                        , "statusTask", myTask.getStatusTask()
                        ,  "takenByUser" ,myTask.getTakenByUser() )

                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        listener.onComplete();

                    }
                });
    }

    public void deleteUser(MyTask myTask, ListenerVoid listener) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("task").document(myTask.getPhoneUser())
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
        final StorageReference imageRef = storage.getReference().child("images_task").child(name);
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
