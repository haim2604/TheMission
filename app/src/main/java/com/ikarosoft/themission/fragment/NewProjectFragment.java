package com.ikarosoft.themission.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
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
import com.ikarosoft.themission.Project.MyProject;
import com.ikarosoft.themission.Project.ProjectModel;
import com.ikarosoft.themission.R;

import java.util.Random;


public class NewProjectFragment extends Fragment {
    Button addBtn;
    Spinner sp2,sp3,sp4;
    String[] usersItem;
    View view;
    EditText nameProj,description;
    TextView myphone;
    String myPhoneString="0545444444";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_new_project, container, false);

        nameProj = view.findViewById(R.id.newproj_et_name);
        description = view.findViewById(R.id.newproj_et_description);
        myphone = view.findViewById(R.id.newproj_et_user1);

        sp2 = view.findViewById(R.id.newproj_spinner_user2);
        sp3 = view.findViewById(R.id.newproj_spinner_user3);
        sp4 = view.findViewById(R.id.newproj_spinner_user4);

        addBtn=view.findViewById(R.id.newproj_btn_add);

        myphone.setText(myPhoneString);

        usersItem = new String[]{"0523888888", "0546998772", "055975652", "0502111118", "0523344997", "0528001910"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, usersItem);
        sp2.setAdapter(adapter);
        sp3.setAdapter(adapter);
        sp4.setAdapter(adapter);

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text2 = sp2.getSelectedItem().toString();
           //TODO : add removeitem after pic

            //    removeItem(i);
            //adapter.notifyDataSetChanged();
                Log.d("TAGADD","spinerrrr ........"+i+"...."+text2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text2 = sp2.getSelectedItem().toString();
               // removeItem(i);

                Log.d("TAGADD","spinerrrr ........"+i+"...."+text2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text2 = sp2.getSelectedItem().toString();
                // removeItem(i);

                Log.d("TAGADD","spinerrrr ........"+i+"...."+text2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addProject();
            }
        });
        return view;
    }

    private void addProject() {
        MyProject project=new MyProject();
        project.setName(nameProj.getText().toString());
        project.setDescription(description.getText().toString());
        project.setStatus("start");
        project.setUsersPhone(myPhoneString+"#"+sp2.getSelectedItem().toString()+"#"+sp3.getSelectedItem().toString()+"#"+sp4.getSelectedItem().toString());

        int min=1000;
        int max=9999;
        Random rand = new Random();
        String randomNum = ""+rand.nextInt(((max - min) + 1) + min);

        project.setNumProj(nameProj.getText().toString()+randomNum);

        ProjectModel.instance.addProject(project, new ListenerVoid() {
            @Override
            public void onComplete() {
                Navigation.findNavController(view).popBackStack();

            }
        });

    }


    void removeItem(int numItem){
        String[] tmp = new String[usersItem.length - 1];
        int j = 0;
        for (int i = 0; i < usersItem.length; i++) {
            if (i != numItem) {
                tmp[j++] = usersItem[i];
            }
        }
        usersItem = tmp;
    }

}