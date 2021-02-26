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
import com.ikarosoft.themission.MyListener;
import com.ikarosoft.themission.R;
import com.ikarosoft.themission.Task.TaskModel;
import com.ikarosoft.themission.adapters.MyAdapter;
import com.ikarosoft.themission.Task.MyTask;

import java.util.List;


public class AllMissionFragment extends Fragment {
    RecyclerView listMission;
    MissionAdapterViewModel viewModel;
    MyAdapter adapter = null;
    SwipeRefreshLayout sref;


    Button replaceBtn;
    Button addMissionBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_mission, container, false);
        viewModel = new ViewModelProvider(this).get(MissionAdapterViewModel.class);

        String idd = AllMissionFragmentArgs.fromBundle(getArguments()).getUserd();

        //TODO : arg object
        MyTask task = AllMissionFragmentArgs.fromBundle(getArguments()).getTes();
        Log.d("TAGAr", idd + "  " + task.getNameTask());

        replaceBtn = view.findViewById(R.id.allmission_btn_proje_replace);
        addMissionBtn = view.findViewById(R.id.allproj_btn_newproj);
        sref = view.findViewById(R.id.allmission_swipe);

        sref.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                sref.setRefreshing(true);
                reloadData();

            }
        });

        listMission = view.findViewById(R.id.allmission_recyclerView);
        listMission.hasFixedSize();
        listMission.setHasFixedSize(true);

        // lRecyclerView stage 2
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        listMission.setLayoutManager(layoutManager);

        //  MyAdapter adapter = new MyAdapter(this,getLayoutInflater());


        MyAdapter adapter = new MyAdapter(viewModel, getLayoutInflater());
        listMission.setAdapter(adapter);

//        MyAdapter adapter=null;
//        TaskModel.instance.getAllTask(new MyListener<List<MyTask>>() {
//            @Override
//            public void onComplete(List<MyTask> result) {
//
//                // TODO
//                //lanchList(result);
//                MyAdapter adapter = new MyAdapter(result,getLayoutInflater());
//                //MissAdapter adapter = new MissAdapter();
//                listMission.setAdapter(adapter);
//            }
//        });


        adapter.setOnClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Navigation.findNavController(view).navigate(R.id.action_allMission_to_perframTask);

                Log.d("TAG123", "aaaa  " + position);
            }
        });


        replaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_allMission_to_allProjects);
            }
        });

        addMissionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_allMission_to_newMission);
            }
        });

        viewModel.getData().observe(getViewLifecycleOwner(), new Observer<List<MyTask>>() {
            @Override
            public void onChanged(List<MyTask> myTasks) {
//                adapter = new MyAdapter(viewModel,getLayoutInflater());
//                listMission.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }
        });
        reloadData();
        return view;
    }

    private void reloadData() {
        addMissionBtn.setEnabled(false);
        replaceBtn.setEnabled(false);
        TaskModel.instance.refreshAllTask(new ListenerVoid() {
            @Override
            public void onComplete() {
                // adapter = new MyAdapter(viewModel,getLayoutInflater());
                // listMission.setAdapter(adapter);
                addMissionBtn.setEnabled(true);
                replaceBtn.setEnabled(true);
                sref.setRefreshing(false);

            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }

    }


    //    class MissViewHolder extends RecyclerView.ViewHolder{
//
//
//        public OnItemClickListener listener;
//        TextView numberTask,nameTask,statusTask;
//        TextView tv;
//        ImageView imageTask;
//        int position;
//
//
//        public MissViewHolder(@NonNull View itemView,final OnItemClickListener listener) {
//            super(itemView);
//
//            tv= itemView.findViewById(R.id.tv2);
//            numberTask =itemView.findViewById(R.id.missionrow_number_tv);
//            nameTask=itemView.findViewById(R.id.missionrow_name_tv);
//            statusTask=itemView.findViewById(R.id.missionrow_status_tv);
//            imageTask =itemView.findViewById(R.id.missionrow_avatar_iv);;
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(listener!=null){
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION){
//                            listener.onItemClick(position);
//                        }
//                    }
//                   //4 MissViewHolder.this.listener.onItemClick(position);
//                }
//            });
//        }
//
//        public void bindData(MyTask task,int position) {
//            this.position = position;
//           // tv.setText("a"+task.getNameTask());
//          //  numberTask.setText(" assss ");
//            Log.d("TAG4",task.getNumberTask());
//            numberTask.setText(task.getNumberTask());
//           nameTask.setText(task.getNumberTask());
//          statusTask.setText(task.getStatusTask());
//        }
//    }
//    interface OnItemClickListener{
//        void onItemClick(int position);
//    }
//
//
//    class MissAdapter extends RecyclerView.Adapter<MissViewHolder>{
//        private OnItemClickListener listener;
//        void setOnClickListener(OnItemClickListener listener){
//            this.listener= listener;
//        }
//
//
//        @NonNull
//        @Override
//        public MissViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            //viewType if i have diffrent view
//
//            View view = getLayoutInflater().inflate(R.layout.missiionlist_row,null);
//            MissViewHolder holder = new MissViewHolder(view,listener);//1
//           //2 holder.listener = listener;
//            return holder;
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull MissViewHolder holder, int position) {
//            MyTask task = data.get(position);
//
//            holder.bindData(task,position);
//
//        }
//
//        @Override
//        public int getItemCount() {
//            return data.size();
//        }
//    }

}