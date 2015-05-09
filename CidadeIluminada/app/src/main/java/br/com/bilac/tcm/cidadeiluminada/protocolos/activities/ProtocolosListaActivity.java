package br.com.bilac.tcm.cidadeiluminada.protocolos.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;

import br.com.bilac.tcm.cidadeiluminada.R;
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

    private Handler protocoloListHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            protocolos = (ArrayList<Protocolo>) Protocolo.listAll(Protocolo.class);
            protocoloAdapter = new ProtocoloAdapter(ProtocolosListaActivity.this,
                    R.layout.protocolo_item_view, protocolos);
            setListAdapter(protocoloAdapter);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protocolos_lista);
        protocoloAdapter = new ProtocoloAdapter(this, R.layout.protocolo_item_view,
                new ArrayList<Protocolo>());
        setListAdapter(protocoloAdapter);
        new Thread(null, protocolosListRunnable, "FillProtocolosThread").start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_protocolos_lista, menu);
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
}
