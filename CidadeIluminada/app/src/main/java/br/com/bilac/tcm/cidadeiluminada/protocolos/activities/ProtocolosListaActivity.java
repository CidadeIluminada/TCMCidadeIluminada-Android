package br.com.bilac.tcm.cidadeiluminada.protocolos.activities;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.bilac.tcm.cidadeiluminada.Constants;
import br.com.bilac.tcm.cidadeiluminada.R;
import br.com.bilac.tcm.cidadeiluminada.activities.SettingsActivity;
import br.com.bilac.tcm.cidadeiluminada.models.Protocolo;
import br.com.bilac.tcm.cidadeiluminada.protocolos.adapters.ProtocoloAdapter;

public class ProtocolosListaActivity extends ListActivity {

    private ProtocoloAdapter protocoloAdapter;
    private ArrayList<Protocolo> protocolos;

    private Runnable protocolosListRunnable = new Runnable() {
        @Override
        public void run() {
            protocoloListHandler.sendEmptyMessage(0);
        }
    };

    //TODO: Consertar o provável leak.
    private Handler protocoloListHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            //TODO: Descomentar linha abaixo quando o Sugar lançar a função listAll com order_by
            //https://github.com/satyan/sugar/blob/6bea27ce694cd548462440b48e5e64fb989b10ed/library/src/main/java/com/orm/SugarRecord.java#L93
            //protocolos = (ArrayList<Protocolo>) Protocolo.listAll(Protocolo.class, "ID DESC");
            protocolos = (ArrayList<Protocolo>) Protocolo.find(Protocolo.class, null, null, null, "ID DESC", null);
            protocoloAdapter = new ProtocoloAdapter(ProtocolosListaActivity.this,
                    R.layout.protocolo_item_view, protocolos);
            setListAdapter(protocoloAdapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent protocoloIdIntent = getIntent();
        long protocoloId = protocoloIdIntent.getLongExtra(Constants.PROTOCOLO_ID_KEY, Constants.NO_ID);
        if (protocoloId != Constants.NO_ID) {
            startProtocolosDetalhes(protocoloId);
        }

        setContentView(R.layout.activity_protocolos_lista);
        protocoloAdapter = new ProtocoloAdapter(this, R.layout.protocolo_item_view,
                new ArrayList<Protocolo>());
        setListAdapter(protocoloAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(null, protocolosListRunnable, "FillProtocolosThread").start();
    }

    private void startProtocolosDetalhes(long protocoloId) {
        Intent startShowProtocolo = new Intent(getApplicationContext(),
                ProtocoloDetalheActivity.class);
        startShowProtocolo.putExtra(Constants.PROTOCOLO_ID_KEY, protocoloId);
        startActivity(startShowProtocolo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_protocolos_lista, menu);
        return true;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        startProtocolosDetalhes(protocolos.get(position).getId());
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
