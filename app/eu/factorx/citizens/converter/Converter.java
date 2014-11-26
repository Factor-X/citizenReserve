package eu.factorx.citizens.converter;

import eu.factorx.citizens.dto.technical.DTO;
import play.db.ebean.Model;

/**
 * Created by florian on 20/11/14.
 */
public interface Converter<T extends Model,U extends DTO> {

    public U convert(T dto);
}
