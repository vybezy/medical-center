import java.util.ArrayList;
import java.util.List;

class Doctor extends Person {
    private int doctorId;
    private int years;
    private Specialty specialty;
    private List<Exam> examinations;

    private static int idCounter = 0;

    public Doctor(String name, String phone, Specialty specialty, int years) {
        super(name, phone);
        idCounter++;
        this.doctorId = idCounter;
        this.specialty = specialty;
        this.years = years;
        this.examinations = new ArrayList<>();
    }

    // constructor for when loading from file (because of id)
    public Doctor(int id, String name, String phone, Specialty specialty, int years) {
        super(name, phone);
        this.doctorId = id;
        this.specialty = specialty;
        this.years = years;
        this.examinations = new ArrayList<>();

        // so it doesnt assign existing id to doctors
        if (id > idCounter) {
            idCounter = id;
        }
    }

    public int getId() {
        return doctorId;
    }

    public int getYears() {
        return years;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void addExam(Exam exam) {
        examinations.add(exam);
    }

    public List<Exam> getExaminations() {
        return examinations;
    }

    public void showExaminations() {
        for (Exam e : examinations) {
            System.out.println(e.toString());
            System.out.println("\n ----------------------------------------------- \n");
        }
    }

    @Override
    public String toString() {
        return super.toString() + "\n" + "Doctor ID: " + doctorId + "\nSpecialty: " + specialty
                + "\nYears of Experience: " + years;
    }
}
