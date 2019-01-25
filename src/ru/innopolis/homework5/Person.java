package ru.innopolis.homework5;


import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Person {
    public int id;
    private int age;
    private double height;
    private String firstName;
    public final String origin = "english";
    String education;

    private static transient volatile String staticField;
    Person(){}

    Person (int id, int age, double height, String firstName, String education){
        this.id = id;
        this.age = age;
        this.height = height;
        this.firstName = firstName;
        this.education = education;
    }

    public int getId() {
        return id;
    }

    @XmlAttribute
    public void setId(int id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    @XmlElement
    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    @XmlElement
    public void setHeight(double height) {
        this.height = height;
    }

    public String getFirstName() {
        return firstName;
    }

    @XmlElement
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getOrigin() {
        return origin;
    }


    public String getEducation() {
        return education;
    }

    @XmlElement
    public void setEducation(String education) {
        this.education = education;
    }
}
