package com.antask.flow;

import com.antask.client.Client;
import com.antask.node.Node;
import com.antask.util.BaseModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
public class Flow extends BaseModel {

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "flow", fetch = FetchType.LAZY)
    private Set<Node> flowNodes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

}
