package br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.callbacks;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import br.com.bilac.tcm.cidadeiluminada.R;
import br.com.bilac.tcm.cidadeiluminada.models.Protocolo;
import br.com.bilac.tcm.cidadeiluminada.protocolos.activities.ProtocoloDetalheActivity;
import br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.models.CidadeIluminadaApiResponse;
import br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.models.CidadeIluminadaProtocoloApiResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by arthur on 10/05/15.
 */
public class CidadeIluminadaProtocoloCallback implements Callback<CidadeIluminadaProtocoloApiResponse> {

    private Protocolo protocolo;
    private Context context;

    public CidadeIluminadaProtocoloCallback(Protocolo protocolo, Context context) {
        this.protocolo = protocolo;
        this.context = context;
    }

    @Override
    public void success(CidadeIluminadaProtocoloApiResponse cidadeIluminadaProtocoloApiResponse, Response response) {
        Protocolo payload = cidadeIluminadaProtocoloApiResponse.getPayload();
        protocolo.setStatus(payload.getStatus());
        //TODO: Atualizar o resto quando precisar
        protocolo.save();
        try {
            ProtocoloDetalheActivity detalheActivity = (ProtocoloDetalheActivity) context;
            detalheActivity.preencherDadosProtocolo(protocolo);
        } catch (ClassCastException ignored) {
        }
        Toast.makeText(context, context.getString(R.string.protocolo_atualiza_success), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failure(RetrofitError retrofitError) {
        Toast.makeText(context, context.getString(R.string.protocolo_atualiza_fail), Toast.LENGTH_LONG).show();
        Log.e("error", retrofitError.toString());
    }
}
