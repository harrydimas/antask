package com.antask.group;

import com.antask.flow.Flow;
import com.antask.group_member.GroupMember;
import com.antask.util.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "\"group\"")
@Getter
@Setter
public class Group extends BaseModel {

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flow_id", nullable = false)
    private Flow flow;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private Set<GroupMember> groupGroupMembers;

}
