package com.ikarosoft.themission.User;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ikarosoft.themission.ListenerVoid;
import com.ikarosoft.themission.MyApplication;
import com.ikarosoft.themission.MyListener;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class UserModel {
    public final static UserModel instance = new UserModel();
    UserModelSql modelSql = new UserModelSql();

    UserModelFirebase modelFirebase = new UserModelFirebase();
    private FirebaseAuth mAuth;

    private UserModel() {

    }

    LiveData<List<User>> userList;


    public LiveData<List<User>> getAllUsers() {
        userList = modelSql.getAllUser();
        refreshAllUser(null);
        return userList;
    }

    public void refreshAllUser(final ListenerVoid listener) {

        SharedPreferences sp = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);
        String myPhone = sp.getString("myPhone", "0222222222");
        long lastUpdated = sp.getLong("lastUpdatedUser", 0);
        //2.get all update record from firebase form the last update data
        modelFirebase.getAllUser(lastUpdated, new MyListener<List<User>>() {
            @Override
            public void onComplete(List<User> result) {

                long lastU = 0;

                //3.insret the new updete to local db
                for (User us : result) {

                    modelSql.addUser(us, null);
                    if (us.getLastUpdated() > lastU) {
                        lastU = us.getLastUpdated();
                    }
                }
                //4.update the local last update date
                sp.edit().putLong("lastUpdatedUser", lastU).commit();

                //5.return the update data to the listeners
                if (listener != null) {
                    listener.onComplete();
                }
            }
        });

    }


    public void addUser(User user, ListenerVoid listener) {

        modelFirebase.addUser(user,listener);

    }



    public void uploadImage(Bitmap bitmap, String name, final UserModel.UploadImageListener listener) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        final StorageReference imageRef = storage.getReference().child("images").child(name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();
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
                        Uri downloadUrl = uri;
                        listener.onComplate(downloadUrl.toString());
                    }
                });


            }
        });


    }

    public void signIn(User user,MyListener<Boolean> listener) {
        modelFirebase.signIn(user, listener);
    }


    public interface UploadImageListener {
        void onComplate(String url);
    }


    public  void  deleteAll(){
        modelSql.deleteAll();
    }
}
