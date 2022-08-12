package com.example.democrud.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchProductParam {

    private String keyword = "";

    private String orderColumn;

    private String typeSort;

    private int pageIndex;

    private int sizeOfPage;

}
