public class Subtask extends Task {
    String whoIsEpic;
    int idEpic;

    public Subtask(int id, String name, String description, Status status, String whoIsEpic, int idEpic) {
        super(id, name, description, status);
        this.whoIsEpic = whoIsEpic;
        this.idEpic = idEpic;
    }


}




