import java.sql.Date;

public class Article {
    private int idArticle;
    private String libel;
    private double prix;
    private int quantiteEnStock;
    private Date dateDeCrea;
    private int quantiteSeuil;
    private Categorie categorie;

    public Article(String libel, double prix, int quantiteEnStock, Date dateDeCrea, int quantiteSeuil,
            Categorie categorie) {
        this.libel = libel;
        this.prix = prix;
        this.quantiteEnStock = quantiteEnStock;
        this.dateDeCrea = dateDeCrea;
        this.quantiteSeuil = quantiteSeuil;
        this.categorie = categorie;
    }

    public int getIdArticle() {
        return idArticle;
    }

    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

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

    public int getQuantiteEnStock() {
        return quantiteEnStock;
    }

    public void setQuantiteEnStock(int quantiteEnStock) {
        this.quantiteEnStock = quantiteEnStock;
    }

    public Date getDateDeCrea() {
        return dateDeCrea;
    }

    public void setDateDeCrea(Date dateDeCrea) {
        this.dateDeCrea = dateDeCrea;
    }

    public int getQuantiteSeuil() {
        return quantiteSeuil;
    }

    public void setQuantiteSeuil(int quantiteSeuil) {
        this.quantiteSeuil = quantiteSeuil;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

}