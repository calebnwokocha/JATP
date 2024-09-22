import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

public class AI {
    private final HashMap<int[], int[]> map = new HashMap<>();

    public AI () {
        this.load();
    }

    public int[] test (byte[] X) {
        int[] y;
        if (map.containsKey(this.getIntegerSet(X))) {
            y = map.get(this.getIntegerSet(X));
        } else {
            y = this.predict(X);
        }
        return y;
    }

    public void train (byte[] X, byte[] Y) {
        this.map.putIfAbsent(this.getIntegerSet(X), this.getIntegerSet(Y));
        this.save(this.getIntegerSet(X), this.getIntegerSet(Y));
    }

    private int[] predict (byte[] X) {
        int j, k;
        int[] modifiedX = new int[X.length];
        for (k = 1; k <= 10; k++) {
            for (int p = 0; p < modifiedX.length; p++) {
                modifiedX[p] = this.getIntegerSet(X)[p] + k;
            }
            if (map.containsKey(modifiedX)) {
                return map.get(modifiedX);
            }
            for (j = 1; j <= 10; j++) {
                for (int p = 0; p < modifiedX.length; p++) {
                    modifiedX[p] = this.getIntegerSet(X)[p] - k - j;
                }
                if (map.containsKey(modifiedX)) {
                    return map.get(modifiedX);
                }
            }
        }
        return null;
    }

    private int[] getIntegerSet(byte[] byteSet) {
        int[] integerSet = new int[byteSet.length];
        for(int i = 0; i < integerSet.length; i++) {
            integerSet[i] = (int) byteSet[i];
        }
        return integerSet;
    }

    private void save(int[] X, int[] Y) {
        try (DataOutputStream DOS = new DataOutputStream(new
                FileOutputStream("map.jatp", true))) {
            DOS.writeUTF(Arrays.toString(X));
            DOS.writeUTF(Arrays.toString(Y));
            System.out.println("Saved " + X + "=" + Y);
        } catch (IOException e) {
            System.out.println("Error saving " + X + "=" + Y + " --> " + e.getMessage());
        }
    }

    private void load() {
        this.map.clear();
        try (DataInputStream DIS = new DataInputStream(new FileInputStream("map.jatp"))) {
            while (true) {
                try {
                    String leftString = DIS.readUTF();
                    String rightString = DIS.readUTF();
                    this.map.put(getIntegerSet(leftString.getBytes()),
                            getIntegerSet(rightString.getBytes()));
                } catch (EOFException e) {
                    break;
                }
            }
            System.out.println("Map loaded");
        } catch (IOException e) {
            System.out.println("Error loading map: " + e.getMessage());
        }
    }
}
