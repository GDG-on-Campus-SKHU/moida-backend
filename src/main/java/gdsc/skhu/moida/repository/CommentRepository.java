package gdsc.skhu.moida.repository;

import gdsc.skhu.moida.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
