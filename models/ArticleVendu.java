import java.util.Date;

public class ArticleVendu {
    private String libel;
    private double prix;
    private Date dateDeVente;
    private String designationCat;

    public String getLibel() {
        return libel;
    }

    public void setLibel(String libel) {
        this.libel = libel;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public Date getDateDeVente() {
        return dateDeVente;
    }

    public void setDateDeVente(Date dateDeVente) {
        this.dateDeVente = dateDeVente;
    }

    public String getDesignationCat() {
        return designationCat;
    }

    public void setDesignationCat(String designationCat) {
        this.designationCat = designationCat;
    }

}
