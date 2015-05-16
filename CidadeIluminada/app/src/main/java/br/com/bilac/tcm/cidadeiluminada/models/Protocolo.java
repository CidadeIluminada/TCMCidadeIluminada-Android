package br.com.bilac.tcm.cidadeiluminada.models;

import android.net.Uri;

import com.orm.SugarRecord;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.HashMap;
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
    private String nome;
    private String email;

    private String _timestamp;
    private String status;


    public Protocolo() {
    }

    public static final String NAO_ENVIADO = "nao_enviado";
    public static final String NOVO = "NOVO";
    public static final String INVALIDO = "INVALIDO";
    public static final String PROCESSADO = "PROCESSADO";

    public Protocolo(String cep, String estado, String cidade, String bairro, String logradouro,
                     String numero, String descricao, String nome, String email,
                     String arquivo_protocolo) {
        this.cod_protocolo = UUID.randomUUID().toString();
        this._timestamp = new DateTime(DateTimeZone.UTC).toString();
        this.status = NAO_ENVIADO;

        this.cep = cep;
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.logradouro = logradouro;
        this.numero = numero;
        this.descricao = descricao;
        this.arquivo_protocolo = arquivo_protocolo;
        this.nome = nome;
        this.email = email;
    }

    public static Protocolo novoProtocoloSJC(String cep, String bairro, String logradouro,
                                             String numero, String descricao, String nome,
                                             String email, Uri arquivo_protocolo) {
        return new Protocolo(cep, "SP", "Sâo José dos Campos", bairro, logradouro, numero,
                descricao, nome, email, arquivo_protocolo.toString());
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

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getTimestamp() {
        return _timestamp;
    }

    public String getStatus() {
        return getStatus(false);
    }
    public String getStatus(boolean pretty) {
        if (pretty) {
            HashMap<String, String> prettyStatusMap = new HashMap<>();
            prettyStatusMap.put(NAO_ENVIADO, "Não Enviado");
            prettyStatusMap.put(NOVO, "Novo");
            prettyStatusMap.put(INVALIDO, "Inválido");
            prettyStatusMap.put(PROCESSADO, "Processado");
            return prettyStatusMap.get(this.status);
        }
        else {
            return this.status;
        }
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void update(Protocolo protocolo) {
        setStatus(protocolo.getStatus());
        // Atualizar o resto
        save();
    }
}
