package org.zjw.web.entity;

import lombok.Data;

@Data
public class User {

    public Integer age;
    public String name;
    public Long weight;
    public Boolean married;

    public User() {
        super();
    }

    public User(int age, String name, long weight, boolean married) {
        super();
        this.age = age;
        this.name = name;
        this.weight = weight;
        this.married = married;
    }
}
