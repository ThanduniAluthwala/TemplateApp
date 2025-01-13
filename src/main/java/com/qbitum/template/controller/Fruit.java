package com.qbitum.template.controller;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table("fruits")
public class Fruit {
    @Id
    @Column("id")
    private Integer id;

    @Column("fruit_name")
    private String name;
}
