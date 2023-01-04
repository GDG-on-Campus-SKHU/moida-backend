package gdsc.skhu.moida.domain.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostDTO {
    private Long id;
    private String author;
    private String title;
    private String type;
    private String context;
}
