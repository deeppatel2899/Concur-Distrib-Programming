/** 
Author : Deepkumar Patel
Parallel Matrix Multiplication using Java Threads

**/
import java.util.*;

class MultiplyMatrix extends Thread {
    int start;
    int end;
    int[][] resultMatrix;
    int[][] matrix1;
    int[][] matrix2;

    public MultiplyMatrix(int start, int end, int[][] matrix1, int[][] matrix2, int[][] resultMatrix) {
        this.start = start;
        this.end = end;
        this.matrix1 = matrix1;
        this.matrix2 = matrix2;
        this.resultMatrix = resultMatrix;

    }

    @Override
    public void run() {
        int n = matrix1.length;
        int m = matrix2[0].length;
        System.out.printf(" -> Thread ID : %d %n", this.getId());

        int l = matrix1[0].length;
        int c = 0;

        for (int i = start; i < end; i++) {

            for (int j = 0; j < m; j++) {

                for (int k = 0; k < l; k++) {
                    System.out.printf("%d X %d = %d %n", matrix1[i][k], matrix2[k][j], (matrix1[i][k] * matrix2[k][j]));

                    c = c + (matrix1[i][k] * matrix2[k][j]);

                }
                resultMatrix[i][j] = c;
                c = 0;
            }
        }
        System.out.println();
    }

}

public class Parallel_matrix_multiplication {
    public static void main(String arg[]) {
        Scanner sc = new Scanner(System.in);
        int row = 5;
        int col = 3;

        int row1 = 3;
        int col1 = 2;
        int t;
        int n=0;

        int[][] matrix1;
        int[][] matrix2;
        int[][] resultMatrix = null;

        System.out.print("Enter number of rows for matrix 1 : ");
        row = sc.nextInt();
        System.out.print("Enter number of columns for matrix 1 : ");
        col = sc.nextInt();
        System.out.printf("%d X %d %n", row, col);
        matrix1 = new int[row][col];

        System.out.print("Enter number of rows for matrix 2 : ");
        row1 = sc.nextInt();
        System.out.print("Enter number of columns for matrix 2 : ");
        col1 = sc.nextInt();
        System.out.printf("%d X %d %n", row1, col1);
        matrix2 = new int[row1][col1];

        if (col != row1) {
            System.out.println("Matrix Multiplication is not possible !");
	System.exit(0);
        } else {
            resultMatrix = new int[row][col1];
        }

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                matrix1[i][j] = (int) (Math.random() * (100 - 1)) + 1;
            }
        }

        System.out.println("First Matrix is : ");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print(String.format("%10d", matrix1[i][j]));
            }
            System.out.println();
        }

        for (int i = 0; i < row1; i++) {
            for (int j = 0; j < col1; j++) {
                matrix2[i][j] = (int) (Math.random() * (100 - 1)) + 1;
            }
        }
        System.out.println("Second Matrix is : ");
        for (int i = 0; i < row1; i++) {
            for (int j = 0; j < col1; j++) {
                System.out.print(String.format("%10d", matrix2[i][j]));
            }
            System.out.println();
        }

        System.out.println("Enter number of threads, that you want to use : ");
        t = sc.nextInt();

        int[][] sorted_row = new int[t][2];

        

        try{
            n = (int) row / t;}
        catch(ArithmeticException e){
            System.out.println("Minimum one thread required !");
            System.exit(0);
            }
            
        int temp = 0;

        if (row < t) {
            for (int i = 0; i < row; i++) {
                sorted_row[i][0] = temp;
                sorted_row[i][1] = temp + 1;
                temp = temp + 1;
            }

        } else {
            for (int i = 0; i < t; i++) {

                sorted_row[i][0] = temp;
                sorted_row[i][1] = temp + n;
                temp = temp + n;
            }
            if (t * n != row) {
                sorted_row[t - 1][1] += row - (t * n);
            }

        }

        for (int i = 0; i < t; i++) {

            System.out.printf("Thread %d compute %d row", i, sorted_row[i][1] - sorted_row[i][0]);

            System.out.println();

        }

        MultiplyMatrix[] thread_arr = new MultiplyMatrix[t];
        long start = System.currentTimeMillis();

        for (int i = 0; i < t; i++) {
            if (sorted_row[i][1] != 0) {
                try {
                    thread_arr[i] = new MultiplyMatrix(sorted_row[i][0], sorted_row[i][1], matrix1, matrix2,
                            resultMatrix);
                    thread_arr[i].start();
                    thread_arr[i].join();
                } catch (Exception e) {
                }
            }
        }
        long end = System.currentTimeMillis();

        System.out.println("Result Matrix is : ");
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col1; j++) {
                System.out.print(String.format("%10d", resultMatrix[i][j]));
            }
            System.out.println();
        }

        float sec = (end - start) / 1000F;
        System.out.println("Runtime = "+sec + " seconds");
    }
}
