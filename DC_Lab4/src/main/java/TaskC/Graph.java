package TaskC;

import java.util.ArrayList;
import java.util.Random;

public class Graph {
    private ArrayList<ArrayList<Integer>> matrix;

    private void initGraph(int size) {
        matrix = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            ArrayList<Integer> row = new ArrayList<>();
            for (int j = 0; j < size; j++) {
                row.add(0);
            }
            matrix.add(row);
        }
    }

    private void fillGraph() {
        Random random = new Random();
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = i + 1; j < matrix.get(i).size(); j++) {
                int randomValue = random.nextInt(101);
                matrix.get(i).set(j, randomValue);
                matrix.get(j).set(i, randomValue);
            }
        }
    }

    public Graph(int size) {
        initGraph(size);
        fillGraph() ;
    }

    public void set(int i, int j, int value) {
        matrix.get(i).set(j, value);
        matrix.get(j).set(i, value);
    }

    public int get(int i, int j) {
       try{
             return matrix.get(i).get(j);
        } catch (IndexOutOfBoundsException e){
           return 0;
       }
    }

    public int size() {
        return  matrix.size();
    }

    public void removeCity(int index) {
        matrix.remove(index);
        for (int i = 0; i < matrix.size(); i++) {
            matrix.get(i).remove(index);
        }
    }

    public void printGraph() {
        for (ArrayList<Integer> row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }

    public void addCity() {
        int newSize = size() + 1;

        ArrayList<Integer> newRow = new ArrayList<>();
        for (int i = 0; i < newSize; i++) {
            newRow.add(0);
        }
        matrix.add(newRow);

        for (int i = 0; i < newSize - 1; i++) {
            matrix.get(i).add(0);
        }

        Random random = new Random();
        for (int i = 0; i < newSize - 1; i++) {
            int randomValue = random.nextInt(101);
            matrix.get(newSize - 1).set(i, randomValue);
            matrix.get(i).set(newSize - 1, randomValue);
        }
    }
}
