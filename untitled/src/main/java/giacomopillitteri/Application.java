package giacomopillitteri;

import giacomopillitteri.dao.CatalogoDAO;
import giacomopillitteri.entities.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.time.LocalDate;
import java.util.List;

public class Application {

    // Riga 16 del tuo file Application.java
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("libreria");

    public static void main(String[] args) {
        EntityManager em = emf.createEntityManager();

        try {
            CatalogoDAO catalogoDAO = new CatalogoDAO(em);


            // Crea e salva Elementi Catalogo
            Libro libro1 = new Libro("978-8804705572", "Harry Potter e la pietra Filosofale", 1997, 500, "J. K. Rowling", "Fantasy");
            Libro libro2 = new Libro("978-8806232758", "Le cronache di Narnia: Il leone, la straga e l'armadio", 1950, 180, "C. S. Lewis", "Fantasy");
            Rivista rivista1 = new Rivista("RIV-MEN-001", "Focus Storia", 2025, 100, Periodicita.MENSILE);
            Rivista rivista2 = new Rivista("RIV-SET-001", "Settimanale dello Sport", 2025, 50, Periodicita.SETTIMANALE);

            catalogoDAO.save(libro1);
            catalogoDAO.save(libro2);
            catalogoDAO.save(rivista1);
            catalogoDAO.save(rivista2);

            // Crea e salva un Utente (per il Prestito)
            Utente utente1 = new Utente("Giacomo", "Pillitteri", LocalDate.of(1996, 7, 11), 12345);
            // Salva l'utente
            em.getTransaction().begin();
            em.persist(utente1);
            em.getTransaction().commit();

            // Crea Prestiti
            // Prestito in corso
            Prestito prestito1 = new Prestito(utente1, libro1, LocalDate.now().minusDays(10)); // Scade tra 20 giorni
            catalogoDAO.savePrestito(prestito1);

            // Prestito Scaduto
            Prestito prestito2 = new Prestito(utente1, rivista1, LocalDate.now().minusDays(50)); // Scadeva 20 giorni fa
            catalogoDAO.savePrestito(prestito2);

            System.out.println("\nOperazioni DAO E Ricerche");

            // RICERCHE

            // Ricerca per ISBN
            ElementoCatalogo foundByISBN = catalogoDAO.findByISBN("978-8804705572");
            System.out.println("Trovato per ISBN: " + foundByISBN.getTitolo());

            // Ricerca per anno pubblicazione
            List<ElementoCatalogo> foundByAnno = catalogoDAO.findByAnnoPubblicazione(2024);
            System.out.println("\nTrovati per Anno 2024: " + foundByAnno.size() + " elementi.");

            // Ricerca per autore
            List<Libro> foundByAutore = catalogoDAO.findByAutore("Hermann");
            System.out.println("\nTrovati per Autore 'Hermann': " + foundByAutore.get(0).getTitolo());

            // Ricerca per titolo
            List<ElementoCatalogo> foundByTitolo = catalogoDAO.findByTitolo("storia");
            System.out.println("\nTrovati per Titolo 'storia': " + foundByTitolo.get(0).getTitolo());

            // Ricerca elementi attualmente in prestito per tessera
            List<ElementoCatalogo> inPrestito = catalogoDAO.findElementiInPrestitoByTessera(12345);
            System.out.println("\nElementi in prestito per tessera 12345: " + inPrestito.size() + " elementi.");

            // Ricerca prestiti scaduti e non restituiti
            List<Prestito> prestitiScaduti = catalogoDAO.findPrestitiScadutiNonRestituiti();
            System.out.println("\nPrestiti Scaduti e Non Restituiti: " + prestitiScaduti.size() + " elementi.");
            prestitiScaduti.forEach(p -> System.out.println("- " + p.getElementoPrestato().getTitolo() + " (Previsto: " + p.getDataRestituzionePrevista() + ")"));

            // Elimina richiesta
            catalogoDAO.findByISBNAndDelete("RIV-SET-001");

        } catch (Exception e) {
            System.err.println("Errore nell'applicazione: " + e.getMessage());
        } finally {
            em.close();
            emf.close();
        }
    }
}