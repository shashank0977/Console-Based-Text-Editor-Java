import java.util.Scanner;
import java.util.Stack;

public class ConsoleTextEditor {
    private StringBuilder text;
    private int cursorPos;
    private Stack<String> undoStack;
    private Stack<String> redoStack;

    public ConsoleTextEditor() {
        text = new StringBuilder();
        cursorPos = 0;
        undoStack = new Stack<>();
        redoStack = new Stack<>();
        undoStack.push(""); // initial empty state
    }

    private void saveState() {
        undoStack.push(text.toString());
        redoStack.clear();
    }

    public void insertChar(int pos, char c) {
        if (pos < 0 || pos > text.length()) {
            System.out.println("Invalid position! Valid: 1 to " + (text.length() + 1));
            return;
        }
        text.insert(pos, c);
        cursorPos = pos + 1;
        saveState();
        System.out.println("After insert: " + text);
    }

    public void deleteChar(int pos) {
        if (pos < 0 || pos >= text.length()) {
            System.out.println("Invalid position!");
            return;
        }
        text.deleteCharAt(pos);
        cursorPos = pos;
        saveState();
        System.out.println("After delete: " + text);
    }

    public void moveLeft() {
        if (cursorPos == 0) System.out.println("Already at leftmost");
        else cursorPos--;
    }

    public void moveRight() {
        if (cursorPos == text.length()) System.out.println("Already at rightmost");
        else cursorPos++;
    }

    public void undo() {
        if (undoStack.size() <= 1) {
            System.out.println("Nothing to undo");
            return;
        }
        redoStack.push(undoStack.pop());
        text = new StringBuilder(undoStack.peek());
        System.out.println("After undo: " + text);
    }

    public void redo() {
        if (redoStack.isEmpty()) {
            System.out.println("Nothing to redo");
            return;
        }
        String state = redoStack.pop();
        undoStack.push(state);
        text = new StringBuilder(state);
        System.out.println("After redo: " + text);
    }

    public void display() {
        System.out.println("Text: " + text + " | Cursor at: " + (cursorPos + 1));
    }

    public static void main(String[] args) {
        ConsoleTextEditor editor = new ConsoleTextEditor();
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to Console Text Editor");
        System.out.println("1.Menu 2.Insert 3.Delete 4.Left 5.Right 6.Undo 7.Redo 8.Display 9.Exit");

        while (true) {
            System.out.print("\nEnter choice: ");
            int choice = sc.nextInt();
            if (choice == 9) { System.out.println("Closing..."); break; }

            switch (choice) {
                case 1: System.out.println("1.Menu 2.Insert 3.Delete 4.Left 5.Right 6.Undo 7.Redo 8.Display 9.Exit"); break;
                case 2: System.out.print("Position: "); int p = sc.nextInt(); System.out.print("Char: "); char c = sc.next().charAt(0); editor.insertChar(p-1, c); break;
                case 3: System.out.print("Delete Position: "); int d = sc.nextInt(); editor.deleteChar(d-1); break;
                case 4: editor.moveLeft(); break;
                case 5: editor.moveRight(); break;
                case 6: editor.undo(); break;
                case 7: editor.redo(); break;
                case 8: editor.display(); break;
                default: System.out.println("Invalid choice");
            }
        }
        sc.close();
    }
}