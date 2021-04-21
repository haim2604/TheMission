package com.ikarosoft.themission.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ikarosoft.themission.ProjectAdapterViewModel;
import com.ikarosoft.themission.R;
import com.ikarosoft.themission.Project.MyProject;

public class ProjectAdapter extends RecyclerView.Adapter<PojectViewHolder> {

    ProjectAdapterViewModel viewModel;
    LayoutInflater layoutInflater;


    public ProjectAdapter(ProjectAdapterViewModel viewModel, LayoutInflater layoutInflater) {
        this.viewModel = viewModel;
        this.layoutInflater = layoutInflater;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public OnItemClickListener listener;

    public void setOnClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    @NonNull
    @Override
    public PojectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = layoutInflater.inflate(R.layout.porjectlist_row, null);
        PojectViewHolder holder = new PojectViewHolder(view, listener);//1
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PojectViewHolder holder, int position) {
        MyProject project = viewModel.getData().getValue().get(position);

        holder.bindData(project, position);
    }


    @Override
    public int getItemCount() {
        if (viewModel.getData().getValue() == null) {
            return 0;
        }
        return viewModel.getData().getValue().size();
    }
}
