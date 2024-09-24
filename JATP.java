import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class JATP {
    private final HashMap<String, String> map = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);

    public JATP() {
        System.out.println("Creator: Caleb Princewill Nwokocha");
        System.out.println("Creative Commons Zero v1.0 Universal");
        System.out.println();
        System.out.println("c is left of c=p and p is c=p right");
        System.out.println("c is left of c=c and c is c=c right");
        System.out.println("Equation: a+b=b+a, Left: a+b, Right: b+a");
        System.out.println("Equation: a+b=a+b, Left: a+b, Right: a+b");
        System.out.println();
        this.load();
    }

    public void input(String leftString, String rightString) {
        if (this.prove(leftString, rightString)) {
            this.output(leftString);
        } else {
            this.disprove(leftString, rightString);
        }
    }

    private void output(String leftString) {
        if (this.map.get(this.map.get(leftString)) != null){
            System.out.println("Theorem: " + leftString + "=" + this.map.get(leftString)
                    + "=" + this.map.get(this.map.get(leftString)));
        } else {
            this.define(this.map.get(leftString));
        }
    }

    private void define(String leftString) {
        System.out.print("Definition: " + leftString + "=");
        String rightString = this.scanner.nextLine();
        this.map.put(leftString, rightString);
        this.save(leftString, rightString);
    }

    private void redefine(String leftString, String rightString) {
        System.out.print("Redefinition: " + leftString + "=");
        rightString = this.scanner.nextLine();
        if (!rightString.equals(this.map.get(leftString))) {
            this.map.replace(leftString, rightString);
            this.save(leftString, rightString);
        }
    }

    private boolean prove (String leftString, String rightString) {
        return this.map.containsKey(leftString) && this.map.get(leftString).equals(rightString);
    }

    private void disprove(String leftString, String rightString) {
        if (this.map.containsKey(leftString)) {
            System.out.println("Conjecture: " + leftString + "=" + rightString);
            System.out.println("Recall: " + leftString + "=" + this.map.get(leftString));
            System.out.println("Map: " + this.map);
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
            System.out.println("Saved " + leftString + "=" + rightString);
        } catch (IOException e) {
            System.out.println("Error saving " + leftString + "=" + rightString + " --> " + e.getMessage());
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
            System.out.println("Loaded map");
        } catch (IOException e) {
            System.out.println("Error loading map: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        JATP JATP = new JATP();
        while (true) {
            System.out.print("Left: ");
            String leftString = JATP.scanner.nextLine();
            System.out.print("Right: ");
            String rightString = JATP.scanner.nextLine();
            JATP.input(leftString, rightString);
        }
    }
}