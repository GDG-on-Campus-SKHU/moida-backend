package gdsc.skhu.moida.domain.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PostDTO {
    private Long id;
    private String author;
    private String title;
    private String type;
    private String context;
    private List<CommentDTO> comments;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
}