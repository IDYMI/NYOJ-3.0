package top.hcode.hoj.service.oj;

import java.util.Map;
import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.hcode.hoj.common.result.CommonResult;

public interface RankService {

    public CommonResult<IPage> getRankList(Integer limit, Integer currentPage, String searchUser, Integer type);

    public CommonResult<Void> getUserCodeRecord(List<String> uidList, String startTime, String endTime);

}
