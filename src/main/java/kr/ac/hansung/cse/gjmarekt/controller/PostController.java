package kr.ac.hansung.cse.gjmarekt.controller;

import kr.ac.hansung.cse.gjmarekt.dto.PostDTO;
import kr.ac.hansung.cse.gjmarekt.dto.PostImageDTO;
import kr.ac.hansung.cse.gjmarekt.entity.GJUser;
import kr.ac.hansung.cse.gjmarekt.entity.Post;
import kr.ac.hansung.cse.gjmarekt.jwt.JWTUtil;
import kr.ac.hansung.cse.gjmarekt.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Controller
@ResponseBody
public class PostController {

    private final PostService postService;
    private final JWTUtil jwtUtil;


    public PostController(PostService postService, JWTUtil jwtUtil) {
        this.postService = postService;
        this.jwtUtil = jwtUtil;
    }
/*
    @PostMapping("/api/post")
    public ResponseEntity<PostDTO> post(
            @RequestHeader("Authorization") String authorization,
            PostDTO postDTO
    ) {
        System.out.println(authorization);
        String token = authorization.split(" ")[1];
        jwtUtil.getUserId(token);

        // jwt를 이용해 userId를 찾는다
        // 다른사람 글로 위조하는 것을 막기 위함
        Integer userId = jwtUtil.getUserId(token);
        PostDTO savedPostDTO = postService.createPost(postDTO, userId);

        return ResponseEntity.ok(savedPostDTO);
    }
 */

    @PostMapping("/api/post")
    public ResponseEntity<Post> post(
            @RequestHeader("Authorization") String authorization,
            PostDTO postDTO,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) {
        System.out.println("sadfadsfdafsdasf");

        System.out.println(authorization);
        String token = authorization.split(" ")[1];
        // jwt를 이용해 userId를 찾는다
        // 다른사람 글로 위조하는 것을 막기 위함
        Integer userId = jwtUtil.getUserId(token);

        List<PostImageDTO> imageDTOs = new ArrayList<>();
        if (images != null && !images.isEmpty()) { // images가 null이 아니고 비어있지 않은 경우에만 처리
            for (MultipartFile image : images) {
                PostImageDTO postImageDTO = new PostImageDTO();
                postImageDTO.setImage(image);
                imageDTOs.add(postImageDTO);
            }
        }

        Post savedPost = postService.createPost(postDTO, userId, imageDTOs);
        return ResponseEntity.ok(savedPost);
    }

    // 상품 수정
    @PutMapping("/api/updatepost")
    public ResponseEntity<PostDTO> updatePost(
            @RequestHeader("Authorization") String authorization,
            PostDTO postDTO
    ) {
        System.out.println(authorization);
        String token = authorization.split(" ")[1];
        jwtUtil.getUserId(token);


        // jwt를 이용해 userId를 찾는다
        // 다른사람 글로 위조하는 것을 막기 위함
        Integer userId = jwtUtil.getUserId(token);
        PostDTO savedPostDTO = postService.updatePost(postDTO, userId);

        return ResponseEntity.ok(savedPostDTO);
    }

    // 상품 정보 요청
    @GetMapping("/api/post/{postId}")
    public ResponseEntity<Post> getPost(
            @PathVariable Integer postId
    ) {
        System.out.println("postId: " + postId);
        return postService.findPostById(postId);
    }

    // 페이지로 상품 정보 요청
    @GetMapping("/api/posts")
    public ResponseEntity<Page<Post>> getPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<Post> posts = postService.getPosts(page, size);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }
}
