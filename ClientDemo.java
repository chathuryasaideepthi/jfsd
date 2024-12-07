package com.klef.jfsd.exam;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class ClientDemo {

    public static void main(String[] args) {
        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Customer.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();
        
        try {
            session.beginTransaction();

            Customer customer1 = new Customer();
            customer1.setName("abc");
            customer1.setEmail("abc@gmail.com");
            customer1.setAge(30);
            customer1.setLocation("vijaywada");

            session.save(customer1); // Save the customer object

            session.getTransaction().commit();

            session = factory.getCurrentSession(); // Reopen session
            session.beginTransaction();

            Criteria criteria = session.createCriteria(Customer.class);
            
            criteria.add(Restrictions.eq("age", 20));
            criteria.add(Restrictions.eq("location", "New York"));

            List<Customer> customers = criteria.list();
            for (Customer customer : customers) {
                System.out.println(customer.getName() + " - " + customer.getEmail());
            }

            session.getTransaction().commit();
        } finally {
            factory.close(); 
        }
    }
}
