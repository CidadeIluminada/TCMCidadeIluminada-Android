package br.com.bilac.tcm.cidadeiluminada.protocolos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.bilac.tcm.cidadeiluminada.R;
import br.com.bilac.tcm.cidadeiluminada.models.Protocolo;

/**
 * Created by arthur on 03/05/15.
 */
public class ProtocoloAdapter extends ArrayAdapter<Protocolo> {
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
            TextView protocoloDescricao = (TextView) convertView.findViewById(R.id.descricaoProtocoloLabel);
            TextView protocoloStatus = (TextView) convertView.findViewById(R.id.statusItemProtocolo);

            if (protocoloNumero != null) {
                protocoloNumero.setText(protocolo.getCodProtocolo());
            }
            if (protocoloDescricao != null) {
                protocoloDescricao.setText(protocolo.getDescricao());
            }
            if (protocoloStatus != null) {
                protocoloStatus.setText("Status");
            }
        }

        return convertView;
    }
}