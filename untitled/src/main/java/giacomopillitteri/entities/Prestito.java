package giacomopillitteri.entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "prestiti")
public class Prestito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "utente_id")
    private Utente utente;

    @ManyToOne
    @JoinColumn(name = "elemento_prestato_isbn")
    // Usiamo ElementoCatalogo perché può essere sia Libro che Rivista
    private ElementoCatalogo elementoPrestato;

    @Column(name = "data_inizio_prestito")
    private LocalDate dataInizioPrestito;

    @Column(name = "data_restituzione_prevista")
    private LocalDate dataRestituzionePrevista; // Calcolata a 30gg

    @Column(name = "data_restituzione_effettiva", nullable = true)
    private LocalDate dataRestituzioneEffettiva;

    public Prestito() {
    }

    public Prestito(Utente utente, ElementoCatalogo elementoPrestato, LocalDate dataInizioPrestito) {
        this.utente = utente;
        this.elementoPrestato = elementoPrestato;
        this.dataInizioPrestito = dataInizioPrestito;
        // Calcolo automatico della data
        this.dataRestituzionePrevista = dataInizioPrestito.plusDays(30);
    }


    public long getId() {
        return id;
    }

    public Utente getUtente() {
        return utente;
    }

    public void setUtente(Utente utente) {
        this.utente = utente;
    }

    public ElementoCatalogo getElementoPrestato() {
        return elementoPrestato;
    }

    public void setElementoPrestato(ElementoCatalogo elementoPrestato) {
        this.elementoPrestato = elementoPrestato;
    }

    public LocalDate getDataInizioPrestito() {
        return dataInizioPrestito;
    }

    public void setDataInizioPrestito(LocalDate dataInizioPrestito) {
        this.dataInizioPrestito = dataInizioPrestito;
        // Ricalcola la data prevista se la data di inizio viene modificata
        this.dataRestituzionePrevista = dataInizioPrestito.plusDays(30);
    }

    public LocalDate getDataRestituzionePrevista() {
        return dataRestituzionePrevista;
    }

    public void setDataRestituzionePrevista(LocalDate dataRestituzionePrevista) {
        // Normalmente non si setta direttamente, ma lo includiamo per completezza
        this.dataRestituzionePrevista = dataRestituzionePrevista;
    }

    public LocalDate getDataRestituzioneEffettiva() {
        return dataRestituzioneEffettiva;
    }

    public void setDataRestituzioneEffettiva(LocalDate dataRestituzioneEffettiva) {
        this.dataRestituzioneEffettiva = dataRestituzioneEffettiva;
    }

    @Override
    public String toString() {
        String dataEffettiva = dataRestituzioneEffettiva != null ? dataRestituzioneEffettiva.toString() : "Non ancora restituito";
        return "Prestito{" +
                "id=" + id +
                ", utente=" + utente.getNome() + " " + utente.getCognome() +
                ", elementoPrestato=" + elementoPrestato.getTitolo() +
                ", dataInizioPrestito=" + dataInizioPrestito +
                ", dataRestituzionePrevista=" + dataRestituzionePrevista +
                ", dataRestituzioneEffettiva=" + dataEffettiva +
                '}';
    }
}