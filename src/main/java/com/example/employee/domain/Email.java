package com.example.employee.domain;

import com.example.employee.web.schema.EmailDTO;
import com.example.employee.web.schema.EmailType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(unique = true)
    private String emailAddress;

    @Enumerated(EnumType.STRING)
    private EmailType emailType;

    public Email(String emailAddress, EmailType emailType) {
        this.emailAddress = emailAddress;
        this.emailType = emailType;
    }

    protected Email() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public EmailType getEmailType() {
        return emailType;
    }

    public void setEmailType(EmailType emailType) {
        this.emailType = emailType;
    }

    public static EmailDTO from(Email email){
        return new EmailDTO(email.getEmailAddress(), email.getEmailType());
    }
}
