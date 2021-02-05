package com.ikarosoft.themission.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ikarosoft.themission.R;
import com.ikarosoft.themission.model.MyTask;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>{
    List<MyTask> data;
    LayoutInflater layoutInflater;

    public MyAdapter(List<MyTask> data, LayoutInflater layoutInflater){
        this.data = data;
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
        MyTask task = data.get(position);

        holder.bindData(task,position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
