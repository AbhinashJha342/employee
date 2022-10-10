package com.example.employee.web.schema;

import com.example.employee.domain.Email;

public class EmailDTO {

    private String address;

    private EmailType type;

    public EmailDTO(String address, EmailType type) {
        this.address = address;
        this.type = type;
    }

    public EmailDTO() {
    }

    public String getAddress() {
        return address;
    }

    public EmailType getType() {
        return type;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static Email to(EmailDTO emailDTO){
        return new Email(emailDTO.getAddress(), emailDTO.getType());
    }

    public static class Builder {

        private String emailAddress;

        private EmailType emailType;

        public Builder setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
            return this;
        }

        public Builder setEmailType(EmailType emailType) {
            this.emailType = emailType;
            return this;
        }

        public EmailDTO build(){
            return new EmailDTO(this.emailAddress, this.emailType);
        }
    }
}
