package com.ikarosoft.themission.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.ikarosoft.themission.ListenerVoid;
import com.ikarosoft.themission.MyApplication;
import com.ikarosoft.themission.Project.MyProject;
import com.ikarosoft.themission.R;
import com.ikarosoft.themission.Task.MyTask;
import com.ikarosoft.themission.Task.TaskModel;
import com.ikarosoft.themission.User.UserModel;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class NewMissionFragment extends Fragment {
    private static final int REQUEST_IMAGE_CAPTURE = 0;
    private static final int REQUEST_IMAGE_GALLERY = 1;
    ProgressDialog progressDialog;
    private Bitmap imageBitmap;


    EditText taskName, des;
    Button addB;
    ImageView avatar;
    ImageButton tackPic;
    View view;
    CheckBox cbUser1, cbUser2, cbUser3, cbUser4, cbEverybody;
    // String numProject,phoneUser,nameTask,numberTask;
    MyProject project;
    String[] users;
    boolean tackP = false;
    MyTask task;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_mission, container, false);

        avatar = view.findViewById(R.id.newmission_iv_avatar);
        tackPic = view.findViewById(R.id.newmission_ib_tackpic);
        taskName = view.findViewById(R.id.newmission_et_name);
        des = view.findViewById(R.id.newmission_et_desc);
        addB = view.findViewById(R.id.newmission_btn_addmiss);
        cbUser1 = view.findViewById(R.id.newmission_user1_cb);
        cbUser2 = view.findViewById(R.id.newmission_user2_cb);
        cbUser3 = view.findViewById(R.id.newmission_user3_cb);
        cbUser4 = view.findViewById(R.id.newmission_user4_cb);
        cbEverybody = view.findViewById(R.id.newmission_evrybody_cb);

        project = AllMissionFragmentArgs.fromBundle(getArguments()).getProject();
        users = project.getUsersPhone().split("#");
        cbUser1.setText(users[0]);
        cbUser2.setText(users[1]);
        cbUser3.setText(users[2]);
        cbUser4.setText(users[3]);
        //get project


        tackPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tackPicture();
            }
        });

        addB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask(view);
            }
        });

        cbEverybody.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    cbUser1.setChecked(true);
                    cbUser2.setChecked(true);
                    cbUser3.setChecked(true);
                    cbUser4.setChecked(true);
                } else {
                    cbUser1.setChecked(false);
                    cbUser2.setChecked(false);
                    cbUser3.setChecked(false);
                    cbUser4.setChecked(false);

                }
            }
        });
        return view;
    }

    private boolean addTask(View view) {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("save task......");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        SharedPreferences sp = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);
        String myPhone = sp.getString("myPhone", "0222222222");

        String sName = taskName.getText().toString();
        String sDes = des.getText().toString();
        if (sName.equals("")){
            taskName.setError("Please add a name");
            progressDialog.dismiss();
            return false;
        }
        if (sDes.equals("")){
            taskName.setError("Please add a description");
            progressDialog.dismiss();
            return false;
        }
        String sNumberProj = project.getNumProj();
        Long tsLong = System.currentTimeMillis() / 1000;
        String ts = tsLong.toString();
        String numberTask = sName + ts;

        task = new MyTask(sName, "start", myPhone, sNumberProj, numberTask);

        task.setDescription(sDes);
        task.setDeleted(false);
        task.setProgress("0");
        task.setNote("");
        String nUsers = "n";
        if (cbUser1.isChecked()) {
            nUsers = users[0] + "#";
        }
        if (cbUser2.isChecked()) {
            nUsers = users[1] + "#";
        }
        if (cbUser3.isChecked()) {
            nUsers = users[2] + "#";
        }
        if (cbUser4.isChecked()) {
            nUsers = users[3];
        }
        if (!nUsers.equals("n")) {
            task.setUsers(nUsers);

            if (tackP) {
                BitmapDrawable drawable = (BitmapDrawable) avatar.getDrawable();
                Bitmap image = drawable.getBitmap();

                UserModel.instance.uploadImage(image, task.getNumberTask(), new UserModel.UploadImageListener() {
                    @Override
                    public void onComplate(String url) {
                        if (url.equals(null)) {
                            Log.d("TAGNEW", "url erorr to save");
                        } else {
                            task.setUrlPhotoTask(url);
                            saveTask();
                        }
                    }
                });

            } else {
                task.setUrlPhotoTask("n");
                saveTask();
            }
            return true;
        }else {
            cbUser1.setError("Please select at least one");
            progressDialog.dismiss();

            return false;
        }
    }

    private void saveTask() {


        TaskModel.instance.addTask(task, new ListenerVoid() {
            @Override
            public void onComplete() {
//                SharedPreferences sp = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);
//                sp.edit().putString("myPhone", user.getPhone()).commit();
//                sp.edit().putString("myName", user.getName()).commit();
                Navigation.findNavController(view).popBackStack();
                progressDialog.dismiss();
            }
        });

    }


    private void tackPicture() {
        final CharSequence[] option = {"Tack Photo", "Chose form gallery", "cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle("choose your profile pic");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (option[i].equals("Tack Photo")) {
                    Intent tackpicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(tackpicIntent, REQUEST_IMAGE_CAPTURE);
                } else if (option[i].equals("Chose form gallery")) {
                    // Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    //pickPhotoIntent.setType("image/*");
                    startActivityForResult(pickPhotoIntent, REQUEST_IMAGE_GALLERY);
                } else if (option[i].equals("cancel")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {

            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                avatar.setImageBitmap(imageBitmap);
                tackP = true;

            }
            if (requestCode == REQUEST_IMAGE_GALLERY) {
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    avatar.setImageBitmap(selectedImage);
                    tackP = true;

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }

            }
        } else if (resultCode == RESULT_CANCELED) {
            //Write your code if there's no result
        } else {

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

}