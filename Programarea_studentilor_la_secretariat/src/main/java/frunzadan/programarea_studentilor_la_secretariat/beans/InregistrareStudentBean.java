package frunzadan.programarea_studentilor_la_secretariat.beans;

import frunzadan.programarea_studentilor_la_secretariat.models.Student;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@Named("inregistrareStudentBean")
@RequestScoped
public class InregistrareStudentBean {

    private String nume;
    private String prenume;
    private String username;
    private String password;
    private String categorie;
    private String specializare;
    private Integer anStudiu;

    @PersistenceContext
    private EntityManager entityManager; // Injectăm EntityManager pentru a salva entitățile

    // Getters și Setters pentru toate câmpurile
    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getSpecializare() {
        return specializare;
    }

    public void setSpecializare(String specializare) {
        this.specializare = specializare;
    }

    public Integer getAnStudiu() {
        return anStudiu;
    }

    public void setAnStudiu(Integer anStudiu) {
        this.anStudiu = anStudiu;
    }

    // Metodă de înregistrare student
    @Transactional
    public String inregistreaza() {
        try {
            // Creăm un obiect Student cu datele introduse
            Student student = new Student();
            student.setNume(nume);
            student.setPrenume(prenume);
            student.setUsername(username);
            student.setCategorie(categorie);
            student.setSpecializare(specializare);
            student.setAnStudiu(anStudiu);

            // Criptăm parola folosind Argon2
            Argon2 argon2 = Argon2Factory.create();
            String hashedPassword = argon2.hash(2, 65536, 1, password.toCharArray());

            // Setăm parola criptată
            student.setPassword(hashedPassword);

            // Salvăm studentul în baza de date
            entityManager.persist(student);

            // Dacă totul a mers bine, întoarcem un mesaj de succes
            return "success"; // Redirect la o altă pagină după înregistrare

        } catch (Exception e) {
            // Logica de tratare a erorilor (optional)
            e.printStackTrace();
            return "error"; // Întoarcem o pagină de eroare dacă salvarea a eșuat
        }
    }
}
