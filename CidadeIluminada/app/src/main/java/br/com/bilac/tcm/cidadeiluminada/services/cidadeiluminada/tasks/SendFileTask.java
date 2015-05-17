package br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.io.File;

import br.com.bilac.tcm.cidadeiluminada.Constants;
import br.com.bilac.tcm.cidadeiluminada.R;
import br.com.bilac.tcm.cidadeiluminada.models.Protocolo;
import br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.CidadeIluminadaAdapter;
import br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.CidadeIluminadaService;
import br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.etc.CountedTypedFile;
import br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.listeners.ProgressListener;
import br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.listeners.ProtocoloUploadListener;
import br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.models.CidadeIluminadaApiResponse;
import retrofit.RetrofitError;
import retrofit.mime.TypedFile;

/**
 * Created by arthur on 16/05/15.
 */
public class SendFileTask extends AsyncTask<Protocolo, Integer, CidadeIluminadaApiResponse> {

    private boolean anonimo;
    private ProgressBar progressBar;
    private ProtocoloUploadListener listener;

    public SendFileTask(Activity activity, ProtocoloUploadListener listener, boolean anonimo) {
        this.listener = listener;
        this.anonimo = anonimo;

        progressBar = (ProgressBar) activity.findViewById(R.id.protocoloProgressBar);
    }

    @Override
    protected void onPreExecute() {
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(true);
        }
    }

    @Override
    protected CidadeIluminadaApiResponse doInBackground(Protocolo... params) {
        Protocolo protocolo = params[0];
        File file = new File(protocolo.getArquivoProtocolo().getPath());
        final long totalSize = file.length();
        Log.d("Upload FileSize[%d]", String.valueOf(totalSize));
        ProgressListener listener = new ProgressListener() {
            @Override
            public void transferred(long num) {
                publishProgress((int) ((num / (float) totalSize) * 100));
            }
        };
        TypedFile arquivoProtocolo = new CountedTypedFile(Constants.JPG_MIME_TYPE, file, listener);


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

        CidadeIluminadaService service = CidadeIluminadaAdapter.getCidadeIluminadaService();
        CidadeIluminadaApiResponse response;
        try {
            if (anonimo) {
                response = service.novoProtocolo(codProtocolo, cep, logradouro, cidade, bairro, numero, estado,
                        descricao, arquivoProtocolo);
            } else {
                response = service.novoProtocoloIdentificado(codProtocolo, cep, logradouro, cidade, bairro,
                        numero, estado, descricao, nome, email, arquivoProtocolo);
            }
            return response;
        } catch (RetrofitError retrofitError) {
            Log.e("uploadError", retrofitError.toString());
            if (retrofitError.getKind() != RetrofitError.Kind.HTTP) {
                return new CidadeIluminadaApiResponse(CidadeIluminadaApiResponse.STATUS_ERROR);
            }
            return (CidadeIluminadaApiResponse) retrofitError.getBodyAs(CidadeIluminadaApiResponse.class);
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        Log.d("progressUpdate", String.format("progress[%d]", values[0]));
        if (progressBar != null) {
            if (progressBar.isIndeterminate()) {
                progressBar.setIndeterminate(false);
            }
            progressBar.setProgress(values[0]);
        }
    }

    @Override
    protected void onPostExecute(CidadeIluminadaApiResponse response) {
        listener.onUploadResult(response);
    }
}