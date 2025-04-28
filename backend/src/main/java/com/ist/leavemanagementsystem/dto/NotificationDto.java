package com.ist.leavemanagementsystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@NoArgsConstructor
public class NotificationDto {

    private Long id;
    private Long recipientId;
    private String message;
    private String type;
    private boolean read;
    private OffsetDateTime timestamp;
}