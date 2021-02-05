package com.ikarosoft.themission;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ikarosoft.themission.adapters.MyAdapter;
import com.ikarosoft.themission.adapters.ProjectAdapter;
import com.ikarosoft.themission.model.MyProject;
import com.ikarosoft.themission.model.MyTask;
import com.ikarosoft.themission.model.ProjectModel;
import com.ikarosoft.themission.model.TaskModel;

import java.util.List;


public class AllProjectsFragment extends Fragment {
    RecyclerView listProj;
    List<MyProject> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_projects, container, false);

        Button addPorjBtn = view.findViewById(R.id.allproj_btn_newproj);
        listProj = view.findViewById(R.id.allproj_recyclerView);
        listProj.setHasFixedSize(true);

        // lRecyclerView stage 2
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        listProj.setLayoutManager(layoutManager);

        data = ProjectModel.instance.getAllTask();


        ProjectAdapter adapter = new ProjectAdapter(data,getLayoutInflater());
        listProj.setAdapter(adapter);

        adapter.setOnClickListener(new ProjectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
               // Navigation.findNavController(view).navigate(R.id.action_allMission_to_perframTask);

                Log.d("TAG123","aaaa allll prog selecte "+position);
            }
        });



        addPorjBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_allProjects_to_newProject);
            }
        });
        return view;
    }
}