package dev.rakesh.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDto {
    private Long Id;
    private String name;
    private Double price;
    private String category;
}
