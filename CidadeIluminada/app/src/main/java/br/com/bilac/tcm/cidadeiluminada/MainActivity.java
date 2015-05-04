package br.com.bilac.tcm.cidadeiluminada;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import br.com.bilac.tcm.cidadeiluminada.protocolos.ProtocoloActivity;
import br.com.bilac.tcm.cidadeiluminada.protocolos.ProtocolosListaActivity;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    public void openProtocoloActivity(View view) {
        startActivity(new Intent(this, ProtocoloActivity.class));
    }

    public void openListaProtocolosActivity(View view) {
        startActivity(new Intent(this, ProtocolosListaActivity.class));
    }

    public void openConfiguracoesActivity(View view) {
        startActivity(new Intent(this, ConfigureActivity.class));
    }
}