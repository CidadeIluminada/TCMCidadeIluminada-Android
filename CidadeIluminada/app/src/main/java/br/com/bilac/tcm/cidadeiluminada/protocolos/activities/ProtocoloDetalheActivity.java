package br.com.bilac.tcm.cidadeiluminada.protocolos.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import br.com.bilac.tcm.cidadeiluminada.Constants;
import br.com.bilac.tcm.cidadeiluminada.R;
import br.com.bilac.tcm.cidadeiluminada.activities.SettingsActivity;
import br.com.bilac.tcm.cidadeiluminada.models.Protocolo;

/**
 * Created by Work on 04/05/2015.
 */
public class ProtocoloDetalheActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocolo_detalhe);

        Intent startupIntent = getIntent();
        long protocoloId = startupIntent.getLongExtra(Constants.PROTOCOLO_ID_KEY, -1);

        if (protocoloId == -1) {
            finish();
        }

        preencherDadosProtocolo(protocoloId);
    }

    private void preencherDadosProtocolo(long protocoloId) {
        Protocolo protocolo = Protocolo.findById(Protocolo.class, protocoloId);

        TextView codigo = (TextView) findViewById(R.id.codProtocoloText);
        TextView descricao = (TextView) findViewById(R.id.descricaoProtocoloText);
        TextView cep = (TextView) findViewById(R.id.cepText);
        TextView bairro = (TextView) findViewById(R.id.bairroText);
        TextView rua = (TextView) findViewById(R.id.ruaText);
        TextView numero = (TextView) findViewById(R.id.numeroText);

        codigo.setText(protocolo.getCodProtocolo());
        descricao.setText(protocolo.getDescricao());
        cep.setText(protocolo.getCep());
        bairro.setText(protocolo.getBairro());
        rua.setText(protocolo.getLogradouro());
        numero.setText(protocolo.getNumero());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_protocolos_detalhes, menu);
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
}
