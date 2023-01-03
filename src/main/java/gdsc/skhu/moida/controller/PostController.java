package gdsc.skhu.moida.controller;

import gdsc.skhu.moida.domain.DTO.PostDTO;
import gdsc.skhu.moida.domain.Member;
import gdsc.skhu.moida.domain.Post;
import gdsc.skhu.moida.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @PostMapping("/new")
    public ResponseEntity<String> write(Principal principal, @RequestBody PostDTO postDTO) {
        postService.write(principal, postDTO);
        return ResponseEntity.ok("new post save success");
    }

    @GetMapping("/list")
    public Page<Post> list(@PageableDefault(sort = "post_id", direction = Sort.Direction.ASC) Pageable pageable) {
        return postService.findAllWithPaging(pageable);
    }
}
