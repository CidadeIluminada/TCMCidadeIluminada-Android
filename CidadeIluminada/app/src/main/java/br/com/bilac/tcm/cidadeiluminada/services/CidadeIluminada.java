package br.com.bilac.tcm.cidadeiluminada.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.emil.android.util.Connectivity;

import java.io.File;

import br.com.bilac.tcm.cidadeiluminada.Constants;
import br.com.bilac.tcm.cidadeiluminada.R;
import br.com.bilac.tcm.cidadeiluminada.models.Protocolo;
import br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.CidadeIluminadaAdapter;
import br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.CidadeIluminadaService;
import br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.callbacks.CidadeIluminadaApiCallback;
import br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.callbacks.CidadeIluminadaProtocoloCallback;
import br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.models.CidadeIluminadaProtocoloApiResponse;
import retrofit.mime.TypedFile;

/**
 * Created by arthur on 10/05/15.
 */
public class CidadeIluminada {

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

        CidadeIluminadaApiCallback callback = new CidadeIluminadaApiCallback(protocolo, context);

        if (preferences.getBoolean(Constants.ANONIMO_PREFERENCE_KEY, true)) {
            service.novoProtocolo(codProtocolo, cep, logradouro, cidade, bairro, numero, estado,
                    descricao, arquivoProtocolo, callback);
        } else {
            service.novoProtocoloIdentificado(codProtocolo, cep, logradouro, cidade, bairro,
                    numero, estado, descricao, nome, email, arquivoProtocolo, callback);
        }
    }

    public static void atualizarProtocolo(Protocolo protocolo, Context context) {
        CidadeIluminadaService service = CidadeIluminadaAdapter.getCidadeIluminadaService();
        CidadeIluminadaProtocoloCallback callback =
                new CidadeIluminadaProtocoloCallback(protocolo, context);
        service.atualizarProtocolo(protocolo.getCodProtocolo(), callback);
    }
}
