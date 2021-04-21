package com.ikarosoft.themission.User;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ikarosoft.themission.ListenerVoid;
import com.ikarosoft.themission.MyApplication;
import com.ikarosoft.themission.MyListener;
import com.ikarosoft.themission.Project.MyProject;

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
   // SharedPreferences sp = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);
   // String myPhone = sp.getString("myPhone", "0222222222");

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


    public void addUserOLD(User user, MyListener<FirebaseUser> listener) {

        mAuth = FirebaseAuth.getInstance();
        String username = user.getPhone() + "@mission.com";

        mAuth.createUserWithEmailAndPassword(username, user.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "createUserWithEmail:success");
                            FirebaseUser fbuser = mAuth.getCurrentUser();
                            Log.d("TAGNEW", "user add without detail   ...  ");


                            if (fbuser != null) {
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(user.getName())
                                        .setPhotoUri(Uri.parse(user.getImageUrl()))
                                        .build();

                                fbuser.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("TAGNEW", "User profile updated.");
                                                    listener.onComplete(fbuser);

                                                }
                                            }
                                        });

                            }


                            Log.d("TAGLOGIN", "add user");

                            listener.onComplete(fbuser);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MyApplication.context, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            listener.onComplete(null);

                        }

                        // ...
                    }
                });

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

    public void logout() {
        FirebaseAuth.getInstance().signOut();
        Log.d("TAGLOGIN", "Logout");

    }

    public void getCurrentUser(MyListener<FirebaseUser> listener) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        listener.onComplete(mAuth.getCurrentUser());
    }


//    public void getUserByPhone(String phone, GetUserListener listener) {
//        modelfirebaseClass.getUserByPhone(phone,listener);
//    }


    public interface MListener<T> {
        void onComplete(T result);
    }

    public interface GetAllUserListener extends MListener<List<User>> {
    }

    ;


//    public interface GetUserListener extends UserModelClasse.GetUserListener {
//        void onComplete(User user);
//    }

    //TODO l;fsfs;
////public void getAllUser(GetAllUserListener listener){
//    public void getAllUser(MyListener<List<User>> listener) {
//        //sqlClass.getAllUser(listener);
//        // modelfirebaseClass.getAllUser(listener);
//       modelFirebase.getAllUser(listener);
//    }

    public interface AddUserListener {
        void onComplete();
    }


    interface DeleteListener extends AddUserListener {
    }

    ;

    public void deleteUser(User user, DeleteListener listener) {
      //  modelfirebaseClass.deleteUser(user, listener);
    }
    //all function - delete ,update...and more

    public interface UploadImageListener {
        void onComplate(String url);
    }

    public void uuploadImage(Bitmap bitmap, String name, final UploadImageListener listener) {
       // modelfirebaseClass.uploadImage(bitmap, name, listener);
    }

    public  void  deleteAll(){
        modelSql.deleteAll();
    }
}
