package gentree.server.service;

import gentree.exception.AscendanceViolationException;
import gentree.exception.IncorrectStatusException;
import gentree.exception.NotExistingMemberException;
import gentree.exception.TooManyNullFieldsException;
import gentree.server.domain.entity.FamilyEntity;
import gentree.server.domain.entity.MemberEntity;
import gentree.server.domain.entity.OwnerEntity;
import gentree.server.domain.entity.RelationEntity;
import gentree.server.service.wrappers.NewMemberWrapper;

import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 20/10/2017.
 */
public interface ProjectService {

    FamilyEntity addFamily(FamilyEntity familyEntity);

    NewMemberWrapper addMember(MemberEntity memberEntity);

    FamilyEntity deleteMember(MemberEntity memberEntity);

    List<RelationEntity> addRelation(RelationEntity relationEntity) throws TooManyNullFieldsException, AscendanceViolationException, IncorrectStatusException, NotExistingMemberException;

    FamilyEntity findFamilyById(Long id);

    FamilyEntity findFullFamilyById(Long id);

    List<FamilyEntity> findAllFamiliesByOwner(OwnerEntity owner);

    List<RelationEntity> updateRelation(RelationEntity relationEntity);

    RelationEntity deleteRelation(RelationEntity relationEntity);

}