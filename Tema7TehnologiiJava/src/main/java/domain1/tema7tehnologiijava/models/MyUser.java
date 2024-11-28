package domain1.tema7tehnologiijava.models;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


@Entity
public class MyUser {
    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String passwordHash;

    public enum UserType {
        STUDENT,
        TEACHER,
        ADMIN
    };

    private UserType userType;

    public String getName() {
        return name;
    }

    public long getId() {
        return id;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        Argon2 argon2 = Argon2Factory.create();
        passwordHash = argon2.hash(22, 65536, 1, password.toCharArray());
    }

    public boolean verifyPassword(String password) {
        Argon2 argon2 = Argon2Factory.create();
        return argon2.verify(passwordHash, password.toCharArray());
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
