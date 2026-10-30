import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {

    Scanner scanner = new Scanner(System.in);

    List<Doctor> doctorList = new ArrayList<>();
    List<Patient> patientList = new ArrayList<>();
    List<Exam> examinationList = new ArrayList<>();
    List<Appointment> appointmentList = new ArrayList<>();

    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    private void start() {

        loadData();

        int value;
        boolean exit = false;

        while (!exit) {
            System.out.println(
                    "\n1. Doctors \n2. Patients \n3. Examinations \n4. Appointments \n5. Statistics \n0. Exit");
            System.out.print("\n-- Enter Choice: ");
            value = readInt();

            switch (value) {
                case 1:
                    doctorMenu();
                    break;
                case 2:
                    patientMenu();
                    break;
                case 3:
                    examMenu();
                    break;
                case 4:
                    appointmentMenu();
                    break;
                case 5:
                    statisticsMenu();
                    break;
                case 0:
                    System.out.println("Saving data");
                    saveData();
                    exit = true;
                    break;
            }
        }
    }

    // ----------------- menu functions -----------------
    private void doctorMenu() {
        int choice;

        System.out.println("\n--------------- DOCTOR MENU ---------------");
        System.out.println(
                "1. Enter a doctor \n2. Show all doctors \n3. Show a doctor \n4. Show doctor appointments \n0. Return");
        System.out.print("\n-- Enter Choice: ");

        choice = readInt();

        switch (choice) {
            case 1: // enter a doctor

                scanner.nextLine();

                System.out.println("\nDoctor name:");
                String name = scanner.nextLine();

                System.out.println("\nDoctor phone number:");
                String phone = scanner.nextLine();

                System.out.println("\nYears of Experience:");
                int years = readInt();

                Specialty specialty = chooseOption("Specialty", Specialty.values());

                // creating new doctor object and adding it to doctorList
                Doctor doctor = new Doctor(name, phone, specialty, years);

                doctorList.add(doctor);
                System.out.println("\nDoctor added successfully.");

                break;

            case 2: // show all doctors
                showAllDoctors();
                break;

            case 3: // show one doctor
                Doctor d = selectDoctor();
                if (d != null) {
                    // check if the list is empty before trying to print
                    if (d.getExaminations().isEmpty()) {
                        System.out.println("\nNo examinations registered for this doctor.");
                    } else {
                        System.out.println("\n--------------- EXAMINATIONS ---------------\n");
                        d.showExaminations();
                    }
                }
                break;

            case 4: // show doctor appointments
                if (!doctorList.isEmpty()) {
                    for (Doctor d4 : doctorList) {
                        System.out.println(d4 + "\n");
                    }
                } else {
                    System.out.println("\nNo registered doctors.");
                }
                int id4 = scanner.nextInt();
                Doctor d4 = doctorList.get(id4 - 1);
                List<Exam> examinations = d4.getExaminations();
                if (!examinations.isEmpty()) {
                    System.out.println("\n--------------- APPOINTMENTS ---------------\n");
                    for (Exam e : examinations) {
                        if (e.getDoctor() == d4) {
                            System.out.println("\n--------------- EXAM: " + e.getName() + " ---------------\n");
                            e.showAppointments();
                        }
                    }
                } else {
                    System.out.println("\nNo registered examinations.");
                }
                break;
        }
    }

    private void patientMenu() {
        int choice;
        Patient patient = null;

        do {
            System.out.println("\n--------------- PATIENT MENU ---------------");
            System.out.println("1. Add Patient \n2. Show All Patients \n3. Show One Patient \n0. Back");
            System.out.print("\n-- Choice: ");

            choice = readInt();

            switch (choice) {
                case 1:
                    scanner.nextLine();

                    System.out.print("\nName: ");
                    String name = scanner.nextLine();

                    System.out.print("\nPhone: ");
                    String phone = scanner.nextLine();

                    System.out.print("\nEmail: ");
                    String email = scanner.nextLine();

                    patient = new Patient(name, phone, email, new ArrayList<>());
                    patientList.add(patient);

                    System.out.println("\nPatient added successfully.");
                    break;

                case 2:
                    showAllPatients();
                    break;

                case 3:
                    patient = selectPatient();
                    if (patient == null) {
                        return;
                    }

                    System.out.println(patient);

                    patient.showAppointments();

                    break;

                case 0: // Back
                    break;
            }

        } while (choice != 0);
    }

    private void examMenu() {
        int choice;
        Exam exam = null;

        do {
            System.out.println("\n--------------- EXAM MENU ---------------");
            System.out.println("1. Add Exam \n2. Show All Exams \n3. Show One Exam \n0. Back");
            System.out.print("\n-- Choice: ");

            choice = readInt();

            switch (choice) {
                case 1: // Add Exam
                    Doctor doctor = selectDoctor();

                    if (doctor == null) {
                        return;
                    }

                    scanner.nextLine();

                    System.out.print("\nExam name: ");
                    String examName = scanner.nextLine();

                    System.out.print("\nMax slots: ");
                    int maxSlots = readInt();

                    System.out.print("\nCost: ");
                    BigDecimal cost = readMoney();

                    ExamCategory category = chooseOption("Category", ExamCategory.values());

                    switch (category) {
                        case IMAGING:
                            MachineType machine = chooseOption("Machine type", MachineType.values());
                            exam = new ImagingExamination(examName, machine, maxSlots, cost, doctor);
                            break;

                        case MICROBIOLOGICAL:
                            SampleType sample = chooseOption("Sample type", SampleType.values());
                            exam = new MicrobiologicalExamination(examName, sample, maxSlots, cost, doctor);
                            break;

                        case SPECIALIZED:
                            Specialty specialty = chooseOption("Specialty", Specialty.values());
                            exam = new SpecializedExamination(examName, specialty, maxSlots, cost, doctor);
                            break;
                    }

                    examinationList.add(exam);
                    doctor.addExam(exam);

                    System.out.println("\nExam added successfully.");
                    break;

                case 2: // Show All Exams
                    showAllExams();
                    break;

                case 3: // Show One Exam
                    exam = selectExam();
                    if (exam == null) {
                        return;
                    }

                    System.out.println(exam);
                    exam.showAppointments();
                    break;
                case 0: // Back
                    break;
            }

        } while (choice != 0);
    }

    private void appointmentMenu() {
        int choice;
        Patient patient = null;
        Exam exam = null;
        LocalDate date = null;

        do {
            System.out.println("\n--------------- APPOINTMENT MENU ---------------");
            System.out.println(
                    "1. Add Appointment \n2. Show All Appointments \n3. Show Patient Appointments \n4. Delete Appointment \n5. Show Appointments By Date \n0. Back");
            System.out.print("\n-- Choice: ");

            choice = readInt();

            switch (choice) {
                case 1: // Add Appointment
                    patient = selectPatient();

                    if (patient == null) {
                        return;
                    }

                    exam = selectExam();

                    if (exam == null) {
                        return;
                    }

                    scanner.nextLine();

                    boolean dateAndSlotConfirmed = false;

                    while (!dateAndSlotConfirmed) {
                        date = readDate();

                        // slot capacity validation
                        int bookedSlots = 0;

                        for (Appointment a : appointmentList) {
                            if (a.getExamId() == exam.getId() && a.getExamDate().equals(date)) {
                                bookedSlots++;
                            }
                        }

                        // if there are slots
                        if (bookedSlots >= exam.getMaxSlots()) {
                            System.out.println(
                                    "\nNo available slots for this exam on this date. Please choose another date.\n");
                        } else {
                            // date format valid and available slots so book the appointment
                            dateAndSlotConfirmed = true;
                        }
                    }

                    boolean validBoolean = false;
                    boolean fastResults = false;

                    // validity check for fastResults
                    while (!validBoolean) {
                        System.out.print("\nFast results (true/false): ");

                        // read as string
                        String booleanInput = scanner.nextLine().trim();

                        // accepts True TRUE true
                        if (booleanInput.equalsIgnoreCase("true")) {
                            fastResults = true;
                            validBoolean = true;

                            // accepts False FALSE false
                        } else if (booleanInput.equalsIgnoreCase("false")) {
                            fastResults = false;
                            validBoolean = true;
                        } else {
                            // invalid input
                            System.out.println("\nInvalid input. Please type 'true' or 'false'.");
                        }
                    }

                    Appointment appointment = new Appointment(
                            patient.getId(),
                            exam.getId(),
                            fastResults,
                            date);

                    appointmentList.add(appointment);
                    exam.addAppointment(appointment);

                    patient.addAppointment(appointment);

                    System.out.println("\nAppointment created successfully.");
                    break;

                case 2: // Show All Appointments
                    showAllAppointments();
                    break;

                case 3: // Show Patient Appointments
                    patient = selectPatient();

                    if (patient == null) {
                        return;
                    }
                    System.out.println("\n --------------- LIST OF APPOINTMENTS --------------- \n");
                    if (!appointmentList.isEmpty()) {
                        for (Appointment a : appointmentList) {
                            if (a.getPatientId() == patient.getId()) {
                                System.out.println(a);
                                System.out.println("\n ----------------------------------------------- \n");
                            }
                        }
                    } else {
                        System.out.println("\nNo registered appointments.");
                    }
                    break;

                case 4: // Delete Appointment
                    if (appointmentList.isEmpty()) {
                        System.out.println("\nNo appointments found.");
                        break;
                    }

                    showAllAppointments();

                    System.out.print("\nAppointment ID to delete: ");
                    int id = readInt();

                    Appointment target = null;

                    for (Appointment a : appointmentList) {
                        if (a.getId() == id) {
                            target = a;
                            break;
                        }
                    }

                    if (target == null) {
                        System.out.println("\nAppointment not found.");
                        break;
                    }

                    appointmentList.remove(target);

                    System.out.println("\nAppointment deleted.");
                    break;

                case 5: // Show Appointments By Date
                    scanner.nextLine();

                    date = readDate();

                    System.out.println("\n --------------- LIST OF APPOINTMENTS --------------- \n");
                    if (!appointmentList.isEmpty()) {
                        for (Appointment a : appointmentList) {
                            if (a.getExamDate().equals(date)) {
                                System.out.println(a);
                                System.out.println("\n ----------------------------------------------- \n");
                            }
                        }
                    } else {
                        System.out.println("\nNo registered appointments.");
                    }
                    break;
                case 0: // Back
                    break;
            }

        } while (choice != 0);
    }

    private void statisticsMenu() {
        BigDecimal totalRevenue = BigDecimal.ZERO.setScale(2);
        BigDecimal totalRevenueExams = BigDecimal.ZERO.setScale(2);
        BigDecimal totalRevenueCategories = BigDecimal.ZERO.setScale(2);

        System.out.println("\n--------------- STATISTICS MENU ---------------\n");
        System.out.println(
                "1. Total revenue per patient \n2. Total revenue per exam \n3. Total revenue per exam category \n0. Back");
        System.out.print("\n-- Choice: ");
        int choice = readInt();

        switch (choice) {
            case 1: // Total Revenue Per Patient
                System.out.println("\n--------------- TOTAL REVENUE PER PATIENT ---------------\n");
                if (!patientList.isEmpty()) {
                    for (Patient p : patientList) {
                        System.out.println("Patient: " + p.getName() + " (ID: " + p.getId() + ")");

                        BigDecimal patientTotalRevenue = BigDecimal.ZERO.setScale(2);

                        // search master appointment list for patient's appointments
                        for (Appointment a : appointmentList) {
                            if (a.getPatientId() == p.getId()) {

                                System.out.println("\nAppointment info: \n");
                                // print appointment info
                                System.out.println(a);

                                // search master exam list to calculate the cost
                                for (Exam e : examinationList) {
                                    if (e.getId() == a.getExamId()) {

                                        // calculate cost
                                        BigDecimal cost = e.getCost(a.getFastResults());

                                        // print exam cost
                                        System.out.println("\nCost for this exam: " + cost);

                                        // add to patient's total revenue
                                        patientTotalRevenue = patientTotalRevenue.add(cost);

                                        break;
                                    }
                                }
                            }
                            System.out.println("\n ----------------------------------------------- \n");
                        }

                        // print total revenue from this patient
                        System.out.println("\nTotal revenue from this patient: " + patientTotalRevenue);
                        System.out.println("\n--------------------------------------------------");

                        // add the money to the total
                        totalRevenue = totalRevenue.add(patientTotalRevenue);
                    }

                    // print totalRevenue
                    System.out.println("\nTotal revenue from ALL patients: " + totalRevenue);

                } else {
                    System.out.println("\nNo patients registered.");
                }
                break;

            case 2: // Total Revenue Per Exam
                System.out.println("\n--------------- TOTAL REVENUE PER EXAM ---------------\n");
                if (!examinationList.isEmpty()) {
                    for (Exam e : examinationList) {
                        System.out.println("\nExam info: \n");
                        System.out.println(e);

                        BigDecimal examRevenue = BigDecimal.ZERO.setScale(2);

                        // search master appointment's list for the examination
                        for (Appointment a : appointmentList) {
                            if (a.getExamId() == e.getId()) {

                                System.out.println("\nAppointment info: \n");
                                // print appointment info
                                System.out.println(a);

                                // add cost to total revenue for this exam
                                examRevenue = examRevenue.add(e.getCost(a.getFastResults()));
                            }
                        }
                        System.out.println("\nTotal revenue from this examination: " + examRevenue);
                        System.out.println("\n--------------------------------------------------");

                        // add the money to the total
                        totalRevenueExams = totalRevenueExams.add(examRevenue);
                    }

                    // print totalRevenueExams
                    System.out.println("\nTotal revenue from ALL examinations: " + totalRevenueExams);

                } else {
                    System.out.println("\nNo examinations registered.");
                }
                break;
            case 3: // Total Revenue Per Exam Category
                System.out.println("\n--------------- TOTAL REVENUE PER EXAM CATEGORY ---------------\n");
                for (ExamCategory currentCategory : ExamCategory.values()) {
                    System.out.println("\n-- Category: " + currentCategory);
                    BigDecimal categoryRevenue = BigDecimal.ZERO.setScale(2);

                    for (Appointment a : appointmentList) {

                        // find matching exam
                        for (Exam e : examinationList) {
                            if (e.getId() == a.getExamId()) {
                                if (e.getCategory() == currentCategory) {

                                    System.out.println("\nAppointment info: \n");
                                    // print appointment details
                                    System.out.println(a);

                                    // calculate cost and add to total
                                    BigDecimal cost = e.getCost(a.getFastResults());
                                    System.out.println("\nCost for this exam: " + cost);
                                    categoryRevenue = categoryRevenue.add(cost);
                                }
                                break;
                            }
                        }
                    }

                    // print total for the category
                    System.out
                            .println("\nTotal revenue for " + currentCategory + " exams: " + categoryRevenue);
                    System.out.println("\n--------------------------------------------------");

                    // add to total
                    totalRevenueCategories = totalRevenueCategories.add(categoryRevenue);
                }

                // print total
                System.out.println("\nTotal revenue from ALL categories: " + totalRevenueCategories);
                break;

            case 0: // Back
                break;

        }
    }

    // ----------------- select functions -----------------
    private Doctor selectDoctor() {

        if (doctorList.isEmpty()) {
            System.out.println("\nNo doctors available.");
            return null;
        }

        showAllDoctors();

        System.out.print("\nDoctor ID: ");
        int id = readInt();

        for (Doctor d : doctorList) {
            if (d.getId() == id) {
                return d;
            }
        }

        System.out.println("\nDoctor not found.");
        return null;
    }

    private Patient selectPatient() {

        if (patientList.isEmpty()) {
            System.out.println("\nNo patients available.");
            return null;
        }

        showAllPatients();

        System.out.print("\nPatient ID: ");
        int id = readInt();

        for (Patient p : patientList) {
            if (p.getId() == id) {
                return p;
            }
        }

        System.out.println("\nPatient not found.");
        return null;
    }

    private Exam selectExam() {

        if (examinationList.isEmpty()) {
            System.out.println("\nNo exams available.");
            return null;
        }

        showAllExams();

        System.out.print("\nExam ID: ");
        int id = readInt();

        for (Exam e : examinationList) {
            if (e.getId() == id) {
                return e;
            }
        }

        System.out.println("\nExam not found.");
        return null;
    }

    // ----------------- show functions -----------------

    private void showAllDoctors() {
        System.out.println("\n --------------- LIST OF DOCTORS --------------- \n");
        if (doctorList.isEmpty()) {
            System.out.println("\nNo doctors found.");
            return;
        }

        for (Doctor d : doctorList) {
            System.out.println(d);
            System.out.println("\n ----------------------------------------------- \n");
        }
    }

    private void showAllPatients() {
        System.out.println("\n --------------- LIST OF PATIENTS --------------- \n");
        if (patientList.isEmpty()) {
            System.out.println("\nNo patients found.");
            return;
        }

        for (Patient p : patientList) {
            System.out.println(p);
            System.out.println("\n ----------------------------------------------- \n");
        }
    }

    private void showAllExams() {
        System.out.println("\n --------------- LIST OF EXAMS --------------- \n");
        if (examinationList.isEmpty()) {
            System.out.println("\nNo exams found.");
            return;
        }

        examinationList.sort(Comparator.comparing(Exam::getName));

        for (Exam e : examinationList) {
            System.out.println(e);
            System.out.println("\n ----------------------------------------------- \n");
        }
    }

    private void showAllAppointments() {
        System.out.println("\n --------------- LIST OF APPOINTMENTS --------------- \n");
        if (appointmentList.isEmpty()) {
            System.out.println("\nNo appointments found.");
            return;
        }

        for (Appointment a : appointmentList) {
            System.out.println(a);
            System.out.println("\n ----------------------------------------------- \n");
        }
    }

    // ----------------- input functions -----------------

    private int readInt() {

        while (!scanner.hasNextInt()) {
            System.out.println("\nInvalid input.");
            scanner.next();
        }

        return scanner.nextInt();
    }

    private BigDecimal readMoney() {
        while (true) {
            String token = scanner.next();
            BigDecimal amount;
            try {
                amount = new BigDecimal(token);
            } catch (NumberFormatException e) {
                amount = null;
            }

            if (amount != null && amount.signum() >= 0) {
                return amount.setScale(2, RoundingMode.HALF_UP);
            }
            System.out.println("\nInvalid amount. Enter a number like 30 or 29.90.");
        }
    }

    private LocalDate readDate() {
        while (true) {
            System.out.print("\nDate (dd/MM/yyyy): ");
            String text = scanner.nextLine();
            try {
                return Dates.parse(text);
            } catch (DateTimeParseException e) {
                System.out.println("\nInvalid date. Please use dd/MM/yyyy with a real calendar date.");
            }
        }
    }

    private <E extends Enum<E>> E chooseOption(String title, E[] options) {
        System.out.println("\n" + title + ":");
        for (int i = 0; i < options.length; i++) {
            System.out.println((i + 1) + ". " + options[i]);
        }
        System.out.print("\n-- Enter Choice: ");

        while (true) {
            int choice = readInt();
            if (choice >= 1 && choice <= options.length) {
                return options[choice - 1];
            }
            System.out.println("Invalid choice.");
        }
    }

    // ----------------- file management functions -----------------

    private void loadData() {

        // ---------------- Load Doctors ----------------
        File docPath = new File("doctors.txt");
        if (docPath.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(docPath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] tokens = line.split(",");
                    for (int i = 0; i < tokens.length; i++)
                        tokens[i] = tokens[i].trim();

                    int dId = Integer.parseInt(tokens[0]);
                    String name = tokens[1];
                    String phone = tokens[2];
                    int years = Integer.parseInt(tokens[3]);
                    Specialty specialty = Enums.parse(Specialty.values(), tokens[4]);

                    Doctor doctor = new Doctor(dId, name, phone, specialty, years);
                    doctorList.add(doctor);
                }
            } catch (IOException | IllegalArgumentException e) {
                System.out.println("\nError reading doctors.txt");
            }
        } else {
            System.out.println("\nFile does not exist. Creating data ...");
            Doctor d1 = new Doctor("D1", "111", Specialty.CARDIOLOGY, 10);
            Doctor d2 = new Doctor("D2", "222", Specialty.RADIOLOGY, 8);
            Doctor d3 = new Doctor("D3", "333", Specialty.MICROBIOLOGY, 3);

            doctorList.add(d1);
            doctorList.add(d2);
            doctorList.add(d3);
        }

        // ---------------- Load Patients ----------------
        File patFile = new File("patients.txt");
        if (patFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(patFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] tokens = line.split(",");
                    for (int i = 0; i < tokens.length; i++)
                        tokens[i] = tokens[i].trim();

                    int pId = Integer.parseInt(tokens[0]);
                    String name = tokens[1];
                    String phone = tokens[2];
                    String email = tokens[3];

                    Patient patient = new Patient(pId, name, phone, email, new ArrayList<>());
                    patientList.add(patient);
                }
            } catch (IOException | IllegalArgumentException e) {
                System.out.println("\nError reading patients.txt");
            }
        } else {
            System.out.println("\nFile does not exist. Creating data ...");
            Patient p1 = new Patient("P1", "6901", "a@a.com", new ArrayList<>());
            Patient p2 = new Patient("P2", "6902", "b@b.com", new ArrayList<>());
            Patient p3 = new Patient("P3", "6903", "c@c.com", new ArrayList<>());

            patientList.add(p1);
            patientList.add(p2);
            patientList.add(p3);
        }

        // ---------------- Load Exams ----------------
        File examFile = new File("exams.txt");
        if (examFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(examFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] tokens = line.split(",");
                    for (int i = 0; i < tokens.length; i++)
                        tokens[i] = tokens[i].trim();

                    ExamCategory category = Enums.parse(ExamCategory.values(), tokens[0]);
                    int eId = Integer.parseInt(tokens[1]);
                    String examName = tokens[2];
                    int maxSlots = Integer.parseInt(tokens[3]);
                    int docId = Integer.parseInt(tokens[4]);

                    BigDecimal cost = new BigDecimal(tokens[5]);
                    String detail = tokens[6]; // machineType, sampleType, or specialty

                    // find assigned doctor
                    Doctor assignedDoctor = null;
                    for (Doctor d : doctorList) {
                        if (d.getId() == docId) {
                            assignedDoctor = d;
                            break;
                        }
                    }

                    // if doctor exists proceed w exam creation
                    if (assignedDoctor != null) {
                        Exam exam = null;
                        switch (category) {
                            case IMAGING:
                                exam = new ImagingExamination(eId, examName,
                                        Enums.parse(MachineType.values(), detail), maxSlots, cost, assignedDoctor);
                                break;
                            case MICROBIOLOGICAL:
                                exam = new MicrobiologicalExamination(eId, examName,
                                        Enums.parse(SampleType.values(), detail), maxSlots, cost, assignedDoctor);
                                break;
                            case SPECIALIZED:
                                exam = new SpecializedExamination(eId, examName,
                                        Enums.parse(Specialty.values(), detail), maxSlots, cost, assignedDoctor);
                                break;
                        }

                        examinationList.add(exam);
                        assignedDoctor.addExam(exam); // link exam to doctors list inside the obj
                    }
                }
            } catch (IOException | IllegalArgumentException e) {
                System.out.println("\nError reading exams.txt");
            }
        } else {
            System.out.println("\nFile does not exist. Creating data ...");
            Exam ie1 = new ImagingExamination("XRAY", MachineType.X_RAY, 10, BigDecimal.valueOf(30), doctorList.get(0));
            Exam ie2 = new ImagingExamination("XRAY", MachineType.CT, 20, BigDecimal.valueOf(20), doctorList.get(1));
            Exam ie3 = new ImagingExamination("XRAY", MachineType.MRI, 15, BigDecimal.valueOf(34), doctorList.get(2));

            Exam me1 = new MicrobiologicalExamination("PCR", SampleType.BLOOD, 20, BigDecimal.valueOf(20),
                    doctorList.get(0));
            Exam me2 = new MicrobiologicalExamination("PCR", SampleType.URINE, 10, BigDecimal.valueOf(30),
                    doctorList.get(1));
            Exam me3 = new MicrobiologicalExamination("PCR", SampleType.SWAB, 25, BigDecimal.valueOf(15),
                    doctorList.get(2));

            Exam se1 = new SpecializedExamination("Stress", Specialty.CARDIOLOGY, 3, BigDecimal.valueOf(80),
                    doctorList.get(0));
            Exam se2 = new SpecializedExamination("Holter", Specialty.CARDIOLOGY, 5, BigDecimal.valueOf(50),
                    doctorList.get(1));
            Exam se3 = new SpecializedExamination("EEG", Specialty.NEUROLOGY, 10, BigDecimal.valueOf(20),
                    doctorList.get(2));

            examinationList.add(ie1);
            examinationList.add(ie2);
            examinationList.add(ie3);

            examinationList.add(me1);
            examinationList.add(me2);
            examinationList.add(me3);

            examinationList.add(se1);
            examinationList.add(se2);
            examinationList.add(se3);

            // link exams to doctors
            doctorList.get(0).addExam(ie1);
            doctorList.get(0).addExam(me1);
            doctorList.get(0).addExam(se1);

            doctorList.get(1).addExam(ie2);
            doctorList.get(1).addExam(me2);
            doctorList.get(1).addExam(se2);

            doctorList.get(2).addExam(ie3);
            doctorList.get(2).addExam(me3);
            doctorList.get(2).addExam(se3);
        }

        // ---------------- Load Appointments ----------------
        File apptFile = new File("appointments.txt");
        if (apptFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(apptFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] tokens = line.split(",");
                    for (int i = 0; i < tokens.length; i++)
                        tokens[i] = tokens[i].trim();

                    int appId = Integer.parseInt(tokens[0]);
                    int pId = Integer.parseInt(tokens[1]);
                    int eId = Integer.parseInt(tokens[2]);
                    boolean fastResults = Boolean.parseBoolean(tokens[3]);
                    LocalDate date = Dates.parse(tokens[4]);

                    Appointment appointment = new Appointment(appId, pId, eId, fastResults, date);
                    appointmentList.add(appointment);

                    // link appointment to patient's list inside the obj
                    for (Patient p : patientList) {
                        if (p.getId() == pId) {
                            p.addAppointment(appointment);
                            break;
                        }
                    }

                    // link appointment to exam's list inside the obj
                    for (Exam e : examinationList) {
                        if (e.getId() == eId) {
                            e.addAppointment(appointment);
                            break;
                        }
                    }
                }
            } catch (IOException | IllegalArgumentException | DateTimeParseException e) {
                System.out.println("\nError reading appointments.txt");
            }
        } else {
            System.out.println("\nFile does not exist. Creating data ...");
            Appointment a1 = new Appointment(1, 1, true, LocalDate.of(2026, 5, 10));
            Appointment a2 = new Appointment(2, 2, false, LocalDate.of(2026, 5, 16));
            Appointment a3 = new Appointment(3, 3, false, LocalDate.of(2026, 5, 11));

            appointmentList.add(a1);
            appointmentList.add(a2);
            appointmentList.add(a3);

            // link appointment to patient's / exam's list inside the obj
            for (Appointment a : appointmentList) {
                for (Patient p : patientList) {
                    if (p.getId() == a.getPatientId()) {
                        p.addAppointment(a);
                        break;
                    }
                }

                for (Exam e : examinationList) {
                    if (e.getId() == a.getExamId()) {
                        e.addAppointment(a);
                        break;
                    }
                }
            }
        }

        System.out.println("\nData loaded successfully.");
    }

    private void saveData() {

        // ----------------- save doctors -----------------
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("doctors.txt"));
            for (Doctor d : doctorList) {
                String line = d.getId() + "," + d.getName() + "," + d.getPhone() + "," +
                        d.getYears() + "," + d.getSpecialty().name();
                writer.println(line);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("\nError saving doctors.txt");
        }

        // ----------------- save patients -----------------
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("patients.txt"));
            for (Patient p : patientList) {
                String line = p.getId() + "," + p.getName() + "," + p.getPhone() + "," + p.getEmail();
                writer.println(line);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("\nError saving patients.txt");
        }

        // ----------------- save exams -----------------
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("exams.txt"));
            for (Exam e : examinationList) {
                String line = e.getCategory().name() + "," +
                        e.getId() + "," +
                        e.getName() + "," +
                        e.getMaxSlots() + "," +
                        e.getDoctor().getId() + "," +
                        e.getBaseCost().toPlainString() + "," +
                        e.getDetail();

                writer.println(line);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("\nError saving exams.txt");
        }

        // ----------------- save appointments -----------------
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("appointments.txt"));
            for (Appointment a : appointmentList) {
                String line = a.getId() + "," + a.getPatientId() + "," +
                        a.getExamId() + "," + a.getFastResults() + "," + Dates.format(a.getExamDate());
                writer.println(line);
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("\nError saving appointments.txt");
        }

        System.out.println("\nAll data successfully saved to files.");
    }

}
