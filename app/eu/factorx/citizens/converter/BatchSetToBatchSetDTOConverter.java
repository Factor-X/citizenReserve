package eu.factorx.citizens.converter;

import eu.factorx.citizens.dto.BatchResultSetDTO;
import eu.factorx.citizens.model.batch.BatchResultSet;

/**
 * Created by florian on 4/12/14.
 */
public class BatchSetToBatchSetDTOConverter implements Converter<BatchResultSet, BatchResultSetDTO> {

    BatchResultToBatchResultDTOConverter converter = new BatchResultToBatchResultDTOConverter();

    @Override
    public BatchResultSetDTO convert(BatchResultSet entity) {

        BatchResultSetDTO dto = new BatchResultSetDTO();

        dto.setDate(entity.getLastUpdateDate());

        dto.setEffectiveBatch(converter.convert(entity.getEffectiveBach()));

        dto.setPotentialBatch(converter.convert(entity.getPotentialBach()));

        return dto;
    }
}
