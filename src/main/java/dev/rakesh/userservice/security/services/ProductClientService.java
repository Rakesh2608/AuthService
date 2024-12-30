package dev.rakesh.userservice.security.services;

import dev.rakesh.userservice.dtos.ProductRequestDto;
import dev.rakesh.userservice.dtos.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ProductClientService {
    private final RestTemplateBuilder restTemplateBuilder;

    public ProductResponseDto createProduct(String name,String image,Double price){
        ProductRequestDto requestDto=new ProductRequestDto();
        requestDto.setName(name);
        requestDto.setImage(image);
        requestDto.setPrice(price);

        RestTemplate restTemplate=restTemplateBuilder.build();

        ProductResponseDto response=restTemplate.postForObject("http://localhost:8000",requestDto,ProductResponseDto.class);

        return response;
    }
}
