public class User {
    private String nomutilisateur;

    public User(String nomutilisateur, String password) {
        this.nomutilisateur = nomutilisateur;
        this.password = password;
    }

    private String password;

    public String getNomutilisateur() {
        return nomutilisateur;
    }

    public void setNomutilisateur(String nomutilisateur) {
        this.nomutilisateur = nomutilisateur;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
