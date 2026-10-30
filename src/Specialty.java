enum Specialty {
    CARDIOLOGY("Cardiology"),
    RADIOLOGY("Radiology"),
    MICROBIOLOGY("Microbiology"),
    NEUROLOGY("Neurology"),
    PULMONOLOGY("Pulmonology");

    private final String label;

    Specialty(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
