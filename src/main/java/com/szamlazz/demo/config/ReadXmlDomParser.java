package com.szamlazz.demo.config;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.szamlazz.demo.Model.NyugtaFull;
import com.szamlazz.demo.Model.Tetel;
import com.szamlazz.demo.generate.NyugtaDetails;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
 

@Service
public class ReadXmlDomParser {

       public NyugtaFull nyugtaDetailsGet(String filename) {
         // Instantiate the Factory
         DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
         NyugtaFull nyugta = new NyugtaFull();

         try {

            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(filename));
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("xmlnyugtavalasz");

                    

            for (int temp = 0; temp < list.getLength(); temp++) {
                Node node = list.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                  
                    nyugta.setNyugtaszam(element.getElementsByTagName("nyugtaszam").item(0).getTextContent()); 
                    nyugta.setKelte(element.getElementsByTagName("kelt").item(0).getTextContent());
                    
                }
            }
            List<Tetel> tetelekList = new ArrayList<>();    
             list = doc.getElementsByTagName("tetelek");
             for (int temp = 0; temp < list.getLength(); temp++) {
                Node node = list.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    System.out.println();
                    Tetel tetel = new Tetel();
                    tetel.setMegnevezes(element.getElementsByTagName("megnevezes").item(0).getTextContent());
                    tetel.setMennyiseg(element.getElementsByTagName("mennyiseg").item(0).getTextContent());
                    tetel.setNettoEgysegar(element.getElementsByTagName("nettoEgysegar").item(0).getTextContent()); 
                    tetel.setBrutto(element.getElementsByTagName("brutto").item(0).getTextContent());                       
                    tetelekList.add(tetel);

                }
            }
            list = doc.getElementsByTagName("totalossz");
            for (int temp = 0; temp < list.getLength(); temp++) {
               Node node = list.item(temp);
               if (node.getNodeType() == Node.ELEMENT_NODE) {
                   Element element = (Element) node;
                   nyugta.setTotalnetto(element.getElementsByTagName("netto").item(0).getTextContent());
                   nyugta.setBrutto(element.getElementsByTagName("brutto").item(0).getTextContent());
                   nyugta.setAfa(element.getElementsByTagName("afa").item(0).getTextContent());
                }
           }
           nyugta.setTetelekshortList(tetelekList);
            

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return nyugta;

    }



    public NyugtaDetails getNyugtaDetails(String filename) {


       



        // Instantiate the Factory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        String id = "";
        String nyugta = "";
        String kelte = "";
        String storno = "";
        try {

            // optional, but recommended
            // process XML securely, avoid attacks like XML External Entities (XXE)
            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            // parse XML file
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(filename));
            doc.getDocumentElement().normalize();
            NodeList list = doc.getElementsByTagName("xmlnyugtavalasz");

                    

            for (int temp = 0; temp < list.getLength(); temp++) {
                Node node = list.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    id = element.getElementsByTagName("id").item(0).getTextContent();
                    nyugta = element.getElementsByTagName("nyugtaszam").item(0).getTextContent();
                    kelte = element.getElementsByTagName("kelt").item(0).getTextContent();
                    storno = element.getElementsByTagName("stornozott").item(0).getTextContent();
                    
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
        return new NyugtaDetails(id,nyugta,kelte,storno.equals("true")? true :false,filename);
    }

}
