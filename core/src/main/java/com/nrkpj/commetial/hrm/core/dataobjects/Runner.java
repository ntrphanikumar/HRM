package com.nrkpj.commetial.hrm.core.dataobjects;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Runner {
    public static void main(String[] args) {
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();

        House house = new House();
        house.setHouseName("MIG-154");
        house.setAddress("T V Colony");
        house.setLastModifiedOn(new Date());
        house.setCreatedOn(new Date());

        Long id = (Long) session.save(house);
        house.setId(id);

        session.getTransaction().commit();

        session.close();
    }
}
