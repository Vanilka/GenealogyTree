package gentree.server.service;

import gentree.server.domain.entity.FamilyEntity;
import gentree.server.domain.entity.MemberEntity;
import gentree.server.domain.entity.OwnerEntity;
import gentree.server.domain.entity.RelationEntity;

import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 20/10/2017.
 */
public interface ProjectService {

    FamilyEntity addFamily(FamilyEntity familyEntity);

    MemberEntity addMember(MemberEntity memberEntity);

    RelationEntity addRelation(RelationEntity relationEntity);

    FamilyEntity findFamilyById(Long id);

    List<FamilyEntity> findAllFamiliesByOwner(OwnerEntity owner);
}