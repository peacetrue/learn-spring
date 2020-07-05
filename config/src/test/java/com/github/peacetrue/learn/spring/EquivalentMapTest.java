package com.github.peacetrue.learn.spring;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * @author : xiayx
 * @since : 2020-07-04 13:58
 **/
@ActiveProfiles({"list", "equivalent-map"})
@SpringBootTest
public class EquivalentMapTest {

    @Autowired
    private ConfigListProperties listProperties;
    @Autowired
    private ConfigEquivalentMapProperties mapProperties;

    @Test
    void equals() {
        Assertions.assertEquals(
                listProperties.getStaticResources(),
                to(mapProperties.getMapStaticResources())
        );
        Assertions.assertEquals(
                to(listProperties.getStaticResources()),
                mapProperties.getMapStaticResources()
        );
    }

    public static <T> List<T> to(Map<Integer, T> map) {
        List<T> list = new ArrayList<>(map.size());
        IntStream.range(0, map.size()).forEach(index -> list.add(null));
        map.forEach(list::set);
        return list;
    }

    public static <T> Map<Integer, T> to(List<T> list) {
        Map<Integer, T> map = new LinkedHashMap<>(list.size());
        IntStream.range(0, list.size()).forEach(index -> map.put(index, list.get(index)));
        return map;
    }
}
