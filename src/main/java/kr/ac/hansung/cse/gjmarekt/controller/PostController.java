package kr.ac.hansung.cse.gjmarekt.controller;

import kr.ac.hansung.cse.gjmarekt.dto.PostDTO;
import kr.ac.hansung.cse.gjmarekt.entity.GJUser;
import kr.ac.hansung.cse.gjmarekt.entity.Post;
import kr.ac.hansung.cse.gjmarekt.jwt.JWTUtil;
import kr.ac.hansung.cse.gjmarekt.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
public class PostController {

    private final PostService postService;
    private final JWTUtil jwtUtil;



    public PostController(PostService postService, JWTUtil jwtUtil) {
        this.postService = postService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/api/post")
    public ResponseEntity<PostDTO> post(
            @RequestHeader("Authorization") String authorization,
            PostDTO postDTO
    ) {
        System.out.println(authorization);
        String token = authorization.split(" ")[1];
        jwtUtil.getUserId(token);


//        Post newPost = new Post();
//        newPost.setTitle(postDTO.getTitle());
//        newPost.setContent(postDTO.getContent());
//

//        return ResponseEntity.ok(newPost);
        Integer userId = jwtUtil.getUserId(token);
        PostDTO savedPostDTO = postService.createPost(postDTO, userId);

        return ResponseEntity.ok(savedPostDTO);
    }
}
