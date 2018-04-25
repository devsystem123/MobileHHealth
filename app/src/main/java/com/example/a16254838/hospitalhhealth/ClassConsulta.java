package com.example.a16254838.hospitalhhealth;

/**
 * Created by 16254866 on 25/04/2018.
 */

public class ClassConsulta {
    int idResConsulta;
    int idPaciente;
    int idMedico;
    int idConsulta;
    String Relatorio;

    public static ClassConsulta create(int idResConsulta, int idPaciente, int idMedico, int idConsulta, String Relatorio){
        ClassConsulta Consul = new ClassConsulta();
        Consul.setIdResConsulta(idResConsulta);
        Consul.setIdPaciente(idPaciente);
        Consul.setIdMedico(idMedico);
        Consul.setIdConsulta(idConsulta);
        Consul.setRelatorio(Relatorio);
        return Consul;
    }

    public int getIdResConsulta() {
        return idResConsulta;
    }

    public void setIdResConsulta(int idResConsulta) {
        this.idResConsulta = idResConsulta;
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

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public String getRelatorio() {
        return Relatorio;
    }

    public void setRelatorio(String relatorio) {
        Relatorio = relatorio;
    }
}
