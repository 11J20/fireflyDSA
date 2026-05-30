import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PortScannerBTree {

    // --- B-TREE CONFIGURATION ---
    // T is the minimum degree of the B-Tree. 
    // Max keys per node = 2*T - 1. Max children = 2*T.
    private static final int T = 3; 

    // --- B-TREE NODE STRUCTURE ---
    static class BTreeNode {
        int[] keys = new int[2 * T - 1];  // Array of keys
        BTreeNode[] children = new BTreeNode[2 * T]; // Array of child pointers
        int n = 0; // Current number of keys
        boolean isLeaf = true; // True if node is a leaf
    }

    private BTreeNode root = new BTreeNode();

    // --- B-TREE INSERTION LOGIC ---
    public void insert(int key) {
        BTreeNode r = root;
        // If root is full, the tree grows in height
        if (r.n == 2 * T - 1) {
            BTreeNode s = new BTreeNode();
            root = s;
            s.isLeaf = false;
            s.children[0] = r;
            splitChild(s, 0, r);
            insertNonFull(s, key);
        } else {
            insertNonFull(r, key);
        }
    }

    private void insertNonFull(BTreeNode node, int key) {
        int i = node.n - 1;
        if (node.isLeaf) {
            // Find the location to insert the new key and move greater keys ahead
            while (i >= 0 && node.keys[i] > key) {
                node.keys[i + 1] = node.keys[i];
                i--;
            }
            node.keys[i + 1] = key;
            node.n++;
        } else {
            // Find the child that will have the new key
            while (i >= 0 && node.keys[i] > key) {
                i--;
            }
            i++;
            BTreeNode child = node.children[i];
            if (child.n == 2 * T - 1) {
                // If the child is full, split it
                splitChild(node, i, child);
                if (node.keys[i] < key) {
                    i++;
                }
            }
            insertNonFull(node.children[i], key);
        }
    }

    private void splitChild(BTreeNode parent, int i, BTreeNode fullChild) {
        BTreeNode nextChild = new BTreeNode();
        nextChild.isLeaf = fullChild.isLeaf;
        nextChild.n = T - 1;

        // Copy the last (T-1) keys of fullChild to nextChild
        for (int j = 0; j < T - 1; j++) {
            nextChild.keys[j] = fullChild.keys[j + T];
        }

        // Copy the last T children of fullChild to nextChild
        if (!fullChild.isLeaf) {
            for (int j = 0; j < T; j++) {
                nextChild.children[j] = fullChild.children[j + T];
            }
        }

        fullChild.n = T - 1;

        // Link the new child to the parent
        for (int j = parent.n; j >= i + 1; j--) {
            parent.children[j + 1] = parent.children[j];
        }
        parent.children[i + 1] = nextChild;

        // Move a key of fullChild up to the parent
        for (int j = parent.n - 1; j >= i; j--) {
            parent.keys[j + 1] = parent.keys[j];
        }
        parent.keys[i] = fullChild.keys[T - 1];
        parent.n++;
    }

    // --- ASCII DISPLAY LOGIC ---
    public void display() {
        System.out.println("\n--- B-Tree Structure (ASCII Diagram) ---");
        printNode(root, "", true);
    }

    private void printNode(BTreeNode node, String indent, boolean isLast) {
        if (node == null || node.n == 0) return;

        System.out.print(indent);
        if (isLast) {
            System.out.print("└── ");
            indent += "    ";
        } else {
            System.out.print("├── ");
            indent += "│   ";
        }

        // Build string representation of keys inside this node
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < node.n; i++) {
            sb.append(node.keys[i]);
            if (i < node.n - 1) sb.append(", ");
        }
        sb.append("]");
        System.out.println(sb.toString());

        // Recurse for children if not a leaf
        if (!node.isLeaf) {
            for (int i = 0; i <= node.n; i++) {
                printNode(node.children[i], indent, i == node.n);
            }
        }
    }

    // --- PORT SCANNING LAYER ---
    public static List<Integer> scanPorts(String host, int startPort, int endPort, int timeoutMs) {
        List<Integer> openPorts = new ArrayList<>();
        System.out.println("Scanning " + host + " from port " + startPort + " to " + endPort + "...");
        
        for (int port = startPort; port <= endPort; port++) {
            try (Socket socket = new Socket()) {
                // Attempt to connect to the port
                socket.connect(new InetSocketAddress(host, port), timeoutMs);
                openPorts.add(port);
                System.out.println(" -> Found Open Port: " + port);
            } catch (IOException e) {
                // Port is closed or filtered; ignore and proceed
            }
        }
        return openPorts;
    }

    // --- MAIN METHOD ---
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PortScannerBTree tree = new PortScannerBTree();

        System.out.println("=== Java Port Scanner & B-Tree Storage ===");
        System.out.print("Enter start port (e.g., 1): ");
        int start = scanner.nextInt();
        System.out.print("Enter end port (e.g., 1024): ");
        int end = scanner.nextInt();
        System.out.print("Enter timeout per port in milliseconds (e.g., 200): ");
        int timeout = scanner.nextInt();

        // 1. Scan ports on localhost
        List<Integer> openPorts = scanPorts("127.0.0.1", start, end, timeout);

        System.out.println("\nScan complete. Total open ports found: " + openPorts.size());

        // 2. Populate B-Tree
        for (int port : openPorts) {
            tree.insert(port);
        }

        // For presentation/testing if no ports are actually open on the machine
        if (openPorts.isEmpty()) {
            System.out.println("\n[Note] No active local ports found in that range.");
            System.out.println("Populating B-Tree with synthetic open ports for visual demonstration...");
            int[] demoPorts = {80, 443, 21, 22, 23, 25, 53, 110, 143, 3306, 8080, 9000, 5432};
            for (int port : demoPorts) {
                tree.insert(port);
            }
        }

        // 3. Render ASCII Visualization
        tree.display();
        scanner.close();
    }
}
