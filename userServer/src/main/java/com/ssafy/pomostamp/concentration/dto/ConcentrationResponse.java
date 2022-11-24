package com.ssafy.pomostamp.concentration.dto;

import lombok.Data;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Getter
public class ConcentrationResponse {
    private List<Integer> concentrationList = new ArrayList();

    private int concentrationAvg;

    public ConcentrationResponse(List<Concentration> concentrationList){
        for(Concentration c : concentrationList)
            this.concentrationList.add(c.getConcentration());

        int minVal = Collections.min(this.concentrationList);
        int maxVal = Collections.max(this.concentrationList);
        this.concentrationList = this.concentrationList.stream().map(i -> this.calc(i, minVal, maxVal)).collect(Collectors.toList());
        this.concentrationAvg = this.concentrationList.stream().mapToInt(i->i).sum() / this.concentrationList.size();
    }

    private int calc(int num, int minVal, int maxVal){
        return (num - minVal) * 100 / (maxVal - minVal);
    }
}
