package org.zjw.web.entity;

import lombok.Data;

@Data
public class User2 {

    public Integer age;
    public String name;
    public Long weight;
    public Boolean married;

    public User2() {
        super();
    }

    public User2(int age, String name, long weight, boolean married) {
        super();
        this.age = age;
        this.name = name;
        this.weight = weight;
        this.married = married;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public Boolean getMarried() {
        return married;
    }

    public void setMarried(Boolean married) {
        this.married = married;
    }
}
