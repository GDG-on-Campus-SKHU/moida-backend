package gdsc.skhu.moida.domain;

import javax.persistence.*;

@Entity
public class Member {
    @Id @Column(updatable = false, unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
