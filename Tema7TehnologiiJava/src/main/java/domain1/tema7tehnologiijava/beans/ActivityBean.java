package domain1.tema7tehnologiijava.beans;


import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.annotation.View;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import domain1.tema7tehnologiijava.models.Activity;
import domain1.tema7tehnologiijava.models.MyUser;

import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ActivityBean implements Serializable {
    private Activity newActivity = new Activity();

    @PersistenceContext
    private EntityManager em;

    @Inject
    private AuthBean authBean;

    @Transactional
    public void addActivity() {
        System.out.println("addActivity" + newActivity + " " + newActivity.getTeacher());
        newActivity.setTeacher(authBean.getCurrentMyUser());
        try {
            em.persist(newActivity);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Activity added successfully!", null));
            newActivity = new Activity(); // Reset form
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error adding activity: " + e.toString(), null));
        }
    }

    @Transactional
    public void deleteActivity(Long activityId) {
        try {
            Activity a = em.find(Activity.class, activityId);
            em.remove(a);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Activity deleted successfully!", null));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error deleting activity: " + e.getMessage(), null));
        }
    }

    public List<Activity> getActivities() {
        if (authBean.getCurrentMyUser() == null) return null;
        if (authBean.getCurrentMyUser().getUserType() == MyUser.UserType.ADMIN)
            return em.createQuery("select a from Activity a", Activity.class).getResultList();
        if(authBean.getCurrentMyUser().getUserType() == MyUser.UserType.TEACHER)
            return em.createQuery("select a from Activity a WHERE a.teacher = :TEACHER", Activity.class).setParameter("TEACHER", authBean.getCurrentMyUser()).getResultList();
        return null;
    }

    public List<MyUser> getTeachers() {
        return em.createQuery("select u from MyUser u WHERE u.userType = :TYPE", MyUser.class).setParameter("TYPE", MyUser.UserType.TEACHER).getResultList();
    }

    public Activity getNewActivity() {
        return newActivity;
    }

    public void setNewActivity(Activity newActivity) {
        this.newActivity = newActivity;
    }
}