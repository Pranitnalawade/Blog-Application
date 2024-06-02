package com.springboot.blog.springboot_blog.repository;

import com.springboot.blog.springboot_blog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
