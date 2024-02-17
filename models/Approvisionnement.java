import java.util.Date;

public class Approvisionnement {
    private String articleAppro;
    private Date date;
    private int quantiteAppro;

    public Approvisionnement(String articleAppro, Date date, int quantiteAppro) {
        this.articleAppro = articleAppro;
        this.date = date;
        this.quantiteAppro = quantiteAppro;
    }

    public Approvisionnement() {

    }

    public String getArticleAppro() {
        return articleAppro;
    }

    public void setArticleAppro(String articleAppro) {
        this.articleAppro = articleAppro;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getQuantiteAppro() {
        return quantiteAppro;
    }

    public void setQuantiteAppro(int quantiteAppro) {
        this.quantiteAppro = quantiteAppro;
    }

}
