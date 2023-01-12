package gdsc.skhu.moida.domain.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class CommentDTO {
    private Long id;
    private Long postId;
    private String writer;
    private String nickname;
    private String context;
    private Long parentCommentId;
    private List<CommentDTO> childComments;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}
