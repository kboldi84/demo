package com.szamlazz.demo.generate;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class Exportxml {

    public void export(HttpServletResponse response) throws IOException {
       
  
        
        String myString = "Hello";
        response.setContentType("text/plain");
        response.setHeader("Content-Disposition","attachment;filename=myFile.xml");
        ServletOutputStream out = response.getOutputStream();
        out.println(myString);
        out.flush();
        out.close();
        
     
               
        
     
      
      
  }
    
}
