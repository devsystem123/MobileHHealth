package com.example.a16254838.hospitalhhealth;

/**
 * Created by 16254866 on 24/05/2018.
 */

public class ClassAgendaConsulta {
    int idConsulta;
    String data;
    String hora;
    int idPaciente;
    int idEspecialidade;


    public static ClassAgendaConsulta create(int idConsulta, String data, String hora, int idPaciente, int idEspecialidade){
        ClassAgendaConsulta Consul = new ClassAgendaConsulta();
        Consul.setIdConsulta(idConsulta);
        Consul.setData(data);
        Consul.setHora(hora);
        Consul.setIdPaciente(idPaciente);
        Consul.setIdEspecialidade(idEspecialidade);


        return Consul;
    }

    public int getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(int idConsulta) {
        this.idConsulta = idConsulta;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public int getIdEspecialidade() {
        return idEspecialidade;
    }

    public void setIdEspecialidade(int idEspecialidade) {
        this.idEspecialidade = idEspecialidade;
    }
}
