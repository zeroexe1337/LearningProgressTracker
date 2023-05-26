package tracker;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.List;


public class Main {
    static Scanner scanner = new Scanner(System.in);
    static String run;
    private static final List<Student> students = new ArrayList<>();
    static Map<Student, Integer> studentsMap = new HashMap<>();

    static List<StudentsID> idOfStudents = new ArrayList<>();
    static Map<Student, StudentsID> finishedCourse = new HashMap<>();

    public static void main(String[] args) {
        System.out.println("Learning Progress Tracker!");
        commandLine();
    }

    static void commandLine() {

        while (true) {

            run = scanner.nextLine();
            if (run.matches("add students")) {
                System.out.println("Enter student credentials or 'back' to return");
                addStudents();
            } else if (run.equals("exit")) {
                System.out.println("Bye!");
                break;

            } else if (run.matches("back")) {
                System.out.println("Enter 'exit' to exit the program");

            } else if (run.matches("") || run.matches("\\s+")) {
                System.out.println("No input.");
            } else if (run.equals("list")) {
                list();
            } else if (run.equals("add points")) {
                addPoints();
            } else if (run.equals("find")) {
                find(idOfStudents);
            } else if (run.equals("statistics")) {
                statistics();
            } else if (run.equals("notify")) {
                notifyUsers();
            }
            else {
                System.out.println("Error: unknown command!");
            }
        }
    }

    public static boolean containsEmail(List<Student> students, String email) {
        return students.stream().map(Student::getEmail).anyMatch(email::equals);
    }

    static void addStudents() {
        while (true) {

            String enterEmail = scanner.nextLine();

            String[] split = enterEmail.split(" ");

            if (enterEmail.matches("back")) {
                System.out.println("Total " + students.size() + " students have been added.");
                break;
            }
            try {
                List<String> splitInputList = new ArrayList<>(List.of(split));

                String firstName = split[0];
                splitInputList.remove(firstName);

                String email = split[split.length - 1];
                splitInputList.remove(email);

                StringBuilder builder = new StringBuilder();
                splitInputList.forEach(builder::append);
                String lastName = builder.toString();

                Student student = new Student(firstName, lastName, email);

                if ((!student.isValidEmail() && !student.isFirstNameValid() && !student.isLastNameValid()) || split.length < 3) {
                    System.out.println("Incorrect credentials.");
                } else if (!student.isValidEmail()) {
                    System.out.println("Incorrect email.");
                } else if (!student.isFirstNameValid()) {
                    System.out.println("Incorrect first name.");
                } else if (!student.isLastNameValid()) {
                    System.out.println("Incorrect last name.");
                } else if (student.isValidEmail() && containsEmail(students, email)) {
                    System.out.println("This email is already taken.");
                } else {
                    students.add(student);
                    student.getHashCode();
                    System.out.println("The student has been added.");
                    for (Student s : students) {
                        studentsMap.put(s, 1000 + s.hashCode());
                    }
                }

            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Out of bounds");
            }
        }
    }



    static void list() {

        try {

            if (students.isEmpty()) {
                System.out.println("No students found");
            } else {
                System.out.println("Students:");
                for (var entry : studentsMap.entrySet()) {
                    System.out.println(entry.getValue());
                }
            }

        } catch (MissingFormatArgumentException e) {
            System.out.println("Error");
        }
    }

    static void addPoints() {
        System.out.println("Enter an id and points or 'back' to return");
        while (true) {

            boolean idValid = false;
            try {
                String pointsInput = scanner.nextLine();
                if (pointsInput.matches("back")) {
                    break;
                }
                String[] propertyArray = pointsInput.split(" ");
                String id = propertyArray[0];
                if (!id.matches("^([0-9]+)$")) {
                    System.out.println("No student is found for id=" + id);
                    scanner.nextLine();
                }
                int c1 = Integer.parseInt(propertyArray[1]);
                int c2 = Integer.parseInt(propertyArray[2]);
                int c3 = Integer.parseInt(propertyArray[3]);
                int c4 = Integer.parseInt(propertyArray[4]);

                int idd = Integer.parseInt(id);


                for (var entry : studentsMap.entrySet()) {
                    if (entry.getValue().equals(idd)) {
                        idValid = true;
                        break;
                    }
                }
                
                StudentsID fakeID = new StudentsID(idd, c1, c2, c3, c4);
                Iterator<StudentsID> iter = idOfStudents.iterator();

                while (iter.hasNext()) {
                    StudentsID next = iter.next();

                    if (next.getId() == idd) {
                        fakeID.updateC1(next.getJava());
                        fakeID.updateC2(next.getDataStructuresAndAlgorithms());
                        fakeID.updateC3(next.getDatabases());
                        fakeID.updateC4(next.getSpring());
                        iter.remove();
                        break;
                    }
                }

                if (!idValid) {
                    System.out.println("No student is found for id=" + id);
                } else if (!fakeID.isInt() || !fakeID.isInt2() || !fakeID.isInt3() || !fakeID.isInt4() || propertyArray.length != 5) {
                    System.out.println("Incorrect points format.");
                } else if (fakeID.isInt() && fakeID.isInt2() && fakeID.isInt3() && fakeID.isInt4()) {
                    System.out.println("points updated");
                    idOfStudents.add(fakeID);
                }

            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                System.out.println("Incorrect points format.");
            }
        }
        Map<Student,StudentsID> notifications = new HashMap<>();

        for (StudentsID courseProgress : idOfStudents) {

            for (var entry : studentsMap.entrySet()) {
                if (courseProgress.getId() == entry.getValue()) {
                    notifications.put(entry.getKey(), courseProgress);

                }
            }
        }

        for (var entry : notifications.entrySet()) {

            if (entry.getValue().isJavaFinished() && !finishedCourse.containsKey(entry.getKey())) {
                finishedCourse.put(entry.getKey(), entry.getValue());
            }
            if (entry.getValue().isDsaFinished() && !finishedCourse.containsKey(entry.getKey())) {
                finishedCourse.put(entry.getKey(), entry.getValue());
            }
            if (entry.getValue().isDatabaseFinished() && !finishedCourse.containsKey(entry.getKey())) {
                finishedCourse.put(entry.getKey(), entry.getValue());
            }
            if (entry.getValue().isSpringFinished() && !finishedCourse.containsKey(entry.getKey())) {
                finishedCourse.put(entry.getKey(), entry.getValue());
            }
        }

    }
    static void find(List<StudentsID> idOfStudents) {
        System.out.println("Enter an id or 'back' to return:");

        while (true) {
            try {
                String input = scanner.nextLine();
                if (input.equals("back")) {
                    break;
                }

                for (StudentsID s : idOfStudents) {
                    if (s.containsID(Integer.parseInt(input))) {
                        System.out.println(input + " points: Java=" + s.getJava() + "; DSA="
                                + s.getDataStructuresAndAlgorithms() + "; Databases=" +
                                s.getDatabases() + "; Spring=" + s.getSpring());
                        break;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                System.out.println("Error");
            }

        }
    }

    static void statistics() {
        System.out.println("Type the name of a course to see details or 'back' to quit:");

            int countC1 = 0;
            int countC2 = 0;
            int countC3 = 0;
            int countC4 = 0;
            int countC1Users = 0;
            int countC2Users = 0;
            int countC3Users = 0;
            int countC4Users = 0;

            for (StudentsID s : idOfStudents) {
                countC1 += s.getJava();
                countC2 += s.getDataStructuresAndAlgorithms();
                countC3 += s.getDatabases();
                countC4 += s.getSpring();


                if (s.getJava() > 0) {
                    countC1Users++;
                }
                if (s.getDataStructuresAndAlgorithms() > 0) {
                    countC2Users++;
                }
                if (s.getDatabases() > 0) {
                    countC3Users++;
                }
                if (s.getSpring() > 0) {
                    countC4Users++;
                }
            }

            double averageGradeC1 = (double) countC1 / (double) countC1Users;
            double averageGradeC2 = (double) countC2 / (double) countC2Users;
            double averageGradeC3 = (double) countC3 / (double) countC3Users;
            double averageGradeC4 = (double) countC4 / (double) countC4Users;

            CoursePoints c1 = new CoursePoints("Java", countC1Users, countC1Users, averageGradeC1);
            CoursePoints c2 = new CoursePoints("DSA", countC2Users, countC2Users, averageGradeC2);
            CoursePoints c3 = new CoursePoints("Databases", countC3Users, countC3Users, averageGradeC3);
            CoursePoints c4 = new CoursePoints("Spring", countC4Users, countC4Users, averageGradeC4);

            List<CoursePoints> listOfCourses = new ArrayList<>();
            listOfCourses.add(c1);
            listOfCourses.add(c2);
            listOfCourses.add(c3);
            listOfCourses.add(c4);

            if (countC1Users == 0 && countC2Users == 0 && countC3Users == 0 && countC4Users == 0) {
                System.out.println("Most popular: n/a");
                System.out.println("Least popular: n/a");
                System.out.println("Highest activity: n/a");
                System.out.println("Lowest activity: n/a");
                System.out.println("Easiest course: n/a");
                System.out.println("Hardest course: n/a");
            } else {

                CoursePoints maxPopularity = listOfCourses.stream().max(Comparator.comparing(CoursePoints::getPopularity)).get();
                List<CoursePoints> maxValues = listOfCourses.stream().filter(v -> v.getPopularity() == maxPopularity.getPopularity()).toList();
                StringBuilder maxPop = new StringBuilder();
                for (CoursePoints c : maxValues) {
                    maxPop.append(c).append(", ");
                }
                CoursePoints minPopularity = listOfCourses.stream().min(Comparator.comparing(CoursePoints::getPopularity)).get();
                List<CoursePoints> minValues = listOfCourses.stream().filter(v -> v.getPopularity() == minPopularity.getPopularity()).toList();
                StringBuilder minPop = new StringBuilder();
                for (CoursePoints c : minValues) {
                    if (!maxValues.contains(c)) {
                        minPop.append(c).append(", ");
                    }
                }
                CoursePoints maxActivity = listOfCourses.stream().max(Comparator.comparing(CoursePoints::getActivity)).get();
                List<CoursePoints> maxActivities = listOfCourses.stream().filter(v -> v.getActivity() == maxActivity.getActivity()).toList();
                StringBuilder maxAct = new StringBuilder();
                for (CoursePoints c : maxActivities) {
                    maxAct.append(c).append(", ");
                }
                CoursePoints minActivity = listOfCourses.stream().min(Comparator.comparing(CoursePoints::getActivity)).get();
                List<CoursePoints> minActivities = listOfCourses.stream().filter(v -> v.getActivity() == minActivity.getActivity()).toList();
                StringBuilder minAct = new StringBuilder();
                for (CoursePoints c : minActivities) {
                    if (!maxActivities.contains(c)) {
                        minAct.append(c).append(", ");
                    }
                }
                CoursePoints maxDifficulty = listOfCourses.stream().max(Comparator.comparing(CoursePoints::getDifficultyLevel)).get();
                List<CoursePoints> maxDifficultyList = listOfCourses.stream().filter(v -> v.getDifficultyLevel() == maxDifficulty.getDifficultyLevel()).toList();
                StringBuilder maxDiff = new StringBuilder();
                for (CoursePoints c : maxDifficultyList) {
                    maxDiff.append(c).append(", ");
                }
                CoursePoints minDifficulty = listOfCourses.stream().min(Comparator.comparing(CoursePoints::getDifficultyLevel)).get();
                List<CoursePoints> minDifficultyList = listOfCourses.stream().filter(v -> v.getDifficultyLevel() == minDifficulty.getDifficultyLevel()).toList();
                StringBuilder minDiff = new StringBuilder();
                for (CoursePoints c : minDifficultyList) {
                    if (!maxDifficultyList.contains(c)) {
                        minDiff.append(c).append(", ");
                    }

                }
                if (minPop.length() == 0) {
                    minPop.append("n/a");
                }
                if (maxPop.length() == 0) {
                    maxPop.append("n/a");
                }
                if (minAct.length() == 0) {
                    minAct.append("n/a");
                }
                if (maxAct.length() == 0) {
                    maxAct.append("n/a");
                }
                if (minDiff.length() == 0) {
                    minDiff.append("n/a");
                }
                if (minDiff.length() == 0) {
                    minDiff.append("n/a");
                }

                System.out.println("Most popular: " + maxPop);
                System.out.println("Least popular: " + minPop);
                System.out.println("Highest activity: " + maxAct );
                System.out.println("Lowest activity: " + minAct );
                System.out.println("Easiest course: " + maxDiff );
                System.out.println("Hardest course: " + minDiff);
            }
            while (true) {

            Scanner scanner2 = new Scanner(System.in);
            String input = scanner2.nextLine();

                switch (input) {
                    case "java", "JAVA", "Java" -> {
                        Comparator<StudentsID> byJava = Collections.reverseOrder(Comparator.comparing(StudentsID::getJava));
                        idOfStudents.sort(byJava);
                        System.out.println("Java");
                        System.out.println("id          points   completed");
                        for (StudentsID s : idOfStudents) {

                            if (s.getJava() > 0) {

                                System.out.print(s.getId() + "  ");
                                System.out.print(s.getJava() + "        ");
                                double lol = (double) s.getJava() / 600 * 100;
                                BigDecimal bd = BigDecimal.valueOf(lol);
                                bd = bd.setScale(1, RoundingMode.HALF_UP);
                                double d = bd.doubleValue();
                                System.out.print(d +"%");
                                System.out.println();
                            }
                        }
                    }
                    case "dsa", "DSA", "Dsa" -> {
                        Comparator<StudentsID> byDsa = Collections.reverseOrder(Comparator.comparing(StudentsID::getDataStructuresAndAlgorithms));
                        idOfStudents.sort(byDsa);


                        System.out.println("DSA");
                        System.out.println("id          points   completed");
                        for (StudentsID s : idOfStudents) {

                            if (s.getDataStructuresAndAlgorithms() > 0) {

                                System.out.printf(s.getId() + "  " );
                                System.out.print(s.getDataStructuresAndAlgorithms() + "        ");
                                double lol = (double) s.getDataStructuresAndAlgorithms() / 400 * 100;
                                BigDecimal bd = BigDecimal.valueOf(lol);
                                bd = bd.setScale(1, RoundingMode.HALF_UP);
                                double d = bd.doubleValue();
                                System.out.print(d +"%");
                                System.out.println();
                            }
                        }
                    }
                    case "databases", "DATABASES", "Databases" -> {
                        Comparator<StudentsID> byDatabases = Collections.reverseOrder(Comparator.comparing(StudentsID::getDatabases));
                        idOfStudents.sort(byDatabases);

                        System.out.println("Databases");
                        System.out.println("id          points   completed");
                        for (StudentsID s : idOfStudents) {
                            if (s.getDatabases() > 0) {

                                System.out.print(s.getId() + "  ");
                                System.out.print(s.getDatabases() + "        ");
                                double lol = (double) s.getDatabases() / 480 * 100;
                                BigDecimal bd = BigDecimal.valueOf(lol);
                                bd = bd.setScale(1, RoundingMode.HALF_UP);
                                double d = bd.doubleValue();
                                System.out.print(d +"%");
                                System.out.println();
                            }
                        }
                    }
                    case "spring", "SPRING", "Spring" -> {
                        Comparator<StudentsID> bySpring = Collections.reverseOrder(Comparator.comparing(StudentsID::getSpring));
                        idOfStudents.sort(bySpring);

                        System.out.println("Spring");
                        System.out.println("id          points   completed");
                        for (StudentsID s : idOfStudents) {

                            if (s.getSpring() > 0) {

                                System.out.print(s.getId() + "  ");
                                System.out.print(s.getSpring() + "        ");
                                double lol = (double) s.getSpring() / 550 * 100;
                                BigDecimal bd = BigDecimal.valueOf(lol);
                                bd = bd.setScale(1, RoundingMode.HALF_UP);
                                double d = bd.doubleValue();
                                System.out.print(d +"%");
                                System.out.println();
                            }
                        }
                    }
                    default -> System.out.println("Unknown course.");
                }

            if (input.matches("back")) {
                break;
            }
        }
    }
    static void notifyUsers() {

        Iterator<Map.Entry<Student, StudentsID>> iter = finishedCourse.entrySet().iterator();

        if (finishedCourse.isEmpty()) {
            System.out.println("Total 0 students have been notified.");
        }
        while (iter.hasNext()) {

            Map.Entry<Student, StudentsID> next = iter.next();


            if (next.getValue().isJavaFinished()) {
                System.out.println("To: " + next.getKey().getEmail());
                System.out.println("Re: Your Learning Progress");
                System.out.println("Hello, " + next.getKey().getFirstName() + " " + next.getKey().getLastName() + "! You have accomplished our Java course!");
            }
            if (next.getValue().isDsaFinished()) {
                System.out.println("To: " + next.getKey().getEmail());
                System.out.println("Re: Your Learning Progress");
                System.out.println("Hello, " + next.getKey().getFirstName() + " " + next.getKey().getLastName() + "! You have accomplished our DSA course!");
            }
            if (next.getValue().isDatabaseFinished()) {
                System.out.println("To: " + next.getKey().getEmail());
                System.out.println("Re: Your Learning Progress");
                System.out.println("Hello, " + next.getKey().getFirstName() + " " + next.getKey().getLastName() + "! You have accomplished our Database course!");
            }
            if (next.getValue().isSpringFinished()) {
                System.out.println("To: " + next.getKey().getEmail());
                System.out.println("Re: Your Learning Progress");
                System.out.println("Hello, " + next.getKey().getFirstName() + " " + next.getKey().getLastName() + "! You have accomplished our Spring course!");
            }

            if (finishedCourse.containsKey(next.getKey())) {
                iter.remove();
                System.out.println("Total 1 students have been notified.");
            }

        }

    }

}








