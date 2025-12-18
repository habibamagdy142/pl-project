package com.mycompany.final_plproject;

import java.io.*;
import java.util.ArrayList;

public class ProjectProgressService {

    public static void showProjectProgress() {

        double totalEstimated = 0;
        double totalCompleted = 0;

        try (BufferedReader br = new BufferedReader(
                new FileReader("text files\\tasks.txt"))) {

            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");

                int taskId = Integer.parseInt(data[0]);
                int empId = Integer.parseInt(data[1]);
                String description = data[2];
                String status = data[3];
                String deadline = data[4];
                double completed = Double.parseDouble(data[5]);
                double estimated = Double.parseDouble(data[6]);

                if (!status.equalsIgnoreCase("InProgress")
                        && !status.equalsIgnoreCase("Completed")) {
                    continue;
                }

                double progress =
                        (estimated == 0) ? 0 : (completed / estimated) * 100;

                System.out.printf(
                        "Task ID: %d | Emp ID: %d | %s | %.2f%% complete\n",
                        taskId, empId, description, progress
                );

                totalCompleted += completed;
                totalEstimated += estimated;
            }

        } catch (FileNotFoundException e) {
            System.out.println("No tasks found.");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (totalEstimated == 0) {
            System.out.println("Project Completion: 0%");
            return;
        }

        double projectProgress = (totalCompleted / totalEstimated) * 100;
        System.out.printf("Project Completion: %.2f%%\n", projectProgress);
        
    }
}