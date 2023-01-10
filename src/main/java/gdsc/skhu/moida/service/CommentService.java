package gdsc.skhu.moida.service;

import gdsc.skhu.moida.domain.Comment;
import gdsc.skhu.moida.domain.DTO.CommentDTO;
import gdsc.skhu.moida.domain.Post;
import gdsc.skhu.moida.repository.CommentQueryRepository;
import gdsc.skhu.moida.repository.CommentRepository;
import gdsc.skhu.moida.repository.MemberRepository;
import gdsc.skhu.moida.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final CommentQueryRepository commentQueryRepository;
    private final PostRepository postRepository;

    @Transactional
    public ResponseEntity<String> save(Long postId, CommentDTO commentDTO) {
        Post post;
        if(postRepository.findById(postId).isPresent()) {
            post = postRepository.findById(postId).get();
        } else {
            throw new IllegalStateException("존재하지 않는 게시글입니다.");
        }

        Comment parent = null;
        //부모 댓글이 존재하는 경우
        if(commentDTO.getParentCommentId() != null){
            if(commentRepository.findById(commentDTO.getParentCommentId()).isPresent()) {
                parent = commentRepository.findById(commentDTO.getParentCommentId()).get();
            }
            //해당 Id를 가지는 댓글이 존재하지 않을 경우
            if (parent == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 부모 댓글 Id입니다. 해당 Id를 가지는 댓글이 존재하지 않습니다.");
            }
            //부모 댓글의 게시글 Id와 자식 댓글의 게시글 Id를 같은지 비교
            if(parent.getPost().getId() != postId){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("게시글의 Id가 부모와 자식 댓글 간에 서로 다릅니다.");
            }
        }
        Comment comment;
        if(parent != null) {
            comment = Comment.builder()
                    .writer(memberRepository.findByUsername(commentDTO.getWriter()).get())
                    .post(post)
                    .context(commentDTO.getContext())
                    .parentComment(parent)
                    .build();
        } else {
            comment = Comment.builder()
                    .writer(memberRepository.findByUsername(commentDTO.getWriter()).get())
                    .post(post)
                    .context(commentDTO.getContext())
                    .build();
        }

        commentRepository.save(comment);

        return ResponseEntity.ok("댓글 저장 성공");
    }

    @Transactional
    public List<CommentDTO> findByPostId(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NoSuchElementException("해당 Id를 가진 Post가 존재하지 않습니다."));

        List<Comment> comments = commentQueryRepository.findAllByPost(post);
        List<CommentDTO> commentDTOs = new ArrayList<>();
        Map<Long, CommentDTO> map = new HashMap<>();

        //Post에 저장되어 있는 댓글 전부 불러와서 매핑
        comments.forEach(comment -> {
            //먼저, 부모-자식 관계가 없는 CommentDTO를 하나 생성
            CommentDTO commentDTO;
            //부모 댓글이 존재한다면 commentDTO에 부모 댓글 Id를 저장
            if(comment.getParentComment() != null) {
                commentDTO = CommentDTO.builder()
                        .id(comment.getId())
                        .postId(postId)
                        .writer(comment.getWriter().getUsername())
                        .context(comment.getContext())
                        .parentCommentId(comment.getParentComment().getId())
                        .createdDate(comment.getCreatedDate())
                        .modifiedDate(comment.getModifiedDate())
                        .build();
            } else {
                commentDTO = CommentDTO.builder()
                        .id(comment.getId())
                        .postId(postId)
                        .writer(comment.getWriter().getUsername())
                        .context(comment.getContext())
                        .createdDate(comment.getCreatedDate())
                        .modifiedDate(comment.getModifiedDate())
                        .build();
            }
            //해시 맵에 해당 댓글의 Id를 Key, 댓글을 Value로 저장
            map.put(commentDTO.getId(), commentDTO);

            //부모 댓글이 존재한다면 부모 댓글의 자식 댓글 리스트에 commentDTO 추가 (대댓글)
            if (comment.getParentComment() != null) {
                CommentDTO parentCommentDTO = map.get(comment.getParentComment().getId());
                parentCommentDTO.setChildComments(new ArrayList<>());
                parentCommentDTO.getChildComments().add(commentDTO);
            } //부모 댓글이 존재하지 않는다면 댓글 리스트에 commentDTO 추가
            else {
                commentDTOs.add(commentDTO);
            }
        });
        return commentDTOs;
    }

    @Transactional
    public ResponseEntity<String> update(Long commentId, CommentDTO commentDTO) {
        Comment originalComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("Comment not found"));
        commentRepository.save(Comment.builder()
                        .id(originalComment.getId())
                        .writer(originalComment.getWriter())
                        .post(originalComment.getPost())
                        .context(commentDTO.getContext())
                        .parentComment(originalComment.getParentComment())
                        .childComments(originalComment.getChildComments())
                        .build());
        return ResponseEntity.ok("Update comment success");
    }

    @Transactional
    public ResponseEntity<String> delete(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        commentRepository.delete(comment);
        return ResponseEntity.ok("Delete comment success");
    }
}
