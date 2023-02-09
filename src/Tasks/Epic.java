package Tasks;
import Manager.*;

public class Epic extends Task {
    public Epic( String name, String description ) {
        super(name, description);
    }

    public Epic(int id, String name, String description, Status status) {
        super(id, name, description, status);
    }


    @Override
    public String toString() {
        return "Эпик: " +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status;
    }
}
