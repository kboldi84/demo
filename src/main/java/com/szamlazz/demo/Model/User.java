package com.szamlazz.demo.Model;

public class User {

    

    private String email;

    private String szamlaagentkey;

    private String password;

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
   

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSzamlaagentkey() {
        return this.szamlaagentkey;
    }

    public void setSzamlaagentkey(String szamlaagentkey) {
        this.szamlaagentkey = szamlaagentkey;
    }

    
}
