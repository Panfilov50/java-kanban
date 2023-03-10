package Manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import Server.KVTaskClient;
import Tasks.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

public class HttpTaskManager extends FileBackedTasksManager{

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .registerTypeAdapter(Duration.class, new DurationAdapter())
            .create();
    private KVTaskClient taskClient;

    public HttpTaskManager(String urlKVServer) {
        super(urlKVServer);
        try {
            taskClient = new KVTaskClient(urlKVServer);
        }  catch (InterruptedException | IOException ignored){
            System.out.println("Во время выполнения запроса возникла ошибка. " +
                    "Проверьте, пожалуйста, URL-адрес и повторите попытку.");
        }
    }

    @Override
    public void save() {
        String jsonTasks = gson.toJson(taskList);
        String jsonEpics = gson.toJson(epicTaskList);
        String jsonSubtasks = gson.toJson(subtaskList);
        String jsonHistory = gson.toJson(historyManager.getHistory());

        taskClient.put("tasks", jsonTasks);
        taskClient.put("epics", jsonEpics);
        taskClient.put("subtasks", jsonSubtasks);
        taskClient.put("history", jsonHistory);
    }

    public static HttpTaskManager loadFromServer(String urlKVServer){
        HttpTaskManager taskManager = new HttpTaskManager(urlKVServer);
        KVTaskClient client = taskManager.getTaskClient();
        try {
            String jsonTasks = client.load("tasks");
            String jsonEpics = client.load("epics");
            String jsonSubtasks = client.load("subtasks");
            String jsonHistory = client.load("history");

            Type mapType = new TypeToken<HashMap<Integer, Task>>(){}.getType();
            HashMap<Integer, Task> tasksRecover = gson.fromJson(jsonTasks, mapType);
            for (Integer key : tasksRecover.keySet()) {
                taskManager.taskList.put(key, tasksRecover.get(key));
            }

            mapType = new TypeToken<HashMap<Integer, Epic>>(){}.getType();
            HashMap<Integer, Epic> epicsRecover = gson.fromJson(jsonEpics, mapType);
            for (Integer key : epicsRecover.keySet()) {
                taskManager.epicTaskList.put(key, epicsRecover.get(key));
            }

            mapType = new TypeToken<HashMap<Integer, Subtask>>(){}.getType();
            HashMap<Integer, Subtask> subtasksRecover = gson.fromJson(jsonSubtasks, mapType);
            for (Integer key : subtasksRecover.keySet()) {
                taskManager.subtaskList.put(key, subtasksRecover.get(key));
            }

            Type listType = new TypeToken<List<Task>>(){}.getType();
            List<Task> historyRecover = gson.fromJson(jsonHistory, listType);
            HashMap<Integer, Task> allTasks = new HashMap<>(taskManager.taskList);
            allTasks.putAll(taskManager.epicTaskList);
            allTasks.putAll(taskManager.subtaskList);
            historyRecover.forEach(task -> taskManager.historyManager.addHistoryList(allTasks.get(task.getId())));

        } catch (IOException | InterruptedException e) {
            System.out.println("Во время выполнения запроса возникла ошибка. " +
                    "Проверьте, пожалуйста, URL-адрес и повторите попытку.");
        }
        return taskManager;
    }

    public KVTaskClient getTaskClient() {
        return taskClient;
    }


    static class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
        private DateTimeFormatter formatterWriter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");


        @Override
        public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
            jsonWriter.value(localDateTime.format(formatterWriter));
        }

        @Override
        public LocalDateTime read(JsonReader jsonReader) throws IOException {
            return LocalDateTime.parse(jsonReader.nextString(), formatterWriter);
        }
    }

    static class DurationAdapter extends TypeAdapter<Duration> {
        @Override
        public void write(JsonWriter jsonWriter, Duration duration) throws IOException {
            jsonWriter.value(duration.toMinutes());
        }

        @Override
        public Duration read(JsonReader jsonReader) throws IOException {
            return Duration.ofMinutes(Long.parseLong(jsonReader.nextString()));
        }
    }
}
