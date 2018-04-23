package com.example.a16254838.hospitalhhealth;

/**
 * Created by 16254866 on 23/04/2018.
 */

public class Exame {
    int idResultadoExame;
    int idPaciente;
    int idMedico;
    int idExame;
    String resultado;


    public static Exame create(int idResultadoExame, int idPaciente, int idMedico, int idExame, String resultado){
        Exame exame = new Exame();
        exame.setIdResultadoExame(idResultadoExame);
        exame.setIdPaciente(idPaciente);
        exame.setIdMedico(idMedico);
        exame.setIdExame(idExame);
        exame.setResultado(resultado);
        return exame;
    }

    public int getIdResultadoExame() {
        return idResultadoExame;
    }

    public void setIdResultadoExame(int idResultadoExame) {
        this.idResultadoExame = idResultadoExame;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(int idMedico) {
        this.idMedico = idMedico;
    }

    public int getIdExame() {
        return idExame;
    }

    public void setIdExame(int idExame) {
        this.idExame = idExame;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
