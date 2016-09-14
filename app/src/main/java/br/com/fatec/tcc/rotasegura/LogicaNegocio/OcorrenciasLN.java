package br.com.fatec.tcc.rotasegura.LogicaNegocio;

import android.app.Activity;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import br.com.fatec.tcc.rotasegura.R;
import br.com.fatec.tcc.rotasegura.model.Denuncia;

/**
 * Created by Inmetrics on 11/08/2016.
 */
public class OcorrenciasLN {

    public ArrayList<Denuncia> obterOcorrencias() throws IOException, JSONException {

        Document doc = Jsoup.connect("http://www.ondefuiroubado.com.br/sao-paulo/SP/").timeout(0).get();

        String[] split = doc.toString().split("initialize");
        String[] json = split[1].split("\\);");
        String substring = json[0].substring(1);

        JSONArray ja = new JSONArray(substring);
        ArrayList<Denuncia> ds = new ArrayList<Denuncia>();

        for (int i = 0; i < ja.length(); i++) {
            Denuncia d = new Denuncia();
            d.setLatitude(ja.getJSONObject(i).getDouble("latitude"));
            d.setLongitude(ja.getJSONObject(i).getDouble("longitude"));
            d.setDescricao(ja.getJSONObject(i).getString("descricao"));
            d.setLogradouro(ja.getJSONObject(i).getString("endereco"));
            d.setData(ja.getJSONObject(i).getString("data"));
            d.setTipo(String.valueOf(ja.getJSONObject(i).getInt("tipo_assalto_id")));
            ds.add(d);
        }

        return ds;
    }

    public String getJsonRoutes(Activity a, LatLng destino, LatLng origem) throws IOException {

        URL obj = new URL("https://maps.googleapis.com/maps/api/directions/" +
                "json?" +
                "origin=" + origem.latitude + "," + origem.longitude +
                "&destination=" + destino.latitude + "," + destino.longitude +
                "&alternatives=true" +
                "&key="+a.getResources().getString(R.string.google_maps_key));

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("content-type", "application/json");

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());

        wr.flush();
        wr.close();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine).append("\n");
        }
        in.close();

        return response.toString();
    }

}
