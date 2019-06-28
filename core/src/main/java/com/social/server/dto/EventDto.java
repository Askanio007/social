package com.social.server.dto;

import com.social.server.entity.Event;
import com.social.server.util.DateFormatterUtil;
import com.social.server.util.ImageUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Data
public class EventDto {
    private long id;
    private long userId;
    private String userName;
    private String userAvatar64code;
    private String description;
    private String targetActionName;
    private String dateView;
    private long targetActionId;

    public static EventDto of(Event event) {
        if (event == null) {
            log.warn("Entity Event is null");
            return null;
        }

        EventDto eventDto = new EventDto();
        eventDto.setId(event.getId());
        eventDto.setDescription(event.getType().getDescriptionCode());
        eventDto.setTargetActionId(event.getTargetActionId());
        eventDto.setTargetActionName(event.getTargetActionName());
        eventDto.setUserName(event.getUser().getFullName());
        eventDto.setUserId(event.getUser().getId());
        eventDto.setUserAvatar64code(ImageUtil.convertImageTo64encode(event.getUser().getDetails().getImage()));
        eventDto.setDateView(DateFormatterUtil.withoutTimeFormat(event.getDate()));
        return eventDto;
    }

    public static List<EventDto> of(List<Event> events) {
        return events.stream()
                .map(EventDto::of)
                .collect(Collectors.toList());
    }
}
