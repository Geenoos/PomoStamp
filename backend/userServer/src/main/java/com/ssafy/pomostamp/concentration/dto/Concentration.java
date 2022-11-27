package com.ssafy.pomostamp.concentration.dto;

import com.ssafy.pomostamp.pomo.dto.PomoImage;
import lombok.*;

import javax.persistence.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "concentration")
public class Concentration {
    @Id
    @Column(name = "frame_id")
    @JoinColumn(name = "frame_id", referencedColumnName = "frame_id")
    private int frameId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "pomo_id", nullable = false)
    private int pomoId;

    private float angry;
    private float disgust;
    private float scared;
    private float happy;
    private float sad;
    private float surprised;
    private float neutral;

    @Column(name="max_column", nullable = false)
    private int maxColumn;

    private int concentration;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "frame_id", referencedColumnName = "frame_id", insertable = false, updatable = false),
            @JoinColumn(name = "pomo_id", referencedColumnName = "pomo_id", insertable = false, updatable = false),
            @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    })
//    @MapsId
    private PomoImage pomoImage;
}
