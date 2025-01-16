package frunzadan.programarea_studentilor_la_secretariat.beans;

import de.mkammerer.argon2.Argon2Factory;
import frunzadan.programarea_studentilor_la_secretariat.models.Admin;
import frunzadan.programarea_studentilor_la_secretariat.models.CodAdmin;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Named("inregistrareAdminBean")
@RequestScoped
public class InregistrareAdminBean {

    private String username;
    private String password;
    private String nume;
    private String prenume;
    private String codAdmin;  // Codul care va fi introdus de administrator

    @PersistenceContext
    private EntityManager entityManager;

    // Getters și Setters
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

    public String getCodAdmin() {
        return codAdmin;
    }

    public void setCodAdmin(String codAdmin) {
        this.codAdmin = codAdmin;
    }

    // Metodă de înregistrare Admin cu validarea codului
    @Transactional
    public String inregistreaza() {
        try {
            // Validare cod Admin
            if (!CodAdmin.isCodValid(codAdmin)) {
                return "error"; // Codul nu este valid
            }

            // Criptarea parolei cu Argon2
            String hashedPassword = Argon2Factory.create().hash(2, 65536, 1, password.toCharArray());

            // Creăm un obiect Admin cu datele introduse
            Admin admin = new Admin(username, hashedPassword, nume, prenume);

            // Salvăm admin-ul în baza de date
            entityManager.persist(admin);

            // Dacă totul a mers bine, întoarcem un mesaj de succes
            return "success"; // Redirect la o altă pagină după înregistrare
        } catch (Exception e) {
            // Logica de tratare a erorilor
            e.printStackTrace();
            return "error"; // Întoarcem o pagină de eroare dacă salvarea a eșuat
        }
    }
}
