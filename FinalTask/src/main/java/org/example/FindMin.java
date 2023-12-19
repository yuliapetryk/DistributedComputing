package org.example;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class FindMin extends ForkJoinTask {

    private Integer[] elements;
    private int a;
    private int b;
    public FindMin(Integer[] elements,int a, int b){
        this.elements=elements;
        this.a=a;
        this.b=b;


    }
    public Integer compute(){
        if ((b-a)<2)
            return Math.min(elements[a],elements[b]);
    else{
    int m=a+((b-a)/2);
    System.out.println(a+ m+b);
    FindMin t1=new FindMin(elements, a,m);
    int result= (int) t1.fork().join();
    return Math.min(new FindMin(elements,m,b).compute(),result);
    }
    }
    public static void main (String[] args) throws InterruptedException, ExecutionException{
        Integer[] elements = new Integer[]{8,-3,2};
        FindMin task= new FindMin(elements,0,elements.length-1);
        ForkJoinPool pool= new ForkJoinPool(1);
        Integer sum= (Integer) pool.invoke(task);
        System.out.println("min"+sum);

    }
    @Override
    public Object getRawResult() {
        return null;
    }

    @Override
    protected void setRawResult(Object value) {

    }

    @Override
    protected boolean exec() {
        return false;
    }
}
