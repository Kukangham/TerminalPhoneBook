package terminalpb.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Person extends Contact {
    private String birthDate;
    private String gender;

    Person(String name, String surname, String  birthDate, String gender, String number) {
        super.name = name;
        super.surname = surname;
        this.birthDate = birthDate;
        this.gender = gender;
        super.number = number;
        super.createdTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        super.lastEditTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    }

    public String getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Name: " + super.name + "\n"
                + "Surname: " + super.surname + "\n"
                + "Birth date: " + this.birthDate + "\n"
                + "Gender: " + this.gender + "\n"
                + "Number: " + super.number + "\n"
                + "Time created: " + super.createdTime + "\n"
                + "Time last edit: " + super.lastEditTime + "\n";
    }

    public static class PersonBuilder extends ContactBuilder {
        private String birthDate;
        private String gender;

        @Override
        public PersonBuilder setName(String name) {
            super.setName(name);
            return this;
        }

        @Override
        public PersonBuilder setSurname(String surname) {
            super.setSurname(surname);
            return this;
        }

        public PersonBuilder setBirthDate(String birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        public PersonBuilder setGender(String gender) {
            this.gender = gender;
            return this;
        }

        @Override
        public PersonBuilder setNumber(String number) {
            super.setNumber(number);
            return this;
        }

        @Override
        public Person build() {
            return new Person(super.name, super.surname, this.birthDate, this.gender, super.number);
        }
    }
}