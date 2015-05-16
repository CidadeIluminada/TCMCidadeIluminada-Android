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
import br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.SendFileTask;
import br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.callbacks.CidadeIluminadaProtocoloCallback;

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

        boolean anonimo = preferences.getBoolean(Constants.ANONIMO_PREFERENCE_KEY, true);
        new SendFileTask(anonimo).execute(protocolo);
    }

    public static void atualizarProtocolo(Protocolo protocolo, Context context) {
        CidadeIluminadaService service = CidadeIluminadaAdapter.getCidadeIluminadaService();
        CidadeIluminadaProtocoloCallback callback =
                new CidadeIluminadaProtocoloCallback(protocolo, context);
        service.atualizarProtocolo(protocolo.getCodProtocolo(), callback);
    }
}
