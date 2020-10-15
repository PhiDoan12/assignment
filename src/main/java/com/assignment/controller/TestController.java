package com.assignment.controller;

import com.assignment.controller.entity.Data;
import com.assignment.controller.entity.MinerData;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/assignment")
public class TestController {

    @PostMapping(path = "/one", produces = "application/json")
    public Data one(@RequestBody List<String> data) {
        Data response = new Data();
        for (String d : data) {
            response.setData(d);
        }
        return response;
    }

    @PostMapping(path = "/two", produces = "application/json")
    public MinerData two(@RequestBody List<List<Boolean>> data) {
        if(data.size() == 0){
            return new MinerData();
        }
        MinerData minerData = Util.getBoardResult(data);
        return minerData;
    }
}
