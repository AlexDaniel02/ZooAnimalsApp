package com.example.zooapp;
public class Animal {
    private String name;
    private String continent;
    private int id;

    public Animal(int id, String name, String continent) {
        this.id=id;
        this.name = name;
        this.continent = continent;
    }
public int getId()
{
    return id;
}
public void setId(int id)
{
    this.id=id;
}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContinent() {
        return continent;
    }

    public void setContinent(String continent) {
        this.continent = continent;
    }
}