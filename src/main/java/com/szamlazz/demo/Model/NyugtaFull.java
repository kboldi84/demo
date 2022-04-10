package com.szamlazz.demo.Model;

import java.util.List;

public class NyugtaFull extends Nyugta{

        private String totalnetto;
        private String afa;
        private String brutto;
        private String nyugtaszam;
        private String kelte;
        private List<Tetel> tetelekshortList;

    public List<Tetel> getTetelekshortList() {
        return this.tetelekshortList;
    }

    public void setTetelekshortList(List<Tetel> tetelekshortList) {
        this.tetelekshortList = tetelekshortList;
    }


    public String getKelte() {
        return this.kelte;
    }

    public String getTotalnetto() {
        return this.totalnetto;
    }

    public void setTotalnetto(String totalnetto) {
        this.totalnetto = totalnetto;
    }

    public void setKelte(String kelte) {
        this.kelte = kelte;
    }

    public String getNyugtaszam() {
        return this.nyugtaszam;
    }

    public void setNyugtaszam(String nyugtaszam) {
        this.nyugtaszam = nyugtaszam;
    }

    

    public String getAfa() {
        return this.afa;
    }

    public void setAfa(String afa) {
        this.afa = afa;
    }

    public String getBrutto() {
        return this.brutto;
    }

    public void setBrutto(String brutto) {
        this.brutto = brutto;
    }


}
