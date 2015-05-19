package br.com.bilac.tcm.cidadeiluminada2.services.cidadeiluminada.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.io.File;

import br.com.bilac.tcm.cidadeiluminada2.Constants;
import br.com.bilac.tcm.cidadeiluminada2.R;
import br.com.bilac.tcm.cidadeiluminada2.models.Protocolo;
import br.com.bilac.tcm.cidadeiluminada2.services.cidadeiluminada.CidadeIluminadaAdapter;
import br.com.bilac.tcm.cidadeiluminada2.services.cidadeiluminada.CidadeIluminadaService;
import br.com.bilac.tcm.cidadeiluminada2.services.cidadeiluminada.etc.CountedTypedFile;
import br.com.bilac.tcm.cidadeiluminada2.services.cidadeiluminada.listeners.ProgressListener;
import br.com.bilac.tcm.cidadeiluminada2.services.cidadeiluminada.listeners.ProtocoloUploadListener;
import br.com.bilac.tcm.cidadeiluminada2.services.cidadeiluminada.models.CidadeIluminadaApiResponse;
import retrofit.RetrofitError;
import retrofit.mime.TypedFile;

/**
 * Created by arthur on 16/05/15.
 */
public class UploadProtocoloTask extends AsyncTask<Protocolo, Integer, CidadeIluminadaApiResponse> {

    private boolean anonimo;
    private ProgressBar progressBar;
    private ProtocoloUploadListener listener;

    private Protocolo protocolo;

    public UploadProtocoloTask(Activity activity, ProtocoloUploadListener listener, boolean anonimo) {
        this.listener = listener;
        this.anonimo = anonimo;

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
        try {
            if (anonimo) {
                return service.novoProtocolo(codProtocolo, cep, logradouro, cidade, bairro, numero, estado,
                        descricao, arquivoProtocolo);
            } else {
                return service.novoProtocoloIdentificado(codProtocolo, cep, logradouro, cidade, bairro,
                        numero, estado, descricao, nome, email, arquivoProtocolo);
            }
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
        if (progressBar.isIndeterminate()) {
            progressBar.setIndeterminate(false);
        }
        progressBar.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(CidadeIluminadaApiResponse response) {
        progressBar.setIndeterminate(true);
        if (response.isOk()) {
            protocolo.update(response.getProtocolo());
        }
        progressBar.setVisibility(View.GONE);
        listener.onUploadResult(response);
    }
}