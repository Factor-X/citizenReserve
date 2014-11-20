package eu.factorx.citizenReserve.converter;

import eu.factorx.citizenReserve.dto.technical.DTO;
import play.db.ebean.Model;

import javax.persistence.Entity;

/**
 * Created by florian on 20/11/14.
 */
public interface Converter<T extends Model,U extends DTO> {

    public U convert(T dto);
}
