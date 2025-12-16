package com.mycompany.final_plproject ;
import java.io.* ; 
import java.util.* ; 
public class Services{
    
    public void sendReport(Report r) throws Exception {

    File f = new File("text files\\reports.txt");

    if (!f.exists()) {
        f.createNewFile();
    }

    FileWriter fwR = new FileWriter(f , true); 
    fwR.append(r.toString()) ;
    fwR.close();

    System.out.println("The report was successfully sent.");
}
    
    public void viewReport() throws Exception { 
      File f = new File("text files\\reports.txt") ;
      
      Scanner output = new Scanner(f) ;
      
      while(output.hasNextLine()) {
         String line= output.nextLine();
          System.out.println(line);
      }
      output.close();
    }

}
