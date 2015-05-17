package br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.listeners;

import br.com.bilac.tcm.cidadeiluminada.services.cidadeiluminada.models.CidadeIluminadaApiResponse;

/**
 * Created by arthur on 16/05/15.
 */
public interface ProtocoloUploadListener {
    void onUploadResult(CidadeIluminadaApiResponse response);
}
