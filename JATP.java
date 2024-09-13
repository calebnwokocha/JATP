import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class JATP {
    private final Scanner scanner = new Scanner(System.in);
    private final HashMap<String, String> map = new HashMap<>();

    public JATP() {
        System.out.println("JAVA AUTOMATED THEOREM PROVER (JATP)");
        System.out.println("Creator: Caleb Princewill Nwokocha");
        System.out.println("License: Creative Commons Zero v1.0 Universal");
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
            this.output(leftString);
        } else {
            this.prove(leftString, rightString);
        }
    }

    private void output(String leftString) {
        if (this.map.get(this.map.get(leftString)) != null){
            System.out.println("theorem: " + leftString + "=" + this.map.get(leftString)
                    + "=" + this.map.get(this.map.get(leftString)));
            System.out.println("map: " + this.map);
        } else { this.define(this.map.get(leftString));}
    }

    private void define(String leftString) {
        System.out.print("definition: " + leftString + "=");
        String rightString = this.scanner.nextLine();
        this.map.put(leftString, rightString);
        this.save(leftString, rightString);
    }

    private void redefine(String leftString, String rightString) {
        System.out.print("redefinition: " + leftString + "=");
        rightString = this.scanner.nextLine();
        if (!rightString.equals(this.map.get(leftString))) {
            this.map.replace(leftString, rightString);
            this.save(leftString, rightString);
        }
    }

    private void prove(String leftString, String rightString) {
        if (this.map.containsKey(leftString)) {
            System.out.println("contradiction: " + leftString + "=" + rightString);
            System.out.println("recall: " + leftString + "=" + this.map.get(leftString));
            this.redefine(leftString, rightString);
        } else {
            this.map.put(leftString, rightString);
            this.save(leftString, rightString);
        }
    }

    private void save(String leftString, String rightString) {
        try (DataOutputStream DOS = new DataOutputStream(new
                FileOutputStream("map.jatp", true))) {
            DOS.writeUTF(leftString);
            DOS.writeUTF(rightString);
            System.out.println("saved " + leftString + "=" + rightString);
        } catch (IOException e) {
            System.out.println("error saving " + leftString + "=" + rightString + " --> " + e.getMessage());
        }
    }

    private void load() {
        this.map.clear();
        try (DataInputStream DIS = new DataInputStream(new FileInputStream("map.jatp"))) {
            while (true) {
                try {
                    String leftString = DIS.readUTF();
                    String rightString = DIS.readUTF();
                    this.map.put(leftString, rightString);
                } catch (EOFException e) {
                    break;
                }
            }
            System.out.println("map loaded");
        } catch (IOException e) {
            System.out.println("error loading map: " + e.getMessage());
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
