import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class JATP {
    private final HashMap<String, String> map = new HashMap<>();
    private final Scanner scanner = new Scanner(System.in);

    public JATP() {
     /* System.out.println("Creator: Caleb Princewill Nwokocha");
        System.out.println("Creative Commons Zero v1.0 Universal");
        System.out.println();
        System.out.println("Every equation has left and right");
        System.out.println("c is left of c=p and p is c=p right");
        System.out.println("c is left of c=c and c is c=c right");
        System.out.println("Equation: a+b=b+a, Left: a+b, Right: b+a");
        System.out.println("Equation: a+b=a+b, Left: a+b, Right: a+b");
        System.out.println();*/
        this.load();
    }

    public void input(String left, String right) {
        if (this.prove(left, right)) {
            this.output(left);
        } else {
            this.disprove(left, right);
        }
    }

    private void output(String left) {
        if (this.map.get(this.map.get(left)) != null){
            System.out.println("Theorem: " + left + "=" + this.map.get(left)
                    + "=" + this.map.get(this.map.get(left)));
        } else {
            this.define(this.map.get(left));
        }
    }

    private void define(String left) {
        System.out.print("Definition: " + left + "=");
        String rightString = this.scanner.nextLine();
        this.map.put(left, rightString);
        this.save(left, rightString);
    }

    private void redefine(String left, String right) {
        System.out.print("Redefinition: " + left + "=");
        String newRight = this.scanner.nextLine();
        if (newRight.equals(right)) {
            this.map.replace(left, newRight);
            this.save(left, newRight);
        }
    }

    private boolean prove (String left, String right) {
        return this.map.containsKey(left) && this.map.get(left).equals(right);
    }

    private void disprove(String left, String right) {
        if (this.map.containsKey(left)) {
            System.out.println("Map: " + this.map);
            System.out.println("Conjecture: " + left + "=" + right);
            System.out.println("Recall: " + left + "=" + this.map.get(left));
            this.redefine(left, right);
        } else {
            this.map.put(left, right);
            this.save(left, right);
        }
    }

    private void save(String left, String right) {
        try (DataOutputStream DOS = new DataOutputStream(new
                FileOutputStream("map.jatp", true))) {
            DOS.writeUTF(left);
            DOS.writeUTF(right);
            System.out.println("Saved " + left + "=" + right);
        } catch (IOException e) {
            System.out.println("Error saving " + left + "=" + right + " --> " + e.getMessage());
        }
    }

    private void load() {
        this.map.clear();
        try (DataInputStream DIS = new DataInputStream(new FileInputStream("map.jatp"))) {
            while (true) {
                try {
                    String left = DIS.readUTF();
                    String right = DIS.readUTF();
                    this.map.put(left, right);
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
            String left = JATP.scanner.nextLine();
            System.out.print("Right: ");
            String right = JATP.scanner.nextLine();
            JATP.input(left, right);
        }
    }
}