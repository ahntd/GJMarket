package kr.ac.hansung.cse.gjmarekt.repository;

import kr.ac.hansung.cse.gjmarekt.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer> {

    Page<Post> findAll(Pageable pageable);

}
