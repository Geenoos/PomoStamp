package com.ssafy.pomostamp.pomo.dto;

import com.ssafy.pomostamp.concentration.dto.Concentration;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@IdClass(PomoImageId.class)
@Table(name = "pomoImage")
public class PomoImage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "frame_id")
    private int frameId;

    @Id
    @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
    private String userId;

    @Id
    @Column(name = "pomo_id", nullable = false, insertable = false, updatable = false)
    private int pomoId;

    @Lob
    private Blob image;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "pomo_id", referencedColumnName = "pomo_id", updatable = false, insertable = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", updatable = false, insertable = false)
    private Pomo pomo;
}


