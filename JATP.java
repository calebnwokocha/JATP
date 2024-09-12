import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class JATP {
    private final Scanner scanner = new Scanner(System.in);
    private final HashMap<String, String> memory = new HashMap<>();
    private final HashMap<String, String> reverseMemory = new HashMap<>();

    public JATP() {
        System.out.println("JAVA AUTOMATED THEOREM PROVER (JATP)");
        System.out.println("Copyright: Caleb Princewill Nwokocha");
        System.out.println();
        System.out.println("Theorem: Every equation has a left string and a right string.");
        System.out.println("Example 1: a is left string of a=b and b is its right string.");
        System.out.println("Example 2: a is left string of a=a and a is its right string.");
        System.out.println("Example 3: a+b is left string of a+b=b+a and b+a is its right string.");
        System.out.println("Example 4: a+b is left string of a+b=a+b and a+b is its right string.");
        System.out.println();
        this.load();
    }

    public void input(String leftString, String rightString) {
        if (this.memory.containsKey(leftString) &&
                this.memory.get(leftString).equals(rightString)) {
            output(leftString);
        } else {
            prove(leftString, rightString);
        }
    }

    private void output(String leftString) {
        System.out.println("theorem found: " + leftString + "=" + this.memory.get(leftString)
                + "=" + this.reverseMemory.get(leftString)
                + "=" + this.memory.get(this.memory.get(leftString))
                + "=" + this.reverseMemory.get(this.reverseMemory.get(leftString))
                + "=" + this.memory.get(this.reverseMemory.get(leftString))
                + "=" + this.reverseMemory.get(this.memory.get(leftString)));
        System.out.println("memory map: " + this.memory);
        System.out.println("reverse memory map: " + this.reverseMemory);
    }

    private void prove(String leftString, String rightString) {
        if (this.memory.containsKey(leftString)) {
            System.out.println("contradiction found: " + leftString + "≠" + rightString);
            System.out.println("equation found: " + leftString + "=" + this.memory.get(leftString));
            System.out.print("redefinition: " + leftString + "=");
            rightString = this.scanner.nextLine();
            if (!this.reverseMemory.get(rightString).equals(leftString)){
                this.memory.replace(leftString, rightString);
                this.reverseMemory.replace(rightString, leftString);
                this.save(leftString, rightString);
            }
        } else {
            this.memory.put(leftString, rightString);
            this.reverseMemory.put(rightString, leftString);
            this.save(leftString, rightString);
        }
    }

    private void save(String leftString, String rightString) {
        try (DataOutputStream dos = new DataOutputStream(new
                FileOutputStream("memory.jatp", true))) {
            dos.writeUTF(leftString.toString());
            dos.writeUTF(rightString.toString());
            System.out.println("saved " + leftString + "=" + rightString);
        } catch (IOException e) {
            System.out.println("error saving " + leftString + "=" + rightString + "; " + e.getMessage());
        }
    }

    private void load() {
        this.memory.clear();
        this.reverseMemory.clear();

        try (DataInputStream dis = new DataInputStream(new FileInputStream("memory.jatp"))) {
            while (true) {
                try {
                    String leftString = dis.readUTF();
                    String rightString = dis.readUTF();
                    this.memory.put(leftString, rightString);
                    this.reverseMemory.put(rightString, leftString);
                } catch (EOFException e) {
                    break;
                }
            }
            System.out.println("memory map loaded");
        } catch (IOException e) {
            System.out.println("error loading memory map: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        JATP JATP = new JATP();

        while (true) {
            System.out.print("left string: ");
            String leftString = JATP.scanner.nextLine();
            System.out.print("right string: ");
            String rightString = JATP.scanner.nextLine();
            JATP.input(leftString, rightString);
        }
    }
}
