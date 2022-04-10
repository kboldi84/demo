package com.szamlazz.demo.Model;

public class Nyugta {
    private String felhasznalo; 
    private String jelszo; 
    private String szamlaagentkulcs;
    private String pdfLetoltes;
    private String fizmod;
    private String penznem;
    private String pdfSablon;
    private Tetel[] tetelek;

    public String getFelhasznalo() {
        return this.felhasznalo;
    }

    public void setFelhasznalo(String felhasznalo) {
        this.felhasznalo = felhasznalo;
    }

    public String getJelszo() {
        return this.jelszo;
    }

    public void setJelszo(String jelszo) {
        this.jelszo = jelszo;
    }

    public String getSzamlaagentkulcs() {
        return this.szamlaagentkulcs;
    }

    public void setSzamlaagentkulcs(String szamlaagentkulcs) {
        this.szamlaagentkulcs = szamlaagentkulcs;
    }

    public String getPdfLetoltes() {
        return this.pdfLetoltes;
    }

    public void setPdfLetoltes(String pdfLetoltes) {
        this.pdfLetoltes = pdfLetoltes;
    }

    public String getFizmod() {
        return this.fizmod;
    }

    public void setFizmod(String fizmod) {
        this.fizmod = fizmod;
    }

    public String getPenznem() {
        return this.penznem;
    }

    public void setPenznem(String penznem) {
        this.penznem = penznem;
    }

    public String getPdfSablon() {
        return this.pdfSablon;
    }

    public void setPdfSablon(String pdfSablon) {
        this.pdfSablon = pdfSablon;
    }

    public Tetel[] getTetelek() {
        return this.tetelek;
    }

    public void setTetelek(Tetel[] tetelek) {
        this.tetelek = tetelek;
    }
}
