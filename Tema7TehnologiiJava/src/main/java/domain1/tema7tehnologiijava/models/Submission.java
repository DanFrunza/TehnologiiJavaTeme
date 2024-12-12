package domain1.tema7tehnologiijava.models;

import jakarta.persistence.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Activity activity;

    @ManyToOne
    @JoinColumn
    private MyUser myUser;

    @NotNull
    private SubmissionType submissionType;

    @Max(10)
    @Min(1)
    private int grade;

    private String comment;

    @NotNull
    private Date createdAt;

    @NotNull
    private String registrationNumber;

    public long getId() {
        return id;
    }

    public Activity getActivity() {
        return activity;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    @Max(10)
    @Min(1)
    public int getGrade() {
        return grade;
    }

    public String getComment() {
        return comment;
    }

    public @NotNull SubmissionType getSubmissionType() {
        return submissionType;
    }

    public MyUser getUser() {
        return myUser;
    }

    public @NotNull String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setGrade(@Max(10) @Min(1) int grade) {
        this.grade = grade;
    }

    public void setSubmissionType(@NotNull SubmissionType submissionType) {
        this.submissionType = submissionType;
    }

    public void setUser(MyUser myUser) {
        this.myUser = myUser;
    }

    public void setRegistrationNumber(@NotNull String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    @Override
    public String toString() {
        return "Submission{" +
                "id=" + id +
                ", activity=" + activity +
                ", myUser=" + myUser +
                ", submissionType=" + submissionType +
                ", grade=" + grade +
                ", comment='" + comment + '\'' +
                ", createdAt=" + createdAt +
                ", registrationNumber='" + registrationNumber + '\'' +
                '}';
    }
}
