package top.hcode.hoj.manager.oj;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.util.CollectionUtils;
import top.hcode.hoj.common.exception.StatusFailException;
import top.hcode.hoj.common.exception.StatusNotFoundException;
import top.hcode.hoj.pojo.dto.ClocResultJsonDTO;
import top.hcode.hoj.pojo.entity.user.UserInfo;
import top.hcode.hoj.pojo.entity.user.UserSign;
import top.hcode.hoj.pojo.vo.ACMRankVO;
import top.hcode.hoj.pojo.vo.CODERankVO;
import top.hcode.hoj.pojo.vo.OIRankVO;
import top.hcode.hoj.pojo.vo.OJRankVO;
import top.hcode.hoj.dao.user.UserInfoEntityService;
import top.hcode.hoj.dao.user.UserRecordEntityService;
import top.hcode.hoj.dao.user.UserSignEntityService;
import top.hcode.hoj.utils.Constants;
import top.hcode.hoj.utils.RedisUtils;
import top.hcode.hoj.utils.ClocUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.stream.Collectors;
import java.text.ParseException;

/**
 * @Author: Himit_ZH
 * @Date: 2022/3/10 20:47
 * @Description:
 */
@Component
public class RankManager {

    @Autowired
    private UserRecordEntityService userRecordEntityService;

    @Autowired
    private UserInfoEntityService userInfoEntityService;

    @Autowired
    private UserSignEntityService userSignEntityService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private ClocUtils clocUtils;

    // 排行榜缓存时间 60s
    private static final long cacheRankSecond = 60;

    /**
     * @MethodName get-rank-list
     * @Params * @param null
     * @Description 获取排行榜数据
     * @Return CommonResult
     * @Since 2020/10/27
     */
    public IPage getRankList(Integer limit, Integer currentPage, String searchUser, Integer type)
            throws StatusFailException {

        // 页数，每页题数若为空，设置默认值
        if (currentPage == null || currentPage < 1)
            currentPage = 1;
        if (limit == null || limit < 1)
            limit = 30;

        List<String> uidList = null;
        if (!StringUtils.isEmpty(searchUser)) {
            QueryWrapper<UserInfo> userInfoQueryWrapper = new QueryWrapper<>();

            userInfoQueryWrapper.select("uuid");

            userInfoQueryWrapper.and(wrapper -> wrapper
                    .like("username", searchUser)
                    .or()
                    .like("nickname", searchUser));

            userInfoQueryWrapper.eq("status", 0);

            uidList = userInfoEntityService.list(userInfoQueryWrapper)
                    .stream()
                    .map(UserInfo::getUuid)
                    .collect(Collectors.toList());

            QueryWrapper<UserSign> userSignQueryWrapper = new QueryWrapper<>();

            userSignQueryWrapper.select("uid");
            userSignQueryWrapper.and(wrapper -> wrapper.like("realname", searchUser));

            List<String> userSignList = userSignEntityService.list(userSignQueryWrapper)
                    .stream()
                    .map(UserSign::getUid)
                    .collect(Collectors.toList());

            if (!CollectionUtils.isEmpty(userSignList)) {
                uidList.addAll(userSignList);
                uidList = uidList.stream().distinct().collect(Collectors.toList());
            }
        }
        IPage rankList = null;
        // 根据type查询不同类型的排行榜
        if (type.intValue() == Constants.Contest.TYPE_ACM.getCode()) {
            rankList = getACMRankList(limit, currentPage, uidList, false);
        } else if (type.intValue() == Constants.Contest.TYPE_OI.getCode()) {
            rankList = getOIRankList(limit, currentPage, uidList, false);
        } else if (type.intValue() == Constants.Contest.TYPE_NEWACM.getCode()) {
            rankList = getACMRankList(limit, currentPage, uidList, true);
        } else if (type.intValue() == Constants.Contest.TYPE_NEWOI.getCode()) {
            rankList = getOIRankList(limit, currentPage, uidList, true);
        } else if (type.intValue() == Constants.Contest.TYPE_OJ.getCode()) {
            rankList = getOJRankList(limit, currentPage, uidList, false);
        } else if (type.intValue() == Constants.Contest.TYPE_NEWOJ.getCode()) {
            rankList = getOJRankList(limit, currentPage, uidList, true);
        } else if (type.intValue() == Constants.Contest.TYPE_CODE.getCode()) {
            rankList = getCODERankList(limit, currentPage, uidList);
        } else {
            throw new StatusFailException("排行榜类型代码不正确，请使用0(ACM),1(OI),2(NewACM),3(NewOI),4(OJ),5(NewOJ)！");
        }
        return rankList;
    }

    public void getUserCodeRecord(List<String> uidList, String startTime, String endTime)
            throws StatusFailException, StatusNotFoundException, IOException {

        // uidList = new ArrayList<>();
        uidList.add("48d8792b425c40e39d616cac56ac33a6");
        uidList.add("877c99a86f2d4d17adb9805971a3ce4c");
        uidList.add("ae5f347ab6ad451c9328dc8ca8539117");
                
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        try {
            Date startTimeDate = startTime != null ? sdf.parse(startTime) : null;
            Date endTimeDate = endTime != null ? sdf.parse(endTime) : null;

            clocUtils.getUserCodeLines(uidList, startTimeDate, endTimeDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private IPage<ACMRankVO> getACMRankList(int limit, int currentPage, List<String> uidList, Boolean isNew) {

        IPage<ACMRankVO> data = null;
        if (uidList != null) {
            Page<ACMRankVO> page = new Page<>(currentPage, limit);
            page.setSearchCount(false);
            page.setOptimizeCountSql(false);
            if (uidList.size() > 0) {
                data = userRecordEntityService.getACMRankList(page, uidList, isNew);
            } else {
                data = page;
            }
        } else {
            String key = isNew ? Constants.Account.NEW_ACM_RANK_CACHE.getCode()
                    : Constants.Account.ACM_RANK_CACHE.getCode() + "_" + limit + "_" + currentPage;
            data = (IPage<ACMRankVO>) redisUtils.get(key);
            if (data == null) {
                Page<ACMRankVO> page = new Page<>(currentPage, limit);
                page.setSearchCount(false);
                page.setOptimizeCountSql(false);
                data = userRecordEntityService.getACMRankList(page, null, isNew);
                redisUtils.set(key, data, cacheRankSecond);
            }
        }

        return data;
    }

    private IPage<OIRankVO> getOIRankList(int limit, int currentPage, List<String> uidList, Boolean isNew) {

        IPage<OIRankVO> data = null;
        if (uidList != null) {
            Page<OIRankVO> page = new Page<>(currentPage, limit);
            page.setSearchCount(false);
            page.setOptimizeCountSql(false);
            if (uidList.size() > 0) {
                data = userRecordEntityService.getOIRankList(page, uidList, isNew);
            } else {
                data = page;
            }
        } else {
            String key = isNew ? Constants.Account.NEW_OI_RANK_CACHE.getCode()
                    : Constants.Account.OI_RANK_CACHE.getCode() + "_" + limit + "_" + currentPage;
            data = (IPage<OIRankVO>) redisUtils.get(key);
            if (data == null) {
                Page<OIRankVO> page = new Page<>(currentPage, limit);
                page.setSearchCount(false);
                page.setOptimizeCountSql(false);
                data = userRecordEntityService.getOIRankList(page, null, isNew);
                redisUtils.set(key, data, cacheRankSecond);
            }
        }

        return data;
    }

    private IPage<OJRankVO> getOJRankList(int limit, int currentPage, List<String> uidList, Boolean isNew) {

        IPage<OJRankVO> data = null;
        if (uidList != null) {
            Page<OJRankVO> page = new Page<>(currentPage, limit);
            page.setSearchCount(false);
            page.setOptimizeCountSql(false);
            if (uidList.size() > 0) {
                data = userRecordEntityService.getOJRankList(page, uidList, isNew);
            } else {
                data = page;
            }
        } else {
            String key = isNew ? Constants.Account.NEW_OJ_RANK_CACHE.getCode()
                    : Constants.Account.OJ_RANK_CACHE.getCode() + "_" + limit + "_" + currentPage;
            data = (IPage<OJRankVO>) redisUtils.get(key);
            if (data == null) {
                Page<OJRankVO> page = new Page<>(currentPage, limit);
                page.setSearchCount(false);
                page.setOptimizeCountSql(false);
                data = userRecordEntityService.getOJRankList(page, null, isNew);
                redisUtils.set(key, data, cacheRankSecond);
            }
        }

        return data;
    }

    private IPage<CODERankVO> getCODERankList(int limit, int currentPage, List<String> uidList) {

        // 从数据库中筛选数据
        IPage<CODERankVO> data = null;
        if (uidList != null) {
            Page<CODERankVO> page = new Page<>(currentPage, limit);
            page.setSearchCount(false);
            page.setOptimizeCountSql(false);
            if (uidList.size() > 0) {
                data = userRecordEntityService.getCODERankList(page, uidList);
            } else {
                data = page;
            }
        } else {
            String key = Constants.Account.CODE_RANK_CACHE.getCode() + "_" + limit + "_" + currentPage;

            data = (IPage<CODERankVO>) redisUtils.get(key);
            if (data == null) {
                Page<CODERankVO> page = new Page<>(currentPage, limit);
                page.setSearchCount(false);
                page.setOptimizeCountSql(false);
                data = userRecordEntityService.getCODERankList(page, null);
                redisUtils.set(key, data, cacheRankSecond);
            }
        }

        // 将字符串整理为列表
        data.getRecords().forEach(codeRank -> {
            JSONObject jsonObject = JSONUtil.parseObj(codeRank.getJson());
            List<ClocResultJsonDTO> codeConfigList = jsonObject.get("config", List.class);
            codeRank.setListJson(codeConfigList);
        });

        return data;
    }
}