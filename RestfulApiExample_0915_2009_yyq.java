// 代码生成时间: 2025-09-15 20:09:49
package com.example.demo;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.PathVariable;
import io.micronaut.http.annotation.QueryValue;
import io.micronaut.http.exceptions.HttpStatusException;
import io.micronaut.http.status.HttpStatus;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// 使用@Controller注解定义控制器类，用于处理API请求
@Controller("/api/items")
public class RestfulApiExample {

    private final List<Item> items = new ArrayList<>();

    public RestfulApiExample() {
        // 初始化一些示例数据
        items.add(new Item(1L, "It's a book"));
        items.add(new Item(2L, "It's a pen"));
    }

    // 使用@Get注解定义GET请求方法，返回所有项目
    @Get("/")
    public List<Item> listAllItems() {
        return items;
    }

    // 使用@Get和@PathVariable注解定义GET请求方法，根据ID返回单个项目
    @Get("/{id}")
    public HttpResponse<Item> getItemById(@PathVariable Long id) {
        return Optional.ofNullable(items.stream()
                .filter(item -> item.getId().equals(id))
                .findFirst())
                .map(HttpResponse::ok)
                .orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, "Item not found"));
    }

    // 内部类，用于表示项目
    public static class Item {
        private Long id;
        private String description;

        public Item(Long id, String description) {
            this.id = id;
            this.description = description;
        }

        public Long getId() {
            return id;
        }

        public String getDescription() {
            return description;
        }
    }
}
