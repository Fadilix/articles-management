public class Categorie {
    private int idCat;
    private String designation;

    public int getIdCat() {
        return idCat;
    }

    public Categorie(int idCat, String designation) {
        this.idCat = idCat;
        this.designation = designation;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

}