package br.com.bilac.tcm.cidadeiluminada.models;

/**
 * Created by Work on 04/05/2015.
 */
public class Denuncia {

    private String denuncia_descricao;
    private String denuncia_image_path;
    private Protocolo protocolo;

    public Denuncia(String denuncia_descricao, String denuncia_image_path, Protocolo protocolo) {
        this.denuncia_descricao = denuncia_descricao;
        this.denuncia_image_path = denuncia_image_path;
        this.protocolo = protocolo;
    }

    public String getDenuncia_descricao() {
        return denuncia_descricao;
    }

    public void setDenuncia_descricao(String denuncia_descricao) {
        this.denuncia_descricao = denuncia_descricao;
    }

    public String getDenuncia_image_path() {
        return denuncia_image_path;
    }

    public void setDenuncia_image_path(String denuncia_image_path) {
        this.denuncia_image_path = denuncia_image_path;
    }

    public Protocolo getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(Protocolo protocolo) {
        this.protocolo = protocolo;
    }


}
