package frunzadan.programarea_studentilor_la_secretariat.models;

public enum CodAdmin {
    SECRETARIAT("secret2025"),
    DEV("developer2025"),
    SUPER_ADMIN("superadmin2025");

    private final String cod;

    // Constructor care primește un String (codul adminului)
    CodAdmin(String cod) {
        this.cod = cod;
    }

    // Getter pentru a obține codul
    public String getCod() {
        return cod;
    }

    // Metodă statică pentru a verifica dacă un cod există în enum
    public static boolean isCodValid(String codAdmin) {
        for (CodAdmin cod : CodAdmin.values()) {
            if (cod.getCod().equals(codAdmin)) {
                return true;
            }
        }
        return false; // Dacă codul nu există
    }
}
