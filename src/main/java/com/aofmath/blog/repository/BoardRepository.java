package com.aofmath.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aofmath.blog.model.Board;

public interface BoardRepository extends JpaRepository<Board, Integer>{

}
