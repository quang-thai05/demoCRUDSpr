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
public class InsertProductParam {

    @NotNull
    private String name;

    private String description;

    @NotNull
    private String orgId;

}
