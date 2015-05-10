package br.com.bilac.tcm.cidadeiluminada.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import br.com.bilac.tcm.cidadeiluminada.Constants;
import br.com.bilac.tcm.cidadeiluminada.R;
import br.com.bilac.tcm.cidadeiluminada.protocolos.activities.ProtocoloActivity;
import br.com.bilac.tcm.cidadeiluminada.protocolos.activities.ProtocolosListaActivity;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().hide();
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.NOVO_PROTOCOLO_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                long protocoloId = data.getLongExtra(Constants.PROTOCOLO_ID_KEY, -1);
                if (protocoloId == -1) {
                    return;
                }
                Intent listaIntent = new Intent(this, ProtocolosListaActivity.class);
                listaIntent.putExtra(Constants.PROTOCOLO_ID_KEY, protocoloId);
                startActivity(listaIntent);
            }
        }
    }

    public void openProtocoloActivity(View view) {
        startActivityForResult(new Intent(this, ProtocoloActivity.class),
                Constants.NOVO_PROTOCOLO_REQUEST_CODE);
    }

    public void openListaProtocolosActivity(View view) {
        startActivity(new Intent(this, ProtocolosListaActivity.class));
    }

    public void openConfiguracoesActivity(View view) {
        startActivity(new Intent(this, SettingsActivity.class));
    }

}
