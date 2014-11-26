package eu.factorx.citizens.dto.technical;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.factorx.citizens.util.exception.MyRuntimeException;
import play.mvc.Content;

import java.io.IOException;

/**
 * Created by florian on 18/11/14.
 */
public class DTO implements Content {

    private String __type;

    public static <T extends DTO> T getDTO(JsonNode data, Class<T> type) {
        if (data != null) {
            ObjectMapper mapper = new ObjectMapper();
            JsonParser jp = data.traverse();
            try {
                T dto = mapper.readValue(jp, type);
                /*
                if (dto == null) {
					throw new MyRuntimeException("Validation of DTO : dto is null");
				}
				*/
                //dto.validate();
                return dto;

            } catch (IOException e) {
                throw new MyRuntimeException(e, "Validation of DTO failed");
            }
        }
        throw new MyRuntimeException("Validation of DTO : data is null");
    }

    public String get__type() {
        return this.getClass().getCanonicalName();
    }

    public void set__type(String __type) {
        if (!get__type().equals(__type)) {
            throw new MyRuntimeException("Wrong type of DTO received. Expected : " + get__type() + ", receive : " + __type);
        }
    }

    @Override
    public String body() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new MyRuntimeException(e, e.getMessage());
        }
    }

    @Override
    public String contentType() {
        return "application/json; charset=utf-8";
    }
}
