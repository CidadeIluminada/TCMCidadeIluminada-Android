package br.com.bilac.tcm.cidadeiluminada2.services.cidadeiluminada.listeners;

import br.com.bilac.tcm.cidadeiluminada2.services.cidadeiluminada.models.CidadeIluminadaApiResponse;

/**
 * Created by arthur on 17/05/15.
 */
public interface ProtocoloUpdateListener {
    void onUpdateResult(CidadeIluminadaApiResponse response);
}
