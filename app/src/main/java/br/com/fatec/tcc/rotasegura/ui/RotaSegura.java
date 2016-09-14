package br.com.fatec.tcc.rotasegura.ui;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import br.com.fatec.tcc.rotasegura.R;
import br.com.fatec.tcc.rotasegura.model.Denuncia;
import br.com.fatec.tcc.rotasegura.utils.Mensagens;

public class RotaSegura extends FragmentActivity implements OnMapReadyCallback {

    private  ArrayList<Denuncia> denuncias;
    private Bundle extras;
    private LatLng origem, destino;
    private GoogleMap mMap;
    private int furto, arrombVeic, roubo, seqRelam, roubVeic, arrastao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rota_segura);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        getBundles();
    }

    private void getBundles() {
        extras = getIntent().getExtras();
        origem = (LatLng) extras.get("origem");
        destino = (LatLng) extras.get("destino");
        denuncias = (ArrayList<Denuncia>) extras.get("denuncias");

        Log.e("DENUNCIAS -> ", String.valueOf(denuncias.size()));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        Location l1 = new Location("");
        l1.setLongitude(destino.longitude);
        l1.setLatitude(destino.latitude);

        for (Denuncia d: denuncias) {

            SimpleDateFormat format = new SimpleDateFormat("MM");
           // if(d.getData().contains("-"+format.format(new Date())+"-")){ //PARAMETRO DATA (ULTIMOS 30 DIAS, E NÃO ULTIMO MES

            Location l2 = new Location("");
            l2.setLongitude(d.getLongitude());
            l2.setLatitude(d.getLatitude());

                if(l1.distanceTo(l2) < 5000 && validarTipos(d.getTipo())){ //PARAMETRO RAIO DE DISTANCIA (em metros)
                       somarTipos(d.getTipo());
                }
           // }

        }

        validacaoOcorrencias();

        mMap.moveCamera(CameraUpdateFactory.newLatLng(destino));

        // Add a marker in Sydney and move the camera
        mMap.addMarker(new MarkerOptions().title("Aqui está o seu destino")
                .position(destino)
                .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

    }

    private void validacaoOcorrencias() {

        if(furto+roubo+roubVeic+arrastao+arrombVeic+seqRelam > 0){
            new Mensagens(this).alertDialogMensagemSIMeNAO("Alerta de segurança", "Em um raio de 5km há muitas ocorrencias de " +
                    "assaltos no ultimo mes. Deseja mesmo assim continuar nesta rota?\n\n" +
                    "Furtos: "+furto+"\n"+
                    "Roubos:"+roubo+"\n"+
                    "Arrombamentos:"+arrombVeic+"\n"+
                    "Sequestro Relampago:"+seqRelam+"\n"+
                    "Arrastao:"+arrastao+"\n"+
                    "Roubos de Veiculos:"+roubVeic, null).show();
        }

    }

    private void somarTipos(String tipo) {

        if(tipo.toLowerCase().contains("arrombamento")){ //Arrombamento
            arrombVeic++;
            return;
        }

        if(tipo.toLowerCase().contains("arrastao")){ //Arrombamento
            arrastao++;
            return;
        }

        if(tipo.toLowerCase().contains("roubo de veiculo")){ //Arrombamento
            roubVeic++;
            return;
        }

        if(tipo.toLowerCase().contains("roubo")){ //Arrombamento
            roubo++;
            return;
        }

        if(tipo.toLowerCase().contains("sequestro relampago")){ //Arrombamento
            seqRelam++;
            return;
        }

        if(tipo.toLowerCase().contains("furto")){ //Arrombamento
            furto++;
            return;
        }

    }

    private boolean validarTipos(String tipo) {

        try {
            Integer.valueOf(tipo);
        } catch (Exception e) { //Não conseguiu converter para int.
            return true;
        }
        return false;
    }

}
