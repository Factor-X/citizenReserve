package eu.factorx.citizens.converter;

import eu.factorx.citizens.dto.BatchResultItemDTO;
import eu.factorx.citizens.model.batch.BatchResultItem;

/**
 * Created by florian on 4/12/14.
 */
public class BatchResultItemToBatchResultItemDTOConverter implements Converter<BatchResultItem, BatchResultItemDTO> {
    @Override
    public BatchResultItemDTO convert(BatchResultItem entity) {
        BatchResultItemDTO dto = new BatchResultItemDTO();

        dto.setPeriodKey(entity.getPeriod().name());
        dto.setPowerReduction(entity.getPowerReduction());
        if (entity.getDay() != null) {
            dto.setDayKey(entity.getDay().name());
        }

        return dto;
    }
}
