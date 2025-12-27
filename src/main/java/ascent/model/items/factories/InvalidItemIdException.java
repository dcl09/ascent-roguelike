package ascent.model.items.factories;

public class InvalidItemIdException extends RuntimeException {

    private final int invalidId;

    public InvalidItemIdException(int id) {
        super("ID de item inválido: " + id);
        this.invalidId = id;
    }

    public int getInvalidId() {
        return invalidId;
    }
}