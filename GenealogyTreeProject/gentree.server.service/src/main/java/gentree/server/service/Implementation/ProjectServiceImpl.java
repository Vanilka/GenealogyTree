package gentree.server.service.Implementation;

import gentree.exception.AscendanceViolationException;
import gentree.exception.IncorrectStatusException;
import gentree.exception.NotExistingMemberException;
import gentree.exception.TooManyNullFieldsException;
import gentree.server.domain.entity.FamilyEntity;
import gentree.server.domain.entity.MemberEntity;
import gentree.server.domain.entity.OwnerEntity;
import gentree.server.domain.entity.RelationEntity;
import gentree.server.service.FamilyService;
import gentree.server.service.MemberService;
import gentree.server.service.ProjectService;
import gentree.server.service.RelationService;
import gentree.server.service.validator.RelationValidator;
import gentree.server.service.wrappers.NewMemberWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

/**
 * Created by Martyna SZYMKOWIAK on 20/10/2017.
 */
@Service
@Transactional
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    FamilyService familyService;

    @Autowired
    MemberService memberService;

    @Autowired
    RelationService relationService;

    @Autowired
    RelationValidator relationValidator;


    /* ************************************************************
         Family Management
    ************************************************************ */

    @Override
    public FamilyEntity addFamily(FamilyEntity familyEntity) {
        return familyService.addNewFamily(familyEntity);
    }

    @Override
    public FamilyEntity findFamilyById(Long id) {
        return familyService.findFamilyById(id);
    }

    @Override
    public FamilyEntity findFullFamilyById(Long id) {

        return familyService.findFullFamilyById(id);
    }

    @Override
    public List<FamilyEntity> findAllFamiliesByOwner(OwnerEntity owner) {
        return familyService.findAllByOwner(owner);
    }



    /* ************************************************************
        Member Management
    ************************************************************ */

    @Override
    public NewMemberWrapper addMember(MemberEntity memberEntity) {
        NewMemberWrapper wrapper = new NewMemberWrapper();
        try {
            wrapper.setMember(memberService.addNewMember(memberEntity));
            wrapper.setBornRelation(relationService.addNewBornRelation(wrapper.getMember()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return wrapper;
    }

    @Override
    public FamilyEntity deleteMember(MemberEntity memberEntity) {
        memberService.deleteMember(memberEntity);
        relationService.removeOrphans(memberEntity.getFamily().getId());
        return findFullFamilyById(memberEntity.getFamily().getId());
    }

    /* ************************************************************
        Relation Management
    ************************************************************ */


    @Override
    public List<RelationEntity> addRelation(RelationEntity relationEntity)
            throws TooManyNullFieldsException, AscendanceViolationException, IncorrectStatusException, NotExistingMemberException {


        FamilyEntity familyEntity = familyService.findFamilyById(relationEntity.getFamily().getId());

        /*
           Validate relation
         */
        relationValidator.validate(relationEntity, familyEntity);


        RelationEntity target;

        /*
         * Verify Existing
         */
        RelationEntity existing = relationService.findRelationBysimLeftAndsimRight(relationEntity.getLeft(), relationEntity.getRight());

        if (existing != null) {

            target = mergeChildrenAndStatus(existing, relationEntity);

        } else {
            target = relationService.addNewRelation(relationEntity);
        }


        return relationService.findAllRelationsByFamilyId(target.getFamily().getId());
    }

    @Override
    public List<RelationEntity> updateRelation(RelationEntity relationEntity) {
        return null;
    }

    @Override
    public RelationEntity deleteRelation(RelationEntity relationEntity) {

        return relationService.deleteRelation(relationEntity);
    }


    /**
     * For two relation with same  Member Left and Member Right merge Children
     * @param existing
     * @param candidate
     * @return relation
     */
    private RelationEntity mergeChildrenAndStatus(RelationEntity existing, RelationEntity candidate) {
        existing.setType(candidate.getType());
        existing.setActive(candidate.isActive());

        if (candidate.getChildren() != null && !candidate.getChildren().isEmpty()) {
            candidate.getChildren().forEach(child -> {
                if ((existing.getChildren().stream().filter(c -> Objects.equals(child.getId(), c.getId())).count() == 0))
                    existing.getChildren().add(child);
            });
        }
        return relationService.updateRelation(existing);

    }

}