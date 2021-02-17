package com.ikarosoft.themission.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.ikarosoft.themission.MissionAdapterViewModel;
import com.ikarosoft.themission.MyApplication;
import com.ikarosoft.themission.MyListener;
import com.ikarosoft.themission.R;
import com.ikarosoft.themission.Task.MyTask;
import com.ikarosoft.themission.Task.TaskModel;
import com.ikarosoft.themission.fragment.AllMissionFragment;

import java.util.LinkedList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{



    //List<MyTask> data;
    MissionAdapterViewModel viewModel;


    LayoutInflater layoutInflater;

    public MyAdapter(MissionAdapterViewModel viewModel, LayoutInflater layoutInflater){
        this.viewModel= viewModel;
     //   viewModel = new ViewModelProvider(result).get(MissionAdapterViewModel.class);
       // this.data = result;

    //viewModel.getData().observe(new );



//        TaskModel.instance.getAllTask(new MyListener<List<MyTask>>() {
//            @Override
//            public void onComplete(List<MyTask> result) {
//                viewModel.setData(result);
//            }
//        });
        this.layoutInflater = layoutInflater;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public OnItemClickListener listener;

    public void setOnClickListener(OnItemClickListener listener){
        this.listener= listener;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //LayoutInflater inflater = LayoutInflater.from(context);

          View view = layoutInflater.inflate(R.layout.missiionlist_row,null);
        //   View view = inflater.inflate(R.layout.missiionlist_row,null);
        //   View view = getLayoutInflater()getAc.inflate(R.layout.missiionlist_row,null);
        MyViewHolder holder = new MissionViewHolder(view,listener);//1

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyTask task = viewModel.getData().getValue().get(position);

        holder.bindData(task,position);
    }

    @Override
    public int getItemCount() {
        if (viewModel.getData().getValue()==null){
            return 0;
        }
        return viewModel.getData().getValue().size();
    }
}
