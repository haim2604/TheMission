package com.ikarosoft.themission.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

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

import com.ikarosoft.themission.ListenerVoid;
import com.ikarosoft.themission.MyApplication;
import com.ikarosoft.themission.R;
import com.ikarosoft.themission.Task.MyTask;
import com.ikarosoft.themission.Task.TaskModel;


public class PerframTaskFragment extends Fragment {

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
    String users, status;
    String[] allUsers;


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
                disable(false);
                break;
        }

        if (!(myTask.getTakenByUser() == null)) {
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
        if(selected.isChecked()){
            myTask.setTakenByUser(myName+" - "+myPhone);
        }

        myTask.setProgress(sbProgress.getProgress()+"");
        TaskModel.instance.updateTask(myTask, new ListenerVoid() {
            @Override
            public void onComplete() {
                Navigation.findNavController(view).popBackStack();
                progressDialog.dismiss();

            }

        });

        progressDialog.dismiss();

    }

    private void disable(boolean bo) {
        for (int i = 0; i < 4; i++) {
            ((RadioButton) radioGroup.getChildAt(i)).setEnabled(bo);
        }

    }
//    progressDialog = new ProgressDialog(getContext());
//        progressDialog.setMessage("connect...");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.show();
//                          progressDialog.dismiss();

}