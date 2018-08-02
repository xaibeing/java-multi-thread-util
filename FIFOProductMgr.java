package com.util.thread.producer_consumer;

import java.util.LinkedList;

public class FIFOProductMgr implements IProductMgr {

    public FIFOProductMgr() {
    }

    public FIFOProductMgr(int nStorageSize) {
        this.nStorageSize = nStorageSize;
    }

    private int nStorageSize = 10;

    private LinkedList<String> lstProducts = new LinkedList<>();

    @Override
    public boolean isProductAvailable() {
        return !lstProducts.isEmpty();
    }

    @Override
    public boolean isStorageAvailable() {
        return lstProducts.size() < nStorageSize;
    }

    @Override
    public void putProduct(Object objProduct) {
        System.out.println("FIFOProductMgr  add " + objProduct);
        lstProducts.add(objProduct.toString());
    }

    @Override
    public Object takeProduct() {
        String strCurrentProduct = lstProducts.removeFirst();
        System.out.println("FIFOProductMgr  take " + strCurrentProduct);
        return strCurrentProduct;
    }
}
