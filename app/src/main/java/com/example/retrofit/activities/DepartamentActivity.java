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
import com.example.retrofit.adapter.DepartamentAdapter;
import com.example.retrofit.config.RetrofitConfig;
import com.example.retrofit.config.RoomConfig;
import com.example.retrofit.model.Departament;
import com.example.retrofit.repository.ResultEvent;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DepartamentActivity extends AppCompatActivity {

    private RecyclerView rvDepartament;
    private DepartamentAdapter departamentAdapter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departament);

        rvDepartament = (RecyclerView) findViewById(R.id.rvDepartament);
        rvDepartament.setLayoutManager(new LinearLayoutManager(DepartamentActivity.this));
    }

    @Override
    public void onResume() {
        super.onResume();

        getAllDepartaments(new ResultEvent() {
            @Override
            public <T> void onResult(T result) {
                // Quando houver resultado mostre os valores na tela
                List<Departament> departamentList = (List<Departament>) result; //RoomConfig.getInstance(DepartamentActivity.this).departamentDao().getAll();

                departamentAdapter = new DepartamentAdapter(DepartamentActivity.this, departamentList);
                rvDepartament.setAdapter(departamentAdapter);
            }

            @Override
            public void onFail(String message) {
                // Quando houver falha exiba uma mensagem de erro
                Toast.makeText(DepartamentActivity.this, message, Toast.LENGTH_SHORT).show();
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
                intent = new Intent(DepartamentActivity.this, DepartamentDadosActivity.class);
                intent.putExtra("departament", new Departament());
                startActivity(intent);

                return true;

            case R.id.action_refresh:
                onResume();

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void getAllDepartaments(ResultEvent resultEvent) {

        Call<List<Departament>> call = new RetrofitConfig().getDepartamentService().getAllDepartaments();

        call.enqueue(new Callback<List<Departament>>() {
            @Override
            public void onResponse(Call<List<Departament>> call, Response<List<Departament>> response) {
                List<Departament> departamentList = response.body();

                RoomConfig.getInstance(DepartamentActivity.this).departamentDao().insertAll(departamentList);

                resultEvent.onResult(departamentList);
            }

            @Override
            public void onFailure(Call<List<Departament>> call, Throwable t) {
                resultEvent.onFail("Falha na requisição!!!");
            }
        });
    }
}