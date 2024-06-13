package top.hcode.hoj.service.oj;

import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.hcode.hoj.common.result.CommonResult;

public interface RankService {

    public CommonResult<IPage> getRankList(Integer limit, Integer currentPage, String searchUser, Integer type);

    public CommonResult<Void> getUserCode(Map<String, Object> params);

}
