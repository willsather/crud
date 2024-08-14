package com.satherw.crud;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Person {

    private Long id;

    private String name;

    private String surname;
}
