import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    public  HashMap<Integer, Task> taskList = new HashMap<>();
    public  HashMap<Integer, Task> epicTaskList = new HashMap<>();
    public  HashMap<Integer, Task> subtaskList = new HashMap<>();

    public HashMap<Integer, ArrayList<Integer>> epicSubtaskList = new HashMap<>();



    int id = 1;

    public void addTask(String name,String description ){
        Task task = new Task(id,name, description, Status.valueOf("NEW"));
        taskList.put(id, task);
        System.out.println("Задача : "+ name +" поставлена, ее ID: "+ id);
        id++;
    }
    public void addEpicTusk(String name,String description){
        Epic epic = new Epic(id,name, description, Status.valueOf("NEW"));
        ArrayList<Integer> subtasks = new ArrayList<>();
        epicSubtaskList.put(id, subtasks);
        epicTaskList.put(id, epic);
        System.out.println("Задача : "+ name +" поставлена, ее ID: "+ id);
        id++;
    }
    public void addSubtask(String name, String description, String whoIsEpic){
        for (int i : epicTaskList.keySet()) {
            if (!epicTaskList.get(i).name.equals(whoIsEpic) || epicTaskList.get(i) == null ) {
                System.out.println("Такой задачи еше нет, сперва нужно создать глобальную задачу");
            } else {
                Subtask subtask = new Subtask(id, name, description, Status.valueOf("NEW"), whoIsEpic, i);
                epicSubtaskList.get(i).add(id);
                subtaskList.put(id, subtask);
                System.out.println("Задача : " + name + " поставлена, ее ID: " + id);
            }
        }
        id++;
    }

    public void changeStatusForNameTask (String nameTask, Status newStatus){
        for (int i : taskList.keySet()) {
            if (taskList.get(i).name.equals(nameTask)) {
                taskList.get(i).setStatus(newStatus);
            }
        }
        for (int i : epicTaskList.keySet()) {
            if (epicTaskList.get(i).name.equals(nameTask)) {
                epicTaskList.get(i).setStatus(newStatus);
            }
        }
        for (int i : subtaskList.keySet()) {
            if (subtaskList.get(i).name.equals(nameTask)) {
                subtaskList.get(i).setStatus(newStatus);
            }
        }
        for (int j : epicTaskList.keySet())
        checkEpicStatus(j);  // Метод проверки статуса эпика, после смены какого-либо таска
    }

    public void checkEpicStatus(int j) {
        int count = 0;
        int count2 = 0;
        for (int i : epicSubtaskList.get(j)) {
            if (subtaskList.get(i).status.equals(Status.NEW)) {
                count++;
            } else if (subtaskList.get(i).status.equals(Status.DONE)) {
                count2++;
            }
        }
        if (count == epicSubtaskList.get(j).size()) {
            epicTaskList.get(j).status = Status.valueOf("NEW");
        } else if (count2 == epicSubtaskList.get(j).size()) {
            epicTaskList.get(j).status = Status.valueOf("DONE");
            System.out.println("Задача: " + epicTaskList.get(j).name + " Выполнена!" );
        } else {
            epicTaskList.get(j).status = Status.valueOf("IN_PROGRESS");
        }
    }

    public void getAllTask () {
        System.out.println("На данный момент перед нами стоят следующие задачи: ");
        getTask();     // Методы для вывода задач по отдельности, вроде по ТЗ нужны отдельно списки выводить
        getEpicTask(); // Для сабтаска не стал делать отдельно, ведь он вроде как не должен существовать без эпика
    }
    public void getTask () {
        for (int i : taskList.keySet()) {
            System.out.println("Задача: " + taskList.get(i).name + ". Описание: " + taskList.get(i).description);
        }
    }
    public void getEpicTask () {
        for (int i : epicTaskList.keySet()) {
            System.out.println("Большая задача: " + epicTaskList.get(i).name + ". Описание: " + epicTaskList.get(i).description);
            System.out.println("В эту задачу входит:");
            for (int j : epicSubtaskList.get(i)) {
                System.out.println("Маленькая задача: " + subtaskList.get(j).name + ". Описание: " + subtaskList.get(j).description);
            }
        }
    }

    public void removeTask(int id){ // 2.6 Удаление по идентификатору.
        taskList.remove(id);
        epicTaskList.remove(id);
        subtaskList.remove(id);
    }

    public void removeAllTask () {    //Удаление всех задач. Проверять не стал, ну тут даже дырявому ежу ясно что он не работает =)
        taskList.clear();
        epicTaskList.clear();
        subtaskList.clear();
    }




}










