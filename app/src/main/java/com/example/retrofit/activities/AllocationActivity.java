package com.example.retrofit.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;


import com.example.retrofit.R;
import com.example.retrofit.adapter.AllocationAdapter;
import com.example.retrofit.config.RetrofitConfig;
import com.example.retrofit.config.RoomConfig;
import com.example.retrofit.model.Allocation;
import com.example.retrofit.repository.ResultEvent;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllocationActivity extends AppCompatActivity {

    private RecyclerView rvAllocation;
    private AllocationAdapter allocationAdapter;
    private boolean inserirDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allocation);

        // provisório pois não sei como resolver a duplicidade quando insere
        inserirDados = (RoomConfig.getInstance(AllocationActivity.this).allocationDao().getAll().size() == 0);

        rvAllocation = (RecyclerView) findViewById(R.id.rvAllocation);
        rvAllocation.setLayoutManager(new LinearLayoutManager(this));

        getAllAlocation(new ResultEvent() {
            @Override
            public void onResult(List list) {
                List<Allocation> allocationList = RoomConfig.getInstance(AllocationActivity.this).allocationDao().getAll();

                allocationAdapter = new AllocationAdapter(AllocationActivity.this, allocationList);
                rvAllocation.setAdapter(allocationAdapter);
            }

            @Override
            public void onFail(String message) {
                Toast.makeText(AllocationActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAllAlocation(ResultEvent resultEvent) {
        Call<List<Allocation>> call = new RetrofitConfig().getAllocationService().getAllAllocation();

        call.enqueue(new Callback<List<Allocation>>() {
            @Override
            public void onResponse(Call<List<Allocation>> call, Response<List<Allocation>> response) {
                List<Allocation> allocationList = response.body();

                if (inserirDados) {
                    RoomConfig.getInstance(AllocationActivity.this).allocationDao().insertAll(allocationList);
                }

                resultEvent.onResult(allocationList);
            }

            @Override
            public void onFailure(Call<List<Allocation>> call, Throwable t) {
                resultEvent.onFail("Falha na requisição!!!" + t.getMessage().toString());
            }
        });
    }
}