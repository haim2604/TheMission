package com.ikarosoft.themission.adapters;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ikarosoft.themission.R;
import com.ikarosoft.themission.Project.MyProject;


public class PojectViewHolder extends RecyclerView.ViewHolder   {
    public MyAdapter.OnItemClickListener listener;
    TextView numberProject,nameProject,statusProject;
    TextView tv;
    ImageView imageProject;
    int position;


    public PojectViewHolder(@NonNull View itemView, final ProjectAdapter.OnItemClickListener listener) {
        super(itemView);
        tv= itemView.findViewById(R.id.tv2);
        numberProject =itemView.findViewById(R.id.projrow_number_tv);
        nameProject=itemView.findViewById(R.id.porjrow_name_tv);
        statusProject=itemView.findViewById(R.id.porjrow_status_tv);
        imageProject =itemView.findViewById(R.id.projrow_avatar_iv);;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        listener.onItemClick(position);
                    }
                }
            }
        });
    }
    public void bindData(MyProject Project, int position) {
        this.position = position;
        // tv.setText("a"+Project.getNameProject());
        //  numberProject.setText(" assss ");
        Log.d("TAG4",Project.getName());
          numberProject.setText(Project.getNumProj());
          nameProject.setText(Project.getName());
          statusProject.setText(Project.getStatus());
    }

}




