package domain1.tema7tehnologiijava.beans;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import domain1.tema7tehnologiijava.models.MyUser;

import java.io.Serializable;

@Named
@SessionScoped
public class AuthBean implements Serializable {
    private String username;
    private String password;
    private MyUser currentMyUser;

    @PersistenceContext
    private EntityManager em;

    public String login() {
        MyUser myUser = em.createQuery("SELECT u from MyUser u WHERE u.name = :NAME", MyUser.class).setParameter("NAME", username).getSingleResult();
        if (myUser != null && myUser.verifyPassword(password)) {
            currentMyUser = myUser;
            System.out.println("Logged in as " + username);
            return "index.xhtml?faces-redirect=true";
        }
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Invalid credentials"));
        return null;
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        currentMyUser = null;
        return "login.xhtml?faces-redirect=true";
    }

    // Getters and Setters

    public MyUser getCurrentMyUser() {
        return currentMyUser;
    }

    public void setCurrentMyUser(MyUser currentMyUser) {
        this.currentMyUser = currentMyUser;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
