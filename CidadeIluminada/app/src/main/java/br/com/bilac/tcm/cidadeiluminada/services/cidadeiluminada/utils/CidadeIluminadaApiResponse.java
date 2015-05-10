package br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.utils;

/**
 * Created by arthur on 04/04/15.
 */
public class CidadeIluminadaApiResponse {
    private String status;
    private String cod_protocolo;

    private static class Errors {
        private String cod_protocolo;
        private String cep;
        private String arquivo_protocolo;

        public Errors() {
        }

        @Override
        public String toString() {
            return "Errors{" +
                    "cod_protocolo='" + cod_protocolo + '\'' +
                    ", cep='" + cep + '\'' +
                    ", arquivo_protocolo='" + arquivo_protocolo + '\'' +
                    '}';
        }
    }

    private Errors errors;

    public CidadeIluminadaApiResponse() {
    }

    @Override
    public String toString() {
        return "CidadeIluminadaApiResponse{" +
                "status='" + status + '\'' +
                ", cod_protocolo='" + cod_protocolo + '\'' +
                ", errors=" + errors +
                '}';
    }
}
