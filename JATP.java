import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

public class JATP {
    private final Scanner scanner = new Scanner(System.in);
    private final HashMap<Object, Object> map = new HashMap<>();

    public JATP() {
        System.out.println("JAVA AUTOMATED THEOREM PROVER (JATP)");
        System.out.println("Creator: Caleb Princewill Nwokocha");
        System.out.println("License: Creative Commons Zero v1.0 Universal");
        System.out.println();
        System.out.println("Theorem: Every equation has a left object and a right object.");
        System.out.println("Example 1: a is left object of a=b and b is its right object.");
        System.out.println("Example 2: a is left object of a=a and a is its right object.");
        System.out.println("Example 3: a+b is left object of a+b=b+a and b+a is its right object.");
        System.out.println("Example 4: a+b is left object of a+b=a+b and a+b is its right object.");
        System.out.println();
        this.load();
    }

    public void input(Object leftObject, Object rightObject) {
        if (this.map.containsKey(leftObject) &&
                this.map.get(leftObject).equals(rightObject)) {
            output(leftObject.toString());
        } else { prove(leftObject, rightObject); }
    }

    private Object[] output(Object leftObject) {
        if (this.map.get(this.map.get(leftObject)) != null){
            if (leftObject instanceof String ) {
                System.out.println("theorem: " + leftObject + "=" + this.map.get(leftObject)
                        + "=" + this.map.get(this.map.get(leftObject)));
                System.out.println("map: " + this.map);
            } else {
                return new Object[]{leftObject, this.map.get(leftObject), 
                        this.map.get(this.map.get(leftObject))};
            }
        } else { this.define(this.map.get(leftObject), new Object());}
        return null;
    }

    private void define(Object leftObject, Object rightObject) {
        if (leftObject instanceof String ) {
            System.out.print("definition: " + leftObject + "=");
            rightObject = this.scanner.nextLine();
        }
        if (leftObject.getClass().equals(rightObject.getClass())) {
            this.map.put(leftObject, rightObject);
            this.save(leftObject, rightObject);
        }
    }
    
    private void prove(Object leftObject, Object rightObject) {
        if (this.map.containsKey(leftObject)) {
            if (leftObject instanceof String ) {
                System.out.println("contradiction: " + leftObject + "=" + rightObject);
                System.out.println("recall: " + leftObject + "=" + this.map.get(leftObject));
            } this.define(leftObject, rightObject);
        } else {
            this.map.put(leftObject, rightObject);
            this.save(leftObject, rightObject);
        }
    }

    private void save(Object leftObject, Object rightObject) {
        if (leftObject instanceof String && rightObject instanceof String) {
            try (DataOutputStream DOS = new DataOutputStream(new
                    FileOutputStream("map.jatp", true))) {
                DOS.writeUTF(leftObject.toString());
                DOS.writeUTF(rightObject.toString());
                System.out.println("saved " + leftObject + "=" + rightObject);
            } catch (IOException e) {
                System.out.println("error saving " + leftObject + "=" + rightObject + " --> " + e.getMessage());
            }
        }
    }

    private void load() {
        this.map.clear();
        try (DataInputStream DIS = new DataInputStream(new FileInputStream("map.jatp"))) {
            while (true) {
                try {
                    String leftObject = DIS.readUTF();
                    String rightObject = DIS.readUTF();
                    this.map.put(leftObject, rightObject);
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
            String leftObject = JATP.scanner.nextLine();
            System.out.print("right string: ");
            String rightObject = JATP.scanner.nextLine();
            JATP.input(leftObject, rightObject);
        }
    }
}
