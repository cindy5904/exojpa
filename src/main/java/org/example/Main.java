package org.example;

import org.example.entity.Plante;

import javax.persistence.*;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa");
        EntityManager em = emf.createEntityManager();

        Plante plante = Plante.builder().name("Rose").age(1).color("Red").build();

        em.getTransaction().begin();
        em.persist(plante);
        em.getTransaction().commit();

        try{
            Plante plantefound = em.getReference(Plante.class,1);
            if(plantefound != null){
                System.out.println(plantefound);
            }
        }catch (EntityNotFoundException e){
            System.out.println(e.getMessage());
        }

        Query query = em.createQuery("select p from Plante p where p.id = 2 ");
        Plante planteFoundByQuery = (Plante) query.getSingleResult();

        if(planteFoundByQuery != null){
            System.out.println("via quer : "+ planteFoundByQuery);

        }

        Query query1 = em.createQuery("select p from Plante p");
        List planteList = query1.getResultList();

        for (Object planteInList : planteList){
            System.out.println("from list : "+planteInList);
        }

        em.getTransaction().begin();
        Query query2 = em.createQuery("UPDATE Plante p SET p.name = :name, p.age = :age, p.color = :color WHERE p.id = 1");
        query2.setParameter("name", "Updated Rose");
        query2.setParameter("age", 2);
        query2.setParameter("color", "Updated Red");
        int rowsUpdated = query2.executeUpdate();
        if (rowsUpdated > 0) {
            System.out.println("Plante mise à jour avec succès");
        }
        em.getTransaction().commit();

        em.getTransaction().begin();
        Query query3 = em.createQuery("DELETE Plante p WHERE p.id = 2");

        int rowsDelete = query3.executeUpdate();
        if (rowsDelete > 0) {
            System.out.println("Plante supprimée avec succès");
        }
        em.getTransaction().commit();

        ;



        em.close();
        emf.close();
    }
}