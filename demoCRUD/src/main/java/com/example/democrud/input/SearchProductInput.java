package com.example.democrud.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SearchProductInput {

    private String keyword;
    private String orderBy;
    private String sortBy;
    private int page;
    private int limit;

}
