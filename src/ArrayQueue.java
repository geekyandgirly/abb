import java.util.LinkedList;

/**
 * Implement a queue using int[] with int array size limit to 5. Queue size is not limited.
 */
public class ArrayQueue {
	private static int ARR_SIZE = 5;
	static class Node {
		int[] arr;
		int itemCount;

		Node() {
			arr = new int[ARR_SIZE];
			itemCount = 0;
		}
	}

	private final LinkedList<Node> nodes;
	int head = 0, tail = 0;

	public ArrayQueue() {
		nodes = new LinkedList<>();
	}

	public void enqueue(int i) {
		if (nodes.isEmpty() || nodes.getLast().itemCount == ARR_SIZE) {
			nodes.add(new Node());
			tail = 0;
		}
		Node last = nodes.getLast();
		last.arr[tail++] = i;
		last.itemCount++;
	}

	public int dequeue() throws ArrayQueueException {
		if (nodes.isEmpty() || nodes.getFirst().itemCount == 0) {
			throw new ArrayQueueException("cannot deqeue empty queue");
		}
		Node first = nodes.getFirst();
		int ret = first.arr[head++];
		first.itemCount--;
		if (first.itemCount == 0) {
			nodes.removeFirst();
			head = 0;
		}
		return ret;
	}

	public int peek() throws ArrayQueueException {
		if (nodes.isEmpty() || nodes.getFirst().itemCount == 0) {
			throw new ArrayQueueException("cannot deqeue empty queue");
		}
		Node first = nodes.getFirst();
		return first.arr[head];
	}

	public void printQueue() {
		for (int i = 0; i < nodes.size(); i++) {
			Node node = nodes.get(i);
			int start = (i == 0) ? head : 0;
			int end = (i == nodes.size() - 1) ? tail : node.itemCount;

			for (int j = start; j < end; j++) {
				System.out.print(node.arr[j] + " ");
			}
		}
	}

	static class ArrayQueueException extends Exception {
		ArrayQueueException(String msg) {
			super(msg);
		}
	}

	public static void main(String[] args) throws ArrayQueueException {
		ArrayQueue q = new ArrayQueue();
		for (int i = 0; i < 15; i++) {
			q.enqueue(i);
		}
		System.out.println("********* equeue 0-15 ********");
		q.printQueue();

		System.out.println("********* dequeue 0-5 ********");
		for (int i = 0; i < 5; i++) {
			System.out.println("dequeue expect: " + i + ", actual: " + q.dequeue());
		}
		q.printQueue();

		System.out.println("********* peek ********");
		System.out.println("peek expect: 5, actual: " + q.peek());

		for (int i = 15; i < 20; i++) {
			q.enqueue(i);
		}
		System.out.println("********* enqueue 15-20 ********");
		q.printQueue();

		System.out.println("*********dequeue  5-20 ********");
		for (int i = 5; i < 20; i++) {
			System.out.println("dequeue expect: " + i + ", actual: " + q.dequeue());
		}
		q.printQueue();

		try {
			q.peek();
		} catch(ArrayQueueException e) {
			System.out.println(e.getMessage());
		}

		try {
			q.dequeue();
		} catch(ArrayQueueException e) {
			System.out.println(e.getMessage());
		}
	}
}
