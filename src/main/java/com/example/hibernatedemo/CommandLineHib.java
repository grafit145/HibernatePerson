/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.hibernatedemo;

import com.example.hibernatedemo.entity.City;
import com.example.hibernatedemo.entity.Contact;
import com.example.hibernatedemo.entity.Person;
import com.example.hibernatedemo.entity.enums.Gender;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author grafit145
 */
@Component
public class CommandLineHib implements CommandLineRunner{
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        List<City> cities = new ArrayList<>();
        City city1 = new City();
        City city2 = new City();
        city1.setName("Москва");
        city2.setName("Тамбов");
        cities.add(city1);
        cities.add(city2);
        
//        for(City entity : cities){
//            entityManager.persist(entity);
//        }

        List<String> names = List.of("Вася", "Петя", "Гена", "Виталия");
        Random random = new Random();
        IntStream.range(0, 100).
                forEach(i -> {
                    Person person = Person.builder()
                            .name(names.get(random.nextInt(names.size())))
                            .age(random.nextInt(25))
                            .gender(Gender.MALE)
                            .contact(Contact.builder().email("some" + i + "@mail.ru")
                            .phoneNumber("436356457")
                            .build())
                            .city(cities.get(random.nextInt(cities.size())))
                            .build();
 //                   entityManager.persist(person);
                            
                });
        
//        System.out.println(entityManager.find(Person.class, 3L));

          Query query = entityManager.createQuery("select p from Person p where p.age > :age", Person.class);
          query.setParameter("age", 20);
          List<Person> resultList = query.getResultList();
          System.out.println("Old result: " + resultList.size());
        
    }
}
