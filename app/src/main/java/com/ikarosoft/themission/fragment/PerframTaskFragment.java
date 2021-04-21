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
import androidx.navigation.Navigation;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ikarosoft.themission.ListenerVoid;
import com.ikarosoft.themission.MyApplication;
import com.ikarosoft.themission.R;
import com.ikarosoft.themission.Task.MyTask;
import com.ikarosoft.themission.Task.TaskModel;
import com.ikarosoft.themission.User.UserModel;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class PerframTaskFragment extends Fragment {

    private static final int REQUEST_IMAGE_CAPTURE = 0 ;
    private static final int REQUEST_IMAGE_GALLERY = 1;
    private Bitmap imageBitmap;

    View view;
    MyTask myTask;
    ImageView img;
    TextView name, des, tekenBy, prograss;
    EditText note;
    Button confirmBtn;
    RadioGroup radioGroup;
    RadioButton selected, beforeFinish, notDone, finished;
    SeekBar sbProgress;
    String myPhone, myName;
    ProgressDialog progressDialog;
    SharedPreferences sp;
    String users, status,url;
    String[] allUsers;
    boolean photoChanged=false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_perfram_task, container, false);
        sp = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);
        myPhone = sp.getString("myPhone", "nn");
        myName = sp.getString("myName", "0222222222");

        img = view.findViewById(R.id.perform_iv_img);
        name = view.findViewById(R.id.perform_tv_name);
        des = view.findViewById(R.id.perform_tv_des);
        tekenBy = view.findViewById(R.id.perform_tv_tekenby);
        prograss = view.findViewById(R.id.perform_tv_progress);
        note = view.findViewById(R.id.perform_et_note);
        confirmBtn = view.findViewById(R.id.perform_btn_confirm);
        radioGroup = view.findViewById(R.id.perform_radioGroup);
        selected = view.findViewById(R.id.perform_rb_selected);
        beforeFinish = view.findViewById(R.id.perform_rb_beforefinish);
        notDone = view.findViewById(R.id.perform_rb_notfinish);
        finished = view.findViewById(R.id.perform_rb_finish);
        sbProgress = view.findViewById(R.id.perform_seekbar_progress);


        myTask = PerframTaskFragmentArgs.fromBundle(getArguments()).getMyTask();
        name.setText(myTask.getNameTask());
        des.setText(myTask.getDescription());
        note.setText(myTask.getNote());
        users = myTask.getUsers() + " ";
        Log.d("TAG321", "userrt" + users);
        url = myTask.getUrlPhotoTask();
        if(!url.equals("n")){
            Picasso.get().load(url).into(img);
        }else{
            img.setImageResource(R.drawable.clipboard);
        }
        allUsers = users.split("#");
        boolean flag = false;
        for (int i = 0; i < 4; i++) {
            try {
                if (allUsers[i].contains(myPhone)) {
                    flag = true;
                }
            } catch (Exception e) {
            }


        }
        status = "a";
        status = myTask.getStatusTask();
        Log.d("TAG321", "selected" + status);

        switch (status) {
            case "start":
                disable(true);
                break;
            case "selected":
                selected.setChecked(true);

                break;

            case "towards the end":
                radioGroup.check(R.id.perform_rb_beforefinish);

                break;

            case "not done":
                radioGroup.check(R.id.perform_rb_notfinish);


                break;
            case "Finished":
                finished.setChecked(true);
                flag=false;
                break;
        }

        if ((myTask.getTakenByUser() != null)) {
                Log.d("TAGBACKTC", "selected by  ");

            tekenBy.setText("selected by " + myTask.getTakenByUser());
            ((RadioButton) radioGroup.getChildAt(0)).setEnabled(false);

        }

        if (!flag) {
            sbProgress.setVisibility(View.GONE);
            prograss.setVisibility(View.GONE);
            disable(false);
        } else {
            sbProgress.setVisibility(View.VISIBLE);
            prograss.setVisibility(View.VISIBLE);
            sbProgress.setProgress(Integer.parseInt(myTask.getProgress()));
            prograss.setText(myTask.getProgress() + "%");

        }
///        radiobutton1.setClickable(false);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId) {
                    case R.id.perform_rb_selected:
                        Log.d("TAG321", "selected");
                        status = "selected";

                        break;

                    case R.id.perform_rb_beforefinish:
                        Log.d("TAG321", "beforefinish");
                        status = "towards the end";
                        break;

                    case R.id.perform_rb_notfinish:
                        Log.d("TAG321", "perform_rb_notfinish");
                        status = "not done";

                        break;
                    case R.id.perform_rb_finish:
                        Log.d("TAG321", "perform_rb_finish");
                        status = "Finished";

                        break;
                }
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tackPicture();
            }
        });
        sbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                prograss.setText(i + "%");

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });
        return view;
    }


    private void updateData() {

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("save " + "....");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        myTask.setStatusTask(status);
        myTask.setNote(note.getText().toString());
        if(url.equals("n"))
        if(selected.isChecked()){
            myTask.setTakenByUser(myName+" - "+myPhone);
        }

        myTask.setProgress(sbProgress.getProgress()+"");

        if(photoChanged){
            BitmapDrawable drawable = (BitmapDrawable) img.getDrawable();
            Bitmap image = drawable.getBitmap();

            UserModel.instance.uploadImage(image, myTask.getNumberTask(), new UserModel.UploadImageListener() {
                @Override
                public void onComplate(String url) {
                    if(url.equals(null)){
                        Log.d("TAGNEW","url erorr to save");

                    }else {
                        myTask.setUrlPhotoTask(url);


                    }
                    sendTask();

                }
            });
        }else{
            sendTask();
        }


    }

    private void sendTask() {
        TaskModel.instance.updateTask(myTask, new ListenerVoid() {
            @Override
            public void onComplete() {
                Navigation.findNavController(view).popBackStack();
                progressDialog.dismiss();

            }

        });



    }


    private void disable(boolean bo) {
        for (int i = 0; i < 4; i++) {
            ((RadioButton) radioGroup.getChildAt(i)).setEnabled(bo);
        }

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
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && data != null) {

            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                Bundle extras = data.getExtras();
                imageBitmap = (Bitmap) extras.get("data");
                img.setImageBitmap(imageBitmap);
                photoChanged=true;
            }if (requestCode == REQUEST_IMAGE_GALLERY){
                try {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                    final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    img.setImageBitmap(selectedImage);
                    photoChanged=true;

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                }

            }
        }else  if (resultCode == RESULT_CANCELED) {
            //Write your code if there's no result
        }else {

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