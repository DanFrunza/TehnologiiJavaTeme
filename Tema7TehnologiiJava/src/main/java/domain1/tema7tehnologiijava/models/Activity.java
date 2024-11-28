package domain1.tema7tehnologiijava.models;

import jakarta.persistence.*;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Activity {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn
    private MyUser teacher;

    @NotNull
    private String name;

    @NotNull
    private Date startDate;

    @NotNull
    private Date endDate;

    public MyUser getTeacher() {
        return teacher;
    }

    public void setTeacher(MyUser teacher) {
        this.teacher = teacher;
    }

    public long getId() {
        return id;
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull Date getEndDate() {
        return endDate;
    }

    public @NotNull Date getStartDate() {
        return startDate;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public void setEndDate(@NotNull Date endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(@NotNull Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id=" + id +
                ", teacher=" + teacher +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}