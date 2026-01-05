package org.example.javafx;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;

@ApplicationScoped
public class Resources {


    @Produces
    public EntityManager produceEntityManager(){
        return HibernateUtil.getSessionFactory().createEntityManager();
    }

    public void close(@Disposes EntityManager em){
        if(em.isOpen()){
            em.close();
        }
    }

}
