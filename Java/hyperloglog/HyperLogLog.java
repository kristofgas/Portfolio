import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.BitSet;

public class HyperLogLog {

    public BufferedReader br;
    public double m;
    public int threshold;
    public int V;
    public int[] M;
    public double nHat;
    public double am;
    public int[] A = new int[] { 0x21ae4036, 0x32435171, 0xac3338cf, 0xea97b40c, 0x0e504b22, 0x9ff9a4ef, 0x111d014d,
            0x934f3787, 0x6cd079bf, 0x69db5c31, 0xdf3c28ed, 0x40daf2ad, 0x82a5891c, 0x4659c7b0, 0x73dc0ca8, 0xdad3aca2,
            0x00c74c7e, 0x9a2521e2, 0xf38eb6aa, 0x64711ab6, 0x5823150a, 0xd13a3a9a, 0x30a5aa04, 0x0fb9a1da, 0xef785119,
            0xc9f0b067, 0x1e7dde42, 0xdda4a7b2, 0x1a1c2640, 0x297c0633, 0x744edb48, 0x19adce93 };

    //Added constructor to instantiate object
    public HyperLogLog(int m) {
        this.m = m;
    }

    // Function to calculate the
    // log base 2 of an integer
    private static int log2(int N)
    {
        return (int)(Math.log(N) / Math.log(2)); 
    }

    // public void setRegisters() throws NumberFormatException, IOException {
    //     M = new int[(int) m];
    //     br = new BufferedReader(new InputStreamReader(System.in));
    //     br.lines().forEach(i -> register(i));
    // }

    //Needed new method to instantiate the object without reading from stream
    public void setRegistersInput(BitSet bs) {
        M = new int[(int) m];
        bs.stream().forEach(i->registerInt(i));
    }


    // public void register(String input) {
    //     int y = Integer.parseInt(input);
    //     int j = f(y);
    //     int x = h(y);
    //     int px = p(x);
    //     if (px > M[j]) {
    //         M[j] = px;
    //     }
    // }

    public void registerInt(int y) {
        int j = f(y);
        int x = h(y);
        int px = p(x);
        if (px > M[j]) {
            M[j] = px;
        }
    }

    public int f(int x) {
        int bitshift = 31-log2((int) m);
        return ((x * 0xbc164501) & 0x7fffffff) >> bitshift;
    }


    public int h(int x) {
        int h = 0;
        for (int j = 0; j < A.length; j++) {
            int bit = Integer.bitCount((A[j] & x)) % 2;
            h = ((bit << j) | h);
        }
        return h;
    }

    public int p(int x) {
        return Integer.numberOfLeadingZeros(x) + 1;
    }

    public double estimate() {
        V = countZeros();
        nHat = harmonicMean();

        if ((nHat <= (5.0 / 2.0) * m) && V > 0) {
            return m * Math.log(m / V);
        }
        if (nHat > (1.0 / 30.0) * Math.pow(2.0, 32.0)) {
            nHat = Math.pow(-2.0, 32.0) * Math.log(1 - (nHat / Math.pow(2.0, 32.0)));
        }
        return nHat;
    }

    public int countZeros() {
        int count = 0;
        for (int i = 0; i < M.length; i++) {
            if (M[i] == 0) {
                count++;
            }
        }
        return count;
    }

    public double harmonicMean() {
        am = magicConstant();
        double sum = 0;

        for (int i = 0; i < M.length; i++) {
            sum += Math.pow(2.0, (double) -M[i]);
        }

        return (am * (Math.pow(m, 2.0))) * Math.pow(sum, -1.0);
    }

    public double magicConstant() {
        return 0.7213 / (1.0 + (1.079 / m));
    }   


}