package com.mycompany.final_plproject;

public class Report {
  private int employeeID ; 
  private int projectManagerID ; 
  private int teamLeaderID ; 
  private String messageContent ; 
  private String Date ; 

    public Report(int empID, int pm_ID, int tl_ID, String messageC, String date) {
        this.employeeID = empID;
        this.projectManagerID = pm_ID;
        this.teamLeaderID = tl_ID;
        this.messageContent = messageC;
        this.Date = date;
    }

    @Override
    public String toString() {
        return "Report{" + "employeeID=" + employeeID + ", projectManagerID=" + projectManagerID + ", teamLeaderID=" + teamLeaderID + ", message=" + messageContent + ", Date=" + Date + '}' + System.lineSeparator();
    }  
}
