import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class JATP {
    private final Scanner scanner = new Scanner(System.in);
    private final HashMap<String, String> map = new HashMap<>();
    private final HashMap<String, String> reverseMap = new HashMap<>();

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
        if (this.map.containsKey(leftString) &&
                this.map.get(leftString).equals(rightString)) {
            output(leftString);
        } else {
            prove(leftString, rightString);
        }
    }

    private void output(String leftString) {
        System.out.println("theorem: " + leftString + "=" + this.map.get(leftString)
                + "=" + this.reverseMap.get(leftString)
                + "=" + this.map.get(this.map.get(leftString))
                + "=" + this.reverseMap.get(this.reverseMap.get(leftString))
                + "=" + this.map.get(this.reverseMap.get(leftString))
                + "=" + this.reverseMap.get(this.map.get(leftString)));
        System.out.println("map: " + this.map);
        System.out.println("reverse map: " + this.reverseMap);
    }

    private void prove(String leftString, String rightString) {
        if (this.map.containsKey(leftString)) {
            System.out.println("contradiction: " + leftString + "≠" + rightString);
            System.out.println("recall: " + leftString + "=" + this.map.get(leftString));
            System.out.print("redefinition: " + leftString + "=");
            String newRightString = this.scanner.nextLine();
            if (newRightString.equals(rightString)){
                this.reverseMap.remove(this.map.get(leftString));
                this.reverseMap.put(newRightString, leftString);
                this.map.replace(leftString, newRightString);
                this.save(leftString, newRightString);
            }
        } else {
            this.map.put(leftString, rightString);
            this.reverseMap.put(rightString, leftString);
            this.save(leftString, rightString);
        }
    }

    private void save(String leftString, String rightString) {
        try (DataOutputStream dos = new DataOutputStream(new
                FileOutputStream("memory.jatp", true))) {
            dos.writeUTF(leftString);
            dos.writeUTF(rightString);
            System.out.println("saved " + leftString + "=" + rightString);
        } catch (IOException e) {
            System.out.println("error saving " + leftString + "=" + rightString + "; " + e.getMessage());
        }
    }

    private void load() {
        this.map.clear();
        this.reverseMap.clear();

        try (DataInputStream dis = new DataInputStream(new FileInputStream("memory.jatp"))) {
            while (true) {
                try {
                    String leftString = dis.readUTF();
                    String rightString = dis.readUTF();
                    this.map.put(leftString, rightString);
                    this.reverseMap.put(rightString, leftString);
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
