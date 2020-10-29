package it.federicoserini.rediscustom.config.compression;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.xerial.snappy.Snappy;

import java.util.Objects;

/**
 * @author Federico Serini
 */
@Log4j2
public class SnappyCompressorAdapter<T> implements RedisSerializer<T> {

    private final RedisSerializer<T> innerSerializer;

    public SnappyCompressorAdapter(RedisSerializer<T> innerSerializer) {
        this.innerSerializer = innerSerializer;
    }

    @Override
    public byte[] serialize(T t) {
        try {
            byte[] bytes = innerSerializer.serialize(t);
            return Snappy.compress(Objects.requireNonNull(bytes));
        } catch (Exception e) {
            throw new SerializationException(e.getMessage(), e);
        }
    }

    @Override
    public T deserialize(byte[] bytes) {
        try {
            byte[] uncompressedData = Snappy.uncompress(bytes);
            return innerSerializer.deserialize(uncompressedData);
        } catch (Exception e) {
            throw new SerializationException(e.getMessage(), e);
        }
    }

}
