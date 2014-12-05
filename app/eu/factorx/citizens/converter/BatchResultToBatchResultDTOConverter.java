package eu.factorx.citizens.converter;

import eu.factorx.citizens.dto.BatchResultDTO;
import eu.factorx.citizens.dto.BatchResultItemDTO;
import eu.factorx.citizens.model.batch.BatchResult;
import eu.factorx.citizens.model.batch.BatchResultItem;

/**
 * Created by florian on 4/12/14.
 */
public class BatchResultToBatchResultDTOConverter implements Converter<BatchResult, BatchResultDTO> {

    private BatchResultItemToBatchResultItemDTOConverter converter = new BatchResultItemToBatchResultItemDTOConverter();

    @Override
    public BatchResultDTO convert(BatchResult entity) {

        BatchResultDTO dto = new BatchResultDTO();

        dto.setNbErrors(entity.getNbErrors());
        dto.setNbSurveys(entity.getNbSurveys());
        dto.setReductionTypeKey(entity.getReductionType().name());
        dto.setNbParticipants(entity.getNbParticipants());

        for (BatchResultItem batchResultItem : entity.getResultItems()) {
            dto.addBatchResultItem(converter.convert(batchResultItem));
        }

        return dto;
    }
}
