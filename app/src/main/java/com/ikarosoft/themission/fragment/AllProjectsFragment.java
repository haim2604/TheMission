package com.ikarosoft.themission.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseUser;
import com.ikarosoft.themission.ListenerVoid;
import com.ikarosoft.themission.MissionAdapterViewModel;
import com.ikarosoft.themission.MyApplication;
import com.ikarosoft.themission.MyListener;
import com.ikarosoft.themission.Project.ProjectModel;
import com.ikarosoft.themission.ProjectAdapterViewModel;
import com.ikarosoft.themission.R;
import com.ikarosoft.themission.Task.MyTask;
import com.ikarosoft.themission.User.UserModel;
import com.ikarosoft.themission.adapters.ProjectAdapter;
import com.ikarosoft.themission.Project.MyProject;
import com.ikarosoft.themission.Project.ProjectModelteee;

import java.util.List;


public class AllProjectsFragment extends Fragment {
    RecyclerView listProj;
    List<MyProject> data;
    Button addPorjBtn,setting;
    ProjectAdapterViewModel viewModel;
    SwipeRefreshLayout sref;
    String myPhone;
    View view;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_all_projects, container, false);
        viewModel = new ViewModelProvider(this).get(ProjectAdapterViewModel.class);

        sref = view.findViewById(R.id.allproj_swipe);
        addPorjBtn = view.findViewById(R.id.allproj_btn_newproj);
        listProj = view.findViewById(R.id.allproj_recyclerView);
        setting = view.findViewById(R.id.allproj_btn_setting);

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_global_setting);
            }
        });
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

        ItemTouchHelper helper= new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder target, int direction) {
                // Here is where you'll implement swipe to delete
                AlertDialog.Builder builder = new AlertDialog.Builder(target.itemView.getContext())
                        .setMessage("Are you sure?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Get the position of the item to be deleted
                                int position = target.getAdapterPosition();
                                // Then you can remove this item from the adapter
                            }
                });
                 builder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                                public void onClick (DialogInterface dialog,int id){
                                    // User cancelled the dialog,
                                    // so we will refresh the adapter to prevent hiding the item from UI
                                 //   mAdapter.notifyItemChanged(viewHolder.getAdapterPosition());
                                }
                            });
                builder.create().show();

                //livedata.remove(pos)
                //reloada()
            }
        });
        helper.attachToRecyclerView(listProj);

        adapter.setOnClickListener(new ProjectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                SharedPreferences sp = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);
                myPhone = sp.getString("myPhone", "nn");
                sp.edit().putString("mySelectProject"+myPhone, viewModel.getData().getValue().get(position).getNumProj()).commit();
               Navigation.findNavController(view).navigate(R.id.action_allProj_to_allMission);

                Log.d("TAG123","aaaa allll prog selecte "+position+"......"+viewModel.getData().getValue().get(position).getNumProj());
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
      //  reloadData();
        return view;
    }

    private void reloadData() {
        addPorjBtn.setEnabled(false);
        setting.setEnabled(false);
        ProjectModel.instance.refreshAllProject(new ListenerVoid() {
            @Override
            public void onComplete() {
                addPorjBtn.setEnabled(true);
                setting.setEnabled(true);
                sref.setRefreshing(false);

            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
////TODO meybe add in the final
//        SharedPreferences sp = MyApplication.context.getSharedPreferences("TAG", Context.MODE_PRIVATE);
//        String selectProject = sp.getString("mySelectProject"+myPhone, "nn");
//        if (!selectProject.equals("nn")){
//            Navigation.findNavController(view).navigate(R.id.action_allProj_to_allMission);
//
//        }
    }



//    public void onResume() {
//        super.onResume();
//        if (adapter != null) {
//            adapter.notifyDataSetChanged();
//        }
//
//    }
}