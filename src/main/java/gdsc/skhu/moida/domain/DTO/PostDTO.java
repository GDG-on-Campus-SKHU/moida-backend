package gdsc.skhu.moida.domain.DTO;

<<<<<<< HEAD
import gdsc.skhu.moida.domain.Member;
import gdsc.skhu.moida.domain.Post;
=======
>>>>>>> develop
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
<<<<<<< HEAD

    public Post toEntity() {
        return Post.builder()
                .member(author)
                .title(title)
                .type(type)
                .context(context)
                .build();
    }

    public static PostDTO toDTO(Post post) {
        return PostDTO.builder()
                .author(post.getMember())
                .title(post.getTitle())
                .type(post.getType())
                .context(post.getContext())
                .build();
    }
=======
>>>>>>> develop
}
