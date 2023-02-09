package Manager;

public class ManagerSaveException extends Error {
    public ManagerSaveException(String message) {
        super(message);
    }

    public String getDetailMessage() {
        return "Ошибка ввода: " + getMessage();
    }
}
