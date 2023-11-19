package com.example.hometask.util.Sorting;

import org.springframework.data.domain.Sort;
import java.util.Objects;

public class SortingUtil {

    public static Sort createSorting(String sort) {
        String[] parts = sort.split(":");
        if(parts.length <= 1 || parts[0].isEmpty()) {
            return Sort.by(Sort.Direction.ASC, "id");
        }
        if(Objects.equals(parts[1], "asc")){
            return Sort.by(Sort.Direction.ASC, parts[0]);
        } else {
            return Sort.by(Sort.Direction.DESC, parts[0]);
        }
    }
}
