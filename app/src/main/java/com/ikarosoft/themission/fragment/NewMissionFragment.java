package com.ikarosoft.themission.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.ikarosoft.themission.ListenerVoid;
import com.ikarosoft.themission.NewMissionViewModel;
import com.ikarosoft.themission.R;
import com.ikarosoft.themission.Task.MyTask;
import com.ikarosoft.themission.Task.TaskModel;

import java.util.Random;

public class NewMissionFragment extends Fragment {
    NewMissionViewModel viewModel;
    EditText taskName,des;
    Button addB;
    View view;
    CheckBox cbUser1,cbUser2,cbUser3,cbUser4,cbEverybody;
   // String numProject,phoneUser,nameTask,numberTask;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_mission, container, false);
        viewModel = new ViewModelProvider(this).get(NewMissionViewModel.class);
        taskName= view.findViewById(R.id.newproj_et_name);
        des= view.findViewById(R.id.newproj_et_desc);
        addB = view.findViewById(R.id.newmission_btn_addmiss);
        cbUser1=view.findViewById(R.id.newmission_user1_cb);
        cbUser2=view.findViewById(R.id.newmission_user2_cb);
        cbUser3=view.findViewById(R.id.newmission_user3_cb);
        cbUser4=view.findViewById(R.id.newmission_user4_cb);
        cbEverybody=view.findViewById(R.id.newmission_evrybody_cb);


       //get project



        addB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTask(view);
            }
        });
        return  view;
    }

    private void addTask(View view) {
        viewModel.setNumProject("bigproject");
        viewModel.setNameTask(taskName.getText().toString());
        int min=1000;
        int max=9999;

        Random rand = new Random();
        String randomNum = ""+rand.nextInt(((max - min) + 1) + min);

        viewModel.setNumberTask(viewModel.getNumProject()+"_"+viewModel.getNameTask()+randomNum);
        MyTask task = new MyTask(viewModel.getNameTask(),"start","0502" ,viewModel.getNumProject(),viewModel.getNumberTask());

        Log.d("TAGRAND",randomNum+"............."+viewModel.getNumberTask());
        TaskModel.instance.addTask(task, new ListenerVoid() {
            @Override
            public void onComplete() {
                Navigation.findNavController(view).popBackStack();

            }
        });

    }


}