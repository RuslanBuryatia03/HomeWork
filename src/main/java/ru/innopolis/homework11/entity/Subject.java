package ru.innopolis.homework11.entity;

import java.util.Objects;

/**
 * Класс для таблицы Subject
 */

public class Subject {
    private int id;
    private String description;

    public Subject(String description){
        this.description = description;
    }

    public Subject(int id, String description){
        this.id = id;
        this.description = description;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject = (Subject) o;
        return id == subject.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Subject{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }
}
