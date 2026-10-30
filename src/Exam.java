import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

abstract class Exam {
    private int examId;
    private String examName;
    private int maxSlots;
    private BigDecimal cost;
    private Doctor doctor;
    private List<Appointment> appointments;

    private static int idCounter = 0;

    public Exam(String examName, int maxSlots, BigDecimal cost, Doctor doctor) {
        idCounter++;
        this.examId = idCounter;
        this.examName = examName;
        this.maxSlots = maxSlots;
        this.cost = cost.setScale(2, RoundingMode.HALF_UP);
        this.doctor = doctor;
        this.appointments = new ArrayList<>();
    }

    // constructor for when loading from file (because of id)
    public Exam(int id, String examName, int maxSlots, BigDecimal cost, Doctor doctor) {
        this.examId = id;
        this.examName = examName;
        this.maxSlots = maxSlots;
        this.cost = cost.setScale(2, RoundingMode.HALF_UP);
        this.doctor = doctor;
        this.appointments = new ArrayList<>();

        // so it doesnt assign existing id to exams
        if (id > idCounter) {
            idCounter = id;
        }
    }

    public int getId() {
        return examId;
    }

    public String getName() {
        return examName;
    }

    public abstract ExamCategory getCategory();

    public abstract String getDetail();

    public int getMaxSlots() {
        return maxSlots;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public BigDecimal getBaseCost() {
        return cost;
    }

    protected abstract BigDecimal surchargeRate();

    public BigDecimal getCost(boolean fastResults) {
        if (!fastResults) {
            return cost;
        }
        return cost.multiply(BigDecimal.ONE.add(surchargeRate())).setScale(2, RoundingMode.HALF_UP);
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void showAppointments() {
        if (!appointments.isEmpty()) {
            System.out.println("\n--------------- APPOINTMENTS ---------------\n");
            for (Appointment a : appointments)
                System.out.println(a.toString());
        } else {
            System.out.println("\nNo appointments registered for this exam.");
        }
    }

    @Override
    public String toString() {
        return "Exam ID: " + examId + "\nExam Name: " + examName + "\nCategory: " + getCategory()
                + "\nMax Slots: " + maxSlots + "\nDoctor: " + doctor.getName() + "\nCost: " + cost;
    }

}
