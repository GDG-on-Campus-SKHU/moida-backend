package gdsc.skhu.moida.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import gdsc.skhu.moida.domain.Comment;
import gdsc.skhu.moida.domain.Post;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static gdsc.skhu.moida.domain.QComment.comment;

@RequiredArgsConstructor
public class CommentRepository {
    private final JPAQueryFactory queryFactory;

    public List<Comment> findAllByPost(Post post) {
        return queryFactory.selectFrom(comment)
                .leftJoin(comment.parentComment)
                .fetchJoin()
                .where(comment.id.eq(post.getId()))
                .orderBy(
                        comment.parentComment.id.asc().nullsFirst(),
                        comment.id.asc()
                ).fetch();
    }
}
