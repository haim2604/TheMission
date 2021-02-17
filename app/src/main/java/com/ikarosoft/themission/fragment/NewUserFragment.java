package com.ikarosoft.themission.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.ikarosoft.themission.R;
import com.ikarosoft.themission.User.User;
import com.ikarosoft.themission.User.UserModel;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class NewUserFragment extends Fragment {
    private static final int REQUEST_IMAGE_CAPTURE = 0 ;
    private static final int REQUEST_IMAGE_GALLERY = 1;
    Button addBtn;
    ImageView avatar,avatt;
    EditText name,phone,passAgain,password;
    ImageButton tackPic;
    private Bitmap imageBitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_user, container, false);
        addBtn = view.findViewById(R.id.newuser_btn_adduser);
        tackPic = view.findViewById(R.id.newuser_tackpic_ib);
        avatar = view.findViewById(R.id.newuser_pic_iv);
        name= view.findViewById(R.id.newuser_name_et);
        phone = view.findViewById(R.id.newuser_phone_et);
        passAgain = view.findViewById(R.id.newuser_confirmpass_et);
        password = view.findViewById(R.id.newuser_pass_et);
        avatt = view.findViewById(R.id.newuser_t_iv);
        avatt.setImageResource(R.drawable.b5);
        UserModel.instance.getUserByPhone("256666", new UserModel.GetUserListener() {
            @Override
            public void onComplete(User user) {

                Log.d("TAGURL","aaa 2 ffffffffff" +   user.getImageUrl());


                if(user.getImageUrl()!= null){
                    Picasso.get().load(user.getImageUrl()).placeholder(R.drawable.b5).into(avatt);

                }
            }
        });


        //tackPic.bringToFront();

        tackPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                tackPicture();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                saveUser(view);
            }
        });

        return  view;
    }

    private void saveUser(View view) {
        User user = new User();
        user.setName(name.getText().toString());
        user.setPhone(phone.getText().toString());
        String pass= password.getText().toString();
        if (pass.equals(passAgain.getText().toString())){
            user.setPassword(pass);
        }else{
            passAgain.setError("the password is not equal");
        }

        BitmapDrawable drawable = (BitmapDrawable) avatar.getDrawable();

        Bitmap image = drawable.getBitmap();
        UserModel.instance.uploadImage(image, user.getPhone(), new UserModel.UploadImageListener() {
            @Override
            public void onComplate(String url) {
                if(url.equals(null)){
                    Log.d("TAGURL","aaa 2 ffffffffff");

                }else {
                    user.setImageUrl(url);

                    Log.d("TAGURL","aaa 2" + url);
                    UserModel.instance.addUser(user, new UserModel.AddUserListener() {
                        @Override
                        public void onComplete() {
                            Log.d("TAGURL","user addd" + url);
                            Navigation.findNavController(view).popBackStack();

                        }
                    });
                }
            }
        });
    }


    private void tackPicture() {
        final CharSequence[] option = {"Tack Photo","Chose form gallery","cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("choose your profile pic");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(option[i].equals("Tack Photo")){
                    Intent tackpicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(tackpicIntent, REQUEST_IMAGE_CAPTURE);
                }else if(option[i].equals("Chose form gallery")){
                   // Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    //pickPhotoIntent.setType("image/*");
                    startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_GALLERY);
                }else if(option[i].equals("cancel")){
                    dialogInterface.dismiss();

                }
            }
        });
        builder.show();
//        //
//        Intent tackPicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (tackPicIntent.resolveActivity(getActivity().getPackageManager())!=null){
//            startActivityForResult(tackPicIntent,REQUEST_IMAGE_CAPTURE);
//        }

    Log.d("TAGADDDD"," ad       dadad");

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null) {

            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                avatar.setImageBitmap(imageBitmap);

            }if (requestCode == REQUEST_IMAGE_GALLERY){
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    avatar.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }


//                Uri selectedImage =  data.getData();
//                String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                if (selectedImage != null) {
//                    Cursor cursor = getActivity().getContentResolver().query(selectedImage,
//                            filePathColumn, null, null, null);
//                    if (cursor != null) {
//                        cursor.moveToFirst();
//
//                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                        String picturePath = cursor.getString(columnIndex);
//                        avatar.setImageBitmap(BitmapFactory.decodeFile(picturePath));
//                        cursor.close();
//                    }
//                }


            }
        }else  if (resultCode == RESULT_CANCELED) {
                //Write your code if there's no result
            }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("tack a picture is failed");
        builder.setMessage("please try later.......");
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}