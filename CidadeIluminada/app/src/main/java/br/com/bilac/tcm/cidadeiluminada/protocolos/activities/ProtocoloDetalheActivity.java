package br.com.bilac.tcm.cidadeiluminada.protocolos.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.emil.android.util.Connectivity;

import br.com.bilac.tcm.cidadeiluminada.CameraUtils;
import br.com.bilac.tcm.cidadeiluminada.Constants;
import br.com.bilac.tcm.cidadeiluminada.R;
import br.com.bilac.tcm.cidadeiluminada.activities.SettingsActivity;
import br.com.bilac.tcm.cidadeiluminada.models.Protocolo;
import br.com.bilac.tcm.cidadeiluminada.services.CidadeIluminada;

/**
 * Created by Work on 04/05/2015.
 */
public class ProtocoloDetalheActivity extends Activity{

    private Protocolo protocolo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocolo_detalhe);

        Intent startupIntent = getIntent();
        long protocoloId = startupIntent.getLongExtra(Constants.PROTOCOLO_ID_KEY, -1);

        if (protocoloId == -1) {
            finish();
        }

        protocolo = preencherDadosProtocolo(protocoloId);
    }

    private Protocolo preencherDadosProtocolo(long protocoloId) {
        Protocolo protocolo = Protocolo.findById(Protocolo.class, protocoloId);

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

        return protocolo;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_protocolos_detalhes, menu);

        String protocoloStatus = protocolo.getStatus();
        MenuItem item;
        if (protocoloStatus.equals(Protocolo.NAO_ENVIADO)) {
            item = menu.findItem(R.id.action_atualizar_protocolo);
        } else {
            item = menu.findItem(R.id.action_novo_protocolo);
        }
        item.setVisible(false);
        this.invalidateOptionsMenu();

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

    public void enviarProtocolo(MenuItem item) {
        CidadeIluminada.enviarNovoProtocolo(protocolo, getApplicationContext());
    }

    public void atualizarProtocolo(MenuItem item) {
    }
}
