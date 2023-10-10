package com.hkjava.demo.demofinnhubdb.model.Graph;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
/*
 * By default, the equals() method compares two objects by reference. This means that two objects are equal if they are the same object in memory. The hashCode() method is used to generate a hash code
 * for an object, which is used to identify the object in a hash table.
 * 
 * When the lombok.EqualsAndHashCode annotation is applied to a class, Lombok will generate the equals() and hashCode() methods to compare the objects by value. This means that two objects are equal
 * if they have the same values for all of their fields.
 * 
 * answer : used to check pass by value.
 */
public class Price {

  private double price;

}
