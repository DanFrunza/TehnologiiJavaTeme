package domain1.tema7tehnologiijava.services.objects;

public class Authentification {
    String user;
    String password;
    public Authentification() {}
    public Authentification(String user, String password) {
        this.user = user;
        this.password = password;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}