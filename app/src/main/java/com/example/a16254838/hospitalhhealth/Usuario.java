package com.example.a16254838.hospitalhhealth;

import java.util.Date;

/**
 * Created by 16254866 on 16/04/2018.
 */

public class Usuario {

    int idPaciente;
    String nome;
    String dtNasc;
    String sexo;
    int rg;
    int cpf;
    String senha;
    String email;
    String telResidencial;
    String celular;
    int idEndereco;
    int idEstadoCivil;
    int idTipoSanguinio;
    int idConvenio;
    int idPlano;
    String fotoConvenio;
    String fotoPaciente;

    public static Usuario create(int idPaciente){
        Usuario user = new Usuario();
        user.setId(idPaciente);
        return user;
    }

    public int getId() {
        return idPaciente;
    }

    public void setId(int id) {
        this.idPaciente = id;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDtNasc() {
        return dtNasc;
    }

    public void setDtNasc(String dtNasc) {
        this.dtNasc = dtNasc;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getRg() {
        return rg;
    }

    public void setRg(int rg) {
        this.rg = rg;
    }

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelResidencial() {
        return telResidencial;
    }

    public void setTelResidencial(String telResidencial) {
        this.telResidencial = telResidencial;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public int getIdEstadoCivil() {
        return idEstadoCivil;
    }

    public void setIdEstadoCivil(int idEstadoCivil) {
        this.idEstadoCivil = idEstadoCivil;
    }

    public int getIdTipoSanguinio() {
        return idTipoSanguinio;
    }

    public void setIdTipoSanguinio(int idTipoSanguinio) {
        this.idTipoSanguinio = idTipoSanguinio;
    }

    public int getIdConvenio() {
        return idConvenio;
    }

    public void setIdConvenio(int idConvenio) {
        this.idConvenio = idConvenio;
    }

    public int getIdPlano() {
        return idPlano;
    }

    public void setIdPlano(int idPlano) {
        this.idPlano = idPlano;
    }

    public String getFotoConvenio() {
        return fotoConvenio;
    }

    public void setFotoConvenio(String fotoConvenio) {
        this.fotoConvenio = fotoConvenio;
    }

    public String getFotoPaciente() {
        return fotoPaciente;
    }

    public void setFotoPaciente(String fotoPaciente) {
        this.fotoPaciente = fotoPaciente;
    }
}
