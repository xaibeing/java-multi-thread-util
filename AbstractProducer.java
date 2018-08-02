package com.util.thread.producer_consumer;

import java.util.concurrent.ThreadLocalRandom;

import static com.util.thread.producer_consumer.test.Test.productAvailableLock;
import static com.util.thread.producer_consumer.test.Test.storageAvailableLock;

public abstract class AbstractProducer implements Runnable {

    public AbstractProducer(IProductMgr iProductMgr, Object objLock) {
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
        System.out.println("Producer running");
        while (!bEnd) {
            synchronized (objLock) {
                // Wait until Storage is available.
                while (!iProductMgr.isStorageAvailable()) {
                    try {
//                        objLock.wait();
                        storageAvailableLock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // create one product
                Object objProduct = createProduct();

                // put one product into mgr
                iProductMgr.putProduct(objProduct);

                // Notify producer that status has changed.
//                objLock.notifyAll();
                productAvailableLock.notifyAll();
            }

//            // for test only
//            try {
//                Thread.sleep(ThreadLocalRandom.current().nextInt(1, 10));
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        System.out.println("Producer Done");
    }

    public abstract Object createProduct();
}
