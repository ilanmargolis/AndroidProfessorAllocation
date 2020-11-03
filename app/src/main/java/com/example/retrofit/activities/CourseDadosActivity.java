package com.example.retrofit.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.retrofit.R;
import com.example.retrofit.util.LoaddingDialog;
import com.example.retrofit.config.RetrofitConfig;
import com.example.retrofit.model.Course;
import com.example.retrofit.repository.ResultEvent;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseDadosActivity extends AppCompatActivity {

    private EditText etNomeCourse;
    private Button btCourseOk, btCourseCancelar;
    private Course course;

    private final static byte CRUD_INC = 0;
    private final static byte CRUD_UPD = 1;
    private final static byte CRUD_DEL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_dados);

        etNomeCourse = (EditText) findViewById(R.id.etNomeCourse);
        btCourseOk = (Button) findViewById(R.id.btCourseOk);
        btCourseCancelar = (Button) findViewById(R.id.btCourseCancelar);

        course = (Course) getIntent().getSerializableExtra("course");

        if (course.getId() == 0) { // inclusão

        } else { // alteração e exclusão
            etNomeCourse.setText(course.getName());
        }

        btCourseOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeCourse = etNomeCourse.getText().toString().trim();

                if (course.getId() == 0) { // inclusão
                    if (!nomeCourse.equals("")) {
                        course.setName(nomeCourse);

                        opcaoCrud(CRUD_INC);
                    } else {
                        Toast.makeText(CourseDadosActivity.this, "É necessário informar o nome do curso!", Toast.LENGTH_SHORT).show();
                    }
                } else { // alteração
                    if (!course.getName().equalsIgnoreCase(etNomeCourse.getText().toString().trim())) {
                        course.setName(nomeCourse);

                        opcaoCrud(CRUD_UPD);
                    } else {
                        Toast.makeText(CourseDadosActivity.this, "O nome do curso tem que ser diferente do atual!", Toast.LENGTH_SHORT).show();
                    }

                }

                finish();
            }
        });

        btCourseCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { // exclusão
                Toast.makeText(CourseDadosActivity.this, "Operação cancelada pelo usuário", Toast.LENGTH_SHORT).show();

                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_crud, menu);

        if (course.getId() == 0) { // inclusão
            btCourseOk.setText("Incluir");
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
        crudCourse(tipoCrud, new ResultEvent() {
            @Override
            public <T> void onResult(T result) {
                if (tipoCrud == CRUD_INC)
                    //RoomConfig.getInstance(CourseDadosActivity.this).courseDao().insert(course);
                    Toast.makeText(CourseDadosActivity.this, "Curso incluído com sucesso", Toast.LENGTH_SHORT).show();
                else if (tipoCrud == CRUD_UPD)
                    Toast.makeText(CourseDadosActivity.this, "Curso alterado com sucesso", Toast.LENGTH_SHORT).show();
                else if (tipoCrud == CRUD_DEL)
                    Toast.makeText(CourseDadosActivity.this, "Curso excluído com sucesso", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFail(String message) {
                Toast.makeText(CourseDadosActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void crudCourse(byte tipoCrud, ResultEvent resultEvent) {
        final LoaddingDialog loaddingDialog = new LoaddingDialog(CourseDadosActivity.this);

        loaddingDialog.startDialog();

        Call<Course> call = null;

        if (tipoCrud == CRUD_INC)
            call = new RetrofitConfig().getCourseService().create(course);
        else if (tipoCrud == CRUD_UPD)
            call = new RetrofitConfig().getCourseService().update(course.getId(), course);
        else if (tipoCrud == CRUD_DEL)
            call = new RetrofitConfig().getCourseService().delete(course.getId());

        call.enqueue(new Callback<Course>() {
            @Override
            public void onResponse(Call<Course> call, Response<Course> response) {
                course = response.body();

                loaddingDialog.dismissDialog();

                resultEvent.onResult(course);
            }

            @Override
            public void onFailure(Call<Course> call, Throwable t) {
                if (tipoCrud == CRUD_INC)
                    resultEvent.onFail("Erro ao incluir curso");
                else if (tipoCrud == CRUD_UPD)
                    resultEvent.onFail("Erro ao alterar curso");
                else if (tipoCrud == CRUD_DEL)
                    resultEvent.onFail("Erro ao excluir curso");

            }
        });
    }
}