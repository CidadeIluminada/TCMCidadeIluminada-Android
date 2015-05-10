package br.com.bilac.tcm.cidadeiluminada.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.emil.android.util.Connectivity;

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
        private Protocolo protocolo;

        public CidadeIluminadaCallback(Protocolo protocolo, Context context) {
            this.context = context;
            this.protocolo = protocolo;
        }

        @Override
        public void success(CidadeIluminadaApiResponse cidadeIluminadaApiResponse,
                            Response response) {
            Log.d("sucess", cidadeIluminadaApiResponse.toString() + " " +
                    response.toString());
            Toast.makeText(context, R.string.protocolo_envio_sucesso, Toast.LENGTH_SHORT).show();
            protocolo.setStatus(cidadeIluminadaApiResponse.getProtocolo().getStatus());
        }
        @Override
        public void failure(RetrofitError retrofitError) {
            Toast.makeText(context, R.string.protocolo_envio_erro, Toast.LENGTH_LONG).show();
            Log.e("error", retrofitError.toString());
            try {
                CidadeIluminadaApiResponse response = (CidadeIluminadaApiResponse) retrofitError.getBody();
                Log.e("fail", response.toString());
            } catch (ClassCastException exception) {
                Log.w("failCC", exception.getMessage());
            }
        }


    }

    public static void enviarNovoProtocolo(Protocolo protocolo, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

        boolean enviarRedeCelular = preferences.getBoolean(Constants.REDE_CELULAR_PREFERENCE_KEY, true);
        if (!enviarRedeCelular && Connectivity.isConnectedMobile(context)) {
            Toast.makeText(context, context.getString(R.string.enviar_fail_rede_celular),
                    Toast.LENGTH_LONG).show();
            return;
        }
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

        CidadeIluminadaCallback callback = new CidadeIluminadaCallback(protocolo, context);

        if (preferences.getBoolean(Constants.ANONIMO_PREFERENCE_KEY, true)) {
            service.novoProtocolo(codProtocolo, cep, logradouro, cidade, bairro, numero, estado,
                    descricao, arquivoProtocolo, callback);
        } else {
            service.novoProtocoloIdentificado(codProtocolo, cep, logradouro, cidade, bairro,
                    numero, estado, descricao, nome, email, arquivoProtocolo, callback);
        }
    }
}
