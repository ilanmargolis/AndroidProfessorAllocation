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
import com.example.retrofit.activities.DepartamentDadosActivity;
import com.example.retrofit.model.Departament;

import java.io.Serializable;
import java.util.List;

public class DepartamentAdapter extends RecyclerView.Adapter<DepartamentAdapter.DepartamentHolder> {

    private Context context;
    private List<Departament> departamentList;
    private final LayoutInflater layoutInflater;

    public DepartamentAdapter(Context context, List<Departament> departamentList) {
        this.context = context;
        this.departamentList = departamentList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public DepartamentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.layout_departament, parent, false);

        return new DepartamentAdapter.DepartamentHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DepartamentHolder holder, int i) {
        holder.tvDepartament.setText(departamentList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return this.departamentList != null ? this.departamentList.size() : 0;
    }

    public class DepartamentHolder extends RecyclerView.ViewHolder {
        TextView tvDepartament;

        public DepartamentHolder(@NonNull View itemView) {
            super(itemView);

            tvDepartament = (TextView) itemView.findViewById(R.id.tvDepartament);

            tvDepartament.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DepartamentDadosActivity.class);
                    intent.putExtra("departament", (Serializable) departamentList.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
