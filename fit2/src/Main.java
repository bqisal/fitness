import java.util.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {

    private static final String DATA_FILE = "fitness_members.csv";

    public static void main(String[] args) {
        List<Member> members = loadMembersFromFile();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Fitness Center Membership System ---");
            System.out.println("1. Register New Member");
            System.out.println("2. View Member Info");
            System.out.println("3. Update Member Info");
            System.out.println("4. Delete Member");
            System.out.println("5. Display All Members");
            System.out.println("6. Add Workout Session");
            System.out.println("7. Generate Report");
            System.out.println("8. Save Data");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    registerNewMember(members, scanner);
                    break;
                case "2":
                    viewMemberInfo(members, scanner);
                    break;
                case "3":
                    updateMemberInfo(members, scanner);
                    break;
                case "4":
                    deleteMember(members, scanner);
                    break;
                case "5":
                    displayAllMembers(members);
                    break;
                case "6":
                    addWorkoutSession(members, scanner);
                    break;
                case "7":
                    generateReport(members);
                    break;
                case "8":
                    saveMembersToFile(members);
                    break;
                case "9":
                    System.out.println("Exiting system. Stay fit!");
                    saveMembersToFile(members); // Сохраняем данные перед выходом
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static class Member {
        String id, name, email;
        List<Workout> detailedWorkouts;

        public Member(String id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.detailedWorkouts = new ArrayList<>();
        }

        public String toCsv() {
            List<String> workoutData = new ArrayList<>();
            for (Workout w : detailedWorkouts) {
                workoutData.add(w.toCsv());
            }
            return String.format("%s,%s,%s,%s", id, name, email, String.join(";", workoutData));
        }

        public static Member fromCsv(String csv) {
            String[] parts = csv.split(",", 4);
            if (parts.length >= 3) {
                Member m = new Member(parts[0], parts[1], parts[2]);
                if (parts.length == 4 && !parts[3].isEmpty()) {
                    String[] workouts = parts[3].split(";");
                    for (String w : workouts) {
                        Workout session = Workout.fromCsv(w);
                        if (session != null) {
                            m.detailedWorkouts.add(session);
                        }
                    }
                }
                return m;
            }
            return null;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("ID: ").append(id).append(", Name: ").append(name).append(", Email: ").append(email);
            if (!detailedWorkouts.isEmpty()) {
                sb.append("\n  Workouts:");
                for (Workout w : detailedWorkouts) {
                    sb.append("\n    - ").append(w);
                }
            }
            return sb.toString();
        }
    }

    static class Workout {
        int day, month, year;
        String exerciseName;
        int sets;
        int weight;

        public Workout(int day, int month, int year, String exerciseName, int sets, int weight) {
            this.day = day;
            this.month = month;
            this.year = year;
            this.exerciseName = exerciseName;
            this.sets = sets;
            this.weight = weight;
        }

        public String toCsv() {
            return String.format("%d-%d-%d|%s|%d|%d", day, month, year, exerciseName, sets, weight);
        }

        public static Workout fromCsv(String csv) {
            String[] parts = csv.split("\\|");
            if (parts.length == 4) {
                try {
                    String[] dateParts = parts[0].split("-");
                    int day = Integer.parseInt(dateParts[0]);
                    int month = Integer.parseInt(dateParts[1]);
                    int year = Integer.parseInt(dateParts[2]);
                    String name = parts[1];
                    int sets = Integer.parseInt(parts[2]);
                    int weight = Integer.parseInt(parts[3]);
                    return new Workout(day, month, year, name, sets, weight);
                } catch (Exception e) {
                    return null;
                }
            }
            return null;
        }

        public String toString() {
            return String.format("%s (%d/%d/%d), Sets: %d, Weight: %d kg",
                    exerciseName, day, month, year, sets, weight);
        }
    }

    static void registerNewMember(List<Member> members, Scanner scanner) {
        System.out.println("\n--- Register New Member ---");
        String id;
        while (true) {
            System.out.print("Enter ID: ");
            id = scanner.nextLine();
            if (isMemberIdExists(members, id)) {
                System.out.println("This ID already exists. Please enter a different ID.");
            } else {
                break;
            }
        }

        System.out.print("Enter full name: ");
        String name = scanner.nextLine();

        String email;
        while (true) {
            System.out.print("Enter email: ");
            email = scanner.nextLine();
            if (isValidEmail(email)) {
                break;
            } else {
                System.out.println("Invalid email format. Please try again.");
            }
        }

        Member newMember = new Member(id, name, email);
        members.add(newMember);
        System.out.println("Member " + name + " registered successfully.");
    }

    static boolean isMemberIdExists(List<Member> members, String id) {
        return members.stream().anyMatch(m -> m.id.equals(id));
    }

    static boolean isValidEmail(String email) {
        return email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    static void viewMemberInfo(List<Member> members, Scanner scanner) {
        System.out.print("Enter Member ID: ");
        String id = scanner.nextLine();
        for (Member m : members) {
            if (m.id.equals(id)) {
                System.out.println(m);
                return;
            }
        }
        System.out.println("Member not found.");
    }

    static void updateMemberInfo(List<Member> members, Scanner scanner) {
        System.out.print("Enter Member ID: ");
        String id = scanner.nextLine();
        for (Member m : members) {
            if (m.id.equals(id)) {
                System.out.print("Enter new name (leave blank to keep): ");
                String name = scanner.nextLine();
                if (!name.isEmpty()) m.name = name;

                System.out.print("Enter new email (leave blank to keep): ");
                String email = scanner.nextLine();
                if (!email.isEmpty()) {
                    if (isValidEmail(email)) {
                        m.email = email;
                    } else {
                        System.out.println("Invalid email format. Email not updated.");
                    }
                }

                System.out.println("Member updated.");
                return;
            }
        }
        System.out.println("Member not found.");
    }

    static void deleteMember(List<Member> members, Scanner scanner) {
        System.out.print("Enter Member ID to delete: ");
        String id = scanner.nextLine();
        Iterator<Member> iter = members.iterator();
        while (iter.hasNext()) {
            if (iter.next().id.equals(id)) {
                iter.remove();
                System.out.println("Member deleted.");
                return;
            }
        }
        System.out.println("Member not found.");
    }

    static void displayAllMembers(List<Member> members) {
        if (members.isEmpty()) {
            System.out.println("No members found.");
            return;
        }
        for (Member m : members) {
            System.out.println(m);
        }
    }

    static void addWorkoutSession(List<Member> members, Scanner scanner) {
        System.out.print("Enter Member ID: ");
        String id = scanner.nextLine();

        for (Member m : members) {
            if (m.id.equals(id)) {
                try {
                    System.out.println("Enter date (day month year):");
                    int day = Integer.parseInt(scanner.next());
                    int month = Integer.parseInt(scanner.next());
                    int year = Integer.parseInt(scanner.next());
                    scanner.nextLine(); // consume newline

                    System.out.println("Enter exercise name:");
                    String exercise = scanner.nextLine();

                    System.out.println("Number of sets:");
                    int sets = Integer.parseInt(scanner.nextLine());

                    System.out.println("Weight (kg):");
                    int weight = Integer.parseInt(scanner.nextLine());

                    Workout newWorkout = new Workout(day, month, year, exercise, sets, weight);
                    m.detailedWorkouts.add(newWorkout);
                    System.out.println("Workout added!");
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter numbers where required.");
                }
                return;
            }
        }
        System.out.println("Member not found.");
    }

    static void generateReport(List<Member> members) {
        System.out.println("\n--- Report ---");
        System.out.println("Total members: " + members.size());
        int totalWorkouts = members.stream().mapToInt(m -> m.detailedWorkouts.size()).sum();
        System.out.println("Total workouts logged: " + totalWorkouts);

        // Дополнительная статистика
        if (!members.isEmpty()) {
            double avgWorkouts = (double) totalWorkouts / members.size();
            System.out.printf("Average workouts per member: %.1f\n", avgWorkouts);
        }
    }

    static void saveMembersToFile(List<Member> members) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(DATA_FILE))) {
            for (Member m : members) {
                writer.println(m.toCsv());
            }
            System.out.println("Data saved to " + DATA_FILE);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    static List<Member> loadMembersFromFile() {
        List<Member> members = new ArrayList<>();
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return members;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(DATA_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Member m = Member.fromCsv(line);
                if (m != null) {
                    members.add(m);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
        return members;
    }
}