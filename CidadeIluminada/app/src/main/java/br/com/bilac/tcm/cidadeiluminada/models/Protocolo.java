package br.com.bilac.tcm.cidadeiluminada.models;

import android.net.Uri;

import com.orm.SugarRecord;

import java.util.UUID;

/**
 * Created by arthur on 29/03/15.
 */
public class Protocolo extends SugarRecord {

    private String cod_protocolo;
    private String cep;
    private String estado;
    private String cidade;
    private String bairro;
    private String logradouro;
    private String numero;
    private String arquivo_protocolo;
    private String descricao;

    public Protocolo() {
    }

    public Protocolo(String cep, String estado, String cidade, String bairro, String logradouro,
                     String numero, String descricao, String arquivo_protocolo) {
        this.cod_protocolo = UUID.randomUUID().toString();

        this.cep = cep;
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.logradouro = logradouro;
        this.numero = numero;
        this.descricao = descricao;
        this.arquivo_protocolo = arquivo_protocolo;
    }

    public static Protocolo novoProtocoloSJC(String cep, String bairro, String logradouro,
                                             String numero, String descricao,
                                             Uri arquivo_protocolo) {
        return new Protocolo(cep, "SP", "Sâo José dos Campos", bairro, logradouro, numero,
                descricao, arquivo_protocolo.toString());
    }

    public String getCep() {
        return cep;
    }

    public String getNumero() {
        return numero;
    }

    public String getCodProtocolo() {
        return cod_protocolo;
    }

    public Uri getArquivoProtocolo() {
        return Uri.parse(arquivo_protocolo);
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getEstado() {
        return estado;
    }

    public String getCidade() {
        return cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public String getDescricao() {
        return descricao;
    }
}
