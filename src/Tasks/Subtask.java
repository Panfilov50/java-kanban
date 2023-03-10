package Tasks;

import Manager.Status;

import java.time.LocalTime;

public class Subtask extends Task {


    private String whoIsEpic;
    private int idEpic;



    public Subtask(int id, String name, String description, Status status, int idEpic) {
        super(id, name, description, status);
        this.idEpic = idEpic;
    }

    public Subtask(String name, String description, String whoIsEpic) {
        super(name, description);
        this.whoIsEpic = whoIsEpic;
    }

    public Subtask(String name, String description, LocalTime timeStart, long duration, String whoIsEpic) {
        super(name, description, timeStart, duration);
        this.whoIsEpic = whoIsEpic;
    }

    public String getWhoIsEpic() {

        return whoIsEpic;
    }

    public void setWhoIsEpic(String whoIsEpic) {

        this.whoIsEpic = whoIsEpic;
    }

    public int getIdEpic() {

        return idEpic;
    }

    public void setIdEpic(int idEpic) {

        this.idEpic = idEpic;
    }
    @Override
    public String toString() {
        return "Subtask{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", timeStart=" + timeStart +
                ", duration=" + duration +
                ", endTime=" + endTime +
                ", whoIsEpic='" + whoIsEpic + '\'' +
                ", idEpic=" + idEpic +
                '}'+"\n";
    }

}




