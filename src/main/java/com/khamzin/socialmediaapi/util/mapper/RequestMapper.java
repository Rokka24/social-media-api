package com.khamzin.socialmediaapi.util.mapper;

import com.khamzin.socialmediaapi.dto.request.RequestDto;
import com.khamzin.socialmediaapi.model.request.Request;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestMapper {

    private final ModelMapper modelMapper;

    public RequestDto map(Request request) {
        return modelMapper.map(request, RequestDto.class);
    }

    public Request map(RequestDto dto) {
        return modelMapper.map(dto, Request.class);
    }
}
