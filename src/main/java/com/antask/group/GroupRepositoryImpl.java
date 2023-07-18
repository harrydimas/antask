package com.antask.group;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GroupRepositoryImpl {

    private final EntityManager entityManager;

    public Group findByNameAndFlow(String name, String flow) {
        String sql =
            "SELECT model FROM Group model WHERE model.name = :name AND (model.flow.id = :flow OR model.flow.name = :flow) ORDER BY model.dateCreated DESC";
        var list = entityManager.createQuery(sql)
                .setParameter("name", name)
                .setParameter("flow", flow)
                .getResultList();
        return list.isEmpty() ? null : (Group) list.get(0);
    }
}
