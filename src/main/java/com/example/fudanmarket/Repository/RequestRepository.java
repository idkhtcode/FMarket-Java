package com.example.fudanmarket.Repository;

import com.example.fudanmarket.Entity.Request;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    List<Request> findByRequestUserId(int ruid);

    List<Request> findByFinishUserId(int fuid);

    List<Request> findAll();

    Page<Request> findAllByState(String state, Pageable pageable);

    List<Request> findByNameContaining(String name);

    List<Request> findAllByState(String state);

    Page<Request> findPageByRequestUserId(int ruid, Pageable pageable);
}
