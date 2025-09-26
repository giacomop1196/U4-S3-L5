package giacomopillitteri.entities;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "utenti")
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cognome")
    private String cognome;

    @Column(name = "data_di_nascita")
    private LocalDate dataDiNascita;

    @Column(name = "numero_tessera", unique = true)
    // Usiamo il numero di tessera come identificatore logico
    private long numeroTessera;

    @OneToMany(mappedBy = "utente")
    private List<Prestito> listaPrestiti;

    public Utente() {
    }

    public Utente(String nome, String cognome, LocalDate dataDiNascita, long numeroTessera) {
        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;
        this.numeroTessera = numeroTessera;
    }


    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public long getNumeroTessera() {
        return numeroTessera;
    }

    public void setNumeroTessera(long numeroTessera) {
        this.numeroTessera = numeroTessera;
    }

    public List<Prestito> getListaPrestiti() {
        return listaPrestiti;
    }

    public void setListaPrestiti(List<Prestito> listaPrestiti) {
        this.listaPrestiti = listaPrestiti;
    }

    @Override
    public String toString() {
        return "Utente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", numeroTessera=" + numeroTessera +
                '}';
    }
}