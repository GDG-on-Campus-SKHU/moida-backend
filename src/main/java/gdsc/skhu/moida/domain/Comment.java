package gdsc.skhu.moida.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member writer;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Column(name = "context")
    private String context;

    @ManyToOne
    @JoinColumn(name = "nested_comment")
    private Comment comment;

    @OneToMany(mappedBy = "comment")
    @JsonIgnore
    List<Comment> comments;
}
