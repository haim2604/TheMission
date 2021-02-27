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
import android.widget.Spinner;

import com.ikarosoft.themission.R;


public class NewProjectFragment extends Fragment {
    Button addBtn;
    Spinner sp2,sp3,sp4;
    String[] usersItem;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_project, container, false);

        sp2 = view.findViewById(R.id.newproj_spinner_user2);
        sp3 = view.findViewById(R.id.newproj_spinner_user3);
        sp4 = view.findViewById(R.id.newproj_spinner_user4);

        addBtn=view.findViewById(R.id.newproj_btn_add);

        usersItem = new String[]{"1", "2", "three"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, usersItem);
        sp2.setAdapter(adapter);
        String text = sp2.getSelectedItem().toString();

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String text2 = sp2.getSelectedItem().toString();


                Log.d("TAGADD","spinerrrr ......   "+text+"......."+i+"...."+text2);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_newProjectFragment_pop);
            }
        });
        return view;
    }
}