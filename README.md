# Medical Center Management System

A command-line Java application that manages a diagnostic center's doctors, patients, examinations and appointments.
Data is saved to text files on exit and loaded again on start.

Team project (3 students) for *Computer Programming with Java*
Athens University of Economics and Business, spring 2026.

## Features

- Add and list doctors, patients, examinations and appointments
- Three examination types (imaging, microbiological, specialized), each with its own
  surcharge for fast results
- Booking checks: real calendar dates only and a maximum number of appointments per exam and day
- Revenue statistics per patient, per examination and per category
- Sample data is created on the first run, so the program works straight away

## Design

- **Inheritance:** `Person` → `Doctor`, `Patient`. abstract `Exam` → `ImagingExamination`,
  `MicrobiologicalExamination`, `SpecializedExamination`
- **Polymorphism:** `Exam` calculates the cost once; each exam type only supplies its own
  surcharge rate and category (template method)
- **Enums** for categories, specialties, machine and sample types, so invalid values can't exist
- **`BigDecimal`** for money, so totals are exact to the cent
- **`LocalDate`** with strict parsing, so dates like 31/02 are rejected
- Unique IDs across runs: loading a saved record moves each class's ID counter forward

## Run

Requires Java 17 or later.

```
cd src
javac *.java
java Main
```

## Team

Maria Marini, Sabri Osmenaj, and one other student.
