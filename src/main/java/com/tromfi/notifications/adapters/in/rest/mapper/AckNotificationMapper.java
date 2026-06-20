package com.tromfi.notifications.adapters.in.rest.mapper;

import com.tromfi.notifications.adapters.in.rest.dto.NotificationResponseDTO;
import com.tromfi.notifications.application.usecase.notification.result.NotificationAck;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AckNotificationMapper {

    AckNotificationMapper MAPPER = Mappers.getMapper(AckNotificationMapper.class);

    NotificationResponseDTO toNotificationResponseDTO(NotificationAck notification);

}
