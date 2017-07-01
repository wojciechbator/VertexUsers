package com.vertexFun.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.concurrent.atomic.AtomicInteger;

@Getter
@Setter
public class User {
    private static final AtomicInteger COUNTER = new AtomicInteger();
    private final int id;
    private String name;
    private String email;

    public User(String name, String email) {
        this.id = COUNTER.getAndIncrement();
        this.name = name;
        this.email = email;
    }
}
