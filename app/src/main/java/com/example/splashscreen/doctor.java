package com.example.splashscreen;

public class doctor {
    private String name;
    private String specialty;
    private String contact;

    public doctor(String   name, String specialty, String contact) {
        this.name = name;
        this.specialty = specialty;
        this.contact = contact;
    }

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public String getContact() {
        return contact;
    }
}

