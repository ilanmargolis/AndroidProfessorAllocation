package com.example.retrofit.activities;

import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retrofit.R;
import com.example.retrofit.util.LoaddingDialog;
import com.example.retrofit.config.RetrofitConfig;
import com.example.retrofit.config.RoomConfig;
import com.example.retrofit.model.Departament;
import com.example.retrofit.model.Professor;
import com.example.retrofit.repository.ResultEvent;
import com.example.retrofit.util.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfessorDadosActivity extends AppCompatActivity {

    private EditText etNomeProfessor, etCpfProfessor;
    private Button btProfessorOk, btProfessorCancelar;
    private Spinner spDepartament;
    private Professor professor;
    private Departament departament;

    private final static byte CRUD_INC = 0;
    private final static byte CRUD_UPD = 1;
    private final static byte CRUD_DEL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_dados);

        etNomeProfessor = (EditText) findViewById(R.id.etNomeProfessor);
        etCpfProfessor = (EditText) findViewById(R.id.etCpfProfessor);
        spDepartament = (Spinner) findViewById(R.id.spDepartament);
        btProfessorOk = (Button) findViewById(R.id.btProfessorOk);
        btProfessorCancelar = (Button) findViewById(R.id.btProfessorCancelar);

        carregaSpinner();

        preparaDados();

        btProfessorOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeProfessor = etNomeProfessor.getText().toString().trim();
                String cpfProfessor = etCpfProfessor.getText().toString();

                if (professor.getId() == 0) { // inclusão
                    if (!nomeProfessor.equals("")) {
                        professor.setName(nomeProfessor);
                        professor.setCpf(cpfProfessor);
                        professor.setDepartament(departament);

                        opcaoCrud(CRUD_INC);
                    } else {
                        Toast.makeText(ProfessorDadosActivity.this, "É necessário informar o nome do professor!", Toast.LENGTH_SHORT).show();
                    }
                } else { // alteração
                    if (!professor.getName().equalsIgnoreCase(etNomeProfessor.getText().toString().trim()) ||
                            !professor.getCpf().equals(etCpfProfessor.getText().toString().trim()) ||
                            !spDepartament.toString().equals(departament.getName())) {

                        professor.setName(nomeProfessor);
                        professor.setCpf(cpfProfessor);

                        opcaoCrud(CRUD_UPD);
                    } else {
                        Toast.makeText(ProfessorDadosActivity.this, "O nome do professor tem que ser diferente do atual!", Toast.LENGTH_SHORT).show();
                    }
                }

                finish();
            }
        });

        btProfessorCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // exclusão
                Toast.makeText(ProfessorDadosActivity.this, "Operação cancelada pelo usuário", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }

    private void preparaDados() {
        professor = (Professor) getIntent().getSerializableExtra("professor");

        if (professor.getId() == 0) { // inclusão
            spDepartament.setSelection(0);
        } else { // alteração e exclusão
            etNomeProfessor.setText(professor.getName());
            etCpfProfessor.setText(professor.getCpf());

            departament = RoomConfig.getInstance(ProfessorDadosActivity.this).departamentDao().getDepartament(professor.getDepartament().getId());

            // posiciona spinner no departamento do professor
            if (departament != null) {
                spDepartament.setSelection(Utils.getIndex(spDepartament, departament.getName().toString().trim()));
            }
        }
    }

    private void carregaSpinner() {
        // Carrega todos os departamentos no spinner
        List<Departament> departamentList = RoomConfig.getInstance(ProfessorDadosActivity.this).departamentDao().getAll();
        ArrayAdapter aa = new ArrayAdapter<>(ProfessorDadosActivity.this, android.R.layout.simple_spinner_item, departamentList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDepartament.setAdapter(aa);

        spDepartament.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // pega o departamento selecionado no spinner e atribui ao professor
                professor.setDepartament((Departament) spDepartament.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                professor.setDepartament(null);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_crud, menu);

        if (professor.getId() == 0) { // inclusão
            btProfessorOk.setText("Incluir");
            menu.clear();
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_del:
                opcaoCrud(CRUD_DEL);

                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void opcaoCrud(byte tipoCrud) {
        crudProfessor(tipoCrud, new ResultEvent() {
            @Override
            public <T> void onResult(T result) {
                if (tipoCrud == CRUD_INC)
                    Toast.makeText(ProfessorDadosActivity.this, "Professor incluído com sucesso", Toast.LENGTH_SHORT).show();
                else if (tipoCrud == CRUD_UPD)
                    Toast.makeText(ProfessorDadosActivity.this, "Professor alterado com sucesso", Toast.LENGTH_SHORT).show();
                else if (tipoCrud == CRUD_DEL)
                    Toast.makeText(ProfessorDadosActivity.this, "Professor excluído com sucesso", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(String message) {
                Toast.makeText(ProfessorDadosActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void crudProfessor(byte tipoCrud, ResultEvent resultEvent) {
        final LoaddingDialog loaddingDialog = new LoaddingDialog(ProfessorDadosActivity.this);

        Call<Professor> call = null;

        if (tipoCrud == CRUD_INC)
            call = new RetrofitConfig().getProfessorService().create(professor);
        else if (tipoCrud == CRUD_UPD)
            call = new RetrofitConfig().getProfessorService().update(professor.getId(), professor);
        else if (tipoCrud == CRUD_DEL)
            call = new RetrofitConfig().getProfessorService().delete(professor.getId());

        call.enqueue(new Callback<Professor>() {
            @Override
            public void onResponse(Call<Professor> call, Response<Professor> response) {
                professor = response.body();

                resultEvent.onResult(professor);
            }

            @Override
            public void onFailure(Call<Professor> call, Throwable t) {
                if (tipoCrud == CRUD_INC)
                    resultEvent.onFail("Erro ao incluir professor");
                else if (tipoCrud == CRUD_UPD)
                    resultEvent.onFail("Erro ao alterar professor");
                else if (tipoCrud == CRUD_DEL)
                    resultEvent.onFail("Erro ao excluir professor");
            }
        });
    }
}