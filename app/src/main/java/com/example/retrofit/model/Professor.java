package com.example.retrofit.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Professor")
public class Professor {

    @PrimaryKey(autoGenerate = true)
    private int local_id;
    @ColumnInfo(name = "server_id", index = true)
    private int id;
    private String name;
    private String cpf;
    private Departament departament;

    public Professor() {
    }

    public Professor(String name, String cpf, Departament departament) {
        this.name = name;
        this.cpf = cpf;
        this.departament = departament;
    }

    public void setDepartament(Departament departament) {
        this.departament = departament;
    }

    public int getLocal_id() {
        return local_id;
    }

    public void setLocal_id(int local_id) {
        this.local_id = local_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Departament getDepartament() {
        return departament;
    }

    public void setDepartamento(Departament departament) {
        this.departament = departament;
    }

    @Override
    public String toString() {
        return name;
    }
}
