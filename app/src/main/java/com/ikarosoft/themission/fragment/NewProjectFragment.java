package com.ikarosoft.themission.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.ikarosoft.themission.ListenerVoid;
import com.ikarosoft.themission.MyApplication;
import com.ikarosoft.themission.Project.MyProject;
import com.ikarosoft.themission.Project.ProjectModel;
import com.ikarosoft.themission.R;
import com.ikarosoft.themission.User.User;
import com.ikarosoft.themission.User.UserModel;
import com.ikarosoft.themission.UserViewModel;

import java.util.LinkedList;
import java.util.List;


public class NewProjectFragment extends Fragment {
    UserViewModel viewModel;
    Button addBtn;
    Spinner sp2,sp3,sp4;
    String[] usersItem;
    View view;
    EditText nameProj,description;
    TextView tvmyphone;
    String myPhone,myName;
    String user1,user2,user3,user4;
    ProgressDialog progressDialog;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_project, container, false);
        viewModel = new ViewModelProvider(this).get(UserViewModel.class);

        nameProj = view.findViewById(R.id.newmission_et_desc);
        description = view.findViewById(R.id.newproj_et_description);
        tvmyphone = view.findViewById(R.id.newproj_et_user1);

        sp2 = view.findViewById(R.id.newproj_spinner_user2);
        sp3 = view.findViewById(R.id.newproj_spinner_user3);
        sp4 = view.findViewById(R.id.newproj_spinner_user4);

        addBtn=view.findViewById(R.id.newproj_btn_add);


        SharedPreferences sp = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);
        myPhone = sp.getString("myPhone", "0222222222");
        myName = sp.getString("myName", "0222222222");
        user1=myName+" - "+myPhone;
        tvmyphone.setText(user1);

        List<String> data;
        data = new LinkedList<>();
        data.add("NONE");
        UserModel.instance.refreshAllUser(new ListenerVoid() {
            @Override
            public void onComplete() {
                int size;
                if (viewModel.getData().getValue()==null){
                    size= 0;
                }else {
                    size= viewModel.getData().getValue().size();
                }
                for (int i = 0; i < size; i++) {
                    User user = viewModel.getData().getValue().get(i);
                    if (!user.getPhone().equals(myPhone)){
                        data.add(user.getName()+" - "+user.getPhone());
                    }
                }
            }
        });



        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, data);
        sp2.setAdapter(adapter);
        sp3.setAdapter(adapter);
        sp4.setAdapter(adapter);

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                user2 = sp2.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                user2="NONE";
            }
        });



        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                user3 = sp3.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                user3="NONE";

            }
        });

        sp4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                user4 = sp4.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                user4="NONE";

            }
        });


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProject();
            }
        });

        viewModel.getData().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> myTasks) {
                adapter.notifyDataSetChanged();
            }
        });
        return view;
    }

    private void addProject() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("saving...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        MyProject project=new MyProject();
        project.setNameTask(nameProj.getText().toString());
        project.setDescription(description.getText().toString());
        project.setStatusTask("start");
        project.setUsersPhone(user1+"#"+user2+"#"+user3+"#"+user4);
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        project.setNumProj(nameProj.getText().toString()+ts);
        project.setDeleted(false);
        ProjectModel.instance.addProject(project, new ListenerVoid() {
            @Override
            public void onComplete() {

                Navigation.findNavController(view).popBackStack();
                progressDialog.dismiss();
            }
        });

    }



}