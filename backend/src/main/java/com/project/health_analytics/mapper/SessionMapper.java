package com.project.health_analytics.mapper;

import com.project.health_analytics.dto.SessionDto;
import com.project.health_analytics.dto.SessionReturnDto;
import com.project.health_analytics.model.Session;

/**
 * Mapper class for Session model
 * Contains methods to convert Session model to SessionDto and vice versa
 * 
 * @method sessionToDto - Convert Session model to SessionDto
 * @method sessionToModel - Convert SessionDto to Session model
 * @method sessionToReturnDto - Convert Session model to SessionReturnDto
 * 
 */
public class SessionMapper {
    // Convert Session model to SessionDto
    public static SessionDto sessionToDto(Session session) {
        if (session == null) {
            return null;
        }
        return new SessionDto(
                session.getBsn(),
                session.getSessionId(),
                session.getExpiryDate());
    }

    // Convert SessionDto to Session model
    public static Session sessionToModel(SessionDto sessionDto) {
        if (sessionDto == null) {
            return null;
        }
        return new Session(
                sessionDto.getBsn(),
                sessionDto.getSessionId(),
                sessionDto.getExpiryDate());
    }

    // Convert Session model to SessionReturnDto
    public static SessionReturnDto sessionToReturnDto(Session session) {
        if (session == null) {
            return null;
        }
        // currently null because the userType will be added later
        return new SessionReturnDto(session.getSessionId(), null);
    }
}