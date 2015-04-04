package br.com.bilac.tcm.cidadeiluminada.services;

import java.io.File;

import br.com.bilac.tcm.cidadeiluminada.services.utils.CidadeIluminadaApiResponse;
import retrofit.Callback;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.mime.TypedFile;

/**
 * Created by arthur on 04/04/15.
 */
public interface CidadeIluminadaService {

    @Multipart
    @POST("/protocolos/novo/")
    void novoProtocolo(@Part("cod_protocolo") String cod_protocolo,
                       @Part("cep") String cep,
                       @Part("arquivo_protocolo") TypedFile arquivo_protocolo,
                       Callback<CidadeIluminadaApiResponse> callback);
}
