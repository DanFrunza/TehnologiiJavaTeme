package resources.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

import java.util.Date;

@Entity
public class GenericData {
    private String name;

    @Id
    @GeneratedValue
    private String id;

    private Date lastModifiedTimestamp;
    private String lastModifiedUser;

    public Date getLastModifiedTimestamp() {
        return lastModifiedTimestamp;
    }

    public void setLastModifiedTimestamp(Date lastModifiedTimestamp) {
        this.lastModifiedTimestamp = lastModifiedTimestamp;
    }

    public String getLastModifiedUser() {
        return lastModifiedUser;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @PrePersist
    public void prePersist() {
        lastModifiedTimestamp = new Date();
    }
}
