package ru.dm.shop.entity;

import java.math.BigDecimal;

/**
 * Created by Denis on 25.05.16.
 */
public class Num {
    int num;
    BigDecimal fNum;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public BigDecimal getfNum() {
        return fNum;
    }

    public void setfNum(BigDecimal fNum) {
        this.fNum = fNum;
    }
}
