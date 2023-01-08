package gdsc.skhu.moida.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gdsc.skhu.moida.domain.Comment;
import gdsc.skhu.moida.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static gdsc.skhu.moida.domain.QComment.comment;

@Repository
@RequiredArgsConstructor
public class CommentQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<Comment> findAllByPost(Post post) {
        return queryFactory.selectFrom(comment)
                .leftJoin(comment.parentComment)
                .fetchJoin()
                .where(comment.post.id.eq(post.getId()))
                .orderBy(
                        comment.parentComment.id.asc().nullsFirst(),
                        comment.id.asc()
                ).fetch();
    }
}
