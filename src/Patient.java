import java.util.ArrayList;
import java.util.List;

public class Patient extends Person {
    private int patientId;
    private String phone;
    private String email;
    private List<Appointment> appointments;

    private static int idCounter = 0;

    Patient(String name, String phone, String email, List<Appointment> appointments) {
        super(name, phone);
        idCounter++;
        this.patientId = idCounter;
        this.phone = phone;
        this.email = email;
        this.appointments = new ArrayList<>();
    }

    // constructor for when loading from file (because of id)
    Patient(int id, String name, String phone, String email, List<Appointment> appointments) {
        super(name, phone);
        this.patientId = id;
        this.phone = phone;
        this.email = email;
        this.appointments = new ArrayList<>();

        // so it doesnt assign existing id to patients
        if (id > idCounter) {
            idCounter = id;
        }
    }

    public int getId() {
        return patientId;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public void showAppointments() {
        if (!appointments.isEmpty()) {
            System.out.println("\n--------------- APPOINTMENTS ---------------\n");
            for (Appointment a : appointments)
                System.out.println(a.toString() + "\n \n");
        } else
            System.out.println("\nNo appointments registered.");
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + "Patient ID: " + patientId + "\nEmail: " + email;
    }

}
