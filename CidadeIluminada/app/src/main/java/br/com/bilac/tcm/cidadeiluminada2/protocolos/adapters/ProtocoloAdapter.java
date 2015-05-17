package br.com.bilac.tcm.cidadeiluminada2.protocolos.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.bilac.tcm.cidadeiluminada2.CameraUtils;
import br.com.bilac.tcm.cidadeiluminada2.R;
import br.com.bilac.tcm.cidadeiluminada2.models.Protocolo;

/**
 * Created by arthur on 03/05/15.
 */
public class ProtocoloAdapter extends ArrayAdapter<Protocolo> {
    private class ImageViewProtocoloTuple {
        private ImageView imageView;
        private Protocolo protocolo;

        public ImageViewProtocoloTuple(ImageView imageView, Protocolo protocolo) {
            this.imageView = imageView;
            this.protocolo = protocolo;
        }
    }
    private class AsyncImageSetter extends AsyncTask<ImageViewProtocoloTuple, Void, Void> {

        private Bitmap bmp;
        private ImageView imageView;

        @Override
        protected Void doInBackground(ImageViewProtocoloTuple... params) {
            Protocolo protocolo = params[0].protocolo;
            imageView = params[0].imageView;
            bmp = CameraUtils.decodeSampledBitmapFromFile(protocolo.getArquivoProtocolo().getPath(),
                    32, 32);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            imageView.setImageBitmap(bmp);
            bmp = null;
            super.onPostExecute(aVoid);
        }
    }

    private ArrayList<Protocolo> protocolos;

    public ProtocoloAdapter(Context context, int resource, ArrayList<Protocolo> protocolos) {
        super(context, resource, protocolos);
        this.protocolos = protocolos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.protocolo_item_view, null);
        }

        Protocolo protocolo = protocolos.get(position);
        if (protocolo != null) {
            ImageView protocoloImagem = (ImageView) convertView.findViewById(R.id.previewItemProtocolo);
            TextView protocoloNumero = (TextView) convertView.findViewById(R.id.numeroItemProtocolo);
            TextView protocoloDescricao = (TextView) convertView.findViewById(R.id.descricaoItemProtocolo);
            TextView protocoloStatus = (TextView) convertView.findViewById(R.id.statusItemProtocolo);

            if (protocoloImagem != null) {
                new AsyncImageSetter().execute(new ImageViewProtocoloTuple(protocoloImagem, protocolo));
            }

            if (protocoloNumero != null) {
                protocoloNumero.setText(protocolo.getCodProtocolo());
            }
            if (protocoloDescricao != null) {
                protocoloDescricao.setText(protocolo.getDescricao());
            }
            if (protocoloStatus != null) {
                protocoloStatus.setText(protocolo.getStatus(true));
            }
        }

        return convertView;
    }
}
