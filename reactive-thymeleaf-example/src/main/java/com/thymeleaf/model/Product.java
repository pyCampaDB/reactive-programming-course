package com.thymeleaf.model;

public class Product {
    private String concept;
    private int number, amount;

    public Product() {
    }

    public Product(String concept, int number, int amount) {
        this.concept = concept;
        this.number = number;
        this.amount = amount;
    }

    public String getConcept() {
        return concept;
    }

    public void setConcept(String concept) {
        this.concept = concept;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
