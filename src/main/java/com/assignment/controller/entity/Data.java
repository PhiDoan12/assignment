package com.assignment.controller.entity;

import java.util.HashMap;

public class Data extends HashMap<String, Object> {
    private static final String COUNT = "_count";

    public void setData(String line) {
        String[] lineArray = line.split("/");
        if (lineArray.length < 3) {
            return;
        }
        setProductName(lineArray[0]);
        setThemeName(lineArray[0], lineArray[1]);
        setMethodName(lineArray[0], lineArray[1], lineArray[2]);
    }

    private void setProductName(String productName) {
        if (!this.containsKey(productName)) {
            ProductInfo productInfo = new ProductInfo();
            this.put(productName, productInfo);
        }
    }

    private void setThemeName(String productName, String themeName) {
        ProductInfo productInfo = (ProductInfo) this.get(productName);
        if (!productInfo.containsKey(themeName)) {
            ThemeInfo themeInfo = new ThemeInfo();
            themeInfo.put(COUNT, 0);
            if (!productInfo.containsKey(COUNT)) {
                productInfo.put(COUNT, 1);
            } else {
                productInfo.put(COUNT, (Integer) productInfo.get(COUNT) + 1);
            }
            productInfo.put(themeName, themeInfo);
        } else {
            productInfo.put(COUNT, (Integer) productInfo.get(COUNT) + 1);
        }
        this.put(productName, productInfo);
    }

    private void setMethodName(String productName, String themeName, String methodName) {
        ProductInfo productInfo = (ProductInfo) this.get(productName);
        ThemeInfo themeInfo = (ThemeInfo) productInfo.get(themeName);
        if (!themeInfo.containsKey(methodName)) {
            themeInfo.put(methodName, 1);
        } else {
            themeInfo.put(methodName, themeInfo.get(methodName) + 1);
        }
        themeInfo.put(COUNT, themeInfo.get(COUNT) + 1);
        productInfo.put(themeName, themeInfo);
        this.put(productName, productInfo);
    }
}
