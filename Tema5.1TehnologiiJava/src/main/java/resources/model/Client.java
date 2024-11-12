package resources.model;

import jakarta.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Client.findAll", query = "select c from Client c")
})
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private Integer startHour;
    private Integer endHour;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getEndHour() {
        return endHour;
    }

    public Integer getStartHour() {
        return startHour;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartHour(Integer startHour) {
        this.startHour = startHour;
    }

    public void setEndHour(Integer endHour) {
        this.endHour = endHour;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startHour=" + startHour +
                ", endHour=" + endHour +
                '}';
    }
}
