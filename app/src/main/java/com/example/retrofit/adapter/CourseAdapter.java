package com.example.retrofit.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofit.R;
import com.example.retrofit.activities.CourseDadosActivity;
import com.example.retrofit.model.Course;

import java.io.Serializable;
import java.util.List;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseHolder> {

    private Context context;
    private List<Course> courseList;
    private final LayoutInflater layoutInflater;

    public CourseAdapter(Context context, List<Course> courseList) {
        this.context = context;
        this.courseList = courseList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public CourseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.layout_course, parent, false);

        return new CourseHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseHolder holder, int i) {
        holder.tvCourse.setText(courseList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return this.courseList != null ? this.courseList.size() : 0;
    }

    public class CourseHolder extends RecyclerView.ViewHolder {
        TextView tvCourse;

        public CourseHolder(@NonNull View itemView) {
            super(itemView);

            tvCourse = (TextView) itemView.findViewById(R.id.tvCourse);

            tvCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, CourseDadosActivity.class);
                    intent.putExtra("course", (Serializable) courseList.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
