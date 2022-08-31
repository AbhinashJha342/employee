package com.example.employee.domain;

import com.example.employee.web.schema.NameDTO;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="employee_names")
public class Name {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String first;
    private String last;

    public Name(String first, String last) {
        this.first = first;
        this.last = last;
    }

    protected Name() {
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public static NameDTO from(Name name){
        return new NameDTO(name.getFirst(), name.getLast());
    }

    @Override
    public String toString() {
        return "Name{" +
                "id=" + id +
                ", first='" + first + '\'' +
                ", last='" + last + '\'' +
                '}';
    }
}
