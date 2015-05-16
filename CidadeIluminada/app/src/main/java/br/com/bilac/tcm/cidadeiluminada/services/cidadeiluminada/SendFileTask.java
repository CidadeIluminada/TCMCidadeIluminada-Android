package br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada;

import android.os.AsyncTask;
import android.util.Log;

import java.io.File;

import br.com.bilac.tcm.cidadeiluminada.Constants;
import br.com.bilac.tcm.cidadeiluminada.models.Protocolo;
import br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.models.CidadeIluminadaApiResponse;
import retrofit.mime.TypedFile;

/**
 * Created by arthur on 16/05/15.
 */
public class SendFileTask extends AsyncTask<Protocolo, Integer, CidadeIluminadaApiResponse> {
    public SendFileTask() {
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
        return service.novoProtocolo(codProtocolo, cep, logradouro, cidade, bairro, numero, estado, descricao, arquivoProtocolo);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        Log.d("progressUpdate", String.format("progress[%d]", values[0]));
        //do something with values[0], its the percentage so you can easily do
        //progressBar.setProgress(values[0]);
    }
}