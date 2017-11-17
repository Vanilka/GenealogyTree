package gentree.server.service.Implementation;

import gentree.server.domain.entity.FamilyEntity;
import gentree.server.domain.entity.OwnerEntity;
import gentree.server.repository.FamilyRepository;
import gentree.server.service.FamilyService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Martyna SZYMKOWIAK on 18/10/2017.
 */
@Service
public class FamilyServiceImpl implements FamilyService {

    @Autowired
    FamilyRepository familyRepository;

    /* **********************************************
         Family Gestion
     ********************************************** */

    @Override
    public FamilyEntity addNewFamily(FamilyEntity family) {
        return familyRepository.saveAndFlush(family);
    }

    @Override
    public FamilyEntity findFamilyById(Long id) {
        return familyRepository.findFamilyById(id);
    }

    @Override
    public FamilyEntity findFullFamilyById(Long id) {

        FamilyEntity entity = familyRepository.findFamilyById(id);
        Hibernate.initialize(entity.getOwner());
        Hibernate.initialize(entity.getMembers());
        Hibernate.initialize(entity.getRelations());
        return  entity;
    }

    @Override
    public List<FamilyEntity> findAllByOwner(OwnerEntity owner) {
        return familyRepository.findAllByOwner(owner);
    }

        /* **********************************************
         Member Gestion
     ********************************************** */





}