import com.example.config.AppConfig;
import com.example.entity.Person;
import com.example.repository.PersonRepository;
import com.github.javafaker.Faker;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class MainAppTest {
    PersonRepository repository;
    AnnotationConfigApplicationContext context;

    @BeforeClass
    public void beforeClass() {
        context = new AnnotationConfigApplicationContext(AppConfig.class);
        repository = context.getBean(PersonRepository.class);
        generateFakeData();
    }

    void generateFakeData() {
        repository.deleteAll();
        Faker faker = new Faker();
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            persons.add(new Person().name(faker.name().firstName())
                    .lastname(faker.name().lastName())
                    .groupName(faker.options().option("Group1", "Group2", "Group3"))
                    .age(faker.number().numberBetween(1, 100)
                    ));
        }
        repository.saveAll(persons);
    }

    @Test
    public void t1() {
        repository.findAll().forEach(System.out::println);
    }

    @Test
    public void t2() {
        repository.findByGroupName("Group1").forEach(System.out::println);
    }

    @Test
    public void t3() {
        List<Person> byAgeBetween = repository.findByAgeBetween(10, 30);

        byAgeBetween.forEach(System.out::println);
    }

    @Test
    public void insertPersonTest() {
        Person person = new Person()
                .name("Person 1")
                .age(20)
                .groupName("Group1");

        repository.save(person);
    }

    @Test
    public void updatePersonTest() {
        Person person = repository.findLast();
        person.name("Person 1 Updated");

        repository.save(person);
    }

    @Test
    public void deletePersonTest() {
        repository.deleteById(1);
    }

    @AfterClass
    public void afterClass() {
        context.close();
    }

}
