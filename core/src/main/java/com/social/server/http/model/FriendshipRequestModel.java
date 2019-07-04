package com.social.server.http.model;

import com.social.server.http.ErrorCode;
import lombok.Data;

import javax.validation.constraints.Min;

@Data
public class FriendshipRequestModel {
    @Min(value = 1, message = ErrorCode.ID_IS_EMPTY)
    private long fromUserId;
    @Min(value = 1, message = ErrorCode.ID_IS_EMPTY)
    private long toUserId;
}
