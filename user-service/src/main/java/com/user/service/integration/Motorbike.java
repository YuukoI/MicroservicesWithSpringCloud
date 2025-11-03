package com.user.service.integration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Motorbike {

    private String brand;

    private String model;

    private String plate;

    private Long userId;

}
