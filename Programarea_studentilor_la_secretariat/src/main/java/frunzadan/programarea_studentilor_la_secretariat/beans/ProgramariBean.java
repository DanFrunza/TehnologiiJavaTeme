package frunzadan.programarea_studentilor_la_secretariat.beans;

import frunzadan.programarea_studentilor_la_secretariat.models.Programare;
import frunzadan.programarea_studentilor_la_secretariat.models.StatusProgramare;
import frunzadan.programarea_studentilor_la_secretariat.models.Student;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.PostConstruct;
import java.util.stream.Collectors;

@Named("programariBean")
@SessionScoped
public class ProgramariBean implements Serializable {

    private LocalDate dataSelectata;
    private LocalTime oraSelectata = LocalTime.of(0, 0); // Inițializare la ora 00:00

    private List<Programare> programarileStudentului;

    // Atributele pentru ora și minut
    private int oraSelectataHour;
    private int oraSelectataMinute;

    @Inject
    private LoginBean loginBean;

    @PersistenceContext
    private EntityManager entityManager;

    // Listă pentru orele ocupate
    private List<LocalTime> oreOcupate = new ArrayList<>();
    private List<LocalTime> oreLibere = new ArrayList<>();

    // Getters și Setters
    public LocalDate getDataSelectata() {
        return dataSelectata;
    }

    public void setDataSelectata(LocalDate dataSelectata) {
        this.dataSelectata = dataSelectata;
    }

    public LocalTime getOraSelectata() {
        return oraSelectata;
    }

    public void setOraSelectata(LocalTime oraSelectata) {
        this.oraSelectata = oraSelectata;
    }

    // Metode pentru setarea orei și minutelor separate
    public int getOraSelectataHour() {
        return oraSelectata.getHour();
    }

    public void setOraSelectataHour(int oraSelectataHour) {
        this.oraSelectataHour = oraSelectataHour;
        this.oraSelectata = LocalTime.of(oraSelectataHour, this.oraSelectata.getMinute());
    }

    public int getOraSelectataMinute() {
        return oraSelectata.getMinute();
    }

    public void setOraSelectataMinute(int oraSelectataMinute) {
        this.oraSelectataMinute = oraSelectataMinute;
        this.oraSelectata = LocalTime.of(this.oraSelectata.getHour(), oraSelectataMinute);
    }

    // Metoda care obține orele ocupate și libere pentru ziua selectată
    @Transactional
    public void verificaOre() {
        if (dataSelectata != null) {
            oreOcupate.clear();
            oreLibere.clear();

            // Intervalul de programare (10:00 - 13:45)
            LocalTime start = LocalTime.of(10, 0);
            LocalTime end = LocalTime.of(13, 45);

            // Căutăm programările pentru ziua respectivă
            List<Programare> programari = entityManager.createQuery(
                            "SELECT p FROM Programare p WHERE p.dataProgramare BETWEEN :start AND :end", Programare.class)
                    .setParameter("start", LocalDateTime.of(dataSelectata, start))
                    .setParameter("end", LocalDateTime.of(dataSelectata, end))
                    .getResultList();

            // Adăugăm orele ocupate
            for (Programare programare : programari) {
                LocalTime ora = programare.getDataProgramare().toLocalTime();
                oreOcupate.add(ora);
            }

            // Generăm orele libere pe baza programărilor
            for (LocalTime ora = start; ora.isBefore(end); ora = ora.plusMinutes(15)) {
                if (!oreOcupate.contains(ora)) {
                    oreLibere.add(ora);
                }
            }
        }
    }

    @Transactional
    public String programeaza() {
        Student studentLogat = loginBean.getLoggedStudent();
        if (studentLogat == null) {
            return "/faces/login.xhtml?faces-redirect=true"; // Redirecționare dacă studentul nu este logat
        }

        if (dataSelectata == null || oraSelectata == null) {
            return "/faces/registerStudent.xhtml?faces-redirect=true"; // Redirecționare cu eroare
        }

        // Validare ziua să fie între Luni și Vineri
        if (dataSelectata.getDayOfWeek().getValue() > 5) { // 6 sau 7 înseamnă Sâmbătă sau Duminică
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ziua selectată trebuie să fie între Luni și Vineri", ""));
            return null;
        }

        // Validare ora între 10 și 13:45
        if (oraSelectata.isBefore(LocalTime.of(10, 0)) || oraSelectata.isAfter(LocalTime.of(13, 45))) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ora selectată trebuie să fie între 10:00 și 13:45", ""));
            return null;
        }

        // Validare ca minutele să fie multipli de 15
        if (oraSelectata.getMinute() % 15 != 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Minutele selectate trebuie să fie multipli de 15 (00, 15, 30, 45)", ""));
            return null;
        }

        // Verificare dacă există deja o programare în ziua și la ora selectată
        List<Programare> programariExistente = entityManager.createQuery(
                        "SELECT p FROM Programare p WHERE p.dataProgramare = :dataProgramare", Programare.class)
                .setParameter("dataProgramare", LocalDateTime.of(dataSelectata, oraSelectata))
                .getResultList();

        if (!programariExistente.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Există deja o programare la ora selectată!", ""));
            return null;
        }

        // Creăm o nouă programare
        Programare programare = new Programare();
        programare.setStudent(studentLogat);
        programare.setDataProgramare(LocalDateTime.of(dataSelectata, oraSelectata));

        // Salvăm programarea în baza de date
        entityManager.persist(programare);

        return "/faces/index.xhtml?faces-redirect=true"; // Redirecționare după succes
    }


    // Getters pentru orele ocupate și libere
    public List<LocalTime> getOreOcupate() {
        return oreOcupate;
    }

    public List<LocalTime> getOreLibere() {
        return oreLibere;
    }


    @Transactional
    public void incarcaProgramarileStudentului() {
        Student studentLogat = loginBean.getLoggedStudent();
        if (studentLogat != null) {
            // Obținem lista programărilor studentului
            programarileStudentului = entityManager.createQuery(
                            "SELECT p FROM Programare p WHERE p.student = :student", Programare.class)
                    .setParameter("student", studentLogat)
                    .getResultList();

            // Apelăm metoda care actualizează statusul programărilor
            actualizeazaStatusProgramarilor();
        } else {
            programarileStudentului = new ArrayList<>(); // Dacă nu este logat, lista va fi goală
        }
    }

    @Transactional
    public void actualizeazaStatusProgramarilor() {
        LocalDateTime now = LocalDateTime.now();

        for (Programare programare : programarileStudentului) {
            // Verificăm dacă data programării este mai mică decât data curentă (adică programarea a expirat)
            if (now.isAfter(programare.getDataProgramare())) {
                    programare.setStatus(StatusProgramare.EXPIRATA);
                    entityManager.merge(programare); // Salvează modificările în baza de date
            }
        }
    }

    @Transactional
    public List<Programare> getProgramarileStudentului() {
        Student studentLogat = loginBean.getLoggedStudent(); // Obține studentul logat
        if (studentLogat != null) {
            programarileStudentului = entityManager.createQuery(
                            "SELECT p FROM Programare p WHERE p.student = :student", Programare.class)
                    .setParameter("student", studentLogat)
                    .getResultList();
        } else {
            programarileStudentului = new ArrayList<>(); // Dacă nu este logat, lista va fi goală
        }
        return programarileStudentului;
    }

}
