import java.util.Scanner;
import java.util.Arrays;

public class Circular_LL_to_Arrays {

    // Definition for a Circular Linked List Node
    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] input = new int[6];
        
        // 1. Get user input for exactly 6 numbers
        System.out.println("Please enter 6 integers:");
        for (int i = 0; i < 6; i++) {
            System.out.print("Enter value for element " + (i + 1) + ": ");
            while (!scanner.hasNextInt()) {
                System.out.println("That's not a valid integer. Try again.");
                scanner.next(); // Clear invalid input from scanner buffer
                System.out.print("Enter value for element " + (i + 1) + ": ");
            }
            input[i] = scanner.nextInt();
        }
        
        System.out.println("\nInitial Input Array: " + Arrays.toString(input));
        
        // 2. Create the Circular Linked List
        Node head = createCircularList(input);
        
        // 3. Display the Circular Linked List structure
        displayCircularList(head);
        
        // 4. Convert to 2 different arrays
        int n = input.length;
        int[] valueArray = new int[n];
        int[] nextValueArray = new int[n];
        
        populateArrays(head, valueArray, nextValueArray);
        
        // 5. Display results with clear Array Indexes aligned above values
        printArrayWithIndexes("Value Array (Current Nodes)", valueArray);
        printArrayWithIndexes("Next Value Array (Next Nodes)", nextValueArray);
        
        scanner.close();
    }

    /**
     * Creates a circular linked list from an array of integers
     */
    private static Node createCircularList(int[] arr) {
        if (arr == null || arr.length == 0) return null;

        Node head = new Node(arr[0]);
        Node current = head;

        for (int i = 1; i < arr.length; i++) {
            current.next = new Node(arr[i]);
            current = current.next;
        }
        
        // Connect the last node's next pointer back to the head to make it circular
        current.next = head;
        
        return head;
    }

    /**
     * Displays the nodes of the circular linked list visually
     */
    private static void displayCircularList(Node head) {
        if (head == null) {
            System.out.println("\nThe list is empty.");
            return;
        }

        System.out.println("\n--- Circular Linked List Structure ---");
        Node current = head;
        
        // Traverse and print the list visually
        do {
            System.out.print("[" + current.data + "] -> ");
            current = current.next;
        } while (current != head);
        
        // Explicitly show it wraps back around to the head
        System.out.println("(back to head: [" + head.data + "])");
    }

    /**
     * Traverses the circular list to fill the current values and next values arrays
     */
    private static void populateArrays(Node head, int[] values, int[] nextValues) {
        if (head == null) return;

        Node current = head;
        int index = 0;

        // Use a do-while loop because the head needs to be processed before the 'current != head' check
        do {
            values[index] = current.data;
            nextValues[index] = current.next.data; // Peeks ahead at the next node's value
            
            current = current.next;
            index++;
        } while (current != head);
    }

    /**
     * Neatly outputs an array structure matching index positions with data values
     */
    private static void printArrayWithIndexes(String title, int[] array) {
        System.out.println("\n--- " + title + " ---");
        
        // Print index headers
        System.out.print("Index: ");
        for (int i = 0; i < array.length; i++) {
            System.out.printf("[%d]\t", i);
        }
        System.out.println();
        
        // Print data rows
        System.out.print("Value: ");
        for (int val : array) {
            System.out.printf(" %d\t", val);
        }
        System.out.println();
    }
}
