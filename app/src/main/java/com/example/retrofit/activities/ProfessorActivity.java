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
import com.example.retrofit.adapter.ProfessorAdapter;
import com.example.retrofit.config.RetrofitConfig;
import com.example.retrofit.config.RoomConfig;
import com.example.retrofit.model.Professor;
import com.example.retrofit.repository.ResultEvent;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfessorActivity extends AppCompatActivity {

    private RecyclerView rvProfessor;
    private ProfessorAdapter professorAdapter;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor);

        rvProfessor = (RecyclerView) findViewById(R.id.rv);
        rvProfessor.setLayoutManager(new LinearLayoutManager(ProfessorActivity.this));
    }

    @Override
    public void onResume() {
        super.onResume();

        getAllProfessors(new ResultEvent() {
            @Override
            public <T> void onResult(T result) {
                // Quando houver resultado mostre os valores na tela
                List<Professor> professoresList = (List<Professor>) result;

                professorAdapter = new ProfessorAdapter(ProfessorActivity.this, professoresList);
                rvProfessor.setAdapter(professorAdapter);
            }

            @Override
            public void onFail(String message) {
                // Quando houver falha exiba uma mensagem de erro
                Toast.makeText(ProfessorActivity.this, message, Toast.LENGTH_SHORT).show();
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
                intent = new Intent(ProfessorActivity.this, ProfessorDadosActivity.class);
                intent.putExtra("professor", new Professor());
                startActivity(intent);

                return true;

            case R.id.action_refresh:
                onResume();

            default:
                return super.onOptionsItemSelected(item);

        }
    }


    private void getAllProfessors(ResultEvent resultEvent) {

        Call<List<Professor>> call = new RetrofitConfig().getProfessorService().getAllProfessor();

        call.enqueue(new Callback<List<Professor>>() {
            @Override
            public void onResponse(Call<List<Professor>> call, Response<List<Professor>> response) {
                List<Professor> professoresList = response.body();

                RoomConfig.getInstance(ProfessorActivity.this).professorDao().insertAll(professoresList);

                resultEvent.onResult(professoresList);
            }

            @Override
            public void onFailure(Call<List<Professor>> call, Throwable t) {
                resultEvent.onFail("Falha na requisição!!!");
            }
        });
    }
}