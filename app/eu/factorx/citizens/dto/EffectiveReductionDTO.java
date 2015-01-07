package eu.factorx.citizens.dto;

import eu.factorx.citizens.dto.technical.DTO;

import java.util.ArrayList;
import java.util.List;

public class EffectiveReductionDTO extends DTO {


	private List<ReductionDTO> reductions;

	private ArrayList<String> logs;

	public EffectiveReductionDTO() {
	}

	public List<ReductionDTO> getReductions() {
		return reductions;
	}

	public void setReductions(List<ReductionDTO> reductions) {
		this.reductions = reductions;
	}

	public ArrayList<String> getLogs() {
		return logs;
	}

	public void setLogs(ArrayList<String> logs) {
		this.logs = logs;
	}

	@Override
    public String toString() {
        return "EffectiveReductionDTO{" +
                ", reductions=" + reductions +
				", logs=" + logs +
                '}';
    }
}
