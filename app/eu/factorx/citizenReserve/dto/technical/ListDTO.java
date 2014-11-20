package eu.factorx.citizenReserve.dto.technical;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by florian on 20/11/14.
 */
public class ListDTO<T extends DTO> extends DTO {

    private List<T> list;

    public ListDTO() {
        list = new ArrayList<>();
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public void add(T dto) {
        list.add(dto);
    }

    @Override
    public String toString() {
        return "ListDTO{" +
                "list=" + list +
                '}';
    }
}
