package com.example.retrofit.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Professor")
public class Professor implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "server_id")
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

    public Professor(Professor p) { // clone
        this.id = p.id;
        this.name = p.name;
        this.cpf = p.cpf;
        this.departament = p.departament;
    }

    public void setDepartament(Departament departament) {
        this.departament = departament;
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
