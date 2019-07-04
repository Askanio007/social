package com.social.server.http.model;

import com.social.server.entity.PublicMessageRecipientType;
import com.social.server.http.ErrorCode;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class PublicMessageModel {
    @Min(value = 1, message = ErrorCode.ID_IS_EMPTY)
    private long senderId;
    @Min(value = 1, message = ErrorCode.ID_IS_EMPTY)
    private long recipientId;
    @NotNull(message = ErrorCode.RECIPIENT_TYPE_IS_EMPTY)
    private PublicMessageRecipientType recipientType;
    @NotNull(message = ErrorCode.MESSAGE_IS_EMPTY)
    @Length(min = 1, message = ErrorCode.MESSAGE_IS_EMPTY)
    private String message;
}
