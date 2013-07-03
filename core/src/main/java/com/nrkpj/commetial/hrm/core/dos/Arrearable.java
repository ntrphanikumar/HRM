package com.nrkpj.commetial.hrm.core.dos;

public interface Arrearable<T> {

    double getArrears();

    T setArrears(double arrears);
}
