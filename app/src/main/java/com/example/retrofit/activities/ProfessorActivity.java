package com.example.retrofit.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.retrofit.R;
import com.example.retrofit.adapter.ProfessorAdapter;
import com.example.retrofit.config.RetrofitConfig;
import com.example.retrofit.config.RoomConfig;
import com.example.retrofit.model.Departament;
import com.example.retrofit.model.Professor;
import com.example.retrofit.repository.ResultEvent;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfessorActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProfessorAdapter professorAdapter;
    private boolean inserirDados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor);

        // provisório pois não sei como resolver a duplicidade quando insere
        inserirDados = (RoomConfig.getInstance(ProfessorActivity.this).professorDAO().getAll().size() == 0);

        recyclerView = (RecyclerView) findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getAllProfessors(new ResultEvent() {
            @Override
            public void onResult(List listaProfessor) {
                // Quando houver resultado mostre os valores na tela
                List<Professor> professoresList = RoomConfig.getInstance(ProfessorActivity.this).professorDAO().getAll();

                professorAdapter = new ProfessorAdapter(ProfessorActivity.this, professoresList);
                recyclerView.setAdapter(professorAdapter);
            }

            @Override
            public void onFail(String message) {
                // Quando houver falha exiba uma mensagem de erro
                Toast.makeText(ProfessorActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
/*
    private void createProfessor() {
        Departament departament = new Departament(271, "");
        Professor p1 = new Professor("Marcelo novaes", "63552635263", departament);

        Call<Professor> call = new RetrofitConfig().getProfessorService().create(p1);

        call.enqueue(new Callback<Professor>() {
            @Override
            public void onResponse(Call<List<Professor>> call, Response<List<Professor>> response) {
                List<Professor> professores = response.body();

                Toast.makeText(MainActivity.this, professores.get(0).getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Professor>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Falha na requisição!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
 */

    private void getAllProfessors(ResultEvent resultEvent) {

        Call<List<Professor>> call = new RetrofitConfig().getProfessorService().getAllProfessor();

        call.enqueue(new Callback<List<Professor>>() {
            @Override
            public void onResponse(Call<List<Professor>> call, Response<List<Professor>> response) {
                List<Professor> professoresList = response.body();

                if (inserirDados) {
                    RoomConfig.getInstance(ProfessorActivity.this).professorDAO().insertAll(professoresList);
                }

                resultEvent.onResult(professoresList);
            }

            @Override
            public void onFailure(Call<List<Professor>> call, Throwable t) {
                resultEvent.onFail("Falha na requisição!!!");
            }
        });
    }

}