package db;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Created by loke09 on 10/01/17.
 */
public class JsonDateDeserializer implements JsonDeserializer<Date> {


    @Override
    public Date deserialize(JsonElement json, Type arg1,
                            JsonDeserializationContext arg2) throws JsonParseException {
        String s = json.getAsJsonPrimitive().getAsString();
        //long l = Long.parseLong(s.substring(6, s.length() - 2));
        //long l = Long.parseLong(s);
        Date d = new Date(s);
        return d;
    }
}
