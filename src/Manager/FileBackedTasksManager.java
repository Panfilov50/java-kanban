package Manager;

import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private Path fileTasks;

    public FileBackedTasksManager(String file) {
        fileTasks = Paths.get(file);
    }
    @Override
    public void addTask(Task task){
        super.addTask(task);
        save();
    }
    @Override
    public void addEpicTusk(Epic epic){
        super.addEpicTusk(epic);
        save();
    }
    @Override
    public void addSubtask(Subtask subtask){
        super.addSubtask(subtask);
        save();
    }
    @Override
    public void changeStatusForNameTask (String nameTask, Status newStatus){
        super.changeStatusForNameTask(nameTask, newStatus);
        save();
    }
    @Override
    public void checkEpicStatus(int id){
        super.checkEpicStatus(id);
    }
    @Override
    public void getAllTask (){
        super.getAllTask();
        save();
    }
    @Override
    public void getTask (){
        super.getTask();
        save();
    }
    @Override
    public void getEpicTask (){
        super.getEpicTask();
        save();
    }
    @Override
    public void removeTask(int id){
        super.removeTask(id);
        save();
    }
    @Override
    public void removeAllTask (){
        super.removeAllTask();
        save();
    }
    @Override
    public Task getTaskById(int id){
        super.getTaskById(id);
        save();
        return super.getTaskById(id);
    }
    @Override
    public Task getEpicTaskById(int id){
        super.getEpicTaskById(id);
        save();
        return super.getEpicTaskById(id);
    }
    @Override
    public Task getSubtaskById(int id){
        super.getSubtaskById(id);
        save();
        return super.getSubtaskById(id);
    }
    @Override
    public List<Task> getHistory(){
        super.getHistory();
        save();
        return super.getHistory();
    }

    public void save() {
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(fileTasks.toFile()))) {
            fileWriter.write("id,type,name,status,description,epic\n");
            for (Task task : taskList.values()) {
                fileWriter.append(toString(task)).write("\n");
            }
            for (Task task : epicTaskList.values()) {
                fileWriter.append(toString(task)).write("\n");
            }
            for (Task task : subtaskList.values()) {
                fileWriter.append(toString(task)).write("\n");
            }

            fileWriter.append("\n");

            fileWriter.write(historyToString(historyManager));
        } catch (IOException e) {
            throw new ManagerSaveException("Файл не найден");
        }
    }

    private String toString(Task task) {
        String type = String.valueOf(TypeTasks.TASK);
        if (task instanceof Epic) {
            type = String.valueOf(TypeTasks.EPIC);
        }
        if (task instanceof Subtask) {
            type = String.valueOf(TypeTasks.SUBTASK);
        }
        String epic = (task instanceof Subtask) ? String.valueOf(((Subtask) task).getIdEpic()) : "";

        return task.getId() + "," + type + "," + task.getName() + "," + task.getStatus() + "," + task.getDescription() + "," + epic;
    }

    public static String historyToString(HistoryManager manager) {
        StringBuilder history = new StringBuilder();
        if (manager != null) {
            for (Task task : manager.getHistory()) {
                history.append(task.getId()).append(",");
            }
        }
        return history.toString();
    }
    public static List<Integer> historyFromString(String value) {
        List<String> valueSplit = Arrays.asList(value.split(","));
        List<Integer> history = new ArrayList<>();
        valueSplit.forEach(s -> history.add(Integer.parseInt(s)));
        return history;
    }

    public static Task fromString(String value) {
        String[] valueSplit = value.split(",");
        int id = Integer.parseInt(valueSplit[0]);
        String name = valueSplit[2];
        Status status;
        if (valueSplit[3].equals(Status.NEW.toString())) {
            status = Status.NEW;
        } else if (valueSplit[3].equals(Status.DONE.toString())) {
            status = Status.DONE;
        } else {
            status = Status.IN_PROGRESS;
        }
        String description = valueSplit[4];
        int epic = (valueSplit.length == 6) ? Integer.parseInt(valueSplit[5]) : -1;
        if (valueSplit[1].equals(TypeTasks.TASK.toString())) {
            return new Task(id, name, description,  status);
        } else if (valueSplit[1].equals(TypeTasks.EPIC.toString())) {
            return new Epic(id, name, description, status);
        } else {
            return new Subtask(id, name, description, status, epic);
        }
    }

    public static FileBackedTasksManager loadFromFile(File file) { //yt обработаны исключения!!!
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            FileBackedTasksManager fileTasksManager =
                    new FileBackedTasksManager("C:\\Users\\68034\\dev\\first-project\\java-kanban-main\\resources\\Test.csv");
            List<String> lines = new ArrayList<>();

            while (br.ready()) {
                lines.add(br.readLine());
            }

            for (int i = 1; i < lines.size() - 2; i++) {
                String[] linesSplit = lines.get(i).split(",");
                if (linesSplit[1].equals(TypeTasks.TASK.toString())) {
                    fileTasksManager.taskList.put(fromString(lines.get(i)).getId(), fromString(lines.get(i)));
                    fileTasksManager.save();
                } else if (linesSplit[1].equals(TypeTasks.EPIC.toString())) {
                    fileTasksManager.epicTaskList.put(fromString(lines.get(i)).getId(), (Epic) fromString(lines.get(i)));
                    fileTasksManager.save();
                } else {
                    fileTasksManager.subtaskList.put(fromString(lines.get(i)).getId(), (Subtask) fromString(lines.get(i)));
                    fileTasksManager.save();
                }

            }

            List<Integer> history = historyFromString(lines.get(lines.size() - 1));
            HashMap<Integer, Task> allTasks = new HashMap<>(fileTasksManager.taskList);
            allTasks.putAll(fileTasksManager.epicTaskList);
            allTasks.putAll(fileTasksManager.subtaskList);

            history.forEach(i -> fileTasksManager.historyManager.addHistoryList(allTasks.get(i)));

            return fileTasksManager;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}

