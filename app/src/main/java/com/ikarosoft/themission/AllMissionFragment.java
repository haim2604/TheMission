package com.ikarosoft.themission;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ikarosoft.themission.model.MyTask;
import com.ikarosoft.themission.model.TaskModel;

import java.util.List;


public class AllMissionFragment extends Fragment {
    RecyclerView listMission;
    List<MyTask> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_mission, container, false);

        Button replaceBtn = view.findViewById(R.id.allmission_btn_proje_replace);
        Button addMissionBtn = view.findViewById(R.id.allproj_btn_newproj);
        listMission = view.findViewById(R.id.allmission_recyclerView);
        listMission.hasFixedSize();
        listMission.setHasFixedSize(true);


        // lRecyclerView stage 2
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        listMission.setLayoutManager(layoutManager);

        data = TaskModel.instance.getAllTask();

        MissAdapter adapter = new MissAdapter();
        listMission.setAdapter(adapter);

        adapter.setOnClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Log.d("TAG123","aaaa  "+position);
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


        return view;
    }

    class MissViewHolder extends RecyclerView.ViewHolder{
        public OnItemClickListener listener;
        TextView numberTask,nameTask,statusTask;
        TextView tv;
        ImageView imageTask;
        int position;


        public MissViewHolder(@NonNull View itemView) {
            super(itemView);

            tv= itemView.findViewById(R.id.tv2);
            numberTask =itemView.findViewById(R.id.missionrow_number_tv);
            nameTask=itemView.findViewById(R.id.missionrow_name_tv);
            statusTask=itemView.findViewById(R.id.missionrow_status_tv);
            imageTask =itemView.findViewById(R.id.missionrow_avatar_iv);;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(position);
                }
            });
        }

        public void bindData(MyTask task,int position) {
            this.position = position;
            tv.setText("a"+task.getNameTask());
          //  numberTask.setText(" assss ");
           // numberTask.setText(" assss "+task.getNumberTask());
          //  nameTask.setText(" assss "+task.getNameTask());
          //  statusTask.setText(" assss "+task.getStatusTask());
        }
    }
    interface OnItemClickListener{
        void onItemClick(int position);
    }


    class MissAdapter extends RecyclerView.Adapter<MissViewHolder>{
        private OnItemClickListener listener;

        void setOnClickListener(OnItemClickListener listener){
            this.listener= listener;
        }
        @NonNull
        @Override
        public MissViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            //viewType if i have diffrent view

            View view = getLayoutInflater().inflate(R.layout.list_row,null);
            MissViewHolder holder = new MissViewHolder(view);
            holder.listener = listener;
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull MissViewHolder holder, int position) {
            MyTask task = data.get(position);

            holder.bindData(task,position);

        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

}