package com.coffe3.mycoffeeshop.service;

import com.coffe3.mycoffeeshop.domain.Coffee;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CoffeeService {

    public List<Coffee> showLatestItem(List<Coffee> list) {

        List<Coffee> latestList;

        if (list.size() > 8) {
            latestList = list.stream().sorted(Comparator.comparing(Coffee::getCoffeeLastUpdated).reversed()).collect(Collectors.toList()).subList(0, 8);
        } else {
            latestList = list.stream().sorted(Comparator.comparing(Coffee::getCoffeeLastUpdated).reversed()).collect(Collectors.toList()).subList(0, list.size());
        }

        return latestList;
    }

    public List<Coffee> showRelatedItems(Coffee coffee, List<Coffee> list) {

        List<Coffee> relatedList;
        list.remove(coffee);

        if (list.size() > 4) {
            relatedList = list.stream().sorted(Comparator.comparing(Coffee::getCoffeeLastUpdated).reversed()).collect(Collectors.toList()).subList(0, 4);
        } else {
            relatedList = list.stream().sorted(Comparator.comparing(Coffee::getCoffeeLastUpdated).reversed()).collect(Collectors.toList()).subList(0, list.size());
        }

        return relatedList;
    }
}
