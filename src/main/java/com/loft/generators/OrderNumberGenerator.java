package com.loft.generators;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.query.Query;

import java.io.Serializable;
import java.util.Calendar;

public class OrderNumberGenerator implements IdentifierGenerator {

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        String prefix = "ZAM";
        Query query = sharedSessionContractImplementor.createQuery("SELECT COUNT(o.orderNumber) FROM Order o WHERE YEAR(o.createDate) = YEAR(current_date())");

        Long id = (Long) query.uniqueResult();
        //count(order_key) from users WHERE YEAR(create_date) = YEAR(current_date())").getSingleResult();
//long id = 10;
        return String.format("%s/%s/%07d", prefix, Calendar.getInstance().get(Calendar.YEAR), id + 1);
    }
}
