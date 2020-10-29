package it.federicoserini.rediscustom.config.serialization;

import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * @author Federico Serini
 */
public class KryoSerializerAdapter<T> implements RedisSerializer<T> {

    @Override
    public byte[] serialize(T t) throws SerializationException {
        return KryoSerializer.serialize(t);
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        return KryoSerializer.deserialize(bytes);
    }
}
