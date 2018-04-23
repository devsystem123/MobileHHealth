package com.example.a16254838.hospitalhhealth;

/**
 * Created by 16254866 on 19/04/2018.
 */

public class Especialidade {

    int idEspecialidade;
    String nome;

    public static Especialidade create(int idEspecialidade, String nome){
        Especialidade espci = new Especialidade();
        espci.setIdEspecialidade(idEspecialidade);
        espci.setNome(nome);
        return espci;
    }

    public int getIdEspecialidade() {
        return idEspecialidade;
    }

    public void setIdEspecialidade(int idEspecialidade) {
        this.idEspecialidade = idEspecialidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
