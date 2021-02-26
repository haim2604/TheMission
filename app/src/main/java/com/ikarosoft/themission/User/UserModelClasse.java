package com.ikarosoft.themission.User;

import android.graphics.Bitmap;

import com.ikarosoft.themission.MyListener;

import java.util.List;

public class UserModelClasse {
    public final static UserModelClasse instance = new UserModelClasse();
    UserModelSqlClass sqlClass = new UserModelSqlClass();
    UserModelFirebaseClass modelfirebaseClass = new UserModelFirebaseClass();

    UserModelFirebase userModelFirebase = new UserModelFirebase();

    private UserModelClasse() {

    }
//
//    public void getUserByPhone(String phone, GetUserListener listener) {
//        modelfirebaseClass.getUserByPhone(phone, listener);
//    }
//
//
//    public interface MListener<T> {
//        void onComplete(T result);
//    }
//
//    public interface GetAllUserListener extends MListener<List<User>> {
//    }
//
//    ;
//
//
//    public interface GetUserListener {
//        void onComplete(User user);
//    }
//
//    //TODO l;fsfs;
////public void getAllUser(GetAllUserListener listener){
//    public void getAllUser(MyListener<List<User>> listener) {
//        //sqlClass.getAllUser(listener);
//        // modelfirebaseClass.getAllUser(listener);
//        userModelFirebase.getAllUser(listener);
//    }
//
//    public interface AddUserListener {
//        void onComplete();
//    }
//
//
//    public void addUser(User user, AddUserListener listener) {
//
//
//        // modelfirebaseClass.addUser(user,listener);
//
//    }
//
//    interface DeleteListener extends AddUserListener {
//    }
//
//    ;
//
//    public void deleteUser(User user, DeleteListener listener) {
//        modelfirebaseClass.deleteUser(user, listener);
//    }
//    //all function - delete ,update...and more
//
//    public interface UploadImageListener {
//        void onComplate(String url);
//    }
//
//    public void uploadImage(Bitmap bitmap, String name, final UploadImageListener listener) {
//        modelfirebaseClass.uploadImage(bitmap, name, listener);
//    }

}
