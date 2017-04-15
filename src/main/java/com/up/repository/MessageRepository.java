package com.up.repository;

import com.up.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Samsung on 15.04.2017.
 */
@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    Iterable<Message> findAllByCreatedGreaterThan(Long after);
}
