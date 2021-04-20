package com.ikarosoft.themission.adapters;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ikarosoft.themission.R;
import com.ikarosoft.themission.Task.MyTask;
import com.squareup.picasso.Picasso;

public class MissionViewHolder extends MyViewHolder   {
    public MyAdapter.OnItemClickListener listener;
    TextView nameTask,des,statusTask;
    TextView tv;
    ImageView imageTask;
    int position;

    public MissionViewHolder(@NonNull View itemView, final MyAdapter.OnItemClickListener listener) {
        super(itemView,listener);
        tv= itemView.findViewById(R.id.tv2);
        nameTask =itemView.findViewById(R.id.missionrow_tv_name);
        des=itemView.findViewById(R.id.missionrow_tv_des);
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
        Log.d("TAG43",task.getDescription());
        des.setText(task.getDescription());
        nameTask.setText(task.getNameTask());
        statusTask.setText(task.getStatusTask());
        String url = task.getUrlPhotoTask();

        if(url.equals("n")||url.equals(null)){
            imageTask.setImageResource(R.drawable.clipboard);

        }else{
            Picasso.get().load(task.getUrlPhotoTask()).into(imageTask);

        }
    }
}
