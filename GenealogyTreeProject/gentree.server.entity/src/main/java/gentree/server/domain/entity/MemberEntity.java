package gentree.server.domain.entity;

import gentree.common.configuration.enums.Age;
import gentree.common.configuration.enums.DeathCauses;
import gentree.common.configuration.enums.Gender;
import gentree.common.configuration.enums.Race;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by Martyna SZYMKOWIAK on 18/10/2017.
 */
@Entity
@Table(name = "member")
@Setter
@Access(value = AccessType.PROPERTY)
public class MemberEntity implements Serializable {


    private static final long serialVersionUID = -2908410045473436618L;

    private Long version;
    private Long id;
    private FamilyEntity family;
    private String name;
    private String surname;
    private String bornname;
    private boolean alive;
    private DeathCauses deathCauses;
    private Age age;
    private Race race;
    private Gender gender;
    private PhotoEntity photo;
/*
    private RelationEntity bornRelation;*/




    /*
     *  GETTERS
     */

    @Version
    public Long getVersion() {
        return version;
    }

    @Id
    @GeneratedValue(generator = "InvSeqMember")
    @SequenceGenerator(name = "InvSeqMember", sequenceName = "INV_SEQ_MEMBER", allocationSize = 5)
    public Long getId() {
        return id;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "family_id", nullable = false)
    public FamilyEntity getFamily() {
        return family;
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    @Column(nullable = false)
    public String getSurname() {
        return surname;
    }

    @Column
    public String getBornname() {
        return bornname;
    }

    @Column(nullable = false)
    public boolean isAlive() {
        return alive;
    }

    @Column
    public DeathCauses getDeathCauses() {
        return deathCauses;
    }

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    public Age getAge() {
        return age == null ? Age.YOUNG_ADULT : age;
    }

    public void setAge(Age age) {
        this.age = (age == null ? Age.YOUNG_ADULT : age);
    }

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    public Race getRace() {
        return race == null ? Race.HUMAIN : race;
    }

    public void setRace(Race race) {
        this.race = (race == null ? Race.HUMAIN : race);
    }

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    public Gender getGender() {
        return gender == null ? Gender.M : gender;
    }

    public void setGender(Gender gender) {
        this.gender = (gender == null ? Gender.M : gender);
    }

    @OneToOne(mappedBy = "owner")
    public PhotoEntity getPhoto() {
        return photo;
    }

    public void setPhoto(PhotoEntity photo) {
        this.photo = photo;
    }

    /*    @ManyToOne(optional = false,
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.PERSIST},
            targetEntity = RelationEntity.class)
    public RelationEntity getBornRelation() {
        return bornRelation;
    }*/

/*    public void setBornRelation(RelationEntity bornRelation) {
        this.bornRelation = bornRelation;
    }*/

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MemberEntity{");
        sb.append("version=").append(version);
        sb.append(", id=").append(id);
        sb.append(", family=").append(family);
        sb.append(", name='").append(name).append('\'');
        sb.append(", surname='").append(surname).append('\'');
        sb.append(", bornname='").append(bornname).append('\'');
        sb.append(", alive=").append(alive);
        sb.append(", deathCauses=").append(deathCauses);
        sb.append(", age=").append(age);
        sb.append(", race=").append(race);
        sb.append(", gender=").append(gender);
        sb.append('}');
        return sb.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MemberEntity)) return false;
        MemberEntity that = (MemberEntity) o;
        return alive == that.alive &&
                Objects.equals(version, that.version) &&
                Objects.equals(id, that.id) &&
                Objects.equals(family, that.family) &&
                Objects.equals(name, that.name) &&
                Objects.equals(surname, that.surname) &&
                Objects.equals(bornname, that.bornname) &&
                deathCauses == that.deathCauses &&
                age == that.age &&
                race == that.race &&
                gender == that.gender &&
                Objects.equals(photo, that.photo);
    }

    @Override
    public int hashCode() {

        return Objects.hash(version, id, family, name, surname, bornname, alive, deathCauses, age, race, gender, photo);
    }
}
