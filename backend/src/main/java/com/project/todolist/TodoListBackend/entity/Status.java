package com.project.todolist.TodoListBackend.entity;

public enum Status {
    CONCLUIDA,
    PENDENTE;

    public static Status fromString(String value) {
        if (value == null) {
            throw new IllegalArgumentException("Status não pode ser nulo");
        }
        return Status.valueOf(value.trim().toUpperCase());
    }
}
