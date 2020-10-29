package it.federicoserini.rediscustom.config.serialization;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.util.Pool;
import lombok.extern.log4j.Log4j2;

import java.io.ByteArrayOutputStream;

/**
 * @author Federico Serini
 */
@Log4j2
public class KryoSerializer {
    static Pool<Kryo> pool = initializePool();

    private static Pool<Kryo> initializePool(){
        return new Pool<Kryo>(true, false, 8) {
            protected Kryo create () {
                Kryo kryo = new Kryo();

                // TODO registra componenti
                kryo.setRegistrationRequired(false);
                return kryo;
            }
        };
    }

    public static byte[] serialize(final Object object){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Output output = new Output(stream);
        Kryo kryoPoolElement = pool.obtain();
        kryoPoolElement.writeClassAndObject(output, object);
        output.close();
        pool.free(kryoPoolElement);
        return stream.toByteArray();
    }

    @SuppressWarnings("unchecked")
    public static <O> O deserialize(final byte[] dataStream){
        Kryo kryoPoolElement = pool.obtain();
        O deserializedObject = (O) kryoPoolElement.readClassAndObject(new Input(dataStream));
        pool.free(kryoPoolElement);

        return deserializedObject;
    }
}
