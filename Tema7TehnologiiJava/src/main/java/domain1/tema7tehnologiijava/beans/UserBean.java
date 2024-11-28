package domain1.tema7tehnologiijava.beans;

import domain1.tema7tehnologiijava.models.MyUser;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Named
@RequestScoped
public class UserBean {

    @PersistenceContext
    private EntityManager entityManager;

    // Metoda pentru a obține toți utilizatorii din baza de date
    public List<MyUser> getAllUsers() {
        return entityManager.createQuery("SELECT u FROM MyUser u", MyUser.class).getResultList();
    }
}
