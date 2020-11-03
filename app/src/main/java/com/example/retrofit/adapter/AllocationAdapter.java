package com.example.retrofit.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofit.R;
import com.example.retrofit.activities.AllocationDadosActivity;
import com.example.retrofit.model.Allocation;
import com.example.retrofit.util.Utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AllocationAdapter extends RecyclerView.Adapter<AllocationAdapter.AllocationHolder> {

    private Context context;
    private List<Allocation> allocationList;
    private final LayoutInflater layoutInflater;
    private ArrayList<String> hourOfDay;

    public AllocationAdapter(Context context, List<Allocation> allocationList) {
        this.context = context;
        this.allocationList = allocationList;
        this.layoutInflater = LayoutInflater.from(context);
        this.hourOfDay = Utils.geraHoras();
    }

    @NonNull
    @Override
    public AllocationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.layout_allocation, parent, false);

        return new AllocationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AllocationHolder holder, int i) {
        Allocation allocation = allocationList.get(i);

        holder.tvAllocationCurso.setText(allocation.getCourse().getName());
        holder.tvAllocationProfessor.setText(allocation.getProfessor().getName());
        holder.tvAllocationDia.setText(allocation.getDayOfWeek());
        holder.tvAllocationHorario.setText(hourOfDay.get(allocation.getStartHour()) + " às " +
                hourOfDay.get(allocation.getEndHour()));
    }

    @Override
    public int getItemCount() {
        return this.allocationList != null ? this.allocationList.size() : 0;
    }

    public class AllocationHolder extends RecyclerView.ViewHolder {
        LinearLayout llAllocation;
        TextView tvAllocationCurso, tvAllocationProfessor, tvAllocationDia, tvAllocationHorario;

        public AllocationHolder(@NonNull View itemView) {
            super(itemView);

            tvAllocationCurso = (TextView) itemView.findViewById(R.id.tvAllocationCurso);
            tvAllocationProfessor = (TextView) itemView.findViewById(R.id.tvAllocationProfessor);
            tvAllocationDia = (TextView) itemView.findViewById(R.id.tvAllocationDia);
            tvAllocationHorario = (TextView) itemView.findViewById(R.id.tvAllocationHorario);

            llAllocation = (LinearLayout) itemView.findViewById(R.id.llAllocation);

            llAllocation.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, AllocationDadosActivity.class);
                    intent.putExtra("allocation", (Serializable) allocationList.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}