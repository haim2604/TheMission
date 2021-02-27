package com.ikarosoft.themission.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ikarosoft.themission.ListenerVoid;
import com.ikarosoft.themission.MissionAdapterViewModel;
import com.ikarosoft.themission.Project.ProjectModel;
import com.ikarosoft.themission.ProjectAdapterViewModel;
import com.ikarosoft.themission.R;
import com.ikarosoft.themission.Task.MyTask;
import com.ikarosoft.themission.adapters.ProjectAdapter;
import com.ikarosoft.themission.Project.MyProject;
import com.ikarosoft.themission.Project.ProjectModelteee;

import java.util.List;


public class AllProjectsFragment extends Fragment {
    RecyclerView listProj;
    List<MyProject> data;
    Button addPorjBtn;
    ProjectAdapterViewModel viewModel;
    SwipeRefreshLayout sref;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_projects, container, false);
        viewModel = new ViewModelProvider(this).get(ProjectAdapterViewModel.class);

        sref = view.findViewById(R.id.allproj_swipe);
        addPorjBtn = view.findViewById(R.id.allproj_btn_newproj);
        listProj = view.findViewById(R.id.allproj_recyclerView);
        listProj.setHasFixedSize(true);

        sref.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sref.setRefreshing(true);
                reloadData();

            }
        });


        // lRecyclerView stage 2
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        listProj.setLayoutManager(layoutManager);

       // data = ProjectModelteee.instance.getAllTask();


        ProjectAdapter adapter = new ProjectAdapter(viewModel,getLayoutInflater());
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



        viewModel.getData().observe(getViewLifecycleOwner(), new Observer<List<MyProject>>() {
            @Override
            public void onChanged(List<MyProject> myTasks) {
                adapter.notifyDataSetChanged();
            }
        });
        reloadData();
        return view;
    }

    private void reloadData() {
        addPorjBtn.setEnabled(false);
        ProjectModel.instance.refreshAllProject(new ListenerVoid() {
            @Override
            public void onComplete() {
                addPorjBtn.setEnabled(true);
                sref.setRefreshing(false);

            }
        });

    }
//
//    public void onResume() {
//        super.onResume();
//        if (adapter != null) {
//            adapter.notifyDataSetChanged();
//        }
//
//    }
}