
package br.com.fatec.tcc.rotasegura.roadMap;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.fatec.tcc.rotasegura.model.FiltroCheckBoxes;

public class Route implements Comparable<Route> {

    //Todas as classes do pacote roarMap foram geradas pelo site http://www.jsonschema2pojo.org/
    //para poder fazer o parse no json
    private FiltroCheckBoxes fcb;
    private Bounds bounds;
    private String copyrights;
    private List<Leg> legs = new ArrayList<Leg>();
    private OverviewPolyline overviewPolyline;
    private String summary;
    private List<Object> warnings = new ArrayList<Object>();
    private List<Object> waypointOrder = new ArrayList<Object>();
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    private int furto, arrombVeic, roubo, seqRelam, roubVeic, arrastao;


    public FiltroCheckBoxes getFcb() {
        return fcb;
    }

    public void setFcb(FiltroCheckBoxes fcb) {
        this.fcb = fcb;
    }

    public int getFurto() {
        return furto;
    }

    public void setFurto(int furto) {
        this.furto = furto;
    }

    public int getArrombVeic() {
        return arrombVeic;
    }

    public void setArrombVeic(int arrombVeic) {
        this.arrombVeic = arrombVeic;
    }

    public int getRoubo() {
        return roubo;
    }

    public void setRoubo(int roubo) {
        this.roubo = roubo;
    }

    public int getSeqRelam() {
        return seqRelam;
    }

    public void setSeqRelam(int seqRelam) {
        this.seqRelam = seqRelam;
    }

    public int getRoubVeic() {
        return roubVeic;
    }

    public void setRoubVeic(int roubVeic) {
        this.roubVeic = roubVeic;
    }

    public int getArrastao() {
        return arrastao;
    }

    public void setArrastao(int arrastao) {
        this.arrastao = arrastao;
    }

    /**
     * @return The bounds
     */
    public Bounds getBounds() {
        return bounds;
    }

    /**
     * @param bounds The bounds
     */
    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    /**
     * @return The copyrights
     */
    public String getCopyrights() {
        return copyrights;
    }

    /**
     * @param copyrights The copyrights
     */
    public void setCopyrights(String copyrights) {
        this.copyrights = copyrights;
    }

    /**
     * @return The legs
     */
    public List<Leg> getLegs() {
        return legs;
    }

    /**
     * @param legs The legs
     */
    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

    /**
     * @return The overviewPolyline
     */
    public OverviewPolyline getOverviewPolyline() {
        return overviewPolyline;
    }

    /**
     * @param overviewPolyline The overview_polyline
     */
    public void setOverviewPolyline(OverviewPolyline overviewPolyline) {
        this.overviewPolyline = overviewPolyline;
    }

    /**
     * @return The summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * @param summary The summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * @return The warnings
     */
    public List<Object> getWarnings() {
        return warnings;
    }

    /**
     * @param warnings The warnings
     */
    public void setWarnings(List<Object> warnings) {
        this.warnings = warnings;
    }

    /**
     * @return The waypointOrder
     */
    public List<Object> getWaypointOrder() {
        return waypointOrder;
    }

    /**
     * @param waypointOrder The waypoint_order
     */
    public void setWaypointOrder(List<Object> waypointOrder) {
        this.waypointOrder = waypointOrder;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public int compareTo(Route another) {

        int thisVotes = 0;
        int anotherVotes = 0;

        if (another.getFcb().isArrastao()) {
            if (this.arrastao < another.arrastao) {
                thisVotes++;
            } else {
                anotherVotes++;
            }
        } else {
            this.arrastao = 0;
            another.setArrastao(0);
        }

        if (another.getFcb().isArrombVeic()) {
            if (this.arrombVeic < another.arrombVeic) {
                thisVotes++;
            } else {
                anotherVotes++;
            }
        } else {
            this.arrombVeic = 0;
            another.setArrombVeic(0);
        }

        if(another.getFcb().isFurto()){
            if (this.furto < another.furto) {
                thisVotes++;
            } else {
                anotherVotes++;
            }
        } else {
            this.furto = 0;
            another.setFurto(0);
        }

        if(another.getFcb().isRoubo()){
            if (this.roubo < another.roubo) {
                thisVotes++;
            } else {
                anotherVotes++;
            }
        } else {
            this.roubo = 0;
            another.setRoubo(0);
        }

        if(another.getFcb().isSeqRelam()){
            if (this.seqRelam < another.seqRelam) {
                thisVotes++;
            } else {
                anotherVotes++;
            }
        }else {
            this.seqRelam = 0;
            another.setSeqRelam(0);
        }

        if(another.getFcb().isRoubVeic()){
            if (this.roubVeic < another.roubVeic) {
                thisVotes++;
            } else {
                anotherVotes++;
            }
        } else {
            this.roubVeic = 0;
            another.setRoubVeic(0);
        }

        apresentarQuantidadesDeOcorrenciasPorRota(another);

        if (thisVotes < anotherVotes) {
            return 0;                               //0 = THIS TEM MENOS OCORRENCIAS
        } else if (thisVotes > anotherVotes) {
            return 1;                               //1 = OBJETO COMPARADO TEM MAIS OCORRENCIAS
        } else if (thisVotes == anotherVotes) {
            return 0;                               //TANTO FAZ
        }

        return 0;                                   //NÃO CHEGA NESSA PARTE
    }

    private void apresentarQuantidadesDeOcorrenciasPorRota(Route r) {

        Log.e("ARRASTÃO", String.valueOf(r.getArrastao()));
        Log.e("ROUBO", String.valueOf(r.getRoubo()));
        Log.e("FURTO", String.valueOf(r.getFurto()));
        Log.e("ARROMBAMENTO VEICULAR", String.valueOf(r.getArrombVeic()));
        Log.e("SEQUESTRO RELAMPAGO", String.valueOf(r.getSeqRelam()));
        Log.e("ROUBO DE VEICULO", String.valueOf(r.getRoubVeic()));

        Log.e("----", "----------------------");

    }

}
