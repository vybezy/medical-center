import java.math.BigDecimal;

class SpecializedExamination extends Exam {
    private static final BigDecimal chargeRate = new BigDecimal("0.30");

    private Specialty specialty;

    public SpecializedExamination(String examName, Specialty specialty, int maxSlots,
            BigDecimal cost, Doctor doctor) {
        super(examName, maxSlots, cost, doctor);
        this.specialty = specialty;
    }

    // constructor for when loading from file (cause of id)
    public SpecializedExamination(int id, String examName, Specialty specialty, int maxSlots,
            BigDecimal cost, Doctor doctor) {
        super(id, examName, maxSlots, cost, doctor);
        this.specialty = specialty;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    @Override
    public ExamCategory getCategory() {
        return ExamCategory.SPECIALIZED;
    }

    @Override
    public String getDetail() {
        return specialty.name();
    }

    @Override
    protected BigDecimal chargeRate() {
        return chargeRate;
    }

    @Override
    public String toString() {
        return super.toString() + "\nSpecialty: " + specialty;
    }
}
