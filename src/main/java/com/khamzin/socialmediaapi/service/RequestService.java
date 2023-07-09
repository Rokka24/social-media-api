package com.khamzin.socialmediaapi.service;

import com.khamzin.socialmediaapi.dto.request.RequestDto;
import com.khamzin.socialmediaapi.model.request.Request;
import com.khamzin.socialmediaapi.model.request.Status;
import com.khamzin.socialmediaapi.model.request.Type;
import com.khamzin.socialmediaapi.model.user.User;
import com.khamzin.socialmediaapi.repository.RequestRepository;
import com.khamzin.socialmediaapi.util.mapper.RequestMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestMapper requestMapper;
    private final RequestRepository requestRepository;
    private final UserService userService;

    @Transactional
    @PreAuthorize("#requestDto.senderId == authentication.principal.userId")
    public RequestDto sendFriendRequest(RequestDto requestDto) {
        User sender = userService.getUserById(requestDto.getSenderId());
        User recipient = userService.getUserById(requestDto.getRecipientId());

        sender.getSubscriptions().add(recipient);
        recipient.getSubscribers().add(sender);

        Request request = Request.builder()
                .type(Type.FRIEND)
                .status(Status.WAITING_FOR_ANSWER)
                .sender(sender)
                .recipient(recipient)
                .build();
        requestRepository.save(request);

        requestDto.setType(Type.FRIEND);
        requestDto.setStatus(Status.WAITING_FOR_ANSWER);

        return requestDto;
    }


    @Transactional
    public Status answerToRequest(Long requestId, Boolean answer) {
        Request request = getRequestById(requestId);
        if (answer) {
            User recipient = request.getRecipient();
            User sender = request.getSender();

            recipient.getSubscriptions().add(sender);
            recipient.getFriends().add(sender);

            sender.getFriends().add(recipient);
            sender.getSubscribers().add(recipient);

            request.setStatus(Status.ACCEPTED);
        } else {
            request.setStatus(Status.DECLINED);
        }

        return request.getStatus();
    }

    @Transactional
    public void cancelFriendRequest(Long requestId) {
        Request request = getRequestById(requestId);

        User recipient = request.getRecipient();
        User sender = request.getSender();

        sender.getSubscriptions().remove(recipient);
        recipient.getSubscribers().remove(sender);

        requestRepository.delete(request);
    }


    @Transactional(readOnly = true)
    public Request getRequestById(Long id) {
        return requestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
    }

    @Transactional(readOnly = true)
    public RequestDto getRequestDtoById(Long id) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        return requestMapper.map(request);
    }
}
