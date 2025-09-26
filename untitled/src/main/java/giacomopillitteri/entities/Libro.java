package giacomopillitteri.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;

@Entity
@Table(name = "libri")
public class Libro extends ElementoCatalogo {

    @Column(name = "autore")
    private String autore;

    @Column(name = "genere")
    private String genere;

    @OneToMany(mappedBy = "elementoPrestato")
    private List<Prestito> prestiti;

    public Libro() {
        super();
    }

    public Libro(String codiceISBN, String titolo, int annoPubblicazione, int numeroPagine, String autore, String genere) {
        super(codiceISBN, titolo, annoPubblicazione, numeroPagine);
        this.autore = autore;
        this.genere = genere;
    }

    public String getAutore() {
        return autore;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public List<Prestito> getPrestiti() {
        return prestiti;
    }

    public void setPrestiti(List<Prestito> prestiti) {
        this.prestiti = prestiti;
    }

    @Override
    public String toString() {
        return "Libro{" +
                "codiceISBN='" + getCodiceISBN() + '\'' +
                ", titolo='" + getTitolo() + '\'' +
                ", annoPubblicazione=" + getAnnoPubblicazione() +
                ", numeroPagine=" + getNumeroPagine() +
                ", autore='" + autore + '\'' +
                ", genere='" + genere + '\'' +
                '}';
    }
}