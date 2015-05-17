package br.com.bilac.tcm.cidadeiluminada2.services.cidadeiluminada.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import br.com.bilac.tcm.cidadeiluminada2.R;
import br.com.bilac.tcm.cidadeiluminada2.models.Protocolo;
import br.com.bilac.tcm.cidadeiluminada2.services.cidadeiluminada.CidadeIluminadaAdapter;
import br.com.bilac.tcm.cidadeiluminada2.services.cidadeiluminada.CidadeIluminadaService;
import br.com.bilac.tcm.cidadeiluminada2.services.cidadeiluminada.listeners.ProtocoloUpdateListener;
import br.com.bilac.tcm.cidadeiluminada2.services.cidadeiluminada.models.CidadeIluminadaApiResponse;
import retrofit.RetrofitError;

/**
 * Created by arthur on 17/05/15.
 */
public class UpdateProtocoloTask extends AsyncTask<Protocolo, Void, CidadeIluminadaApiResponse> {

    private ProgressBar progressBar;
    private ProtocoloUpdateListener listener;
    private Protocolo protocolo;

    public UpdateProtocoloTask(Activity activity, ProtocoloUpdateListener listener) {
        this.listener = listener;

        progressBar = (ProgressBar) activity.findViewById(R.id.protocoloProgressBar);
    }

    @Override
    protected void onPreExecute() {
        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected CidadeIluminadaApiResponse doInBackground(Protocolo... params) {
        protocolo = params[0];
        CidadeIluminadaService service = CidadeIluminadaAdapter.getCidadeIluminadaService();
        try {
            return service.atualizarProtocolo(protocolo.getCodProtocolo());
        } catch (RetrofitError retrofitError) {
            Log.e("uploadError", retrofitError.toString());
            if (retrofitError.getKind() != RetrofitError.Kind.HTTP) {
                return new CidadeIluminadaApiResponse(CidadeIluminadaApiResponse.STATUS_ERROR);
            }
            return (CidadeIluminadaApiResponse) retrofitError.getBodyAs(CidadeIluminadaApiResponse.class);
        }
    }

    @Override
    protected void onPostExecute(CidadeIluminadaApiResponse response) {
        if (response.isOk()) {
            protocolo.update(response.getProtocolo());
        }
        progressBar.setVisibility(View.GONE);
        listener.onUpdateResult(response);
    }
}
