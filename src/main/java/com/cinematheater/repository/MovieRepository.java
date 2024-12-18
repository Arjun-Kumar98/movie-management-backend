package com.cinematheater.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;
import com.cinematheater.model.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MovieRepository extends JpaRepository<MovieEntity,Integer> {

	Page<MovieEntity> findByUserIdUserId(Integer userId, Pageable pageable);
}
