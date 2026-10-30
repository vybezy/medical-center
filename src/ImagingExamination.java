import java.math.BigDecimal;

class ImagingExamination extends Exam {
    private static final BigDecimal chargeRate = new BigDecimal("0.10");

    private MachineType machineType;

    public ImagingExamination(String examName, MachineType machineType, int maxSlots,
            BigDecimal cost, Doctor doctor) {
        super(examName, maxSlots, cost, doctor);
        this.machineType = machineType;
    }

    // constructor for when loading from file (cause of id)
    public ImagingExamination(int id, String examName, MachineType machineType, int maxSlots,
            BigDecimal cost, Doctor doctor) {
        super(id, examName, maxSlots, cost, doctor);
        this.machineType = machineType;
    }

    public MachineType getMachineType() {
        return machineType;
    }

    @Override
    public ExamCategory getCategory() {
        return ExamCategory.IMAGING;
    }

    @Override
    public String getDetail() {
        return machineType.name();
    }

    @Override
    protected BigDecimal chargeRate() {
        return chargeRate;
    }

    @Override
    public String toString() {
        return super.toString() + "\nMachine Type: " + machineType;
    }
}
