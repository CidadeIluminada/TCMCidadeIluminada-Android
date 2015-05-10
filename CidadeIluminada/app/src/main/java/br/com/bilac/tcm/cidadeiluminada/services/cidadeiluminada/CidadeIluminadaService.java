package br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada;

import br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.models.CidadeIluminadaApiResponse;
import br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.models.CidadeIluminadaProtocoloApiResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

/**
 * Created by arthur on 04/04/15.
 */
public interface CidadeIluminadaService {

    @Multipart
    @POST("/protocolos/novo/")
    void novoProtocolo(@Part("cod_protocolo") String cod_protocolo,
                       @Part("cep") String cep,
                       @Part("logradouro") String logradouro,
                       @Part("cidade") String cidade,
                       @Part("bairro") String bairro,
                       @Part("numero") String numero,
                       @Part("estado") String estado,
                       @Part("descricao") String descricao,
                       @Part("arquivo_protocolo") TypedFile arquivo_protocolo,
                       Callback<CidadeIluminadaApiResponse> callback);
    @Multipart
    @POST("/protocolos/novo/")
    void novoProtocoloIdentificado(@Part("cod_protocolo") String cod_protocolo,
                                   @Part("cep") String cep,
                                   @Part("logradouro") String logradouro,
                                   @Part("cidade") String cidade,
                                   @Part("bairro") String bairro,
                                   @Part("numero") String numero,
                                   @Part("estado") String estado,
                                   @Part("descricao") String descricao,
                                   @Part("nome") String nome,
                                   @Part("email") String email,
                                   @Part("arquivo_protocolo") TypedFile arquivo_protocolo,
                                   Callback<CidadeIluminadaApiResponse> callback);

    @GET("/protocolos/protocolo.json")
    void atualizarProtocolo(@Query("cod_protocolo") String codProtocolo,
                            Callback<CidadeIluminadaProtocoloApiResponse> callback);
}