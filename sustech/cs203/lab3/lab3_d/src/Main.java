import java.io.*;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class Main {
    static PrintWriter out;
    static InputReader in;

    public static void main(String args[]) throws IOException {
        out = new PrintWriter(System.out);
        in = new InputReader(System.in);
        for (int t = in.nextInt(); t > 0; t--) { //As same as scanner.nextInt()
            int time1 = in.nextInt();
            int[][] poly1 = new int[time1 + 1][2];
            for (int i = 0; i < time1; i++) {
                poly1[i][1] = in.nextInt();
                poly1[i][0] = in.nextInt();
                // out.print(poly1[i][0]+ " ");
            }
            poly1[time1][1] = 65535;
            poly1[time1][0] = 65535;
            quicksort(poly1, 0, time1 - 1);
            int tempLength1 = time1;
            for (int i = 0; i < time1; i++) {
                poly1[i][1] = poly1[i][1] * poly1[i][0];
                poly1[i][0] = poly1[i][0] - 1;
            }
            for (int i = 0; i < time1 - 1; i++) {
                if (poly1[i][0] == poly1[i + 1][0]) {
                    poly1[i + 1][1] += poly1[i][1];
                }
            }
//          for (int i = 0; i < time1 - 1; i++) {
//              poly1[i][1] = poly1[i][1] * poly1[i][0];
//              poly1[i][0] = poly1[i][0] -1;
//          }
            LinkedList<Integer> linked1 = new LinkedList<Integer>();
            LinkedList<Integer> linked2 = new LinkedList<Integer>();
            for (int i = 0; i < time1; i++) {
                linked1.addLast(poly1[i][0]);
                linked2.addLast(poly1[i][1]);
                if (i < time1 - 1 && poly1[i][0] == poly1[i + 1][0]) {
                    linked1.removeLast();
                    linked2.removeLast();
                    tempLength1--;
                }

            }
            boolean name2 = false;
            boolean signFirst = false;
            for (int i = 0; i < tempLength1; i++) {
                if (linked2.getFirst() > 0 && i != 0 && signFirst) {
                    out.print("+");
                    name2 = true;
                }
                switch (linked2.getFirst()) {
                    case 1: {
                        name2 = true;
                        signFirst = true;
                        switch (linked1.getFirst()) {
                            case 0: {
                                out.print("1");
                                break;
                            }
                            case 1: {
                                out.print("x");
                                break;
                            }
                            default: {
                                out.printf("%s%d", "x^", linked1.getFirst());
                                break;
                            }
                        }
                        break;
                    }
                    case -1: {
                        name2 = true;
                        signFirst = true;
                        switch (linked1.getFirst()) {
                            case 0: {
                                out.print("-1");
                                ;
                                break;
                            }
                            case 1: {
                                out.print("-x");
                                break;
                            }
                            default: {
                                out.printf("%s%d", "-x^", linked1.getFirst());
                                break;
                            }
                        }
                        break;
                    }
                    case 0: {
                        break;
                    }
                    default: {
                        name2 = true;
                        signFirst = true;
                        switch (linked1.getFirst()) {
                            case 0: {
                                out.print(linked2.getFirst());
                                break;
                            }
                            case 1: {
                                out.print(linked2.getFirst() + "x");
                                break;
                            }
                            default: {
                                out.printf("%d%s%d", linked2.getFirst(), "x^", linked1.getFirst());
                                break;
                            }
                        }
                        break;
                    }
                }
                linked1.removeFirst();
                linked2.removeFirst();
            }
            if (!name2) {
                out.print(0);
            }
            if (t != 1) {out.println();}

        }
        out.close(); //Don't forget this line, otherwise you will output nothing. This sentence flush the buffer.
    }

    public static int getMiddle(int array[][], int begin, int finalNumber) {
        int temp = array[begin][0];
        int temp2 = array[begin][1];
        while (begin < finalNumber) {
            while (begin < finalNumber && array[finalNumber][0] >= temp) {
                finalNumber--;
            }
            // if (begin < finalNumber) {
            array[begin][0] = array[finalNumber][0];
            array[begin][1] = array[finalNumber][1];
            // }
            while (begin < finalNumber && array[begin][0] <= temp) {
                begin++;
            }
            // if(begin < finalNumber) {
            array[finalNumber][0] = array[begin][0];
            array[finalNumber][1] = array[begin][1];
            // }
        }
        array[begin][0] = temp;
        array[begin][1] = temp2;
        return begin;
    }

    public static void quicksort(int array[][], int begin, int finalNumber) {
        int middle;
        if (begin < finalNumber) {
            middle = getMiddle(array, begin, finalNumber);
            quicksort(array, begin, middle - 1);
            quicksort(array, middle + 1, finalNumber);
        }
    }
}

class InputReader {
    public BufferedReader br;
    public StringTokenizer tokenizer;

    public InputReader(InputStream stream) throws FileNotFoundException {
        br = new BufferedReader(new InputStreamReader(stream), 327680);
        tokenizer = null;
    }

    public boolean hasNext() {
        while (tokenizer == null || !tokenizer.hasMoreElements()) {
            try {
                tokenizer = new StringTokenizer(br.readLine());
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

    public String next() {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            try {
                tokenizer = new StringTokenizer(br.readLine());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return tokenizer.nextToken();
    }

    public int nextInt() {
        try {
            int c = br.read();
            while (c <= 32) {
                c = br.read();
            }
            boolean negative = false;
            if (c == '-') {
                negative = true;
                c = br.read();
            }
            int x = 0;
            while (c > 32) {
                x = x * 10 + c - '0';
                c = br.read();
            }
            return negative ? -x : x;
        } catch (IOException e) {
            return -1;
        }
    }

    public long nextLong() {
        try {
            int c = br.read();
            while (c <= 32) {
                c = br.read();
            }
            boolean negative = false;
            if (c == '-') {
                negative = true;
                c = br.read();
            }
            long x = 0;
            while (c > 32) {
                x = x * 10 + c - '0';
                c = br.read();
            }
            return negative ? -x : x;
        } catch (IOException e) {
            return -1;
        }
    }
}