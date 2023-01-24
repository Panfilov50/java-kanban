package Tasks;
import Manager.*;

public class Epic extends Task {
    public Epic( String name, String description ) {
        super(name, description);
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
