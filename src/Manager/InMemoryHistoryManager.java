package Manager;

import Tasks.Task;

import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager{ // Такс, дальше идет послденй, не обязательный пункта ТЗ, но решил пока попробовать, не суди строго)


    public static final ArrayList<Task> HistoryList = new ArrayList<>();



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
    public void getHistory(){
        //   System.out.println(HistoryList); // Первый вариант печати истории. Все идет в одну строчку и не призентабельно
        for (Task i : HistoryList){
            System.out.println(i);
        }
    }

}
