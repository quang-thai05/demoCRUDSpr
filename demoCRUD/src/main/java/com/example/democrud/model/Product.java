package com.example.democrud.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private int id;

    private String name;

    private String description;

    private String orgId;

    private String createdBy;

    private long createdDate;

    private String lastModifiedBy;

    private long lastModifiedDate;

    private boolean activeFlag;

    private boolean deleteFlag;

}
