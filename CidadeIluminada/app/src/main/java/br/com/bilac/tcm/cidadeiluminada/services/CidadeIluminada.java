package br.com.bilac.tcm.cidadeiluminada.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import br.com.bilac.tcm.cidadeiluminada.Constants;
import br.com.bilac.tcm.cidadeiluminada.R;
import br.com.bilac.tcm.cidadeiluminada.models.Protocolo;
import br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.CidadeIluminadaAdapter;
import br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.CidadeIluminadaService;
import br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.utils.CidadeIluminadaApiResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by arthur on 10/05/15.
 */
public class CidadeIluminada {

    private static class CidadeIluminadaCallback implements Callback<CidadeIluminadaApiResponse> {

        private Context context;

        public CidadeIluminadaCallback(Context context) {
            this.context = context;
        }

        @Override
        public void success(CidadeIluminadaApiResponse cidadeIluminadaApiResponse,
                            Response response) {
            Log.d("sucess", cidadeIluminadaApiResponse.toString() + " " +
                    response.toString());
            Toast.makeText(context, R.string.protocolo_envio_sucesso, Toast.LENGTH_SHORT).show();
        }
        @Override
        public void failure(RetrofitError retrofitError) {
            Toast.makeText(context, R.string.protocolo_envio_erro, Toast.LENGTH_LONG).show();
            Log.e("error", retrofitError.toString());
        }
    }

    public static void enviarNovoProtocolo(Protocolo protocolo, Boolean anonimo, Context context) {
        CidadeIluminadaService service = CidadeIluminadaAdapter.getCidadeIluminadaService();

        TypedFile arquivoProtocolo = new TypedFile(Constants.JPG_MIME_TYPE,
                new File(protocolo.getArquivoProtocolo().getPath()));
        String codProtocolo = protocolo.getCodProtocolo();
        String cep = protocolo.getCep();
        String logradouro = protocolo.getLogradouro();
        String estado = protocolo.getEstado();
        String cidade = protocolo.getCidade();
        String bairro = protocolo.getBairro();
        String numero = protocolo.getNumero();
        String descricao = protocolo.getDescricao();
        String nome = protocolo.getNome();
        String email = protocolo.getEmail();

        CidadeIluminadaCallback callback = new CidadeIluminadaCallback(context);

        if (anonimo) {
            service.novoProtocolo(codProtocolo, cep, logradouro, cidade, bairro, numero, estado,
                    descricao, arquivoProtocolo, callback);
        } else {
            service.novoProtocoloIdentificado(codProtocolo, cep, logradouro, cidade, bairro,
                    numero, estado, descricao, nome, email, arquivoProtocolo, callback);
        }
    }
}
