package gentree.server.domain.entity;

import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Martyna SZYMKOWIAK on 18/10/2017.
 */
@Entity
@Table(name = "family")
@Setter
public class FamilyEntity implements Serializable {

    private static final long serialVersionUID = -8252895002186338434L;

    private Long version;
    private Long id;
    private String name;
    private OwnerEntity owner;

    private List<MemberEntity> members = new ArrayList<>();
    private List<RelationEntity> relations = new ArrayList<>();


    /*
        GETTERS AND SETTERS
     */

    @Version
    public Long getVersion() {
        return version;
    }

    @Id
    @GeneratedValue(generator = "InvSeqFamily")
    @SequenceGenerator(name = "InvSeqFamily", sequenceName = "INV_SEQ_FAMILY", allocationSize = 5)
    public Long getId() {
        return id;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    public OwnerEntity getOwner() {
        return owner;
    }

    @OneToMany(mappedBy = "family", fetch = FetchType.LAZY)
    public List<MemberEntity> getMembers() {
        return members;
    }


    @OneToMany(mappedBy = "family", fetch = FetchType.LAZY)
    @OrderBy("id")
    public List<RelationEntity> getRelations() {
        return relations;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FamilyEntity{");
        sb.append("version=").append(version);
        sb.append(", id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", owner=").append(owner);
        sb.append(", members=").append(members);
        sb.append(", relations=").append(relations);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FamilyEntity)) return false;
        FamilyEntity that = (FamilyEntity) o;
        return Objects.equals(version, that.version) &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {

        return Objects.hash(version, id, name, owner);
    }
}


