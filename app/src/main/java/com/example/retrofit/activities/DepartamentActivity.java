package com.example.retrofit.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
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

    private RecyclerView recyclerView;
    private DepartamentAdapter departamentAdapter;
    private boolean inserirDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departament);

        // provisório pois não sei como resolver a duplicidade quando insere
        inserirDados = (RoomConfig.getInstance(DepartamentActivity.this).departamentDao().getAll().size() == 0);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getAllDepartaments(new ResultEvent() {
            @Override
            public void onResult(List listaDepartament) {
                // Quando houver resultado mostre os valores na tela
                List<Departament> departamentList = RoomConfig.getInstance(DepartamentActivity.this).departamentDao().getAll();

                departamentAdapter = new DepartamentAdapter(DepartamentActivity.this, departamentList);
                recyclerView.setAdapter(departamentAdapter);
            }

            @Override
            public void onFail(String message) {
                // Quando houver falha exiba uma mensagem de erro
                Toast.makeText(DepartamentActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAllDepartaments(ResultEvent resultEvent) {

        Call<List<Departament>> call = new RetrofitConfig().getDepartamentService().getAllDepartaments();

        call.enqueue(new Callback<List<Departament>>() {
            @Override
            public void onResponse(Call<List<Departament>> call, Response<List<Departament>> response) {
                List<Departament> departamentList = response.body();

                if (inserirDados) {
                    RoomConfig.getInstance(DepartamentActivity.this).departamentDao().insertAll(departamentList);
                }

                resultEvent.onResult(departamentList);
            }

            @Override
            public void onFailure(Call<List<Departament>> call, Throwable t) {
                resultEvent.onFail("Falha na requisição!!!");
            }
        });
    }
}