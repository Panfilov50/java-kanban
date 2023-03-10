package Manager;

public class Managers {

    public static Manager getDefault() {

        return new InMemoryTaskManager();
    }

    public static Manager getHttpTaskManager(String url){
        return new HttpTaskManager(url);
    }
    public static HistoryManager getDefaultHistory() {

        return new InMemoryHistoryManager ();
    }

}
