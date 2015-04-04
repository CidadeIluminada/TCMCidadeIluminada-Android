package br.com.bilac.tcm.cidadeiluminada.services;

import br.com.bilac.tcm.cidadeiluminada.Constants;
import retrofit.RestAdapter;

/**
 * Created by arthur on 04/04/15.
 */
public class CidadeIluminadaAdapter {
    public static CidadeIluminadaService getCidadeIluminadaService() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                //.setEndpoint(Constants.CIDADEILUMINADA_HOST_DEVICE)
                .setEndpoint(Constants.CIDADEILUMINADA_HOST_EMULATOR)
                .build();
        return restAdapter.create(CidadeIluminadaService.class);
    }
}
