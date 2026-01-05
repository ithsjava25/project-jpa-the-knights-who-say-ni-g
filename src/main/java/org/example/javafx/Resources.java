package org.example.javafx;

import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Resources {


    @Produces
    public EntityManager openEntityManager(){
        return HibernateUtil.getSessionFactory().createEntityManager();
    }

    public void closeEntityManager(@Disposes EntityManager em){
        if(em.isOpen()){
            em.close();
        }
    }

}
