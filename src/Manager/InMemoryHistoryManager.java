package Manager;

import Tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class InMemoryHistoryManager implements HistoryManager {
    private final HashMap<Integer, Node<Task>> HistoryMap = new HashMap<>();
    private Node<Task> head;
    private Node<Task> tail;

    @Override
    public void addHistoryList(Task task) {
        if (task == null) {
            return;
        }
        final Node<Task> node = HistoryMap.get(task.getId());
        if (node != null) {
            removeNode(node);
        }
        linkLast(task);
    }

    @Override
    public void removeHistoryTask(int id) {
        removeNode(HistoryMap.get(id));
        HistoryMap.remove(id);
    }
    @Override
    public List<Task> getHistory() {

        return getTasks();
    }

    public void linkLast(Task task) {
        Node<Task> oldTail = tail;
        Node<Task> newNode = new Node<Task>(oldTail, task, null);
        tail = newNode;
        HistoryMap.put(task.getId(), newNode);
        if (oldTail == null) {
            head = newNode;
        } else {
            oldTail.next = newNode;
        }
    }

    private List<Task> getTasks() {
        List<Task> HistoryList = new ArrayList<>();
        Node<Task> node = head;
        while (node != null) {
            HistoryList.add(node.data);
            node = node.next;
        }
        return HistoryList;
    }

    public void removeNode(Node<Task> node) {
        if (!(node == null)) {
            node.data = null;

            if (head == node && tail == node) {
                head = null;
                tail = null;

            } else if (head == node) {
                head = node.next;
                head.prev = null;

            } else if (tail == node) {
                tail = node.prev;
                tail.next = null;

            } else {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }
        }
    }
}










