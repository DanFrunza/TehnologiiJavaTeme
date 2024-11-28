package domain1.tema7tehnologiijava.beans;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import domain1.tema7tehnologiijava.models.MyUser;

@Named
@RequestScoped
public class UserRegistrationBean {
    private MyUser newMyUser = new MyUser();
    private String password;
    private String confirmPassword;

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public String registerUser() {
        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Passwords do not match", null));
            return null;
        }

        // Hash password and set to new user
        newMyUser.setPassword(password);

        // Save user to the database
        try {
            em.persist(newMyUser);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "User registered successfully!", null));
            return null;
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error registering user: " + e, null));
            return null;
        }
    }

    // Getters and Setters
    public MyUser getNewMyUser() {
        return newMyUser;
    }

    public void setNewMyUser(MyUser newMyUser) {
        this.newMyUser = newMyUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
