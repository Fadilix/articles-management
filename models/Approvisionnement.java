import java.util.Date;

public class Approvisionnement {
    private String articleAppro;
    private Date date;
    private int quantiteAppro;

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
