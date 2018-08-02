package com.util.thread.producer_consumer;

public abstract class AbstractProducer implements Runnable {

    public AbstractProducer(IProductMgr iProductMgr, Object objLock) {
        this.iProductMgr = iProductMgr;
        this.objLock = objLock;
    }

    private IProductMgr iProductMgr;

    private Object objLock;

    private volatile boolean bEnd = false;

    public void endWork() {
        bEnd = true;
        System.out.println("    Producer bEnd = true");
    }

    @Override
    public void run() {

        System.out.println("Producer running");

        while (!bEnd) {
            synchronized (objLock) {
                // Wait until Storage is available.
                while (!iProductMgr.isStorageAvailable()) {
                    try {
                        System.out.println("    Producer waiting ......");
                        objLock.wait();
                        System.out.println("    Producer wakeup");

                        if (bEnd) {
                            System.out.println("Producer Done");
                            return;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // create one product
                Object objProduct = createProduct();

                // put one product into mgr
                iProductMgr.putProduct(objProduct);

                // Notify producer that status has changed.
                objLock.notifyAll();
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
