import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class JATP {
    private final HashMap<String, String> equationMap = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);

    public JATP() {
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
        if (this.prove(leftString, rightString)) {
            this.output(leftString);
        } else {
            this.disprove(leftString, rightString);
        }
    }

    private void output(String leftString) {
        if (this.equationMap.get(this.equationMap.get(leftString)) != null){
            System.out.println("Theorem: " + leftString + "=" + this.equationMap.get(leftString)
                    + "=" + this.equationMap.get(this.equationMap.get(leftString)));
        } else {
            this.define(this.equationMap.get(leftString));
        }
    }

    private void define(String leftString) {
        System.out.print("Definition: " + leftString + "=");
        String rightString = this.scanner.nextLine();
        this.equationMap.put(leftString, rightString);
        this.save(leftString, rightString);
    }

    private void redefine(String leftString, String rightString) {
        System.out.print("Redefinition: " + leftString + "=");
        rightString = this.scanner.nextLine();
        if (!rightString.equals(this.equationMap.get(leftString))) {
            this.equationMap.replace(leftString, rightString);
            this.save(leftString, rightString);
        }
    }

    private boolean prove (String leftString, String rightString) {
        return this.equationMap.containsKey(leftString) && this.equationMap.get(leftString).equals(rightString);
    }

    private void disprove(String leftString, String rightString) {
        if (this.equationMap.containsKey(leftString)) {
            System.out.println("Conjecture: " + leftString + "=" + rightString);
            System.out.println("Recall: " + leftString + "=" + this.equationMap.get(leftString));
            System.out.println("Equation Map: " + this.equationMap);
            this.redefine(leftString, rightString);
        } else {
            this.equationMap.put(leftString, rightString);
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
        this.equationMap.clear();
        try (DataInputStream DIS = new DataInputStream(new FileInputStream("map.jatp"))) {
            while (true) {
                try {
                    String leftString = DIS.readUTF();
                    String rightString = DIS.readUTF();
                    this.equationMap.put(leftString, rightString);
                } catch (EOFException e) {
                    break;
                }
            }
            System.out.println("Map loaded");
        } catch (IOException e) {
            System.out.println("Error loading map: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        JATP JATP = new JATP();
        while (true) {
            System.out.print("Left String: ");
            String leftString = JATP.scanner.nextLine();
            System.out.print("Right String: ");
            String rightString = JATP.scanner.nextLine();
            JATP.input(leftString, rightString);
        }
    }
}