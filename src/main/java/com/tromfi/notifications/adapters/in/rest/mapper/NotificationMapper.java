package com.tromfi.notifications.adapters.in.rest.mapper;

import com.tromfi.notifications.adapters.in.rest.dto.NotificationRequestDTO;
import com.tromfi.notifications.application.usecase.command.CreateNotificationCommand;
import com.tromfi.notifications.domain.model.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NotificationMapper {

    NotificationMapper MAPPER = Mappers.getMapper(NotificationMapper.class);

    //@Mapping() <- Esto es por si hay diferentes nombres de los properties, sepa cual es a la que se refiere
    CreateNotificationCommand toCreateNotificationCommand(NotificationRequestDTO notification);

}
