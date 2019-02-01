package com.coffe3.mycoffeeshop.service;

import com.coffe3.mycoffeeshop.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductService {

    public List<Product> showLatestItem(List<Product> list) {

        List<Product> latestList;

        if (list.size() > 8) {
            latestList = list.stream()
                    .sorted(Comparator.comparing(Product::getProductLastUpdated).reversed())
                    .collect(Collectors.toList()).subList(0, 8);
        } else {
            latestList = list.stream()
                    .sorted(Comparator.comparing(Product::getProductLastUpdated).reversed())
                    .collect(Collectors.toList()).subList(0, list.size());
        }

        return latestList;
    }

    public List<Product> showRelatedItems(Product product, List<Product> list) {

        List<Product> relatedList;
        list.remove(product);

        if (list.size() > 4) {
            relatedList = list.stream()
                    .sorted(Comparator.comparing(Product::getProductLastUpdated).reversed())
                    .collect(Collectors.toList()).subList(0, 4);
        } else {
            relatedList = list.stream()
                    .sorted(Comparator.comparing(Product::getProductLastUpdated).reversed())
                    .collect(Collectors.toList()).subList(0, list.size());
        }

        return relatedList;
    }
}
