package gentree.server.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Martyna SZYMKOWIAK on 06/11/2017.
 */
@Getter
@Setter
@Entity
@Table(name = "SimPhotos")
public class PhotoEntity implements Serializable {

    private static final long serialVersionUID = -5105985807626911414L;

    private Long version;
    private Long id;
    private String photo;
    private MemberEntity owner;

    private String encodedStringPhoto;

    @Version
    public Long getVersion() {
        return version;
    }

    @Id
    @GeneratedValue(generator = "InvSeqPhoto")
    @SequenceGenerator(name = "InvSeqPhoto", sequenceName = "INV_SEQ_PHOTO", allocationSize = 5)
    public Long getId() {
        return id;
    }

    public String getPhoto() {
        return photo;
    }


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    public MemberEntity getOwner() {
        return owner;
    }

    @Transient
    public String getEncodedStringPhoto() {
        return encodedStringPhoto;
    }

    public void setEncodedStringPhoto(String encodedStringPhoto) {
        this.encodedStringPhoto = encodedStringPhoto;
    }
}
