import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Student {
    int id;
    String name;
    double marks;

    public Student(int id, String name, double marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    public String getRank() {
        if (marks < 5.0) return "Fail";
        if (marks < 6.5) return "Medium";
        if (marks < 7.5) return "Good";
        if (marks < 9.0) return "Very Good";
        return "Excellent";
    }
}

public class StudentManager {
    private ArrayList<Student> students;

    public StudentManager() {
        students = new ArrayList<>();
    }

    public void addStudent(int id, String name, double marks) {
        students.add(new Student(id, name, marks));
        System.out.println("Student has been added successfully!");
    }

    public void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("There are no students in the list yet..");
        } else {
            for (Student s : students) {
                System.out.println("ID: " + s.id + ", Name: " + s.name + ", Score: " + s.marks + ", Rating: " + s.getRank());
            }
        }
    }

    public void sortStudents() {
        if (students.isEmpty()) {
            System.out.println("No students to arrange.");
        } else {
            for (int i = 0; i < students.size() - 1; i++) {
                for (int j = 0; j < students.size() - 1 - i; j++) {
                    if (students.get(j).marks < students.get(j + 1).marks) {
                        Student temp = students.get(j);
                        students.set(j, students.get(j + 1));
                        students.set(j + 1, temp);
                    }
                }
            }
            System.out.println("List of students sorted by score (high to low).");
        }
    }

    public void heapSort() {
        int n = students.size();

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(students, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            Student temp = students.get(0);
            students.set(0, students.get(i));
            students.set(i, temp);

            heapify(students, i, 0);

        }
        System.out.println("List of students sorted by score.");
    }

    private void heapify(ArrayList<Student> students, int n, int i) {
        int largest = i; // Khởi tạo largest là gốc
        int left = 2 * i + 1; // left = 2*i + 1
        int right = 2 * i + 2; // right = 2*i + 2

        if (left < n && students.get(left).marks > students.get(largest).marks) {
            largest = left;
        }

        if (right < n && students.get(right).marks > students.get(largest).marks) {
            largest = right;
        }

        if (largest != i) {
            Student swap = students.get(i);
            students.set(i, students.get(largest));
            students.set(largest, swap);

            heapify(students, n, largest);
        }
    }

    public void editStudent(int id, String name, double marks) {
        boolean found = false;
        for (Student s : students) {
            if (s.id == id) {
                s.name = name;
                s.marks = marks;
                found = true;
                System.out.println("Students have been updated.");
                break;
            }
        }
        if (!found) {
            System.out.println("No student found with ID: " + id);
        }
    }

    public void deleteStudent(int id) {
        boolean removed = students.removeIf(s -> s.id == id);
        if (removed) {
            System.out.println("Student has been deleted.");
        } else {
            System.out.println("No student found with ID: " + id);
        }
    }

    public static void main(String[] args) {
        StudentManager manager = new StudentManager();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("\nSelect an option:");
                System.out.println("1. Add students");
                System.out.println("2. Edit students");
                System.out.println("3. Delete students");
                System.out.println("4. Show students");
                System.out.println("5. Sort students by score (Bubble Sort)");
                System.out.println("6. Sort students by score (Heap Sort)");
                System.out.println("7. Exit");
                System.out.print("Enter your selection: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Enter Student ID: ");
                        int id = scanner.nextInt();
                        scanner.nextLine();

                        String name;
                        while (true) {
                            System.out.print("Enter Student Name: ");
                            name = scanner.nextLine();
                            if (name.matches("[a-zA-Z\s]+")) {
                                break;
                            } else {
                                System.out.println("Invalid name. Please enter again.");
                            }
                        }

                        System.out.print("Enter Score (0-10): ");
                        double marks;
                        while (true) {
                            try {
                                marks = Double.parseDouble(scanner.nextLine());
                                if (marks >= 0 && marks <= 10) {
                                    break;
                                } else {
                                    System.out.print("Invalid score. Score must be between 0 and 10. Please re-enter: ");
                                }
                            } catch (NumberFormatException e) {
                                System.out.print("Invalid input. Please enter a valid score (0-10): ");
                            }
                        }

                        manager.addStudent(id, name, marks);
                        break;

                    case 2:
                        System.out.print("Enter the Student ID to be edited: ");
                        int editId = scanner.nextInt();
                        scanner.nextLine();

                        String newName;
                        while (true) {
                            System.out.print("Enter New Name: ");
                            newName = scanner.nextLine();
                            if (newName.matches("[a-zA-Z\s]+")) {
                                break;
                            } else {
                                System.out.println("Invalid name. Please enter again.");
                            }
                        }

                        System.out.print("Enter New Points: ");
                        double newMarks = scanner.nextDouble();
                        if (newMarks < 0 || newMarks > 10) {
                            System.out.println("Invalid score. Score must be between 0 and 10.");
                        } else {
                            manager.editStudent(editId, newName, newMarks);
                        }
                        break;

                    case 3:
                        System.out.print("Enter the Student ID to delete: ");
                        int deleteId = scanner.nextInt();
                        manager.deleteStudent(deleteId);
                        break;

                    case 4:
                        System.out.println("List of Students:");
                        manager.displayStudents();
                        break;

                    case 5:
                        manager.sortStudents();
                        break;

                    case 6:
                        manager.heapSort();
                        break;

                    case 7:
                        System.out.println("Program ends.");
                        scanner.close();
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid selection, please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number..");
                scanner.nextLine();
            }
        }
    }
}
