import java.io.*;
import java.util.*;

public class InputReader {
    BufferedReader in;
    StringTokenizer st;

    public InputReader(InputStream stream) {
        in = new BufferedReader(new InputStreamReader(stream));
        eat("");
    }

    public int nextInt() {
        return Integer.parseInt(next());
    }

    public double nextDouble() {
        return Double.parseDouble(next());
    }

    public Date nextDate() {
        int month = nextInt();
        int day = nextInt();
        int year = nextInt();
        int hrs = nextInt();
        int min = nextInt();

        return new Date(year, month, day, hrs, min);
    }

    public String next() {
        while (!st.hasMoreTokens())
            eat(nextLine());
        return st.nextToken();
    }

    public String nextLine() {
        try {
            return in.readLine();
        } catch (IOException e) {
            throw new InputMismatchException();
        }
    }

    public void eat(String str) {
        if (str == null) throw new InputMismatchException();
        st = new StringTokenizer(str, ",/ :");
    }
}