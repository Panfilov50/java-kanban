package Tasks;
import Manager.*;

public class Subtask extends Task {
    private String whoIsEpic;
    private int idEpic;

    public Subtask(int id, String name, String description, Status status, String whoIsEpic, int idEpic) {
        super(id, name, description, status);
        this.whoIsEpic = whoIsEpic;
        this.idEpic = idEpic;
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
}




