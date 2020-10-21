package com.example.retrofit.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retrofit.activities.ProfessorActivity;
import com.example.retrofit.R;
import com.example.retrofit.model.Professor;

import java.util.List;

public class ProfessorAdapter extends RecyclerView.Adapter<ProfessorAdapter.ProfessorHolder> {

    private List<Professor> professorList;
    private final LayoutInflater layoutInflater;
    private Context context;

    public ProfessorAdapter(Context context, List<Professor> professores) {
        this.context = context;
        this.professorList = professores;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ProfessorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Cria referência de layout
        View itemView = layoutInflater.inflate(R.layout.layout_professor, parent, false);

        return new ProfessorHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfessorHolder holder, int i) {
        // pega os dados da lista e joga na tela
        holder.tvNome.setText(professorList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        // retorna quantos itens tem na lista
        return this.professorList.size();
    }

    public class ProfessorHolder extends RecyclerView.ViewHolder {
        // gerenciador de itens/componente do XML

        ImageView ivProfessor;
        TextView tvNome;

        public ProfessorHolder(@NonNull View itemView) {
            super(itemView);

            ivProfessor = (ImageView) itemView.findViewById(R.id.ivProfessor);
            tvNome = (TextView) itemView.findViewById(R.id.tvProfessor);

            tvNome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, tvNome.getText().toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, ProfessorActivity.class);
                    intent.putExtra("nome", tvNome.getText().toString());
                    context.startActivity(intent);
                }
            });
        }
    }
}
