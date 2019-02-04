package ru.innopolis.homework11.entity;

import java.util.Objects;

/**
 * Класс для таблицы Course с дополнительными полями Person, Subject
 */
public class Course {
    private int person_id;
    private int subject_id;
    private Person person;
    private Subject subject;


    public int getPerson_id() {
        return person_id;
    }

    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return person_id == course.person_id &&
                subject_id == course.subject_id &&
                Objects.equals(person, course.person) &&
                Objects.equals(subject, course.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person_id, subject_id, person, subject);
    }

    @Override
    public String toString() {
        return "Course{" +
                "person_id=" + person_id +
                ", subject_id=" + subject_id +
                ", person=" + person +
                ", subject=" + subject +
                '}';
    }
}
