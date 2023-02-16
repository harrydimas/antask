package com.antask.group;

import jakarta.persistence.EntityManager;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupRepositoryImpl {

    private final EntityManager entityManager;

    public Group findByNameAndFlow(String name, UUID flowId) {
        String sql =
            "SELECT model FROM Group model WHERE model.name = :name AND model.flow.id = :flowId ORDER BY model.dateCreated DESC";
        var list = entityManager.createQuery(sql)
                .setParameter("name", name)
                .setParameter("flowId", flowId)
                .getResultList();
        return list.isEmpty() ? null : (Group) list.get(0);
    }
}
