package com.smart_justice.smart_justice.controller;

import com.smart_justice.smart_justice.algorithm.CaseAnalysis;
import com.smart_justice.smart_justice.algorithm.Execute;
import com.smart_justice.smart_justice.model.ParamsOfAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className:
 * @description:
 * @author: ZSZ
 * @date: 2020/7/3 10:23
 */
@RestController
@RequestMapping("/test")
public class HelloController {

    @Autowired
    CaseAnalysis caseAnalysis;

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("/test")
    public String getResult(){

        ParamsOfAlgorithm params = new ParamsOfAlgorithm();
        params.setFile_name("Z:\\SmartJustice\\intelligent_justice_flask\\algorithm\\data\\testdata_gysh_2.txt");
        //设置参数 file_type 文件类型
        params.setFile_type("txt");
        //设置参数 Id 案件Id
        params.setId("None");
        //设置参数 Original_claim 原告诉称
        params.setOriginal_claim("None");
        //设置参数 Defendant_argued 被告辩称
        params.setDefendant_argued("None");
        //设置参数 Threadhold [marry0.2, traffic0.55, zpz0.55, injury0.6...]
        params.setThreadhold("0.6");
        //设置参数 case_type {"marry","traffic","zpz","gysh"}
        params.setCase_type("gysh");
        return caseAnalysis.predict(params);
    }

}