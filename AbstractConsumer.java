package com.util.thread.producer_consumer;

public abstract class AbstractConsumer implements Runnable {

    public AbstractConsumer(IProductMgr iProductMgr, Object objLock) {
        this.iProductMgr = iProductMgr;
        this.objLock = objLock;
    }

    private IProductMgr iProductMgr;

    private Object objLock;

    private volatile boolean bEnd = false;

    public void endWork() {
        bEnd = true;
        System.out.println("  Consumer bEnd = true");
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
                        System.out.println("    Consumer waiting ......");
                        objLock.wait();
                        System.out.println("    Consumer wakeup");

                        if (bEnd) {
                            System.out.println("Consumer Done");
                            return;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // take one product from mgr
                objProduct = iProductMgr.takeProduct();

                // Notify producer that status has changed.
                objLock.notifyAll();
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
