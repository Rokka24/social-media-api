package com.khamzin.socialmediaapi.repository;

import com.khamzin.socialmediaapi.model.request.Request;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RequestRepository extends JpaRepository<Request, Long> {

}
