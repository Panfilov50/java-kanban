package Manager;

import Tasks.Epic;
import Tasks.Subtask;
import Tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements Manager {
        public static final HashMap<Integer, Task> taskList = new HashMap<>();
        public static final HashMap<Integer, Task> epicTaskList = new HashMap<>();
        public static final HashMap<Integer, Task> subtaskList = new HashMap<>();
        public static final HashMap<Integer, ArrayList<Integer>> epicSubtaskList = new HashMap<>();


     InMemoryHistoryManager inMemoryHistoryManager = new InMemoryHistoryManager();


        int id = 1;

    @Override
    public void addTask(Task task){
        task.setId(id);
        task.setStatus(Status.valueOf("NEW"));
        taskList.put(id, task);
        System.out.println("Задача : "+ task.getName() +" поставлена, ее ID: "+ id);
        id++;
    }

    @Override
    public void addEpicTusk(Epic epic){
        ArrayList<Integer> subtasks = new ArrayList<>();
        epic.setId(id);
        epic.setStatus(Status.valueOf("NEW"));
        epicSubtaskList.put(id, subtasks);
        epicTaskList.put(id, epic);
        System.out.println("Задача : "+ epic.getName() +" поставлена, ее ID: "+ id);
        id++;

    }
    @Override
    public void addSubtask(Subtask subtask) {
        for (int i : epicTaskList.keySet()) {
            if (epicTaskList.get(i).getName().equals(subtask.getWhoIsEpic()) ) {
                subtask.setId(id);
                subtask.setStatus(Status.valueOf("NEW"));
                epicSubtaskList.get(i).add(id);
                subtaskList.put(id, subtask);
                subtask.setIdEpic(i);
                System.out.println("Задача : " + subtask.getName() + " поставлена, ее ID: " + id);
            }
        }
        id++;
    }

    @Override
    public void changeStatusForNameTask (String nameTask, Status newStatus){
        for (int i : taskList.keySet()) {
                if (taskList.get(i).getName().equals(nameTask)) {
                    taskList.get(i).setStatus(newStatus);
                }
            }
            for (int i : epicTaskList.keySet()) {
                if (epicTaskList.get(i).getName().equals(nameTask)) {
                    epicTaskList.get(i).setStatus(newStatus);
                }
            }
            for (int i : subtaskList.keySet()) {
                if (subtaskList.get(i).getName().equals(nameTask)) {
                    subtaskList.get(i).setStatus(newStatus);
                }
            }
            for (int i : epicTaskList.keySet())
                checkEpicStatus(i);  // Метод проверки статуса эпика, после смены какого-либо таска
        }
    @Override
    public void checkEpicStatus(int id) {
            int count = 0;
            int count2 = 0;
            for (int i : epicSubtaskList.get(id)) {
                if (subtaskList.get(i).getStatus().equals(Status.NEW)) {
                    count++;
                } else if (subtaskList.get(i).getStatus().equals(Status.DONE)) {
                    count2++;
                }
            }
            if (count == epicSubtaskList.get(id).size()) {
                epicTaskList.get(id).setStatus(Status.valueOf("NEW"));
            } else if (count2 == epicSubtaskList.get(id).size()) {
                epicTaskList.get(id).setStatus(Status.valueOf("DONE"));
                System.out.println("Задача: " + epicTaskList.get(id).getName() + " Выполнена!" );
            } else {
                epicTaskList.get(id).setStatus(Status.valueOf("IN_PROGRESS"));
            }
        }
    @Override
    public void getAllTask () {  // Думаю надо ли избавиться от этого метода ?
            System.out.println("На данный момент перед нами стоят следующие задачи: ");
            getTask();
            getEpicTask();
        }
    @Override
    public void getTask () { //Нужно ли добавить чтение истории при вызове просмотра всех тасков?
            for (int i : taskList.keySet()) {
                System.out.println("Задача: " + taskList.get(i).getName() + ". Описание: " + taskList.get(i).getDescription());
            }
        }

    @Override
    public void getEpicTask () {
            for (int i : epicTaskList.keySet()) {
                System.out.println("Большая задача: " + epicTaskList.get(i).getName() + ". Описание: " + epicTaskList.get(i).getDescription());
                System.out.println("В эту задачу входит:");
                for (int j : epicSubtaskList.get(i)) {
                    System.out.println("Маленькая задача: " + subtaskList.get(j).getName() + ". Описание: " + subtaskList.get(j).getDescription());
                }
            }
        }
    @Override
    public void removeTask(int id) {
            taskList.remove(id);
            epicTaskList.remove(id);
            epicSubtaskList.remove(id);
            subtaskList.remove(id);

            for (int i : epicSubtaskList.keySet()) {
                if(epicSubtaskList.get(i).contains(id)) {
                    epicSubtaskList.get(i).remove((Integer)id);

                }
            }
        }
    @Override
    public void removeAllTask() {
            taskList.clear();
            epicTaskList.clear();
            subtaskList.clear();
            epicSubtaskList.clear();
        }
    @Override
    public Task getTaskById(int id){ // Просмотр таска. Я Сделал вывод информации через toString(), а не как выше, так как почему-то мне кажется именно, так и должно быть ¯\_(ツ)_/¯
        inMemoryHistoryManager.addToHistoryList(taskList.get(id)); // Вызов метода добавления в историю.
        System.out.println(taskList.get(id).toString());
        return taskList.get(id);
    }
    @Override
    public Task getEpicTaskById(int id){
        inMemoryHistoryManager.addToHistoryList(epicTaskList.get(id));
        System.out.println(epicTaskList.get(id).toString());
        return  epicTaskList.get(id);

    }
    @Override
    public Task getSubtaskById(int id){
        inMemoryHistoryManager.addToHistoryList(subtaskList.get(id));
        System.out.println(subtaskList.get(id).toString());
        return  subtaskList.get(id);

    }

}

