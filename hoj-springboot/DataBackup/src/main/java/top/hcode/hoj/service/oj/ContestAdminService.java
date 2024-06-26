package top.hcode.hoj.service.oj;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.metadata.IPage;
import top.hcode.hoj.common.result.CommonResult;
import top.hcode.hoj.pojo.dto.CheckACDTO;
import top.hcode.hoj.pojo.entity.contest.ContestPrint;
import top.hcode.hoj.pojo.entity.contest.ContestRecord;
import top.hcode.hoj.pojo.entity.contest.ContestSign;
import top.hcode.hoj.pojo.vo.ContestSignVO;
import top.hcode.hoj.pojo.vo.SessionVO;

public interface ContestAdminService {

	public CommonResult<IPage<ContestRecord>> getContestACInfo(Long cid, Integer currentPage, Integer limit);

	public CommonResult<Void> checkContestACInfo(CheckACDTO checkACDto);

	public CommonResult<IPage<ContestPrint>> getContestPrint(Long cid, Integer currentPage, Integer limit);

	public CommonResult<Void> checkContestPrintStatus(Long id, Long cid);

	public CommonResult<IPage<ContestSign>> getContestSign(Long cid, Integer currentPage, Integer limit,
			Boolean type, Boolean gender, Integer status, String keyword);

	public CommonResult<ContestSignVO> getContestSignInfo(Long cid, Long id);

	public CommonResult<Void> checkContestSignStatus(Map<String, Object> params);

	public CommonResult<Void> updateContestSign(ContestSignVO contestSignVo);

	public CommonResult<IPage<SessionVO>> getContestSession(Long cid, Integer currentPage, Integer limit,
			String keyword, String unkeyword);

	public CommonResult<List<SessionVO>> getContestIpList(Long cid);

	public CommonResult<Void> rejudgeContestIp(Long cid, String uid);

}
