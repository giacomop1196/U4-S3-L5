package giacomopillitteri.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(long id) {
        super("Il record con id " + id + " non è stato trovato");
    }

    public NotFoundException(String id) {
        super("Il record con ID " + id + " non è stato trovato");
    }
}