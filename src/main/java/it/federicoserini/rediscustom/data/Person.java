package it.federicoserini.rediscustom.data;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

/**
 * @author Federico Serini
 */
@Data
@RedisHash("PERSON")
public class Person implements Serializable {

    @Id
    private Long id;
    private String name;
    private String surname;

}
