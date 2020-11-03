package com.example.retrofit.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allocation);

        rvAllocation = (RecyclerView) findViewById(R.id.rvAllocation);
        rvAllocation.setLayoutManager(new LinearLayoutManager(AllocationActivity.this));
    }

    @Override
    public void onResume() {
        super.onResume();

        getAllAlocation(new ResultEvent() {
            @Override
            public <T> void onResult(T result) {
                List<Allocation> allocationList = (List<Allocation>) result;

                allocationAdapter = new AllocationAdapter(AllocationActivity.this, allocationList);
                rvAllocation.setAdapter(allocationAdapter);
            }

            @Override
            public void onFail(String message) {
                Toast.makeText(AllocationActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_generic, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                intent = new Intent(AllocationActivity.this, AllocationDadosActivity.class);
                intent.putExtra("allocation", new Allocation());
                startActivity(intent);

                return true;

            case R.id.action_refresh:
                onResume();

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void getAllAlocation(ResultEvent resultEvent) {
        Call<List<Allocation>> call = new RetrofitConfig().getAllocationService().getAllAllocation();

        call.enqueue(new Callback<List<Allocation>>() {
            @Override
            public void onResponse(Call<List<Allocation>> call, Response<List<Allocation>> response) {
                List<Allocation> allocationList = response.body();

                RoomConfig.getInstance(AllocationActivity.this).allocationDao().insertAll(allocationList);

                resultEvent.onResult(allocationList);
            }

            @Override
            public void onFailure(Call<List<Allocation>> call, Throwable t) {
                resultEvent.onFail("Falha na requisição!!!" + t.getMessage().toString());
            }
        });
    }
}