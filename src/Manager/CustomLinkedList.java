package Manager;

import Tasks.Task;

public class CustomLinkedList {

    private Node<Task> head;
    private Node<Task> tail;

    public Node<Task> getHead() {
        return head;
    }

    public void setHead(Node<Task> head) {
        this.head = head;
    }

    public Node<Task> getTail() {
        return tail;
    }

    public void setTail(Node<Task> tail) {
        this.tail = tail;
    }
}
