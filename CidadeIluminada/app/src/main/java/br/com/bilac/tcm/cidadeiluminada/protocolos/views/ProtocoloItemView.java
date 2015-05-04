package br.com.bilac.tcm.cidadeiluminada.protocolos.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.bilac.tcm.cidadeiluminada.R;

/**
 * Created by arthur on 03/05/15.
 */
public class ProtocoloItemView extends LinearLayout {
    private ImageView protocoloImagem;
    private TextView protocoloNumero;
    private TextView protocoloDescricao;
    private TextView protocoloStatus;

    public ProtocoloItemView(Context context) {
        super(context);
        setUp(context);
    }

    public ProtocoloItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUp(context);
    }

    private void setUp(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.protocolo_item_view, this);
        protocoloImagem = (ImageView) findViewById(R.id.previewItemProtocolo);
        protocoloNumero = (TextView) findViewById(R.id.numeroItemProtocolo);
        protocoloDescricao = (TextView) findViewById(R.id.descricaoProtocoloLabel);
        protocoloStatus = (TextView) findViewById(R.id.statusItemProtocolo);
    }
}
