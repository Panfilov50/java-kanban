package Manager;

import Tasks.Task;


import java.util.LinkedList;

public class InMemoryHistoryManager implements HistoryManager{ // Такс, дальше идет последний, не обязательный пункта ТЗ, но решил пока попробовать, не суди строго)


    public static final LinkedList <Task> HistoryList = new LinkedList<>(); // Почитал про List'ы. Спасибо, буду иметь в виду)



    @Override
    public void addToHistoryList(Task task){ // Метод проверяет меньше ли в листе 10 объектов, и если больше то удаляет 0 индекс и записывает новый объект.
        if (HistoryList.size() <= 10){
            HistoryList.add(task);
        }else {
            HistoryList.remove(0);
            HistoryList.add(task);

        }
    }
    @Override
    public LinkedList<Task> getHistory(){   // Что то я тупанул, по названию метода должно было быть понятно что он должен делать)
        return HistoryList;
    }

}
