package gdsc.skhu.moida.controller;

import gdsc.skhu.moida.domain.DTO.CommentDTO;
import gdsc.skhu.moida.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{postId}/comments")
    public List<CommentDTO> comments(@PathVariable Long postId) {
        return commentService.findByPostId(postId);
    }

    @PostMapping("/{postId}/comments/new")
    public ResponseEntity<String> reply(@PathVariable Long postId, @RequestBody CommentDTO commentDTO) {
        return commentService.save(postId, commentDTO);
    }

    @PatchMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<String> edit(@PathVariable Long commentId, @RequestBody CommentDTO commentDTO) {
        return commentService.update(commentId, commentDTO);
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<String> delete(@PathVariable("commentId") Long commentId) {
        return commentService.delete(commentId);
    }
}
