package com.thymeleaf.repository;

import com.thymeleaf.model.Product;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository {
    private static List<Product> list = new ArrayList<>();
    private static List<Product> list2 = new ArrayList<>();
    static {
        list.add(new Product("laptop", 1, 200));
        list.add(new Product("tablet", 2, 759));
        list.add(new Product("knife", 3, 74));

        list2.add(new Product("mobile", 4, 269));
        list2.add(new Product("mouse", 5, 722));
        list2.add(new Product("keyboard", 6, 89));
    }

    public Flux<Product> searchAll(){
        return Flux.fromIterable(list).delayElements(Duration.ofSeconds(3));
    }

    public Flux<Product> searchOthers(){
        return Flux.fromIterable(list2).delayElements(Duration.ofSeconds(5));
    }
}
