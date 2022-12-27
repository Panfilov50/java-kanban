package Tasks;
import Manager.*;

public class Subtask extends Task {
    private String whoIsEpic;
    private int idEpic;

    public Subtask(String name, String description, String whoIsEpic) {
        super(name, description);
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
                "whoIsEpic='" + whoIsEpic + '\'' +
                ", idEpic=" + idEpic +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}




