package br.com.fatec.tcc.rotasegura.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.security.auth.callback.PasswordCallback;

import br.com.fatec.tcc.rotasegura.LogicaNegocio.OcorrenciasLN;
import br.com.fatec.tcc.rotasegura.R;
import br.com.fatec.tcc.rotasegura.model.Denuncia;
import br.com.fatec.tcc.rotasegura.model.Endereco;
import br.com.fatec.tcc.rotasegura.model.FiltroCheckBoxes;
import br.com.fatec.tcc.rotasegura.roadMap.Leg;
import br.com.fatec.tcc.rotasegura.roadMap.Route;
import br.com.fatec.tcc.rotasegura.roadMap.Step;
import br.com.fatec.tcc.rotasegura.utils.Localizador;
import br.com.fatec.tcc.rotasegura.utils.Mensagens;

public class Filtros extends AppCompatActivity {

    EditText destinoText;
    Button botao;
    private LocationManager mLocationManager;
    private Location lastKnownLocation;
    private int furto, arrombVeic, roubo, seqRelam, roubVeic, arrastao;
    private CheckBox checkArrastao, checkArrVeic, checkFurtos, checkRoubos, checkRouboVeic, checkSeqRelamp;
    private RelativeLayout loadingContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filtros);

        loadingContent = (RelativeLayout) findViewById(R.id.loading_content);

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    && ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.

            }

        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }

        lastKnownLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        lastKnownLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, lgps);
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 10, lntw);

        destinoText = (EditText) findViewById(R.id.editDestino);
        botao = (Button) findViewById(R.id.botaoIrParaMapa);

        checkArrastao = (CheckBox) findViewById(R.id.checkBoxArrastao);
        checkArrVeic = (CheckBox) findViewById(R.id.checkBoxArrombamentosVeicular);
        checkFurtos = (CheckBox) findViewById(R.id.checkBoxFurtos);
        checkRoubos = (CheckBox) findViewById(R.id.checkBoxRoubos);
        checkRouboVeic = (CheckBox) findViewById(R.id.checkBoxRouboVeiculo);
        checkSeqRelamp = (CheckBox) findViewById(R.id.checkBoxSequestroRelamp);

        botao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            irParaMapa();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                t.start();
            }
        });
    }

    public void irParaMapa() throws IOException, JSONException {

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingContent.setVisibility(View.VISIBLE);
                botao.setEnabled(false);
            }
        });

        if (destinoText.getText().toString().equals(null) || destinoText.getText().toString().equals("")) {

            Filtros.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new Mensagens(Filtros.this).toastMensagem("Digite um endereço...", 0, 0, 0, R.drawable.ic_cast_dark).show();
                }
            });
            loadingContent.setVisibility(View.GONE);
            botao.setEnabled(true);
            return;
        }

        if (!checkArrastao.isChecked() && !checkArrVeic.isChecked() && !checkFurtos.isChecked()
                && !checkRoubos.isChecked() && !checkRouboVeic.isChecked() && !checkSeqRelamp.isChecked()) {

            Filtros.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    new Mensagens(Filtros.this).toastMensagem("Selecione um filtro ao menos...", 0, 0, 0, R.drawable.ic_cast_dark).show();
                }
            });
            loadingContent.setVisibility(View.GONE);
            botao.setEnabled(true);
            return;
        }

        FiltroCheckBoxes fcb = new FiltroCheckBoxes();
        fcb.setArrastao(checkArrastao.isChecked());
        fcb.setArrombVeic(checkArrVeic.isChecked());
        fcb.setFurto(checkFurtos.isChecked());
        fcb.setRoubo(checkRoubos.isChecked());
        fcb.setRoubVeic(checkRouboVeic.isChecked());
        fcb.setSeqRelam(checkSeqRelamp.isChecked());

        LatLng destino = new Localizador(this).getCoordenadas(destinoText.getText().toString());
        LatLng origem = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());

        String json = new OcorrenciasLN().getJsonRoutes(this, destino, origem);

        JSONObject jsonObject = new JSONObject(json);
        JSONArray routes = jsonObject.getJSONArray("routes");

        ArrayList<Route> rs = new ArrayList<>();

        for (int i = 0; i < routes.length(); i++) {
            Route r = new Gson().fromJson(routes.getJSONObject(i).toString(), Route.class);
            rs.add(r);
        }

        ArrayList<Denuncia> denuncias = new OcorrenciasLN().obterOcorrencias();
        Route route = escolherRotaComMenosOcorrencias(rs, denuncias, fcb);                  //CORE S2 DO SYSTEM

        StringBuilder sb = new StringBuilder();
        sb.append("https://maps.google.ch/maps?daddr=");

        for(int i=0; i<route.getLegs().get(0).getSteps().size(); i++){
            sb.append(route.getLegs().get(0).getSteps().get(i).getEndLocation().getLat());
            sb.append(",");
            sb.append(route.getLegs().get(0).getSteps().get(i).getEndLocation().getLng());
            sb.append(" to:");
        }

        sb.delete(sb.length()-4, sb.length());

        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadingContent.setVisibility(View.GONE);
                botao.setEnabled(true);
            }
        });

        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(sb.toString()));
        startActivity(intent);

    }

    private Route escolherRotaComMenosOcorrencias(ArrayList<Route> rotas, ArrayList<Denuncia> denuncias, FiltroCheckBoxes fcb) {

        if (rotas.size() == 1) {  //se o google maps gerar apenas uma alternativa de rota, o sistema nao perde tempo executando a logica de ocorrencias
            return rotas.get(0);
        }

        for (Route r : rotas) {                                                         //PARA CADA ROTA
            for (Leg l : r.getLegs()) {                                                 //PARA CADA PERNA DA ROTA
                for (Step s : l.getSteps()) {                                           //PARA CADA STEP DA ROTA
                    for (Denuncia d : denuncias) {                                      //PARA CADA DENUNCIA

                        SimpleDateFormat format = new SimpleDateFormat("MM");
                        // if(d.getData().contains("-"+format.format(new Date())+"-")){ //PARAMETRO DATA (ULTIMOS 30 DIAS, E NÃO ULTIMO MES

                        Location l2 = new Location("");
                        l2.setLongitude(d.getLongitude());                              //LOCALIDADE DA DENUNCIA (LATITUDE E LONGITUDE)
                        l2.setLatitude(d.getLatitude());

                        Location l1 = new Location("");
                        l1.setLongitude(s.getEndLocation().getLng());                   //LOCALIDADE DA RUA SO STEP, DA PERNA, DA ROTA (LATITUDE E LONGITUDE)
                        l1.setLatitude(s.getEndLocation().getLat());

                        if (l1.distanceTo(l2) <= s.getDistance().getValue() && validarTipos(d.getTipo())) {     //RAIO DE DISTANCIA DO PONTO 'A' AO PONTO 'B' (s.getDistance().getValue() == TAMANHO DA RUA EM METROS)
                            somarTipos(d.getTipo());
                        }
                        // }
                    }
                }
            }

            r.setFurto(furto);
            r.setArrastao(arrastao);
            r.setArrombVeic(arrombVeic);
            r.setRoubo(roubo);
            r.setRoubVeic(roubVeic);
            r.setSeqRelam(seqRelam);
            r.setFcb(fcb);
            zerarOcorrencias();
        }

        /**
         *
         * coração (s2) do systema
         *
         * O objeto routeSelected é sobrescrito toda vez que a rota for melhor (retorno do 'r')
         *
         */
        Route routeSelected = rotas.get(0);  //primeira rota

        for (Route r : rotas) {
            if(routeSelected.compareTo(r) == 0){ //routeSelected tem menos ocorrencias
                //não faz nada
            } else if(routeSelected.compareTo(r) == 1) { //r tem menos ocorrencias
                routeSelected = r;
            }
        }

        return routeSelected;
    }

    private void zerarOcorrencias() {
        furto = 0;
        arrombVeic = 0;
        roubo = 0;
        seqRelam = 0;
        roubVeic = 0;
        arrastao = 0;
    }


    private Endereco getEndereco(LatLng endereco) throws IOException {

        Geocoder g = new Geocoder(this, Locale.getDefault());
        List<Address> local = g.getFromLocation(endereco.latitude, endereco.longitude, 1);

        Endereco e = new Endereco();

        e.setPais(local.get(0).getCountryName());
        e.setEstado(local.get(0).getAdminArea());
        e.setCidade(local.get(0).getLocality());
        e.setBairro(local.get(0).getSubLocality());
        e.setRua(local.get(0).getThoroughfare());
        e.setNumero(local.get(0).getSubThoroughfare());
        e.setCep(local.get(0).getPostalCode());

        return e;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    Log.e("PERMISSÕES", "CONCEDIDAS!!! HEUEHUEHUEHEUHEUEHUEHE");

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    Log.e("PERMISSÕES", "NÃO CONCEDIDAS!!! :(");
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
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

    private void somarTipos(String tipo) {

        if (tipo.toLowerCase().contains("arrombamento")) { //Arrombamento
            arrombVeic++;
            return;
        }

        if (tipo.toLowerCase().contains("arrastao")) { //Arrombamento
            arrastao++;
            return;
        }

        if (tipo.toLowerCase().contains("roubo de veiculo")) { //Arrombamento
            roubVeic++;
            return;
        }

        if (tipo.toLowerCase().contains("roubo")) { //Arrombamento
            roubo++;
            return;
        }

        if (tipo.toLowerCase().contains("sequestro relampago")) { //Arrombamento
            seqRelam++;
            return;
        }

        if (tipo.toLowerCase().contains("furto")) { //Arrombamento
            furto++;
            return;
        }

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }


    private LocationListener lgps = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            try {
                mLocationManager.removeUpdates(lntw);
            } catch (SecurityException se) {

            }
            lastKnownLocation.setLatitude(location.getLatitude());
            lastKnownLocation.setLongitude(location.getLongitude());

            Log.e("PASSOU AQUI", "-------------------------- GPS");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.e("GPS --->", "GPS location available again\n");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.e("GPS --->", "GPS location is out of service\n");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.e("GPS --->", "GPS location is temporarily available\n");
                    break;
            }
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private LocationListener lntw = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            lastKnownLocation.setLatitude(location.getLatitude());
            lastKnownLocation.setLongitude(location.getLongitude());

            Log.e("PASSOU AQUI", "-------------------------- NETWORK");
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            switch (status) {
                case LocationProvider.AVAILABLE:
                    Log.e("NETWORK --->", "Network location available again\n");
                    break;
                case LocationProvider.OUT_OF_SERVICE:
                    Log.e("NETWORK --->", "Network location is out of service\n");
                    break;
                case LocationProvider.TEMPORARILY_UNAVAILABLE:
                    Log.e("NETWORK --->", "Network location is temporarily available\n");
                    break;
            }
        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

}
