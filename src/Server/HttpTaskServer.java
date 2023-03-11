package Server;

import Manager.Manager;
import Manager.Managers;
import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpTaskServer {

    private static final int PORT = 8080;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;
    private final HttpServer httpServer;
    private final Manager manager;
    private final Gson gson;

    public HttpTaskServer() throws IOException, InterruptedException {
        httpServer = HttpServer.create();
        httpServer.bind(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/tasks/", new TasksHandler());
        manager = Managers.getDefault();
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .registerTypeAdapter(Duration.class, new DurationAdapter())
                .create();
    }

    public void start() {
        System.out.println("\nЗапускаем HttpTaskServer на порту " + PORT);
        System.out.println("Открой в браузере http://localhost:" + PORT + "/");
        httpServer.start();
    }
    public void stop(){
        System.out.println("Cервер на порту " + PORT + "остановлен.");
        httpServer.stop(0);
    }

    class TasksHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {

            try {
                switch (exchange.getRequestMethod()) {
                    case "GET":
                        System.out.println("\nОбработка GET-запроса по пути:");
                        handleGET(exchange);
                        break;
                    case "POST":
                        System.out.println("\nОбработка POST-запроса по пути:");
                        handlePOST(exchange);
                        break;
                    case "DELETE":
                        System.out.println("\nОбработка DELETE-запроса по пути:");
                        handleDELETE(exchange);
                        break;
                    default:
                        System.out.println("/tasks/ ждёт GET, POST или DELETE запрос а получил: "
                                + exchange.getRequestMethod());
                        exchange.sendResponseHeaders(405, 0);
                        break;
                }
            } finally {
                exchange.close();
            }
        }

        private void handleGET(HttpExchange exchange) throws IOException {
            String url = exchange.getRequestURI().toString();
            String[] urlSplit = url.split("/");
            String classTask = urlSplit[2];

            if (urlSplit.length < 3) {
                System.out.println("\n/tasks/");
                String response = gson.toJson(manager.getPrioritizedTasks());
                sendText(exchange, response, 200);
                System.out.println("Запрос успешно выполнен.");
                return;
            }

            switch (classTask) {
                case "task":
                    if (hasID(exchange)) {
                        System.out.println("\n/tasks/task/?id=" + getID(exchange));
                        getTask(exchange);
                    } else {
                        System.out.println("\n/tasks/task/");
                        Type mapType = new TypeToken<HashMap<Integer, Task>>(){}.getType();
                        String response = gson.toJson(manager.getTasks(), mapType);
                        sendText(exchange, response, 200);
                        System.out.println("Запрос успешно выполнен.");
                    }
                    break;
                case "epic":
                    if (hasID(exchange)) {
                        System.out.println("\n/tasks/epic/?id=" + getID(exchange));
                        getEpic(exchange);
                    } else {
                        System.out.println("\n/tasks/epic/");
                        Type mapType = new TypeToken<HashMap<Integer, Epic>>(){}.getType();
                        String response = gson.toJson(manager.getEpics(), mapType);
                        sendText(exchange, response, 200);
                        System.out.println("Запрос успешно выполнен.");
                    }
                    break;
                case "subtask":
                    if (hasID(exchange)) {
                        getSubtask(exchange);
                    } else {
                        System.out.println("\n/tasks/subtask/");
                        Type mapType = new TypeToken<HashMap<Integer, Subtask>>(){}.getType();
                        String response = gson.toJson(manager.getSubtasks(), mapType);
                        sendText(exchange, response, 200);
                        System.out.println("Запрос успешно выполнен.");
                    }
                    break;
                case "history":
                    List<Task> history = manager.getHistory();
                    if (history.isEmpty()){
                        System.out.println("Менеджер не имеет истории просмотров.");
                        exchange.sendResponseHeaders(204, 0);
                    }
                    Type listType = new TypeToken<List<Task>>(){}.getType();
                    String response = gson.toJson(manager.getHistory(), listType);
                    sendText(exchange, response, 200);
                    System.out.println("Запрос успешно выполнен.");
                    break;
            }
        }

        private void handlePOST(HttpExchange exchange) throws IOException {
            InputStream inputStream = exchange.getRequestBody();
            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);

            String url = exchange.getRequestURI().toString();
            String[] splitURL = url.split("/");
            String classTask = splitURL[2];
            switch (classTask) {
                case "task":
                    System.out.println("\n/tasks/task/");
                    Task task = gson.fromJson(body, Task.class);
                    manager.addTask(task);
                    sendText(exchange, String.valueOf(task.getId()), 200);

                    break;
                case "epic":
                    System.out.println("\n/tasks/epic/");
                    Epic epic = gson.fromJson(body, Epic.class);
                    manager.addEpicTusk(epic);
                        sendText(exchange, String.valueOf(epic.getId()), 200);

                    break;
                case "subtask":
                    System.out.println("\n/tasks/subtask/");
                    Subtask subtask = gson.fromJson(body, Subtask.class);
                    manager.addSubtask(subtask);
                    sendText(exchange, String.valueOf(subtask.getId()), 200);
                    break;
            }
        }

        private void handleDELETE(HttpExchange exchange) throws IOException {
            String url = exchange.getRequestURI().toString();
            String[] splitURL = url.split("/");
            String classTask = splitURL[2];
            if (splitURL.length == 2) {
                System.out.println("/tasks/");
                manager.removeAllTask();
                sendSuccess(exchange, 200);
                return;
            }

            if (hasID(exchange)) {
                int id = getID(exchange);
                switch (classTask) {
                    case "task":
                        System.out.println("/tasks/task/&id=" + id);
                        manager.removeTask(id);
                        sendSuccess(exchange, 200);
                        break;
                    case "epic":
                        System.out.println("/tasks/epic/&id=" + id);
                        manager.removeTask(id);
                        sendSuccess(exchange, 200);
                        break;
                    case "subtask":
                        System.out.println("/tasks/subtask/&id=" + id);
                        manager.removeTask(id);
                        sendSuccess(exchange, 200);
                        break;
                }
            } else {
                switch (classTask) {
                    case "task":
                        System.out.println("/tasks/task/");
                        manager.getTasks().clear();
                        sendSuccess(exchange, 200);
                        break;
                    case "epic":
                        System.out.println("/tasks/epic/");
                        manager.getEpics().clear();
                        sendSuccess(exchange, 200);
                        break;
                    case "subtask":
                        System.out.println("/tasks/subtask/");
                        manager.getSubtasks().clear();
                        sendSuccess(exchange, 200);
                        break;

                }
            }
        }

        private void sendText(HttpExchange h, String text, int rCode) throws IOException {
            byte[] resp = text.getBytes(UTF_8);
            h.getResponseHeaders().add("Content-Type", "application/json");
            h.sendResponseHeaders(rCode, resp.length);
            h.getResponseBody().write(resp);
        }

        public void sendSuccess(HttpExchange h, int rCode) throws IOException {
            System.out.println("Запрос успешно выполнен.");
            h.getResponseHeaders().add("Content-Type", "application/json");
            h.sendResponseHeaders(rCode, 0);
        }

        private boolean hasID(HttpExchange h) {
            String rawQuery = h.getRequestURI().getRawQuery();
            return rawQuery != null && (rawQuery.contains("id="));
        }

        private int getID(HttpExchange h) {
            String idString = h.getRequestURI().getRawQuery().substring("id=".length());
            return Integer.parseInt(idString);
        }

        private void getTask(HttpExchange exchange) throws IOException {
            int id = getID(exchange);
            if (manager.getTasks().get(id)!=null) {
                String response = gson.toJson(manager.getTaskById(id));
                sendText(exchange, response, 200);
                System.out.println("Запрос успешно выполнен.");
            } else {
                System.out.println("Задачи по указанному id не существует");
                exchange.sendResponseHeaders(404, 0);
            }
        }

        private void getEpic(HttpExchange exchange) throws IOException {
            int id = getID(exchange);
            if (manager.getEpics().get(id)!=null) {
                String response = gson.toJson(manager.getEpicTaskById(id));
                sendText(exchange, response, 200);
                System.out.println("Запрос успешно выполнен.");
            } else {
                System.out.println("Эпика по указанному id не существует");
                exchange.sendResponseHeaders(404, 0);
            }
        }

        private void getSubtask(HttpExchange exchange) throws IOException {
            int id = getID(exchange);
            String url = exchange.getRequestURI().toString();
            String[] urlSplit = url.split("/");

            if (urlSplit.length == 4) {
                System.out.println("\n/tasks/subtask/?id=");
                if (manager.getSubtasks().get(id)!=null) {
                    String response = gson.toJson(manager.getSubtaskById(id));
                    sendText(exchange, response, 200);
                    System.out.println("Запрос успешно выполнен.");
                } else {
                    System.out.println("Подзадачи по указанному id не существует");
                    exchange.sendResponseHeaders(404, 0);
                }
            } else {
                System.out.println("\n/tasks/subtask/epic/?id=");


                if (manager.getEpics().get(id)!=null) {
                    ArrayList<Subtask> sub = new ArrayList<>();
                    for (Integer i : manager.epicSubtaskLists().get(id)) {
                        sub.add((Subtask) manager.getSubtaskById(i));
                    }
                    String response = gson.toJson(sub);
                    sendText(exchange, response, 200);
                    System.out.println("Запрос успешно выполнен.");
                } else {
                    System.out.println("Эпика по указанному id не существует");
                    exchange.sendResponseHeaders(404, 0);
                }
            }
        }
    }


    static class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
        private final DateTimeFormatter formatterWriter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");


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