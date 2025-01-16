package frunzadan.programarea_studentilor_la_secretariat.beans;

import frunzadan.programarea_studentilor_la_secretariat.models.Programare;
import frunzadan.programarea_studentilor_la_secretariat.models.StatusProgramare;
import jakarta.enterprise.context.SessionScoped; // Import corect pentru SessionScoped
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable; // Trebuie să implementezi Serializable pentru SessionScoped
import java.time.LocalDate;
import java.util.List;

@Named("adminProgramariBean")
@SessionScoped  // Anotare pentru a marca bean-ul ca fiind de tip SessionScoped
public class AdminProgramariBean implements Serializable {  // Trebuie să implementezi Serializable

    private LocalDate dataSelectata;
    private List<Programare> programari;

    @PersistenceContext
    private EntityManager entityManager;

    @Inject
    private LoginBean loginBean;

    // Getters și Setters
    public LocalDate getDataSelectata() {
        return dataSelectata;
    }

    public void setDataSelectata(LocalDate dataSelectata) {
        this.dataSelectata = dataSelectata;
    }

    public List<Programare> getProgramari() {
        return programari;
    }

    public void setProgramari(List<Programare> programari) {
        this.programari = programari;
    }

    // Metodă pentru a obține programările din ziua selectată
    @Transactional
    public void veziProgramari() {
        if (dataSelectata != null) {
            programari = entityManager.createQuery(
                            "SELECT p FROM Programare p WHERE p.dataProgramare BETWEEN :start AND :end", Programare.class)
                    .setParameter("start", dataSelectata.atStartOfDay())
                    .setParameter("end", dataSelectata.atTime(23, 59, 59))
                    .getResultList();
            System.out.println("Programări găsite: " + programari.size());
        }
    }

    @Transactional
    public void anuleazaProgramare(Programare programare) {
        if (programare != null) {
            System.out.println("Programare primită pentru anulare: " + programare.getId());
            programare.setStatus(StatusProgramare.ANULATA); // Setăm statusul la "ANULATA"
            entityManager.merge(programare);
            veziProgramari();
        } else {
            System.out.println("Obiectul programare este nul.");
        }
    }

    @Transactional
    public void seteazaExpirata(Programare programare) {
        if (programare != null) {
            System.out.println("Programare primită pentru setarea statusului 'Expirată': " + programare.getId());
            programare.setStatus(StatusProgramare.EXPIRATA); // Setăm statusul la "EXPIRATA"
            entityManager.merge(programare);
            veziProgramari(); // Reîncarcă programările
        } else {
            System.out.println("Obiectul programare este nul.");
        }
    }

    @Transactional
    public void activeazaProgramare(Programare programare) {
        if (programare != null) {
            System.out.println("Programare primită pentru setarea statusului 'Activă': " + programare.getId());
            programare.setStatus(StatusProgramare.ACTIVA); // Setăm statusul la "ACTIVA"
            entityManager.merge(programare);
            veziProgramari(); // Reîncarcă programările
        } else {
            System.out.println("Obiectul programare este nul.");
        }
    }
}
