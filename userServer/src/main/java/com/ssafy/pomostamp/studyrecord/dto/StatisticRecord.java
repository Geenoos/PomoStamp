package com.ssafy.pomostamp.studyrecord.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "statisticRecord")
public class StatisticRecord {

    @EmbeddedId
    private StatisticRecordId statisticRecordId;

    @Column(name = "total_time", nullable = false)
    private Long studySec;

    public static StatisticRecord statisticRecordCreate(StatisticRecordId statisticRecordId, Long sec){
        return StatisticRecord.builder()
                .statisticRecordId(statisticRecordId)
                .studySec(sec)
                .build();
    }
}
