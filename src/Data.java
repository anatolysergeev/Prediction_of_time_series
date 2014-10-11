import java.util.ArrayList;
import java.util.InputMismatchException;

public class Data {
    String nameClose;

    class Element {
        int id;
        double value;

        Element(double close, int id) {
            this.id = id;
            this.value = close;
        }

        @Override
        public String toString() {
            return id + " " + value;
        }
    }

    ArrayList<Element> elements = null;

    Data (InputReader in) {
        readData(in);
    }

    public void readData(InputReader in) {
        elements = new ArrayList<>();
        nameClose = in.nextLine();
        int cntId = 0;
        try {
            while (true) {
                double tmpC = in.nextDouble();
                elements.add(new Element(tmpC, ++cntId));
            }
        } catch (InputMismatchException e) {
            System.out.println("Data is read");
        }
    }
}