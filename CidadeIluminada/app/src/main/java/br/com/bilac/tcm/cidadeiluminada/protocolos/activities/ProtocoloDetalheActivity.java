package br.com.bilac.tcm.cidadeiluminada.protocolos.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import br.com.bilac.tcm.cidadeiluminada.CameraUtils;
import br.com.bilac.tcm.cidadeiluminada.Constants;
import br.com.bilac.tcm.cidadeiluminada.R;
import br.com.bilac.tcm.cidadeiluminada.activities.SettingsActivity;
import br.com.bilac.tcm.cidadeiluminada.models.Protocolo;
import br.com.bilac.tcm.cidadeiluminada.services.CidadeIluminada;
import br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.listeners.ProtocoloUploadListener;
import br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.models.CidadeIluminadaApiResponse;

/**
 * Created by Work on 04/05/2015.
 */
public class ProtocoloDetalheActivity extends Activity implements ProtocoloUploadListener{

    private Protocolo protocolo;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocolo_detalhe);

        Intent startupIntent = getIntent();
        long protocoloId = startupIntent.getLongExtra(Constants.PROTOCOLO_ID_KEY, Constants.NO_ID);

        if (protocoloId == Constants.NO_ID) {
            finish();
        }

        protocolo = Protocolo.findById(Protocolo.class, protocoloId);
        preencherDadosProtocolo(protocolo);
        String protocoloStatus = protocolo.getStatus();
        if (protocoloStatus.equals(Protocolo.NAO_ENVIADO)) {
            enviarProtocolo(protocolo);
        } else if (protocoloStatus.equals(Protocolo.NOVO)) {
            atualizarProtocolo(protocolo);
        }
    }

    public void preencherDadosProtocolo(Protocolo protocolo) {

        ImageView fotoProtocolo = (ImageView) findViewById(R.id.fotoProtocoloView);

        TextView codigo = (TextView) findViewById(R.id.codProtocoloText);
        TextView situacao = (TextView) findViewById(R.id.statusProtocoloText);
        TextView criado = (TextView) findViewById(R.id.dataProtocoloText);
        TextView descricao = (TextView) findViewById(R.id.descricaoProtocoloText);
        TextView cep = (TextView) findViewById(R.id.cepText);
        TextView bairro = (TextView) findViewById(R.id.bairroText);
        TextView rua = (TextView) findViewById(R.id.ruaText);
        TextView numero = (TextView) findViewById(R.id.numeroText);

        fotoProtocolo.setImageBitmap(
                CameraUtils.decodeSampledBitmapFromFile(protocolo.getArquivoProtocolo().getPath(),
                        fotoProtocolo.getLayoutParams().width, fotoProtocolo.getLayoutParams().height));

        codigo.setText(protocolo.getCodProtocolo());
        situacao.setText(protocolo.getStatus(true));
        criado.setText(protocolo.getTimestamp());
        descricao.setText(protocolo.getDescricao());
        cep.setText(protocolo.getCep());
        bairro.setText(protocolo.getBairro());
        rua.setText(protocolo.getLogradouro());
        numero.setText(protocolo.getNumero());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_protocolos_detalhes, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openConfiguracoesActivity(MenuItem item) {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    public void enviarProtocoloMenu(MenuItem item) {
        enviarProtocolo(protocolo);
    }

    private void enviarProtocolo(Protocolo protocolo) {
        visibleMenuItem(R.id.action_detalhes_novo_protocolo, false);
        CidadeIluminada.enviarNovoProtocolo(protocolo, this);
    }

    public void atualizarProtocoloMenu(MenuItem item) {
        atualizarProtocolo(protocolo);
    }

    private void atualizarProtocolo(Protocolo protocolo) {
        visibleMenuItem(R.id.action_atualizar_protocolo, false);
        CidadeIluminada.atualizarProtocolo(protocolo, this);
        visibleMenuItem(R.id.action_atualizar_protocolo, true);
    }

    private void visibleMenuItem(int resId, boolean visible) {
        if (menu == null) {
            return;
        }
        MenuItem menuItem = menu.findItem(resId);
        if (menuItem != null) {
            menuItem.setVisible(visible);
        }
    }

    @Override
    public void onUploadResult(CidadeIluminadaApiResponse response) {
        visibleMenuItem(R.id.action_detalhes_novo_protocolo, true);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.protocoloProgressBar);
        progressBar.setVisibility(View.GONE);
        if (response.isOk()) {
            protocolo.update(response.getProtocolo());
            preencherDadosProtocolo(protocolo);
        } else {
            //TODO: Pegar o erro certinho
            Toast.makeText(this, getText(R.string.protocolo_envio_erro), Toast.LENGTH_LONG).show();
        }
    }
}
