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
                Project proj = new Project("Our Project");

                Task t1 = new Task("Employee Module", 100);
                t1.setCompletedHours(70);

                Task t2 = new Task("Team leader Module", 60);
                t2.setCompletedHours(30);

                Task t3 = new Task("Project manager Module", 40);
                t3.setCompletedHours(40);

                Task t4 = new Task("Admin Module", 40);
                t4.setCompletedHours(40);

                proj.addTask(t1);
                proj.addTask(t2);
                proj.addTask(t3);
                proj.addTask(t4);

                ProjectManagerView.showProjectCompletion(proj);
        }
}
