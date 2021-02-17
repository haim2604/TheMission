package com.ikarosoft.themission.adapters;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ikarosoft.themission.R;
import com.ikarosoft.themission.Task.MyTask;

public class MissionViewHolder extends MyViewHolder   {
    public MyAdapter.OnItemClickListener listener;
    TextView numberTask,nameTask,statusTask;
    TextView tv;
    ImageView imageTask;
    int position;

    public MissionViewHolder(@NonNull View itemView, final MyAdapter.OnItemClickListener listener) {
        super(itemView,listener);
        tv= itemView.findViewById(R.id.tv2);
        numberTask =itemView.findViewById(R.id.missionrow_number_tv);
        nameTask=itemView.findViewById(R.id.missionrow_name_tv);
        statusTask=itemView.findViewById(R.id.missionrow_status_tv);
        imageTask =itemView.findViewById(R.id.missionrow_avatar_iv);;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener!=null){
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION){
                        listener.onItemClick(position);
                    }
                }
                //4 MissViewHolder.this.listener.onItemClick(position);
            }
        });
    }
    public void bindData(MyTask task, int position) {
        this.position = position;
        // tv.setText("a"+task.getNameTask());
        //  numberTask.setText(" assss ");
        Log.d("TAG4",task.getNumberTask());
        numberTask.setText(task.getNumberTask());
        nameTask.setText(task.getNameTask());
        statusTask.setText(task.getStatusTask());
    }
}
