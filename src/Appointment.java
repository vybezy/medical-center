import java.time.LocalDate;

public class Appointment {
    private int appointmentId;
    private int patientId;
    private int examId;
    private boolean fastResults;
    private LocalDate examDate;

    private static int idCounter = 0;

    public Appointment(int patientId, int examId, boolean fastResults, LocalDate examDate) {
        idCounter++;
        this.appointmentId = idCounter;
        this.patientId = patientId;
        this.examId = examId;
        this.fastResults = fastResults;
        this.examDate = examDate;
    }

    // constructor for when loading from file (because of id)
    public Appointment(int id, int patientId, int examId, boolean fastResults, LocalDate examDate) {
        this.appointmentId = id;
        this.patientId = patientId;
        this.examId = examId;
        this.fastResults = fastResults;
        this.examDate = examDate;

        // so it doesnt assign existing id to appointments
        if (id > idCounter) {
            idCounter = id;
        }
    }

    public int getId() {
        return appointmentId;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getExamId() {
        return examId;
    }

    public boolean getFastResults() {
        return fastResults;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    @Override
    public String toString() {
        return "Appointment ID: " + appointmentId + "\nPatient ID: " + patientId + "\nExam ID: " + examId
                + "\nFast Results: " + fastResults + "\nExam Date: " + Dates.format(examDate);
    }
}
