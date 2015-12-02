package br.com.bilac.tcm.cidadeiluminada2.protocolos.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.com.bilac.tcm.cidadeiluminada2.CameraUtils;
import br.com.bilac.tcm.cidadeiluminada2.Constants;
import br.com.bilac.tcm.cidadeiluminada2.R;
import br.com.bilac.tcm.cidadeiluminada2.activities.SettingsActivity;
import br.com.bilac.tcm.cidadeiluminada2.models.Protocolo;
import br.com.bilac.tcm.cidadeiluminada2.services.CidadeIluminada;
import br.com.bilac.tcm.cidadeiluminada2.services.cidadeiluminada.listeners.ProtocoloUpdateListener;
import br.com.bilac.tcm.cidadeiluminada2.services.cidadeiluminada.listeners.ProtocoloUploadListener;
import br.com.bilac.tcm.cidadeiluminada2.services.cidadeiluminada.models.CidadeIluminadaApiResponse;

/**
 * Created by Work on 04/05/2015.
 */
public class ProtocoloDetalheActivity extends Activity implements ProtocoloUploadListener,
        ProtocoloUpdateListener{

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
        criado.setText(protocolo.getFormattedTimestamp());
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
        String protocoloStatus = protocolo.getStatus();
        if (protocoloStatus.equals(Protocolo.NAO_ENVIADO)) {
            enviarProtocolo(protocolo);
        } else if (protocoloStatus.equals(Protocolo.NOVO)) {
            atualizarProtocolo(protocolo);
        }
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
        if (response.isOk()) {
            preencherDadosProtocolo(protocolo);
            visibleMenuItem(R.id.action_atualizar_protocolo, true);
            Toast.makeText(this, R.string.protocolo_envio_sucesso, Toast.LENGTH_SHORT).show();
        } else if (response.getStatus().equals(CidadeIluminadaApiResponse.STATUS_ERROR_MOBILE_NETWORK)) {
            visibleMenuItem(R.id.action_detalhes_novo_protocolo, true);
        } else {
            visibleMenuItem(R.id.action_detalhes_novo_protocolo, true);
            //TODO: Pegar o erro certinho
            Toast.makeText(this, getText(R.string.protocolo_envio_erro), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onUpdateResult(CidadeIluminadaApiResponse response) {
        if (response.isOk()) {
            preencherDadosProtocolo(protocolo);
            Toast.makeText(this, R.string.protocolo_atualiza_success, Toast.LENGTH_SHORT).show();
        } else {
            //TODO: Pegar o erro certinho
            Toast.makeText(this, R.string.protocolo_atualiza_fail, Toast.LENGTH_LONG).show();
        }
        if (protocolo.getStatus().equals(Protocolo.NOVO)) {
            visibleMenuItem(R.id.action_atualizar_protocolo, true);
        }
    }
}
