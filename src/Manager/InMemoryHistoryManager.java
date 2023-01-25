package Manager;

import Tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public class InMemoryHistoryManager implements HistoryManager {
    private final HashMap<Integer, Node<Task>> HistoryMap = new HashMap<>();
    CustomLinkedList customLinkedList = new CustomLinkedList();
  //  private Node<Task> head;
  //  private Node<Task> tail;

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
        Node<Task> oldTail = customLinkedList.getTail();
        Node<Task> newNode = new Node<Task>(oldTail, task, null);
        customLinkedList.setTail(newNode);
        HistoryMap.put(task.getId(), newNode);
        if (oldTail == null) {
            customLinkedList.setHead(newNode);
        } else {
            oldTail.next = newNode;
        }
    }

    private List<Task> getTasks() {
        List<Task> HistoryList = new ArrayList<>();
        Node<Task> node = customLinkedList.getHead();
        while (node != null) {
            HistoryList.add(node.data);
            node = node.next;
        }
        return HistoryList;
    }

    public void removeNode(Node<Task> node) {
        if (!(node == null)) {
            node.data = null;

            if (customLinkedList.getHead() == node && customLinkedList.getTail() == node) {
                customLinkedList.setHead(null);
                customLinkedList.setTail(null);

            } else if (customLinkedList.getHead() == node) {
                customLinkedList.setHead(node.next);
                customLinkedList.getHead().prev = null;

            } else if (customLinkedList.getTail() == node) {
                customLinkedList.setTail(node.prev);
                customLinkedList.getTail().next = null;

            } else {
                node.prev.next = node.next;
                node.next.prev = node.prev;
            }
        }
    }
}










