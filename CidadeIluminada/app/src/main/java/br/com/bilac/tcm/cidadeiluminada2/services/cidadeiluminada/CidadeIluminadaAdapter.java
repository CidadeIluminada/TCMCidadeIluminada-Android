package br.com.bilac.tcm.cidadeiluminada2.services.cidadeiluminada;

import br.com.bilac.tcm.cidadeiluminada2.Constants;
import retrofit.RestAdapter;

/**
 * Created by arthur on 04/04/15.
 */
public class CidadeIluminadaAdapter {
    public static CidadeIluminadaService getCidadeIluminadaService() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(Constants.CIDADEILUMINADA_HOST)
                .build();
        return restAdapter.create(CidadeIluminadaService.class);
    }
}
