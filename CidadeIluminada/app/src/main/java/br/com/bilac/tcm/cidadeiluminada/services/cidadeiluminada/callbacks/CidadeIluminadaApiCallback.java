package br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.callbacks;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import br.com.bilac.tcm.cidadeiluminada.R;
import br.com.bilac.tcm.cidadeiluminada.models.Protocolo;
import br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.models.CidadeIluminadaApiResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by arthur on 10/05/15.
 */
public class CidadeIluminadaApiCallback implements Callback<CidadeIluminadaApiResponse> {

    private Context context;
    private Protocolo protocolo;

    public CidadeIluminadaApiCallback(Protocolo protocolo, Context context) {
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
        protocolo.save();
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
