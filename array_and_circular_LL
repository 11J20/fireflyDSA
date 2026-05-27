import java.util.Scanner;

/**
 * Java program to:
 * 1. Store 10 elements in an array
 * 2. Prompt user to either display the array or create a circular linked list from the array and display it
 */
public class ArrayOrCircularList {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] arr = new int[10];

        // Input 10 elements
        System.out.println("Enter 10 integer elements:");
        for (int i = 0; i < 10; i++) {
            System.out.print("Element " + (i + 1) + ": ");
            arr[i] = scanner.nextInt();
        }

        // Prompt user for action
        System.out.println("\nChoose an option:");
        System.out.println("1. Display array elements");
        System.out.println("2. Create and display circular linked list");
        System.out.print("Enter 1 or 2: ");
        int choice = scanner.nextInt();

        if (choice == 1) {
            System.out.println("\nArray elements:");
            for (int i = 0; i < 10; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();
        } else if (choice == 2) {
            // Create circular linked list
            Node head = null, tail = null;
            for (int i = 0; i < 10; i++) {
                Node newNode = new Node(arr[i]);
                if (head == null) {
                    head = newNode;
                    tail = newNode;
                } else {
                    tail.next = newNode;
                    tail = newNode;
                }
            }
            // Make it circular
            if (tail != null) {
                tail.next = head;
            }
            // Display circular linked list (one full cycle)
            System.out.println("\nCircular Linked List elements:");
            Node temp = head;
            int count = 0;
            while (temp != null && count < 10) {
                System.out.print(temp.data + " ");
                temp = temp.next;
                count++;
            }
            System.out.println();
        } else {
            System.out.println("Invalid choice.");
        }
        scanner.close();
    }

    // Node class for circular linked list
    static class Node {
        int data;
        Node next;
        Node(int data) {
            this.data = data;
            this.next = null;
        }
    }
}
