import java.util.*;

class Task {
        private final String name;
        private final double estimatedHours;
        private double completedHours;

        public Task(String name, double estimatedHours) {
                this.name = name;
                this.estimatedHours = Math.max(estimatedHours, 0.0);
                this.completedHours = 0.0;
        }

        public String getName() {
                return name;
        }

        public double getEstimatedHours() {
                return estimatedHours;
        }

        public void setCompletedHours(double hours) {
                completedHours = Math.max(0.0, Math.min(hours, estimatedHours));
        }

        public double getProgressFraction() {
                if (estimatedHours == 0)
                        return 0.0;
                return completedHours / estimatedHours;
        }

        public boolean isComplete() {
                return Double.compare(getProgressFraction(), 1.0) >= 0;
        }

        @Override
        public String toString() {
                return name + " [Estimated: " + estimatedHours + "h, Completed: " + completedHours + "h, Progress: "
                                + String.format("%.2f", getProgressFraction() * 100.0) + "%]";
        }
}

class Project {
        private final String title;
        private final List<Task> tasks = new ArrayList<>();

        public Project(String title) {
                this.title = title;
        }

        public void addTask(Task t) {
                tasks.add(t);
        }

        public String getTitle() {
                return title;
        }

        public double getCompletionPercentageSimple() {
                if (tasks.isEmpty())
                        return 0.0;
                long completedCount = 0;
                for (Task t : tasks) {
                        if (t.isComplete()) {
                                completedCount++;
                        }
                }
                return (completedCount * 100.0) / tasks.size();
        }

        public double getCompletionPercentageWeighted() {
                double totalEstimated = 0.0;
                for (Task t : tasks) {
                        totalEstimated += t.getEstimatedHours();
                }
                if (totalEstimated <= 0) {
                        double sumFractions = 0.0;
                        for (Task t : tasks) {
                                sumFractions += t.getProgressFraction();
                        }
                        if (tasks.isEmpty())
                                return 0.0;
                        double averageFraction = sumFractions / tasks.size();
                        return averageFraction * 100.0;
                }
                double weightedCompleted = 0.0;
                for (Task t : tasks) {
                        weightedCompleted += t.getEstimatedHours() * t.getProgressFraction();
                }
                return (weightedCompleted * 100.0) / totalEstimated;
        }

        public void printTaskList() {
                tasks.forEach(t -> System.out.println("  - " + t));
        }
}

class ProjectManagerView {
        public static void showProjectCompletion(Project p) {
                System.out.println("Project: " + p.getTitle());
                System.out.println("Tasks:");
                p.printTaskList();
                double simple = p.getCompletionPercentageSimple();
                double weighted = p.getCompletionPercentageWeighted();
                System.out.printf("Completion (simple, task-count): %.2f%%\n", simple);
                System.out.printf("Completion (weighted by estimated hours): %.2f%%\n", weighted);
        }
}

public class PMCompletionDemo {
        public static void main(String[] args) {

                int employeeHours = 0;
                int teamLeaderHours = 0;
                int projectManagerHours = 0;
                int adminHours = 0;

                Scanner input = new Scanner(System.in);

                try {
                        System.out.print("Enter completed hours for Employee Module (out of 100): ");
                        employeeHours = Integer.parseInt(input.nextLine());
                        if (employeeHours < 0 || employeeHours > 100)
                                throw new IllegalArgumentException("Employee Module hours must be between 0 and 100.");

                        System.out.print("Enter completed hours for Team Leader Module (out of 60): ");
                        teamLeaderHours = Integer.parseInt(input.nextLine());
                        if (teamLeaderHours < 0 || teamLeaderHours > 60)
                                throw new IllegalArgumentException("Team Leader Module hours must be between 0 and 60.");

                        System.out.print("Enter completed hours for Project Manager Module (out of 40): ");
                        projectManagerHours = Integer.parseInt(input.nextLine());
                        if (projectManagerHours < 0 || projectManagerHours > 40)
                                throw new IllegalArgumentException("Project Manager Module hours must be between 0 and 40.");

                        System.out.print("Enter completed hours for Admin Module (out of 40): ");
                        adminHours = Integer.parseInt(input.nextLine());
                        if (adminHours < 0 || adminHours > 40)
                                throw new IllegalArgumentException("Admin Module hours must be between 0 and 40.");

                } catch (NumberFormatException e) {
                        System.out.println("Invalid input. Please enter numeric values only.");
                        return;

                } catch (IllegalArgumentException e) {
                        System.out.println("Error: " + e.getMessage());
                        return;
                }

                Project proj = new Project("Our Project");

                Task t1 = new Task("Employee Module", 100);
                t1.setCompletedHours(employeeHours);

                Task t2 = new Task("Team leader Module", 60);
                t2.setCompletedHours(teamLeaderHours);

                Task t3 = new Task("Project manager Module", 40);
                t3.setCompletedHours(projectManagerHours);

                Task t4 = new Task("Admin Module", 40);
                t4.setCompletedHours(adminHours);

                proj.addTask(t1);
                proj.addTask(t2);
                proj.addTask(t3);
                proj.addTask(t4);

                ProjectManagerView.showProjectCompletion(proj);
        }
}
