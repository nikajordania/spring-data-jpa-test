package com.example.repository;

import com.example.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    List<Person> findByGroupName(String groupName);

    List<Person> findByAgeBetween(int minAge, int maxAge);

    List<Person> findByGroupNameAndAgeLessThan(String groupName, int age);

    @Query("SELECT COUNT(p) FROM Person p WHERE p.groupName = ?1")
    int countByGroupName(String groupName);

    @Query("SELECT p FROM Person p ORDER BY p.id DESC limit 1")
    Person findLast();
}
