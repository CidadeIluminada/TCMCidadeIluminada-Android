package br.com.bilac.tcm.cidadeiluminada.protocolos;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import br.com.bilac.tcm.cidadeiluminada.CameraUtils;
import br.com.bilac.tcm.cidadeiluminada.Constants;
import br.com.bilac.tcm.cidadeiluminada.R;
import br.com.bilac.tcm.cidadeiluminada.models.Protocolo;
import br.com.bilac.tcm.cidadeiluminada.protocolos.validators.EmptyValidator;
import br.com.bilac.tcm.cidadeiluminada.protocolos.validators.ValidationState;
import br.com.bilac.tcm.cidadeiluminada.services.CidadeIluminadaAdapter;
import br.com.bilac.tcm.cidadeiluminada.services.CidadeIluminadaService;
import br.com.bilac.tcm.cidadeiluminada.services.utils.CidadeIluminadaApiResponse;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class ProtocoloActivity extends Activity {

    private Uri fileUri;

    private ValidationState descricaoValidationState;
    private ValidationState cepValidationState;
    private ValidationState numeroValidationState;
    private EditText descricaoEditText;
    private EditText cepEditText;
    private EditText bairroEditText;
    private EditText ruaEditText;
    private EditText numeroEditText;

    public void openPlacePicker(View view) {
        PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
        try {
            startActivityForResult(builder.build(getApplicationContext()),
                    Constants.PLACE_PICKER_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }
    }

    private void preencherCamposEndereco(Address endereco) {
        cepEditText.setText(endereco.getPostalCode());
        bairroEditText.setText(endereco.getSubLocality());
        ruaEditText.setText(endereco.getThoroughfare());
        numeroEditText.setText(endereco.getSubThoroughfare());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        fileUri = savedInstanceState.getParcelable(Constants.FILE_URI_KEY);
        if (fileUri != null) {
            setCameraButtonImage();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        descricaoValidationState = new ValidationState();
        cepValidationState = new ValidationState();
        numeroValidationState = new ValidationState();
        setContentView(R.layout.activity_protocolo);

        descricaoEditText = (EditText) findViewById(R.id.protocoloDescricaoEditText);
        cepEditText = (EditText) findViewById(R.id.cepEditText);
        bairroEditText = (EditText) findViewById(R.id.bairroEditText);
        ruaEditText = (EditText) findViewById(R.id.ruaEditText);
        numeroEditText = (EditText) findViewById(R.id.numeroEditText);

        descricaoEditText.addTextChangedListener(new EmptyValidator(descricaoEditText,
                descricaoValidationState));
        cepEditText.addTextChangedListener(new EmptyValidator(cepEditText, cepValidationState));
        numeroEditText.addTextChangedListener(new EmptyValidator(numeroEditText,
                numeroValidationState));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Constants.FILE_URI_KEY, fileUri);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_protocolo, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_novo_protocolo:
                enviarNovoProtocolo();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void enviarNovoProtocolo() {
        if (descricaoValidationState.isValid() && numeroValidationState.isValid()
                && cepValidationState.isValid() && fileUri != null) {
            Protocolo protocolo =
                    Protocolo.novoProtocoloSJC(cepEditText.getText().toString(),
                            bairroEditText.getText().toString(), ruaEditText.getText().toString(),
                            numeroEditText.getText().toString(), fileUri);

            protocolo.save();
            File arquivo_protocolo = new File(protocolo.getArquivoProtocolo().getPath());
            CidadeIluminadaService service = CidadeIluminadaAdapter.getCidadeIluminadaService();
            service.novoProtocolo(protocolo.getCodProtocolo(), protocolo.getCep(),
                    new TypedFile(Constants.JPG_MIME_TYPE, arquivo_protocolo),
                    new Callback<CidadeIluminadaApiResponse>() {
                        @Override
                        public void success(CidadeIluminadaApiResponse cidadeIluminadaApiResponse,
                                            Response response) {
                            Log.d("sucess", cidadeIluminadaApiResponse.toString() + " " +
                                    response.toString());
                            Toast.makeText(getApplicationContext(),
                                    R.string.protocolo_envio_sucesso, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            Toast.makeText(getApplicationContext(),
                                    R.string.protocolo_envio_erro,
                                    Toast.LENGTH_LONG).show();
                            Log.e("error", retrofitError.toString());
                        }
                    });
        } else {
            Toast.makeText(this, R.string.erro_formulario_protocolo, Toast.LENGTH_SHORT).show();
        }
    }

    public void openProtocoloCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = CameraUtils.getOutputMediaFileUri();
        if (fileUri == null) {
            Log.e("ProtocoloActivity", "Falha ao criar arquivo da foto.");
            Toast.makeText(this, R.string.erro_criacao_arquivo_foto, Toast.LENGTH_SHORT).show();
            return;
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, Constants.CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Constants.CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                setCameraButtonImage();
            } else if (resultCode == RESULT_CANCELED) {
                ImageButton img = (ImageButton) findViewById(R.id.openCameraButton);
                img.setImageDrawable(getResources().getDrawable(R.drawable.cameraadd128));
            }
        } else if (requestCode == Constants.PLACE_PICKER_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                Geocoder geocoder = new Geocoder(this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(place.getLatLng().latitude,
                            place.getLatLng().longitude, 1);
                    preencherCamposEndereco(addresses.get(0));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setCameraButtonImage() {
        ImageButton img = (ImageButton) findViewById(R.id.openCameraButton);
        Bitmap bmp = CameraUtils.decodeSampledBitmapFromFile(fileUri.getPath(), 128, 128);
        img.setImageBitmap(bmp);
    }
}