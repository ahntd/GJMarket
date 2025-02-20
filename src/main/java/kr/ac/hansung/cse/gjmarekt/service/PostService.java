package kr.ac.hansung.cse.gjmarekt.service;

import kr.ac.hansung.cse.gjmarekt.dto.PostDTO;
import kr.ac.hansung.cse.gjmarekt.entity.GJUser;
import kr.ac.hansung.cse.gjmarekt.entity.Post;
import kr.ac.hansung.cse.gjmarekt.repository.PostRepository;
import kr.ac.hansung.cse.gjmarekt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.springframework.http.ResponseEntity.ok;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;
    private final UserRepository userRepository;


    public PostService(PostRepository postRepository, UserService userService, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public PostDTO createPost(PostDTO postDTO, Integer userId) {
        GJUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setUser(user);
        post.setPrice(postDTO.getPrice());

        Post savedpost = postRepository.save(post);

        PostDTO newPostDTO = new PostDTO();
        newPostDTO.setId(savedpost.getId());
        newPostDTO.setTitle(savedpost.getTitle());
        newPostDTO.setContent(savedpost.getContent());
        newPostDTO.setUserId(savedpost.getUser().getId());
        newPostDTO.setPrice(savedpost.getPrice());
        newPostDTO.setStatus(savedpost.getStatus());
        newPostDTO.setCreatedAt(savedpost.getCreatedAt());
        newPostDTO.setUpdatedAt(savedpost.getUpdatedAt());
        newPostDTO.setViewCount(savedpost.getViewCount());
        newPostDTO.setWishlistCount(savedpost.getWishlistCount());

        return newPostDTO;
    }

    public PostDTO updatePost(PostDTO postDTO, Integer userId) {
        GJUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        // postId를 통해 수정할 post를 찾는다
        Post post = postRepository.findById(postDTO.getId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        //post의 userid가 다르면 막아야 한다.
        if (!post.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("User not authorized to update this post");
        }


        // 덮어쓰기할 post
        if (postDTO.getTitle() != null)
            post.setTitle(postDTO.getTitle());
        if (postDTO.getContent() != null)
            post.setContent(postDTO.getContent());
        if (postDTO.getPrice() != null)
            post.setPrice(postDTO.getPrice());
        if (postDTO.getStatus() != null)
            post.setStatus(postDTO.getStatus());

        Post savedpost = postRepository.save(post);

        // 업데이트후 응답으로 보낼 DTO
        PostDTO newPostDTO = new PostDTO();
        newPostDTO.setId(savedpost.getId());
        newPostDTO.setTitle(savedpost.getTitle());
        newPostDTO.setContent(savedpost.getContent());
        newPostDTO.setUserId(savedpost.getUser().getId());
        newPostDTO.setCreatedAt(savedpost.getCreatedAt());
        newPostDTO.setUpdatedAt(savedpost.getUpdatedAt());
        newPostDTO.setPrice(savedpost.getPrice());
        newPostDTO.setStatus(savedpost.getStatus());
        newPostDTO.setViewCount(savedpost.getViewCount());
        newPostDTO.setWishlistCount(savedpost.getWishlistCount());

        return newPostDTO;
    }

    public ResponseEntity<PostDTO> findPostById(Integer postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post not found"));

        PostDTO newPostDTO = new PostDTO();
        return ResponseEntity.ok(newPostDTO);
    }
}
