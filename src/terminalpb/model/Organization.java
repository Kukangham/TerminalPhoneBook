package terminalpb.model;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Organization extends Contact {
    private String address;

    public Organization(String name, String address, String number) {
        super.name = name;
        this.address = address;
        super.number = number;
        super.createdTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        super.lastEditTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Organization name: " + super.name + "\n"
                + "Address: " + this.address + "\n"
                + "Number: " + super.number + "\n"
                + "Time created: " + super.createdTime + "\n"
                + "Time last edit: " + super.lastEditTime + "\n";
    }

    public static class OrganizationBuilder extends ContactBuilder {
        private String address;

        @Override
        public OrganizationBuilder setName(String name) {
            super.setName(name);
            return this;
        }

        @Override
        public OrganizationBuilder setSurname(String surname) {
            super.setSurname(surname);
            return this;
        }

        public OrganizationBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        @Override
        public OrganizationBuilder setNumber(String number) {
            super.setNumber(number);
            return this;
        }

        @Override
        public Organization build() {
            return new Organization(super.name, this.address, super.number);
        }
    }
}