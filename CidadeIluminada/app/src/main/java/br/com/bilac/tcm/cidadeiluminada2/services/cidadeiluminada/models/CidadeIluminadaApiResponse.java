package br.com.bilac.tcm.cidadeiluminada2.services.cidadeiluminada.models;

import java.util.Arrays;

import br.com.bilac.tcm.cidadeiluminada2.models.Protocolo;

/**
 * Created by arthur on 04/04/15.
 */
public class CidadeIluminadaApiResponse {
    private String status;
    public CidadeIluminadaApiResponse() {
    }

    public CidadeIluminadaApiResponse(String status) {
        this.status = status;
    }

    public Protocolo getProtocolo() {
        return protocolo;
    }

    private Protocolo protocolo;
    private Protocolo[] protocolos;

    public Protocolo[] getProtocolos() {
        return protocolos;
    }

    public String getStatus() {
        return status;
    }

    private static class Errors {

        private String cod_protocolo;
        private String[] cep;
        private String[] email;
        private String nome;
        private String[] estado;
        private String cidade;
        private String bairro;
        private String logradouro;
        private String numero;
        private String arquivo_protocolo;


        public Errors() {
        }

        @Override
        public String toString() {
            return "Errors{" +
                    "cod_protocolo='" + cod_protocolo + '\'' +
                    ", cep=" + Arrays.toString(cep) +
                    ", email=" + Arrays.toString(email) +
                    ", nome='" + nome + '\'' +
                    ", estado=" + Arrays.toString(estado) +
                    ", cidade='" + cidade + '\'' +
                    ", bairro='" + bairro + '\'' +
                    ", logradouro='" + logradouro + '\'' +
                    ", numero='" + numero + '\'' +
                    ", arquivo_protocolo='" + arquivo_protocolo + '\'' +
                    '}';
        }
    }

    private Errors errors;
    @Override
    public String toString() {
        return "CidadeIluminadaApiResponse{" +
                "status='" + status + '\'' +
                ", errors=" + errors +
                ", protocolo=" + protocolo +
                '}';
    }

    public boolean isOk() {
        return status.equals(STATUS_OK);
    }

    public static final String STATUS_OK = "OK";
    public static final String STATUS_ERROR = "ERROR";
    public static final String STATUS_ERROR_MOBILE_NETWORK = "ERROR_MOBILE_NETWORK";
}
