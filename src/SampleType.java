enum SampleType {
    BLOOD("Blood"),
    URINE("Urine"),
    SWAB("Swab");

    private final String label;

    SampleType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
