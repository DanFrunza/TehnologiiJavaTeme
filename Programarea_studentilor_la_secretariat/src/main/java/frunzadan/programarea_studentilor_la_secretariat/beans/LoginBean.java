package frunzadan.programarea_studentilor_la_secretariat.beans;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import frunzadan.programarea_studentilor_la_secretariat.models.Admin;
import frunzadan.programarea_studentilor_la_secretariat.models.Student;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.io.Serializable;
import java.time.LocalDate;

@Named("loginBean")
@SessionScoped
public class LoginBean implements Serializable {

    private String username;
    private String password;
    private Object currentUser; // Poate fi de tip Admin sau Student

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

    public Object getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Object currentUser) {
        this.currentUser = currentUser;
    }

    // Metodă de autentificare
    @Transactional
    public String login() {
        // Verificăm întâi dacă utilizatorul este un Admin
        Admin admin = entityManager.createQuery("SELECT a FROM Admin a WHERE a.username = :username", Admin.class)
                .setParameter("username", username)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);

        if (admin != null && verifyPassword(password, admin.getPassword())) {
            currentUser = admin;
            return "/faces/index.xhtml?faces-redirect=true"; // Redirecționare către pagina principală pentru admin
        }

        // Dacă nu este Admin, verificăm dacă este Student
        Student student = entityManager.createQuery("SELECT s FROM Student s WHERE s.username = :username", Student.class)
                .setParameter("username", username)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);

        if (student != null && verifyPassword(password, student.getPassword())) {
            currentUser = student;
            return "/faces/index.xhtml?faces-redirect=true"; // Redirecționare către pagina principală pentru student
        }

        // Dacă nu am găsit utilizatorul, returnăm un mesaj de eroare
        return "login?faces-redirect=true&error=true"; // Redirecționare la login cu un mesaj de eroare
    }

    // Verificarea parolei criptate cu Argon2
    private boolean verifyPassword(String inputPassword, String storedPassword) {
        Argon2 argon2 = Argon2Factory.create();
        return argon2.verify(storedPassword, inputPassword.toCharArray());
    }

    // Metodă de logout
    public String logout() {
        currentUser = null; // Ștergem utilizatorul din sesiune
        return "/faces/login.xhtml?faces-redirect=true"; // Redirecționare la pagina de login
    }

    // Metodă pentru a verifica dacă un Admin este logat
    public boolean isAdminLoggedIn() {
        return currentUser instanceof Admin;
    }

    // Metodă pentru a verifica dacă un Student este logat
    public boolean isStudentLoggedIn() {
        return currentUser instanceof Student;
    }

    // Metodă combinată pentru a verifica dacă oricare dintre utilizatori este logat
    public boolean isLoggedIn() {
        return currentUser != null;
    }

    // Metodă pentru a obține username-ul complet (student/admin)
    public String getUserDetails() {
        if (currentUser instanceof Admin) {
            return username + " (Admin)";
        } else if (currentUser instanceof Student) {
            return username + " (Student)";
        }
        return "";
    }

    public Student getLoggedStudent() {
        if (currentUser instanceof Student) {
            return (Student) currentUser;  // Returnează studentul logat
        }
        return null;  // Dacă nu este un student logat, returnează null
    }
}
