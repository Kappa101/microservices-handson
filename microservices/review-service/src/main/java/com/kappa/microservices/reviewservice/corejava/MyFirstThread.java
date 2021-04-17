package com.kappa.microservices.reviewservice.corejava;


import java.util.concurrent.TimeUnit;

public class MyFirstThread {


    public static void main(String[] args) {
        System.out.println("Inside MAIN...");

        Task task = new Task();

        Thread thread = new Thread(task);
        thread.start();

        try {
            System.out.println("current thread - " + Thread.currentThread().getName());
//            Thread.sleep(3000); /*current thread should seize it's execution till sleep seconds*/
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}

class Task implements Runnable { // when implements Runnable , you need to override run() method

    @Override
    public void run() {
        System.out.println("current-thread : " + Thread.currentThread().getName());
        System.out.println("Inside run...");
        go();
        more();
    }

    private void go() {

        System.out.println("current-thread : " + Thread.currentThread().getName());
        System.out.println("inside GO... Thread 2");
        more();
    }

    private void more() {
        System.out.println("inside MORE... Thread 2");
    }

}