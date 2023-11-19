package com.example.hometask.unit;

import com.example.hometask.util.Sorting.SortingUtil;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Sort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SortingUtilTest {

    @Test
    void testCreateSorting_WithValidInputAscending() {
        String sort = "propertyName:asc";
        Sort expectedSort = Sort.by(Sort.Direction.ASC, "propertyName");
        Sort result = SortingUtil.createSorting(sort);
        assertEquals(expectedSort, result);
    }

    @Test
    void testCreateSorting_WithValidInputDescending() {
        String sort = "propertyName:desc";
        Sort expectedSort = Sort.by(Sort.Direction.DESC, "propertyName");
        Sort result = SortingUtil.createSorting(sort);
        assertEquals(expectedSort, result);
    }

    // Add more test cases to cover edge cases and different scenarios
}
