package frunzadan.programarea_studentilor_la_secretariat.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Programare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student; // Legătura cu entitatea Student

    @Column(nullable = false)
    private LocalDateTime dataRezervare; // Data când studentul a făcut rezervarea

    @Column(nullable = false)
    private LocalDateTime dataProgramare; // Data efectivă a programării

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusProgramare status; // Statusul programării (ACTIVA, EXPIRATA, ANULATA)

    // Constructor implicit
    public Programare() {
        this.dataRezervare = LocalDateTime.now(); // Setăm data rezervării la momentul curent
        this.status = StatusProgramare.ACTIVA; // Inițial, programarea este activă
    }

    // Constructor cu parametri
    public Programare(Student student, LocalDateTime dataProgramare) {
        this.student = student;
        this.dataProgramare = dataProgramare;
        this.dataRezervare = LocalDateTime.now();
        this.status = StatusProgramare.ACTIVA; // Statusul inițial este activ
    }

    // Getters și Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public LocalDateTime getDataRezervare() {
        return dataRezervare;
    }

    public void setDataRezervare(LocalDateTime dataRezervare) {
        this.dataRezervare = dataRezervare;
    }

    public LocalDateTime getDataProgramare() {
        return dataProgramare;
    }

    public void setDataProgramare(LocalDateTime dataProgramare) {
        this.dataProgramare = dataProgramare;
    }

    public StatusProgramare getStatus() {
        return status;
    }

    public void setStatus(StatusProgramare status) {
        this.status = status;
    }

}

