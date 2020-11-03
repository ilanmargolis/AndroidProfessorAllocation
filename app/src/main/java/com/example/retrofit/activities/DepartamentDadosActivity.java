package com.example.retrofit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.retrofit.R;
import com.example.retrofit.util.LoaddingDialog;
import com.example.retrofit.config.RetrofitConfig;
import com.example.retrofit.model.Departament;
import com.example.retrofit.repository.ResultEvent;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DepartamentDadosActivity extends AppCompatActivity {

    private EditText etNomeDepartament;
    private Button btDepartamentOk, btDepartamentCancelar;
    private Departament departament;

    private final static byte CRUD_INC = 0;
    private final static byte CRUD_UPD = 1;
    private final static byte CRUD_DEL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departament_dados);

        etNomeDepartament = (EditText) findViewById(R.id.etNomeDepartament);
        btDepartamentOk = (Button) findViewById(R.id.btDepartamentOk);
        btDepartamentCancelar = (Button) findViewById(R.id.btDepartamentCancelar);

        departament = (Departament) getIntent().getSerializableExtra("departament");

        if (departament.getId() == 0) { // inclusão

        } else {  // alteração e exclusão
            etNomeDepartament.setText(departament.getName());
        }

        btDepartamentOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeDepartament = etNomeDepartament.getText().toString().trim();

                if (departament.getId() == 0) { // inclusão
                    if (!nomeDepartament.equals("")) {
                        departament.setName(nomeDepartament);

                        opcaoCrud(CRUD_INC);
                    } else {
                        Toast.makeText(DepartamentDadosActivity.this, "É necessário informar o nome do departamento!", Toast.LENGTH_SHORT).show();
                    }
                } else { // alteração
                    if (!departament.getName().equalsIgnoreCase(etNomeDepartament.getText().toString().trim())) {
                        departament.setName(nomeDepartament);

                        opcaoCrud(CRUD_UPD);
                    } else {
                        Toast.makeText(DepartamentDadosActivity.this, "O nome do departamento tem que ser diferente do atual!", Toast.LENGTH_SHORT).show();
                    }
                }

                finish();
            }
        });

        btDepartamentCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // exclusão
                Toast.makeText(DepartamentDadosActivity.this, "Operação cancelada pelo usuário", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_crud, menu);

        if (departament.getId() == 0) { // inclusão
            btDepartamentOk.setText("Incluir");
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
        crudDepartament(tipoCrud, new ResultEvent() {
            @Override
            public <T> void onResult(T result) {
                if (tipoCrud == CRUD_INC)
                    Toast.makeText(DepartamentDadosActivity.this, "Departamento incluído com sucesso", Toast.LENGTH_SHORT).show();
                else if (tipoCrud == CRUD_UPD)
                    Toast.makeText(DepartamentDadosActivity.this, "Departamento alterado com sucesso", Toast.LENGTH_SHORT).show();
                else if (tipoCrud == CRUD_DEL)
                    Toast.makeText(DepartamentDadosActivity.this, "Departamento excluído com sucesso", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(String message) {
                Toast.makeText(DepartamentDadosActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void crudDepartament(byte tipoCrud, ResultEvent resultEvent) {
        final LoaddingDialog loaddingDialog = new LoaddingDialog(DepartamentDadosActivity.this);

        Call<Departament> call = null;

        if (tipoCrud == CRUD_INC)
            call = new RetrofitConfig().getDepartamentService().create(departament);
        else if (tipoCrud == CRUD_UPD)
            call = new RetrofitConfig().getDepartamentService().update(departament.getId(), departament);
        else if (tipoCrud == CRUD_DEL)
            call = new RetrofitConfig().getDepartamentService().delete(departament.getId());

        call.enqueue(new Callback<Departament>() {
            @Override
            public void onResponse(Call<Departament> call, Response<Departament> response) {
                departament = response.body();

                resultEvent.onResult(departament);
            }

            @Override
            public void onFailure(Call<Departament> call, Throwable t) {
                if (tipoCrud == CRUD_INC)
                    resultEvent.onFail("Erro ao incluir departamento");
                else if (tipoCrud == CRUD_UPD)
                    resultEvent.onFail("Erro ao alterar departamento");
                else if (tipoCrud == CRUD_DEL)
                    resultEvent.onFail("Erro ao excluir departamento");
            }
        });
    }
}