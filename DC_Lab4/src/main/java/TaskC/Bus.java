package TaskC;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Bus {
    private static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    static int SIZE_OF_GRAPH=5;
    Graph graph= new Graph(SIZE_OF_GRAPH);

    public  void changePrice() {
        while (true) {
            try {
                readWriteLock.writeLock().lock();
                Random random = new Random();
                int randomValue = random.nextInt(101);
                int i = random.nextInt(graph.size() - 1);
                int j = random.nextInt(graph.size() - 1);
                if (i==j) {j++;};
                graph.set(i, j, randomValue);
                graph.set(j, i, randomValue);
                System.out.println("Changing price between " + i + " and " + j);

            } finally {
                readWriteLock.writeLock().unlock();
            }
            try {Thread.sleep(8000);}
            catch (InterruptedException e) {e.printStackTrace();}
        }

    }

    public  void addOrDeleteRoutes() {
        try {
            readWriteLock.writeLock().lock();
            Random random = new Random();
            switch (random.nextInt(5)){
                case 0:
                    int i = random.nextInt(graph.size());
                    int j= random.nextInt(graph.size());
                    graph.set(i, j, 0);
                    graph.set(j, i, 0);
                    System.out.println("Deleting a route between "+ i +" and " + j);
                    break;

                case 1,2,3,4:
                    int randomValue = random.nextInt(101);
                    i = random.nextInt(graph.size()-1);
                    j= random.nextInt(graph.size()-1);
                    if (i==j) {j++;};
                     if (graph.get(i,j)==0) {
                         graph.set(i, j, randomValue);
                         graph.set(j, i, randomValue);
                         System.out.println("Now the price for the route between " + i + " and " + j+ " is "+randomValue);
                     }
                    break;
            }

        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public  void addOrDeleteCities() {
        try {
            readWriteLock.writeLock().lock();
            Random random = new Random();
            switch(random.nextInt(2)){
                case 0:
                    int randomValue=random.nextInt(graph.size());
                    graph.removeCity(randomValue);
                    System.out.println("Deleting routes to the city " +randomValue);
                    break;
                case 1:
                    graph.addCity();
                    System.out.println("Adding routes to the city " +(graph.size()+1));
                    break;
            }
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public  void checkRoute(int city1, int city2) {
        try {
            readWriteLock.readLock().lock();
            graph.printGraph();
           if( graph.get(city1,city2)!=0 && (city1!=city2)){
               System.out.println("At the moment the price for the route between "
                                    + city1 + " and " + city2+ " is "
                                     +graph.get(city1,city2));
           }
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}

