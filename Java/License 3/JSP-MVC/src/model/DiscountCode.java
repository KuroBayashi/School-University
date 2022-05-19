/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author pedago
 */
public class DiscountCode {
    
    private String code;
    private float taux;
    
    public DiscountCode(String code, float taux) {
        this.code = code;
        this.taux = taux;
    }
    
    public String getCode() {
        return this.code;
    }
    
    public DiscountCode setCode(String newCode) {
        this.code = newCode;
        
        return this;
    }
    
    public float getTaux() {
        return this.taux;
    }
    
    public DiscountCode setTaux(float newTaux) {
        this.taux = newTaux;
        
        return this;
    }
    
    public boolean isValid() {
        return this.code != null && !this.code.isEmpty() && this.taux >= 0.0f; 
    }
}
