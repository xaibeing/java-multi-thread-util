package com.util.thread.producer_consumer.test;

import com.util.thread.producer_consumer.FIFOProductMgr;
import com.util.thread.producer_consumer.Lock;

public class Test {

    public static final Lock productAvailableLock = new Lock();
    public static final Lock storageAvailableLock = new Lock();

    public static void main(String[] args) {

        final Lock lock = new Lock();
        FIFOProductMgr FIFOProductMgr = new FIFOProductMgr();
        MsgProducer msgProducer = new MsgProducer(FIFOProductMgr, lock);
        MsgConsumer msgConsumer = new MsgConsumer(FIFOProductMgr, lock);

        new Thread(msgProducer).start();
        new Thread(msgConsumer).start();

        try {
            Thread.sleep(20*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        msgProducer.endWork();
        msgConsumer.endWork();

        synchronized (lock) {
            lock.notifyAll();
        }

        System.out.println("main() Done");
    }
}
