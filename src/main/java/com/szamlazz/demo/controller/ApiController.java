package com.szamlazz.demo.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.szamlazz.demo.Model.MyResponse;
import com.szamlazz.demo.Model.Nyugta;
import com.szamlazz.demo.Model.NyugtaFull;
import com.szamlazz.demo.Model.Tetel;
import com.szamlazz.demo.Model.User;
import com.szamlazz.demo.config.FileExporter;
import com.szamlazz.demo.config.ReadXmlDomParser;
import com.szamlazz.demo.generate.NyugtaDetails;
import com.szamlazz.demo.generate.Nyugtagetdetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ApiController {

    @Autowired
    private FileExporter fileExporter;

    @Autowired
    private ReadXmlDomParser xmldatareader;

    @RequestMapping("")
    public String starterPage(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }


    @RequestMapping("/home")
    public String gotohomePage(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        return "index";
    }

    @RequestMapping("/signin")
    public String homePage(@ModelAttribute User user, Model model, HttpServletRequest request) {
        model.addAttribute("user", user);
        request.getSession().setAttribute("user", user);
        return "index";
    }

    @RequestMapping("/generateNyugta")
    public String createNyugta(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        String[] payment = { "átutalás", "készpénz", "bankkártya", "csekk", "utánvét", "ajándékutalvány", "barion",
                "barter", "csoportos", "beszedés", "OTP Simple", "kompenzáció", "kupon", "PayPal", "PayU",
                "SZÉP kártya", "utalvány" };
        String[] currency = { "Ft", "HUF", "EUR", "USD" };

        String[] vatrate = { "0", "5", "10", "27", "AAM", "TAM", "EU", "EUK", "MAA", "F.AFA", "K.AFA", "ÁKK", "HO",
                "EUE", "EUFADE", "EUFAD37", "ATK", "NAM", "EAM", "KBAUK", "KBAET" };

        model.addAttribute("vatrate", vatrate);
        model.addAttribute("payment", payment);
        model.addAttribute("currency", currency);

        model.addAttribute("user", user);
        return "createNyugta";
    }

    @PostMapping("nyugtacreate")
    public ResponseEntity<Object> exportToXml(@RequestBody Nyugta jsonobject, HttpSession session) {
        String newline = "\n";
        User user = (User) session.getAttribute("user");
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        String fileName = "Create-Nyugta-" + date + "-" + timestamp.getTime() + ".xml";
        String fileContent = "<xmlnyugtacreate xmlns='http://www.szamlazz.hu/xmlnyugtacreate' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xsi:schemaLocation='http://www.szamlazz.hu/xmlnyugtacreate http://www.szamlazz.hu/docs/xsds/nyugta/xmlnyugtacreate.xsd'>"
                +
                "<beallitasok>" +
                "<felhasznalo>" + jsonobject.getFelhasznalo() + "</felhasznalo>" + newline +
                "<jelszo>" + jsonobject.getJelszo() + "</jelszo>" + newline +
                "<szamlaagentkulcs>" + jsonobject.getSzamlaagentkulcs() + "</szamlaagentkulcs>" + newline +
                "<pdfLetoltes>" + jsonobject.getPdfLetoltes() + "</pdfLetoltes>" + newline +
                "</beallitasok>" + newline +
                "<fejlec>" + newline +
                "<hivasAzonosito></hivasAzonosito>" + newline +
                "<elotag>NYGTA</elotag>" +
                "<fizmod>" + jsonobject.getFizmod() + "</fizmod>" + newline +
                "<penznem>" + jsonobject.getPenznem() + "</penznem>" + newline +
                "<devizabank>MNB</devizabank>" + newline +
                "<devizaarf>0.0</devizaarf>" + newline +
                "<megjegyzes></megjegyzes>" + newline +
                "<pdfSablon>" + jsonobject.getPdfSablon() + "</pdfSablon>" + newline +
                "<fokonyvVevo></fokonyvVevo>" + newline +
                "</fejlec>" + newline +
                "<tetelek>" + newline;

        for (Tetel tetel : jsonobject.getTetelek()) {
            fileContent += "<tetel>" + newline +
                    "<megnevezes>" + tetel.getMegnevezes() + "</megnevezes>" + newline +
                    "<azonosito></azonosito>" + newline +
                    "<mennyiseg>" + tetel.getMennyiseg() + "</mennyiseg>" + newline +
                    "<mennyisegiEgyseg>" + tetel.getMennyisegiEgyseg() + "</mennyisegiEgyseg>" + newline +
                    "<nettoEgysegar>" + tetel.getNettoEgysegar() + "</nettoEgysegar>" + newline +
                    "<netto>" + tetel.getNetto() + "</netto>" + newline +
                    "<afakulcs>" + tetel.getAfakulcs() + "</afakulcs>" + newline +
                    "<afa>" + tetel.getAfa() + "</afa>" + newline +
                    "<brutto>" + tetel.getBrutto() + "</brutto>" + newline +
                    "<fokonyv>" + newline +
                    "<arbevetel>...</arbevetel>" + newline +
                    "<afa>...</afa>" + newline +
                    "</fokonyv>" + newline +
                    "</tetel>";

        }

        fileContent = fileContent + "</tetelek></xmlnyugtacreate>";

        // Create text file
        Path exportedPath = fileExporter.export(fileContent, fileName);

        MyResponse<Path> response = new MyResponse<>("success", exportedPath);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }


    @PostMapping("/uploadFilesTeszt")
	public ResponseEntity<Object> dropzoneFileUpload(@RequestParam("file") MultipartFile file) {

        String hibakod = "";
        String status = "";

		try {
            InputStream fis = file.getInputStream();
            Scanner scanner = new Scanner(fis);
            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                if (line.contains("<sikeres>false</sikeres>")){
                    status = "sikertelen";
                }

                if (line.contains("<sikeres>true</sikeres>")){
                    status = "sikeres";
                    hibakod = "-";
                }

                if (line.contains("<hibauzenet>")){
                    hibakod = line;
                }
                 
            }
          

 

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        String message = "{" + 
        "status:" + status + "," +
        "hibakod:" + hibakod + "}";
			 
	 
        MyResponse<String> response = null;
        if (status.equals("sikeres")){
             response = new MyResponse<>("success", message);
        }else{
              response = new MyResponse<>("error", message);
        }


		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}

    @RequestMapping("/nyugtak")
	public String nyugtakList(Model model,HttpSession session) throws IOException {
        User user = (User) session.getAttribute("user");
       

        String directory = "c:\\Szamla-Agent\\NYUGTAK-SUCCESS\\";
        List<NyugtaDetails> fileList = new ArrayList<>();

        try {
            List<File> files = Files.list(Paths.get(directory))
              .map(Path::toFile)
              .filter(File::isFile)
              .collect(Collectors.toList());
          
            //files.forEach(System.out::println);
            for (int i=0;i<files.size();i++){
                //System.out.println(files.get(i).getCanonicalPath());
                fileList.add(xmldatareader.getNyugtaDetails(files.get(i).getCanonicalPath()));   
            }

          } catch (IOException e) {
            e.printStackTrace();
          }
       
        model.addAttribute("fileList", fileList);
        model.addAttribute("user", user);
		return "list-nyugtak";
	}


    @PostMapping("nyugta-get-create")
    public ResponseEntity<Object> nyugtagetcreatexml(@RequestBody Nyugtagetdetails jsonobject, HttpSession session) {
        String newline = "\n";
        User user = (User) session.getAttribute("user");
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        String fileName = "Get-"+jsonobject.getNyugtaszam()+"-" + date + "-" + timestamp.getTime() + ".xml";
        String fileContent = "<?xml version='1.0' encoding='UTF-8'?>"+newline+ 
        "<xmlnyugtaget xmlns='http://www.szamlazz.hu/xmlnyugtaget' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xsi:schemaLocation='http://www.szamlazz.hu/xmlnyugtaget http://www.szamlazz.hu/docs/xsds/nyugtaget/xmlnyugtaget.xsd'>"+newline+
          "<beallitasok>"+newline +
            "<felhasznalo>" + jsonobject.getFelhasznalo()+ "</felhasznalo>"+newline +             
            "<jelszo>"+ jsonobject.getJelszo()+"</jelszo>"+newline +                             
            "<szamlaagentkulcs>"+jsonobject.getSzamlaagentkulcs()+"</szamlaagentkulcs>"+newline +   
            "<pdfLetoltes>false</pdfLetoltes>"+newline +                               
          "</beallitasok>"+newline +   
          "<fejlec>"+newline +   
            "<nyugtaszam>"+jsonobject.getNyugtaszam()+"</nyugtaszam>"+newline+
          "</fejlec>"+newline+
        "</xmlnyugtaget>";

     

        // Create text file
        Path exportedPath = fileExporter.export(fileContent, fileName);

        MyResponse<Path> response = new MyResponse<>("success", exportedPath);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @PostMapping("nyugta-storno-create")
    public ResponseEntity<Object> nyugtastornocreatexml(@RequestBody Nyugtagetdetails jsonobject, HttpSession session) {
        String newline = "\n";
        User user = (User) session.getAttribute("user");
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        String fileName = "Storno-"+jsonobject.getNyugtaszam()+"-" + date + "-" + timestamp.getTime() + ".xml";
        String fileContent = "<?xml version='1.0' encoding='UTF-8'?>"+newline+ 
        "<xmlnyugtast xmlns='http://www.szamlazz.hu/xmlnyugtast' xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance' xsi:schemaLocation='http://www.szamlazz.hu/xmlnyugtast http://www.szamlazz.hu/docs/xsds/nyugtast/xmlnyugtast.xsd'>"+newline+
          "<beallitasok>"+newline +
            "<felhasznalo>" + jsonobject.getFelhasznalo()+ "</felhasznalo>"+newline +             
            "<jelszo>"+ jsonobject.getJelszo()+"</jelszo>"+newline +                             
            "<szamlaagentkulcs>"+jsonobject.getSzamlaagentkulcs()+"</szamlaagentkulcs>"+newline +   
            "<pdfLetoltes>false</pdfLetoltes>"+newline +                               
          "</beallitasok>"+newline +   
          "<fejlec>"+newline +   
            "<nyugtaszam>"+jsonobject.getNyugtaszam()+"</nyugtaszam>"+newline+
          "</fejlec>"+newline+
        "</xmlnyugtast>";

     

        // Create text file
        Path exportedPath = fileExporter.export(fileContent, fileName);

        MyResponse<Path> response = new MyResponse<>("success", exportedPath);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

    @PostMapping("nyugta-open")
    public ResponseEntity<Object> nyugtaXmlReader(@RequestBody NyugtaDetails jsonobject, HttpSession session) {

        NyugtaFull nyugta = xmldatareader.nyugtaDetailsGet(jsonobject.getFilename());
        
        //nyugtaDetailsGet

        MyResponse<NyugtaFull> response = new MyResponse<>("success", nyugta);
        return new ResponseEntity<Object>(response, HttpStatus.OK);
    }

}
