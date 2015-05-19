package br.com.bilac.tcm.cidadeiluminada2.services.cidadeiluminada;

import br.com.bilac.tcm.cidadeiluminada2.services.cidadeiluminada.models.CidadeIluminadaApiResponse;
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
    CidadeIluminadaApiResponse novoProtocolo(@Part("cod_protocolo") String cod_protocolo,
                       @Part("cep") String cep,
                       @Part("logradouro") String logradouro,
                       @Part("cidade") String cidade,
                       @Part("bairro") String bairro,
                       @Part("numero") String numero,
                       @Part("estado") String estado,
                       @Part("descricao") String descricao,
                       @Part("arquivo_protocolo") TypedFile arquivo_protocolo);

    @Multipart
    @POST("/protocolos/novo/")
    CidadeIluminadaApiResponse novoProtocoloIdentificado(@Part("cod_protocolo") String cod_protocolo,
                                   @Part("cep") String cep,
                                   @Part("logradouro") String logradouro,
                                   @Part("cidade") String cidade,
                                   @Part("bairro") String bairro,
                                   @Part("numero") String numero,
                                   @Part("estado") String estado,
                                   @Part("descricao") String descricao,
                                   @Part("nome") String nome,
                                   @Part("email") String email,
                                   @Part("arquivo_protocolo") TypedFile arquivo_protocolo);

    @GET("/protocolos/protocolo.json")
    CidadeIluminadaApiResponse atualizarProtocolo(@Query("cod_protocolo") String codProtocolo);
}