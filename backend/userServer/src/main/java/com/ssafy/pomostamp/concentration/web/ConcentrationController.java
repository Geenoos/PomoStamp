package com.ssafy.pomostamp.concentration.web;


import com.ssafy.pomostamp.SuccessResponseResult;
import com.ssafy.pomostamp.concentration.dto.ConcentrationResponse;
import com.ssafy.pomostamp.concentration.service.ConcentrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/pomo/v1/concentration")
public class ConcentrationController {

    @Autowired
    private ConcentrationService concentrationService;

    @GetMapping("/{pomoId}")
    private SuccessResponseResult findConcentrationList(@PathVariable int pomoId){
        ConcentrationResponse concentrationResponse = new ConcentrationResponse(concentrationService.getAllByPomoId(pomoId));
        return new SuccessResponseResult(concentrationResponse);
    }

}
