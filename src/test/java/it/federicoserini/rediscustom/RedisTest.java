package it.federicoserini.rediscustom;

import it.federicoserini.rediscustom.data.Person;
import it.federicoserini.rediscustom.repository.PersonRepository;
import junit.framework.TestCase;
import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Federico Serini
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Log4j2
public class RedisTest extends TestCase {

    @Autowired
    private PersonRepository personRepository;

    @Test
    public void test() {
        Person person = new Person();
        person.setId(1L);
        person.setName("Nome Test");
        person.setSurname("Cognome Test");

        Long start = System.nanoTime();
        personRepository.save(person);
        Long end = System.nanoTime();

        log.info("tempo di scrittura: {}s", (end-start)/1000000000.0);
        start = System.nanoTime();
        person = personRepository.findById(2L);
        end = System.nanoTime();
        log.info("tempo di lettura: {}s", (end-start)/1000000000.0);

        if (person != null) {
            log.info("**************************** " + person.toString());
        }
    }

}
