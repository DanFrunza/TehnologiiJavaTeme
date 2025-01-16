package frunzadan.programarea_studentilor_la_secretariat.beans;

import frunzadan.programarea_studentilor_la_secretariat.models.Programare;
import frunzadan.programarea_studentilor_la_secretariat.models.Student;
import frunzadan.programarea_studentilor_la_secretariat.models.StatusProgramare;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.util.List;

@Named("adminProgramariStudentBean")
@SessionScoped
public class AdminProgramariStudentBean implements Serializable {

    private String numeStudent;
    private String prenumeStudent;
    private List<Programare> programari;

    @PersistenceContext
    private EntityManager entityManager;

    // Getters și Setters
    public String getNumeStudent() {
        return numeStudent;
    }

    public void setNumeStudent(String numeStudent) {
        this.numeStudent = numeStudent;
    }

    public String getPrenumeStudent() {
        return prenumeStudent;
    }

    public void setPrenumeStudent(String prenumeStudent) {
        this.prenumeStudent = prenumeStudent;
    }

    public List<Programare> getProgramari() {
        return programari;
    }

    public void setProgramari(List<Programare> programari) {
        this.programari = programari;
    }

    // Metodă pentru a căuta programările studentului după nume și prenume
    @Transactional
    public void cautaProgramari() {
        if (numeStudent != null && prenumeStudent != null) {
            programari = entityManager.createQuery(
                            "SELECT p FROM Programare p WHERE p.student.nume = :nume AND p.student.prenume = :prenume", Programare.class)
                    .setParameter("nume", numeStudent)
                    .setParameter("prenume", prenumeStudent)
                    .getResultList();
            System.out.println("Programări găsite: " + programari.size());
        }
    }

    @Transactional
    public void anuleazaProgramare(Programare programare) {
        if (programare != null) {
            programare.setStatus(StatusProgramare.ANULATA);
            entityManager.merge(programare);
            cautaProgramari(); // Reîncarcă programările
        }
    }

    @Transactional
    public void seteazaExpirata(Programare programare) {
        if (programare != null) {
            programare.setStatus(StatusProgramare.EXPIRATA);
            entityManager.merge(programare);
            cautaProgramari(); // Reîncarcă programările
        }
    }

    @Transactional
    public void activeazaProgramare(Programare programare) {
        if (programare != null) {
            programare.setStatus(StatusProgramare.ACTIVA);
            entityManager.merge(programare);
            cautaProgramari(); // Reîncarcă programările
        }
    }
}
