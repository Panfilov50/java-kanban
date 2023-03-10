package Tasks;
import Manager.*;

import java.time.Duration;
import java.time.LocalTime;

public class Epic extends Task {

    public Epic( String name, String description ) {

        super(name, description);
    }

    public Epic(int id, String name, String description, Status status) {

        super(id, name, description, status);
    }

    public Epic(String name, String description, LocalTime timeStart, long duration) {
        super(name, description, timeStart, duration);
    }


    @Override
    public String toString() {
        return "Epic{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", timeStart=" + timeStart +
                ", duration=" + duration +
                ", endTime=" + endTime +
                '}'+"\n";
    }
}
