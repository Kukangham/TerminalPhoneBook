package terminalpb.model;

import java.time.LocalDateTime;

public abstract class Contact {
    protected String name;
    protected String surname;
    protected String number;

    protected LocalDateTime createdTime;

    protected LocalDateTime lastEditTime;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setLastEditTime(LocalDateTime lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    @Override
    public abstract String toString();

    public abstract static class ContactBuilder {
        protected String name;
        protected String surname;
        protected String number;

        public ContactBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public ContactBuilder setSurname(String surname) {
            this.surname = surname;
            return this;
        }

        public ContactBuilder setNumber(String number) {
            this.number = number;
            return this;
        }

        public abstract Contact build();
    }
}