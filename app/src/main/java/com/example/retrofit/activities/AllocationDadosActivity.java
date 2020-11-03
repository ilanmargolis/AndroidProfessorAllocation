package com.example.retrofit.activities;

import android.os.Bundle;
import android.util.Log;
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
import com.example.retrofit.model.Allocation;
import com.example.retrofit.model.Departament;
import com.example.retrofit.util.LoaddingDialog;
import com.example.retrofit.config.RetrofitConfig;
import com.example.retrofit.config.RoomConfig;
import com.example.retrofit.model.Course;
import com.example.retrofit.model.Professor;
import com.example.retrofit.repository.ResultEvent;
import com.example.retrofit.util.Utils;

import java.text.Format;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllocationDadosActivity extends AppCompatActivity {

    private EditText etHoraInicio, etHoraFim;
    private Spinner spCurso, spProfessor, spDiasSemana;
    private Button btAllocatioOk, btAllocatioCancelar;
    private ArrayAdapter aa;
    private Allocation allocation;
    private Professor professor;
    private Course course;
    private String dayOfWeek;
    private ArrayList<String> hourOfDay;

    private final static byte CRUD_INC = 0;
    private final static byte CRUD_UPD = 1;
    private final static byte CRUD_DEL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allocation_dados);

        etHoraInicio = (EditText) findViewById(R.id.etHoraInicio);
        etHoraFim = (EditText) findViewById(R.id.etHoraFim);
        spCurso = (Spinner) findViewById(R.id.spCurso);
        spProfessor = (Spinner) findViewById(R.id.spProfessor);
        spDiasSemana = (Spinner) findViewById(R.id.spDiasSemana);
        btAllocatioOk = (Button) findViewById(R.id.btAllocatioOk);
        btAllocatioCancelar = (Button) findViewById(R.id.btAllocatioCancelar);

        allocation = (Allocation) getIntent().getSerializableExtra("allocation");

        carregaSpinners();

        preparaDados();

        btAllocatioOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int horaInicio = Utils.posHora(hourOfDay, etHoraInicio.getText().toString());
                int horaFim = Utils.posHora(hourOfDay, etHoraFim.getText().toString());

                if (allocation.getId() == 0) { // inclusão
                    if (horaInicio > 0 && horaFim > 0) {
                        allocation.setStartHour(horaInicio);
                        allocation.setEndHour(horaFim);

                        opcaoCrud(CRUD_INC);
                    } else {
                        Toast.makeText(AllocationDadosActivity.this, "É necessário informar o nome do professor!", Toast.LENGTH_SHORT).show();
                    }
                } else { // alteração
                    if (!allocation.getCourse().equals(course.getName()) ||
                            !allocation.getProfessor().equals(professor.getName()) ||
                            !allocation.getDayOfWeek().equals(dayOfWeek) ||
                            allocation.getStartHour() != horaInicio ||
                            allocation.getEndHour() != horaFim) {
                        allocation.setStartHour(horaInicio);
                        allocation.setEndHour(horaFim);

                        opcaoCrud(CRUD_UPD);
                    } else {
                        Toast.makeText(AllocationDadosActivity.this, "O nome do professor tem que ser diferente do atual!", Toast.LENGTH_SHORT).show();
                    }
                }

                finish();
            }
        });

        btAllocatioCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // exclusão
                Toast.makeText(AllocationDadosActivity.this, "Operação cancelada pelo usuário", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }

    private void preparaDados() {
        hourOfDay = Utils.geraHoras();

        if (allocation.getId() == 0) { // inclusão
            spCurso.setSelection(0);
            spProfessor.setSelection(0);
            spDiasSemana.setSelection(0);
        } else { // alteração e exclusão
            course = (Course) RoomConfig.getInstance(AllocationDadosActivity.this).courseDao().getCourse(allocation.getCourse().getId());

            // posiciona spinner no curso do horário
            if (course != null) {
                spCurso.setSelection(Utils.getIndex(spCurso, course.getName().toString().trim()));
            }

            professor = (Professor) RoomConfig.getInstance(AllocationDadosActivity.this).professorDao().getProfessor(allocation.getProfessor().getId());

            // posiciona spinner no professor do horário
            if (professor != null) {
                spProfessor.setSelection(Utils.getIndex(spProfessor, professor.getName().toString().trim()));
            }

            dayOfWeek = allocation.getDayOfWeek();

            spDiasSemana.setSelection(Utils.getIndex(spDiasSemana, dayOfWeek));

            etHoraInicio.setText(hourOfDay.get(allocation.getStartHour()));
            etHoraFim.setText(hourOfDay.get(allocation.getEndHour()));
        }
    }

    private void carregaSpinners() {
        allocation = (Allocation) getIntent().getSerializableExtra("allocation");

        // Carrega todos os cursos no spinner
        List<Course> courseList = RoomConfig.getInstance(AllocationDadosActivity.this).courseDao().getAll();
        aa = new ArrayAdapter<>(AllocationDadosActivity.this, android.R.layout.simple_spinner_item, courseList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spCurso.setAdapter(aa);

        spCurso.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // pega o curso selecionado no spinner e atribui ao horário
                allocation.setCourse((Course) spCurso.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                allocation.setCourse(null);
            }
        });

        // Carrega todos os professores no spinner
        List<Professor> professorList = RoomConfig.getInstance(AllocationDadosActivity.this).professorDao().getAll();
        aa = new ArrayAdapter<>(AllocationDadosActivity.this, android.R.layout.simple_spinner_item, professorList);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProfessor.setAdapter(aa);

        spProfessor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // pega o professor selecionado no spinner e atribui ao horário
                allocation.setProfessor((Professor) spProfessor.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                allocation.setProfessor(null);
            }
        });

        // Carrega todos os dias da semana no spinner
        ArrayList<String> diasSemana = new ArrayList<>();
        diasSemana.add("SUNDAY");
        diasSemana.add("MONDAY");
        diasSemana.add("TUESDAY");
        diasSemana.add("WEDNESDAY");
        diasSemana.add("THURSDAY");
        diasSemana.add("FRIDAY");
        diasSemana.add("SATURDAY");

        aa = new ArrayAdapter<>(AllocationDadosActivity.this, android.R.layout.simple_spinner_item, diasSemana);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spDiasSemana.setAdapter(aa);

        spDiasSemana.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // pega o dia da semana selecionado no spinner e atribui ao horário
                allocation.setDayOfWeek((String) spDiasSemana.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                allocation.setDayOfWeek(null);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_crud, menu);

        if (allocation.getId() == 0) { // inclusão
            btAllocatioOk.setText("Incluir");
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
        crudAllocation(tipoCrud, new ResultEvent() {
            @Override
            public <T> void onResult(T result) {
                if (tipoCrud == CRUD_INC)
                    Toast.makeText(AllocationDadosActivity.this, "Horário da aula incluída com sucesso", Toast.LENGTH_SHORT).show();
                else if (tipoCrud == CRUD_UPD)
                    Toast.makeText(AllocationDadosActivity.this, "Horário da aula alterada com sucesso", Toast.LENGTH_SHORT).show();
                else if (tipoCrud == CRUD_DEL)
                    Toast.makeText(AllocationDadosActivity.this, "Horário da aula excluída com sucesso", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFail(String message) {
                Toast.makeText(AllocationDadosActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void crudAllocation(byte tipoCrud, ResultEvent resultEvent) {
        final LoaddingDialog loaddingDialog = new LoaddingDialog(AllocationDadosActivity.this);

        loaddingDialog.startDialog();

        Call<Allocation> call = null;

        if (tipoCrud == CRUD_INC)
            call = new RetrofitConfig().getAllocationService().create(allocation);
        else if (tipoCrud == CRUD_UPD)
            call = new RetrofitConfig().getAllocationService().update(allocation.getId(), allocation);
        else if (tipoCrud == CRUD_DEL)
            call = new RetrofitConfig().getAllocationService().delete(allocation.getId());

        call.enqueue(new Callback<Allocation>() {
            @Override
            public void onResponse(Call<Allocation> call, Response<Allocation> response) {
                allocation = response.body();

                loaddingDialog.dismissDialog();

                resultEvent.onResult(allocation);
            }

            @Override
            public void onFailure(Call<Allocation> call, Throwable t) {
                if (tipoCrud == CRUD_INC)
                    resultEvent.onFail("Erro ao incluir horário da aula");
                else if (tipoCrud == CRUD_UPD)
                    resultEvent.onFail("Erro ao alterar horário da aula");
                else if (tipoCrud == CRUD_DEL)
                    resultEvent.onFail("Erro ao excluir horário da aula");
            }
        });
    }
}