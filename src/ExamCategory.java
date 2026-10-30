enum ExamCategory {
    IMAGING("Imaging"),
    MICROBIOLOGICAL("Microbiological"),
    SPECIALIZED("Specialized");

    private final String label;

    ExamCategory(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
