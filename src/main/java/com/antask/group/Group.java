package com.antask.group;

import com.antask.flow.Flow;
import com.antask.group_member.GroupMember;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "\"group\"")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Group {

    @Id
    @Column(nullable = false, updatable = false, columnDefinition = "char(36)")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flow_id", nullable = false)
    private Flow flow;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private Set<GroupMember> groupGroupMembers;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;
}
