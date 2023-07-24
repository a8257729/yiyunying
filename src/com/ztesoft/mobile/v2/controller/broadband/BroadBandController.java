package com.ztesoft.mobile.v2.controller.broadband;

import com.ztesoft.mobile.common.exception.DataAccessException;
import com.ztesoft.mobile.v2.core.Result;
import com.ztesoft.mobile.v2.service.broadband.BroadbandService;
import com.ztesoft.mobile.v2.service.broadband.BroadbandServiceImpl;

import java.sql.SQLException;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller("broadBandController")
public class BroadBandController {
    private BroadbandService broadbandService = (BroadbandService)new BroadbandServiceImpl();

    @RequestMapping({"/broadband/question_bank/query/big/title"})
    @ResponseBody
    public Result queryAllBigTitle(@RequestBody Map<String, Object> map) throws DataAccessException {
        return this.broadbandService.queryBigTitle();
    }

    @RequestMapping({"/broadband/question_bank/query/small/title"})
    @ResponseBody
    public Result queryQuestionByBigTitle(@RequestBody Map<String, Object> map) throws DataAccessException {
        String bigTitle = (String)map.get("bigTitle");
        return this.broadbandService.queryQuestionByBigTitle(bigTitle);
    }

    @RequestMapping({"/broadband/question_bank/query/answer"})
    @ResponseBody
    public Result queryAnswerBySmallTitle(@RequestBody Map<String, Object> map) throws SQLException {
        String smallTitle = (String)map.get("smallTitle");
        return this.broadbandService.queryAnswerBySmallTitle(smallTitle);
    }

    @RequestMapping({"/broadband/question_bank/query/hot"})
    @ResponseBody
    public Result queryHotQustion(@RequestBody Map<String, Object> map) throws DataAccessException {
        return this.broadbandService.queryHotQustion();
    }

    @RequestMapping({"/broadband/question_bank/query/blur"})
    @ResponseBody
    public Result blurQuery(@RequestBody Map<String, Object> map) throws SQLException {
        String search = (String)map.get("search");

        return this.broadbandService.blurQuery(search);
    }

    @RequestMapping({"/broadband/question_bank/update/access"})
    @ResponseBody
    public Result updateAccess(@RequestBody Map<String, Object> map) throws DataAccessException {
        String id = (String)map.get("id");
        String smallTitle = (String)map.get("smallTitle");
        return this.broadbandService.updateAccess(smallTitle, id);
    }
}
