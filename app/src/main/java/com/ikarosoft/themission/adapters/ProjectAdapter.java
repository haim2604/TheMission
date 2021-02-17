package com.ikarosoft.themission.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ikarosoft.themission.R;
import com.ikarosoft.themission.model.MyProject;

import java.util.List;

public class ProjectAdapter extends RecyclerView.Adapter<PojectViewHolder>{
    List<MyProject> data;
    LayoutInflater layoutInflater;


    public ProjectAdapter(List<MyProject> data, LayoutInflater layoutInflater){
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
    public PojectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //LayoutInflater inflater = LayoutInflater.from(context);

          View view = layoutInflater.inflate(R.layout.porjectlist_row,null);
        //   View view = inflater.inflate(R.layout.missiionlist_row,null);
        //   View view = getLayoutInflater()getAc.inflate(R.layout.missiionlist_row,null);
        PojectViewHolder holder = new PojectViewHolder(view,listener);//1

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PojectViewHolder holder, int position) {
        MyProject project = data.get(position);

        holder.bindData(project,position);
    }



    @Override
    public int getItemCount() {
        return data.size();
    }
}
