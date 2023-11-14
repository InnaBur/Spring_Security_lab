package com.todo.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Error Info")
public class MyErrorMessage {

    @Schema(description = "Data")
    private LocalDateTime data;

    @Schema(description = "Error message")
    private int status;

    @Schema(description = "Error details")
    private String details;

    public MyErrorMessage(LocalDateTime data, int status, String details) {
        this.data = data;
        this.status = status;
        this.details = details;
    }

    public MyErrorMessage() {
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
