package com.util.thread.producer_consumer;

public interface IProductMgr {
    boolean isProductAvailable();
    boolean isStorageAvailable();
    void putProduct(Object objProduct);
    Object takeProduct();
}
