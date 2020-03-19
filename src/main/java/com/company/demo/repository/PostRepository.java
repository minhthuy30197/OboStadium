package com.company.demo.repository;

import com.company.demo.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    public Page<Post> findAllByStatus(int status, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT * FROM post WHERE status = ?1 AND id != ?2 ORDER BY published_at, created_at DESC LIMIT ?3")
    public List<Post> getLatestPostsNotId(int publicStatus, long id, int limit);

    @Query(nativeQuery = true, value = "SELECT * FROM post WHERE status = ?1 ORDER BY published_at, created_at DESC LIMIT ?2")
    public List<Post> getLatestPosts(int publicStatus, int limit);
}
