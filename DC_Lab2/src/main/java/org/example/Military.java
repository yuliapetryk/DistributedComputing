package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

public class Military   {

    public static class Property{
       int id;

       Property(int number){
           this.id=number;
       }

       int getId(){
           return this.id;
       }
    }

    private final static List<Property> propetry = new ArrayList<>();;

    public static void main(String[] args) throws InterruptedException {

       createProperty();

       Thread ivanovThread = new Thread(() -> {
            try { actIvanov();
            } catch (InterruptedException exeption) {
                exeption.printStackTrace();
            }
        });

        Thread petrovThread = new Thread(() -> {
            try { actPetrov();
            } catch (InterruptedException exeption) {
                exeption.printStackTrace();
            }
        });

        Thread nechyporchukThread = new Thread(() -> {
            try { actNechyporchuk();
            } catch (InterruptedException exeption) {
                exeption.printStackTrace();
            }
        });

        ivanovThread.start();
        petrovThread.start();
        nechyporchukThread.start();

        ivanovThread.join();
        petrovThread.join();
        nechyporchukThread.join();
    }

     public static void createProperty(){
        for (int i=0; i<5; i++){
            Property property= new Property(i);
            propetry.add(property);
        }
     }

    private static final SynchronousQueue<Property> Delivery= new SynchronousQueue();

    private static final SynchronousQueue<Property>Loading =new SynchronousQueue();

    public static void actIvanov() throws InterruptedException {
         int delivered = 0;
         while (delivered!=propetry.size()) {
             System.out.println("Ivanov: "+ propetry.get(delivered).getId()); //rewrite!!!!!!
             Delivery.put(propetry.get(delivered));
             delivered++;
         }
    }

    public static void actPetrov() throws InterruptedException {
        int loaded = 0;
        while ( loaded!=propetry.size()) {
            Property removed = Delivery.take();
            System.out.println("Petrov: "+ propetry.get(loaded).getId());
            Loading.put(removed);
            loaded++;
            }
        }

    public static void actNechyporchuk() throws InterruptedException {
        int counted = 0;
        while (counted!=propetry.size()) {
            Property removed =Loading.take();
            System.out.println("Nechyporchuk: "+ removed.getId());
            counted++;
        }
    }
}
