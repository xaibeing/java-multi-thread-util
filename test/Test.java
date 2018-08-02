package com.util.thread.producer_consumer.test;

import com.util.thread.producer_consumer.FIFOProductMgr;
import com.util.thread.producer_consumer.Lock;

public class Test {

    public static void main(String[] args) {

        final Lock lock = new Lock();
        FIFOProductMgr FIFOProductMgr = new FIFOProductMgr();
        MsgProducer msgProducer = new MsgProducer(FIFOProductMgr, lock);
        MsgConsumer msgConsumer = new MsgConsumer(FIFOProductMgr, lock);

        new Thread(msgProducer).start();
        new Thread(msgConsumer).start();

        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("main() ending threads");
        msgProducer.endWork();
        msgConsumer.endWork();

        synchronized (lock) {
            System.out.println("main() notifyAll 1");
            lock.notifyAll();
            System.out.println("main() notifyAll 2");
        }

        System.out.println("main() Done");
    }
}
