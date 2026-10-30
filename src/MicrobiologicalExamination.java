import java.math.BigDecimal;

class MicrobiologicalExamination extends Exam {
    private static final BigDecimal chargeRate = new BigDecimal("0.20");

    private SampleType sampleType;

    public MicrobiologicalExamination(String examName, SampleType sampleType, int maxSlots,
            BigDecimal cost, Doctor doctor) {
        super(examName, maxSlots, cost, doctor);
        this.sampleType = sampleType;
    }

    // constructor for when loading from file (because of id)
    public MicrobiologicalExamination(int id, String examName, SampleType sampleType, int maxSlots,
            BigDecimal cost, Doctor doctor) {
        super(id, examName, maxSlots, cost, doctor);
        this.sampleType = sampleType;
    }

    public SampleType getSampleType() {
        return sampleType;
    }

    @Override
    public ExamCategory getCategory() {
        return ExamCategory.MICROBIOLOGICAL;
    }

    @Override
    public String getDetail() {
        return sampleType.name();
    }

    @Override
    protected BigDecimal chargeRate() {
        return chargeRate;
    }

    @Override
    public String toString() {
        return super.toString() + "\nSample Type: " + sampleType;
    }
}
