package com.example.digital_queue.dto;

public class SuccessResponse<T> {
    private String message;
    private T data;

    public SuccessResponse(String messege, T data) {
        this.message = messege;
        this.data = data;
    }

    // Getter and Setter

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
