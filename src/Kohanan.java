import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

class Vector {
    int length;
    double[] nVector;
    private double[] vector;

    Vector(ArrayList<Double> vector) {
        length = vector.size();
        this.vector = new double[length];
        int i = 0;
        for (double x : vector) this.vector[i++] = x;
        nVector = normalVector(this.vector);
    }

    Vector(double[] vector) {
        length = vector.length;
        this.vector = vector;
        nVector = normalVector(this.vector);
    }

    //нормализация вектор
    public double[] normalVector (double[] vector) {
        double[] normalVector = new double[length];
        double tmp = 0;
        for (double x  : vector)
            tmp += x*x;
        tmp = Math.sqrt(tmp);

        for (int i = 0; i < length; ++i)
            normalVector[i] = vector[i] / tmp;

        return normalVector;
    }

    //нахождения скалярного произведения
    public static double scalarProduct (Vector v1, Vector v2) {
        double res = 0;
        for (int i = 0; i < v1.length; i++)
            res += v1.nVector[i]*v2.nVector[i];

        return res;
    }
}


public class Kohanan {
    int sizeVector = 25;
    double beta = 0.7;
    Data data;
    Vector down;
    Vector up;
    Vector checkVector;
    boolean answer;

    Kohanan(String fileName) {
        InputReader in = null;
        try {
            InputStream inputStream = new FileInputStream(new File(fileName));
            in = new InputReader(inputStream);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        data = new Data(in);
        train();

        double tmpspup = 0, tmpspdown = 0;


        //прогнозирование
        if (up != null) {
            tmpspup = Vector.scalarProduct(up, checkVector);
            System.out.println("up   - " + tmpspup);

        }
        if (down != null) {
            tmpspdown = Vector.scalarProduct(down, checkVector);
            System.out.println("down - " + tmpspdown);
        }

        if (tmpspup > tmpspdown) answer = true;
        else answer = false;
    }

    //обучения нейронной сети
    private void train() {
        double[] tmp = new double[sizeVector];
        Data.Element[] dataTmp = new Data.Element[data.elements.size()];
        data.elements.toArray(dataTmp);

        ArrayList<Double> checkTmp = new ArrayList<>();
        for (int i = dataTmp.length - sizeVector; i < dataTmp.length; ++i)
            checkTmp.add(dataTmp[i].value);
        checkVector = new Vector(checkTmp);

        for (int i = 0; i + sizeVector < dataTmp.length - sizeVector; ++i) {
            double max = -1;
            double min = 1e9;
            for (int j = 0; j < sizeVector; ++j) {
                tmp[j] = dataTmp[i + j].value;
                max = Math.max(max, tmp[j]);
                min = Math.min(min, tmp[j]);
            }
            Vector vectorTmp = new Vector(tmp);

            if (tmp[0] < tmp[sizeVector - 1] && tmp[0] == min && tmp[sizeVector - 1] == max ) {
                boolean good = false;
                for (int j = 0; j < sizeVector; ++j)
                    if (dataTmp[i + j + sizeVector].value < max) good = true;

                if (good) {
                    if (down == null) {
                        down = vectorTmp;
                    } else {
                        for (int j = 0; j < sizeVector; ++j)
                            down.nVector[j] += beta * (vectorTmp.nVector[j] - down.nVector[j]);
                    }
                }
            } else if (tmp[0] > tmp[sizeVector - 1] && tmp[0] == max && tmp[sizeVector - 1] == min) {
                boolean good = false;
                for (int j = 0; j < sizeVector; ++j)
                    if (dataTmp[i + j + sizeVector].value > min) good = true;

                if (good) {
                    if (up == null) {
                        up = vectorTmp;
                    } else {
                        for (int j = 0; j < sizeVector; ++j)
                            up.nVector[j] += beta * (vectorTmp.nVector[j] - up.nVector[j]);
                    }
                }
            }
        }
    }

//    public static void main(String [] args) {
//        Kohanan kohanan = new Kohanan("data.txt");
//
//        XYChart chart = new XYChart("Prediction", kohanan.data);
//        chart.pack();
//        RefineryUtilities.centerFrameOnScreen(chart);
//        chart.setVisible(true);
//
//
//        if (kohanan.answer == true) {
//            System.out.println("График повысится");
//        } else {
//            System.out.println("График понизится");
//        }
//    }

}