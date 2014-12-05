package eu.factorx.citizens.dto;

import eu.factorx.citizens.dto.technical.DTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florian on 4/12/14.
 */
public class ListDTO extends DTO {

    private List<DTO> list;

    public ListDTO() {
    }

    public List<DTO> getList() {
        return list;
    }

    public void setList(List<DTO> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "ListDTO{" +
                "list=" + list +
                '}';
    }

    public void add(BatchResultDTO convert) {
        if(list==null){
            list= new ArrayList<>();
        }
        list.add(convert);
    }
}
