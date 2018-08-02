package com.util.thread.producer_consumer;

import java.util.concurrent.ThreadLocalRandom;

import static com.util.thread.producer_consumer.test.Test.productAvailableLock;
import static com.util.thread.producer_consumer.test.Test.storageAvailableLock;

public abstract class AbstractConsumer implements Runnable {

    public AbstractConsumer(IProductMgr iProductMgr, Object objLock) {
        this.iProductMgr = iProductMgr;
        this.objLock = objLock;
    }

    private IProductMgr iProductMgr;

    private Object objLock;

    private boolean bEnd = false;

    public void endWork() {
        bEnd = true;
    }

    @Override
    public void run() {

        System.out.println("Consumer running");
        while(!bEnd) {
            Object objProduct;
            synchronized (objLock) {
                // Wait until product is available.
                while (!iProductMgr.isProductAvailable()) {
                    try {
//                        objLock.wait();
                        productAvailableLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // take one product from mgr
                objProduct = iProductMgr.takeProduct();

                // Notify producer that status has changed.
//                objLock.notifyAll();
                storageAvailableLock.notifyAll();
            }

            consumeProduct(objProduct);

//            // for test only
//            try {
//                Thread.sleep(ThreadLocalRandom.current().nextInt(1, 10));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }

        System.out.println("Consumer Done");
    }

    public abstract Object consumeProduct(Object objProduct);
}
