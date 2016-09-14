package br.com.fatec.tcc.rotasegura.model;

/**
 * Created by Inmetrics on 13/09/2016.
 */
public class FiltroCheckBoxes {

    //Essa classe foi criada apenas para validar os filtros.
    //Dentro da classe Route.class, no método compareTo(Route r) ela é usada (apenas nesse método);
    //Neste método são travadas algumas validações (função dos filtros)
    private boolean furto;
    private boolean arrombVeic;
    private boolean roubo;
    private boolean seqRelam;
    private boolean roubVeic;
    private boolean arrastao;

    public boolean isFurto() {
        return furto;
    }

    public void setFurto(boolean furto) {
        this.furto = furto;
    }

    public boolean isArrombVeic() {
        return arrombVeic;
    }

    public void setArrombVeic(boolean arrombVeic) {
        this.arrombVeic = arrombVeic;
    }

    public boolean isRoubo() {
        return roubo;
    }

    public void setRoubo(boolean roubo) {
        this.roubo = roubo;
    }

    public boolean isSeqRelam() {
        return seqRelam;
    }

    public void setSeqRelam(boolean seqRelam) {
        this.seqRelam = seqRelam;
    }

    public boolean isRoubVeic() {
        return roubVeic;
    }

    public void setRoubVeic(boolean roubVeic) {
        this.roubVeic = roubVeic;
    }

    public boolean isArrastao() {
        return arrastao;
    }

    public void setArrastao(boolean arrastao) {
        this.arrastao = arrastao;
    }
}
