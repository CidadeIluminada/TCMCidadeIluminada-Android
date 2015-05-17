package br.com.bilac.tcm.cidadeiluminada.services;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.emil.android.util.Connectivity;

import br.com.bilac.tcm.cidadeiluminada.Constants;
import br.com.bilac.tcm.cidadeiluminada.R;
import br.com.bilac.tcm.cidadeiluminada.models.Protocolo;
import br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.listeners.ProtocoloUpdateListener;
import br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.listeners.ProtocoloUploadListener;
import br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.models.CidadeIluminadaApiResponse;
import br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.tasks.UpdateProtocoloTask;
import br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.tasks.UploadProtocoloTask;

/**
 * Created by arthur on 10/05/15.
 */
public class CidadeIluminada {

    public static <T extends Activity & ProtocoloUploadListener> void enviarNovoProtocolo(Protocolo protocolo, T uploadListenerActivity) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(uploadListenerActivity);

        boolean enviarRedeCelular = preferences.getBoolean(Constants.REDE_CELULAR_PREFERENCE_KEY, true);
        if (!enviarRedeCelular && Connectivity.isConnectedMobile(uploadListenerActivity)) {
            Toast.makeText(uploadListenerActivity, uploadListenerActivity.getString(R.string.enviar_fail_rede_celular),
                    Toast.LENGTH_LONG).show();
            uploadListenerActivity.onUploadResult(new CidadeIluminadaApiResponse(CidadeIluminadaApiResponse.STATUS_ERROR_MOBILE_NETWORK));
            return;
        }

        boolean anonimo = preferences.getBoolean(Constants.ANONIMO_PREFERENCE_KEY, true);
        new UploadProtocoloTask(uploadListenerActivity, uploadListenerActivity, anonimo).execute(protocolo);
    }

    public static <T extends Activity & ProtocoloUpdateListener> void atualizarProtocolo(Protocolo protocolo, T updateListenerActivity) {
        new UpdateProtocoloTask(updateListenerActivity, updateListenerActivity).execute(protocolo);
    }
}
