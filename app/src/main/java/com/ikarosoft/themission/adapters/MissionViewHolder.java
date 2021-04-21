package com.ikarosoft.themission.adapters;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ikarosoft.themission.R;
import com.ikarosoft.themission.Task.MyTask;
import com.squareup.picasso.Picasso;

public class MissionViewHolder extends MyViewHolder {
    public MyAdapter.OnItemClickListener listener;
    TextView nameTask, des, statusTask;
    de.hdodenhof.circleimageview.CircleImageView circleImageView;
    int position;

    public MissionViewHolder(@NonNull View itemView, final MyAdapter.OnItemClickListener listener) {
        super(itemView, listener);
        nameTask = itemView.findViewById(R.id.missionrow_tv_name);
        des = itemView.findViewById(R.id.missionrow_tv_des);
        statusTask = itemView.findViewById(R.id.missionrow_status_tv);
        circleImageView = itemView.findViewById(R.id.missionrow_avatar_iv);
        ;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            }
        });
    }

    public void bindData(MyTask task, int position) {
        this.position = position;
        des.setText(task.getDescription());
        nameTask.setText(task.getNameTask());
        statusTask.setText(task.getStatusTask());
        String url = task.getUrlPhotoTask();
        if (url.equals("n") || url.equals(null)) {
            circleImageView.setImageResource(R.drawable.clipboard);

        } else {
            Picasso.get().load(task.getUrlPhotoTask()).into(circleImageView);

        }
    }
}
