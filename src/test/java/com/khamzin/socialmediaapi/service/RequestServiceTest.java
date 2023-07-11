package com.khamzin.socialmediaapi.service;

import com.khamzin.socialmediaapi.dto.request.RequestDto;
import com.khamzin.socialmediaapi.model.request.Request;
import com.khamzin.socialmediaapi.model.user.User;
import com.khamzin.socialmediaapi.repository.RequestRepository;
import com.khamzin.socialmediaapi.util.EntityUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class RequestServiceTest {

    @InjectMocks
    private RequestService requestService;
    @Mock
    private RequestRepository requestRepository;
    @Mock
    private UserService userService;

    @Test
    void shouldSendRequestAndReturnDto() {
        User sender = EntityUtil.getUser();
        User recipient = EntityUtil.getAnotherUser();
        RequestDto requestDto = EntityUtil.getRequestDto();
        requestDto.setSenderId(sender.getId());
        requestDto.setRecipientId(recipient.getId());

        when(userService.getUserById(sender.getId())).thenReturn(sender);
        when(userService.getUserById(recipient.getId())).thenReturn(recipient);
        when(requestRepository.save(any(Request.class))).thenAnswer(invocation -> {
            Object[] args = invocation.getArguments();
            return args[0];
        });

        assertThat(requestService.sendFriendRequest(requestDto)).isEqualTo(requestDto);
    }
}
