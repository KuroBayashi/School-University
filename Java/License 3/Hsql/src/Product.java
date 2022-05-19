/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testingwithhsqldb;

/**
 *
 * @author tboisnie
 */
public class Product {
    
    private int id;
    private String name;
    private int price;
    
    public Product() {
        this("", -1);
    }
  
    public Product(String name, int price) {
        this(-1, name, price);
    }
    
    public Product(int id, String name, int price){
        this.id = id;
        this.name = name;
        this.price = price;
    }
    
    public int getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public int getPrice() {
        return this.price;
    }
}
