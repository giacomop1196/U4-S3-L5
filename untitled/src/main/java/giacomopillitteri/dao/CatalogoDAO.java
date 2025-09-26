package giacomopillitteri.dao;

import giacomopillitteri.entities.ElementoCatalogo;
import giacomopillitteri.entities.Libro;
import giacomopillitteri.entities.Prestito;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;
import giacomopillitteri.entities.Utente;

public class CatalogoDAO {

    private final EntityManager entityManager;

    public CatalogoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    // Aggiunta di un elemento del catalogo
    public void save(ElementoCatalogo newElement) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(newElement);
        transaction.commit();
        System.out.println("Elemento catalogo (ISBN: " + newElement.getCodiceISBN() + ") salvato correttamente!");
    }

    // Ricerca per ISBN
    public ElementoCatalogo findByISBN(String isbn) {
        ElementoCatalogo found = entityManager.find(ElementoCatalogo.class, isbn);

        if (found == null) {

            throw new RuntimeException("Elemento catalogo con ISBN " + isbn + " non è stato trovato.");
        }
        return found;
    }

    // Rimozione di un elemento del catalogo dato un codice ISBN
    public void findByISBNAndDelete(String isbn) {
        ElementoCatalogo found = this.findByISBN(isbn);

        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.remove(found);
        transaction.commit();

        System.out.println("Elemento catalogo (ISBN: " + found.getCodiceISBN() + ") rimosso correttamente!");
    }


    // Ricerca per anno pubblicazionE
    public List<ElementoCatalogo> findByAnnoPubblicazione(int anno) {
        TypedQuery<ElementoCatalogo> query = entityManager.createQuery(
                "SELECT e FROM ElementoCatalogo e WHERE e.annoPubblicazione = :anno",
                ElementoCatalogo.class
        );
        query.setParameter("anno", anno);
        return query.getResultList();
    }

    // Ricerca per autore
    public List<Libro> findByAutore(String autore) {
        TypedQuery<Libro> query = entityManager.createQuery(
                "SELECT l FROM Libro l WHERE l.autore LIKE :autore",
                Libro.class
        );
        query.setParameter("autore", "%" + autore + "%");
        return query.getResultList();
    }

    // Ricerca per titolo o parte di esso

    public List<ElementoCatalogo> findByTitolo(String titolo) {
        TypedQuery<ElementoCatalogo> query = entityManager.createQuery(
                "SELECT e FROM ElementoCatalogo e WHERE LOWER(e.titolo) LIKE LOWER(:titolo)",
                ElementoCatalogo.class
        );
        query.setParameter("titolo", "%" + titolo + "%");
        return query.getResultList();
    }

    // Ricerca degli elementi attualmente in prestito con il numero di tessera utente

    public List<ElementoCatalogo> findElementiInPrestitoByTessera(long numeroTessera) {
        TypedQuery<ElementoCatalogo> query = entityManager.createQuery(
                "SELECT p.elementoPrestato FROM Prestito p " +
                        "WHERE p.utente.numeroTessera = :tessera AND p.dataRestituzioneEffettiva IS NULL",
                ElementoCatalogo.class
        );
        query.setParameter("tessera", numeroTessera);
        return query.getResultList();
    }

    // Ricerca di tutti i prestiti scaduti
    public List<Prestito> findPrestitiScadutiNonRestituiti() {
        TypedQuery<Prestito> query = entityManager.createQuery(
                "SELECT p FROM Prestito p " +
                        "WHERE p.dataRestituzioneEffettiva IS NULL AND p.dataRestituzionePrevista < :oggi",
                Prestito.class
        );
        query.setParameter("oggi", LocalDate.now());
        return query.getResultList();
    }

    public void savePrestito(Prestito newPrestito) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(newPrestito);
        transaction.commit();
        System.out.println("Prestito (ID: " + newPrestito.getId() + ") salvato correttamente!");
    }

    // Metodo per trovare l'Utente per numero di tessera
    public Utente findUtenteByTessera(long numeroTessera) {
        try {
            TypedQuery<Utente> query = entityManager.createQuery(
                    "SELECT u FROM Utente u WHERE u.numeroTessera = :tessera",
                    Utente.class
            );
            query.setParameter("tessera", numeroTessera);
            return query.getSingleResult();
        } catch (NoResultException e) {
            throw new RuntimeException("Utente con tessera " + numeroTessera + " non trovato.");
        }
    }
}