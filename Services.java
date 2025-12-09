package com.mycompany.pl2_project;
import java.io.* ; 
import java.util.* ; 
public class Services{
    
    public void sendReport(Report r) throws Exception {

    File f = new File("C:/Users/HP/OneDrive/Desktop/Pl Porject/text files/reports.txt");

    if (!f.exists()) {
        f.createNewFile();
    }

    FileWriter fwR = new FileWriter(f , true); 
    fwR.append(r.toString()) ;
    fwR.close();

    System.out.println("The report was successfully sent.");
}
    
    public void viewReport() throws Exception { 
      File f = new File("C:/Users/HP/OneDrive/Desktop/Pl Porject/text files/reports.txt") ;
      
      Scanner output = new Scanner(f) ;
      
      while(output.hasNextLine()) {
         String line= output.nextLine();
          System.out.println(line);
      }
      output.close();
    }

}
