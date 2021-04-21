package com.ikarosoft.themission.adapters;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ikarosoft.themission.Task.MyTask;

public abstract class MyViewHolder extends RecyclerView.ViewHolder {


    public MyViewHolder(@NonNull View itemView, final MyAdapter.OnItemClickListener listener) {
        super(itemView);
    }

    public abstract void bindData(MyTask task, int position);

}
