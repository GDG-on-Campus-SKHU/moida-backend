package gdsc.skhu.moida.repository;

import gdsc.skhu.moida.domain.Comment;
import gdsc.skhu.moida.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByWriter(Pageable pageable, Member member);
}
