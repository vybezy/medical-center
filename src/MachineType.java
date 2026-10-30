enum MachineType {
    MRI("MRI"),
    CT("CT"),
    X_RAY("X-Ray");

    private final String label;

    MachineType(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
