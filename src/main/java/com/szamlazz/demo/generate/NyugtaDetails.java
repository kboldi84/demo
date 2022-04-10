package com.szamlazz.demo.generate;

public class NyugtaDetails {

    private String id;
    private String nyugtaszam;
    private String kelt;
    private String filename;

    public String getFilename() {
        return this.filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
    private boolean storno;

    public NyugtaDetails(String id, String nyugtaszam, String kelt, boolean storno,String filename) {
        this.id = id;
        this.nyugtaszam = nyugtaszam;
        this.kelt = kelt;
        this.storno = storno;
        this.filename = filename;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNyugtaszam() {
        return this.nyugtaszam;
    }

    public void setNyugtaszam(String nyugtaszam) {
        this.nyugtaszam = nyugtaszam;
    }

    public String getKelt() {
        return this.kelt;
    }

    public void setKelt(String kelt) {
        this.kelt = kelt;
    }

    public boolean isStorno() {
        return this.storno;
    }

    public boolean getStorno() {
        return this.storno;
    }

    public void setStorno(boolean storno) {
        this.storno = storno;
    }
    
}
