package domain1.tema7tehnologiijava.beans;


import jakarta.enterprise.context.SessionScoped;
import jakarta.enterprise.inject.Any;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import domain1.tema7tehnologiijava.models.Activity;
import domain1.tema7tehnologiijava.models.Submission;
import jakarta.enterprise.event.Event;
import domain1.tema7tehnologiijava.models.MyUser;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Named
@Logged
@SessionScoped
public class SubmissionBean implements Serializable {
    @PersistenceContext
    private EntityManager em;

    @Any
    @Inject
    private Event<Submission> submissionsEvent;

    @Inject
    @MyQualifier
    private String registrationNumber;

    @Inject
    private AuthBean authBean;

    private Long activityId;

    private Submission submission = new Submission();

    @Transactional
    public String submit() {
        System.out.println(submission);
        submission.setCreatedAt(new Date());
        submission.setRegistrationNumber(registrationNumber);
        submission.setUser(authBean.getCurrentMyUser());
        Activity a = em.createQuery("select a from Activity a WHERE a.id = :ID", Activity.class).setParameter("ID", activityId).getSingleResult();
        submission.setActivity(a);
        System.out.println(submission);
        em.persist(submission);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Evaluation submitted successfully"));
        submissionsEvent.fire(submission);
        return null;
    }

    public Submission getSubmission() {
        return submission;
    }

    public List<Activity> getActivities() {
        return em.createQuery("select a from Activity a", Activity.class).getResultList();
    }

    public void setSubmission(Submission submission) {
        this.submission = submission;
    }

    public List<Submission> getEvaluations() {
        if (authBean.getCurrentMyUser().getUserType() == MyUser.UserType.ADMIN) {
            return em.createQuery("SELECT s from Submission s", Submission.class).getResultList();
        } else if (authBean.getCurrentMyUser().getUserType() == MyUser.UserType.TEACHER) {
            return em.createQuery("SELECT s from Submission s WHERE s.activity.teacher = :TEACHER", Submission.class).setParameter("TEACHER", authBean.getCurrentMyUser()).getResultList();
        } else {
            return em.createQuery("SELECT s FROM Submission s where s.myUser = :USER", Submission.class).setParameter("USER", authBean.getCurrentMyUser()).getResultList();
        }
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

    public Long getActivityId() {
        return activityId;
    }
}