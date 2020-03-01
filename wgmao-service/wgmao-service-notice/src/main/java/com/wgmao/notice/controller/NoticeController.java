package com.wgmao.notice.controller;


import com.baomidou.mybatisplus.plugins.Page;
import com.wgmao.entity.PageResult;
import com.wgmao.entity.Result;
import com.wgmao.entity.StatusCode;
import com.wgmao.notice.pojo.Notice;
import com.wgmao.notice.pojo.NoticeFresh;
import com.wgmao.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: zyxyz zhongyue314@163.com
 * @Date: 2020/3/1 9:45
 */
@RestController
@RequestMapping("/notice")
@CrossOrigin
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    /**
     * 根据id查询通知
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result selectById(@PathVariable String id) {
        Notice notice = noticeService.selectById(id);
        return new Result(true, StatusCode.OK, "查询成功", notice);
    }

    /**
     * 根据条件分页查询
     *
     * @param notice
     * @param page
     * @param size
     * @return
     */
    @PostMapping("/search/{page}/{size}")
    public Result selectByList(@RequestBody Notice notice, @PathVariable Integer page, @PathVariable Integer size) {
        Page<Notice> pageDate = noticeService.selectByPage(notice, page, size);
        PageResult<Notice> pageResult = new PageResult<>(pageDate.getTotal(), pageDate.getRecords());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    /**
     * 修改通知
     *
     * @param notice
     * @return
     */
    @PutMapping
    public Result updateById(@RequestBody Notice notice) {
        noticeService.updateById(notice);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 新增通知
     *
     * @param notice
     * @return
     */
    @PostMapping
    public Result save(@RequestBody Notice notice) {
        noticeService.save(notice);
        return new Result(true, StatusCode.OK, "保存成功");
    }

    /**
     * 根据id查询该用户的待通知消息
     *
     * @param userId
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/fresh/{userId}/{page}/{size}")
    public Result freshPage(@PathVariable String userId, @PathVariable Integer page, @PathVariable Integer size) {
        Page<NoticeFresh> pageData = noticeService.freshPage(userId, page, size);
        PageResult<NoticeFresh> pageResult = new PageResult<>(pageData.getTotal(), pageData.getRecords());
        return new Result(true, StatusCode.OK, "查询成功", pageResult);
    }

    /**
     * 删除待推送消息
     *
     * @param noticeFresh
     * @return
     */
    @DeleteMapping("/fresh")
    public Result freshDelete(@RequestBody NoticeFresh noticeFresh) {
        noticeService.freshDelete(noticeFresh);
        return new Result(true, StatusCode.OK, "删除成功");
    }
}
