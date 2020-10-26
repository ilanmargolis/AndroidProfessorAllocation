package com.example.retrofit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofit.R;
import com.example.retrofit.model.Allocation;

import java.util.List;

public class AllocationAdapter extends RecyclerView.Adapter<AllocationAdapter.AllocationHolder> {

    private Context context;
    private List<Allocation> allocationList;
    private final LayoutInflater layoutInflater;

    public AllocationAdapter(Context context, List<Allocation> allocationList) {
        this.context = context;
        this.allocationList = allocationList;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public AllocationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.layout_allocation, parent, false);

        return new AllocationHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AllocationHolder holder, int i) {
        //Allocation allocation = allocationList.get(i);

        holder.tvAllocation.setText(allocationList.get(i).toString());
    }

    @Override
    public int getItemCount() {
        return this.allocationList.size();
    }

    public class AllocationHolder extends RecyclerView.ViewHolder {
        // gerenciar os itens do XML
        TextView tvAllocation;

        public AllocationHolder(@NonNull View itemView) {
            super(itemView);

            tvAllocation = (TextView) itemView.findViewById(R.id.tvAllocation);
        }
    }
}
