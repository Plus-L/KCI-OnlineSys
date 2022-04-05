package com.plusl.kci_onlinesys.controller;

import com.plusl.kci_onlinesys.annotation.LoginRequired;
import com.plusl.kci_onlinesys.entity.Comment;
import com.plusl.kci_onlinesys.entity.DiscussPost;
import com.plusl.kci_onlinesys.entity.Page;
import com.plusl.kci_onlinesys.entity.User;
import com.plusl.kci_onlinesys.service.CommentService;
import com.plusl.kci_onlinesys.service.DiscussPostService;
import com.plusl.kci_onlinesys.service.UserService;
import com.plusl.kci_onlinesys.util.CommunityConstant;
import com.plusl.kci_onlinesys.util.CommunityUtil;
import com.plusl.kci_onlinesys.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @program: kci_onlinesys
 * @description: 帖子控制器
 * @author: PlusL
 * @create: 2022-03-14 16:41
 **/
@Controller
@RequestMapping("/community")
public class DiscussPostController implements CommunityConstant {

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private CommentService commentService;

    @LoginRequired
    @RequestMapping(value = "/index" , method = RequestMethod.GET)
    public String getIndexPage(Model model, Page page){
        /**
         * 此处传入Model和Page两个参数，在方法调用前SpringMVC会自动实例化Model和Page，并把Page注入Model中
         * 所以我们可以直接在Thymleaf中访问到Page对象
         */

        page.setRows(discussPostService.findDiscussPostRows(0));
        page.setPath("/community/index");

        List<DiscussPost> list = discussPostService.findDiscussPosts(0, page.getOffset(), page.getLimit());
        List<Map<String, Object>> discussPosts = new ArrayList<>();

        if(list != null){
            for (DiscussPost post : list){
                Map<String,Object> map = new HashMap<>();
                map.put("post", post);
                User user = userService.findById(post.getUserId());
                map.put("user", user);
                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts", discussPosts);
        return "/blog_home";
    }

    /**
     * 发布帖子，目前暂时完成文字发布、后续补充资源发布
     * @param discussPost
     * @return
     */
    @LoginRequired
    @RequestMapping(path = "/publish" , method = RequestMethod.POST)
    public String addDiscussPost(DiscussPost discussPost){
        User user = hostHolder.getUser();

        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setContent(discussPost.getContent());
        post.setTitle(discussPost.getTitle());
        post.setGroupId(discussPost.getGroupId());
        post.setCreateTime(new Date());
        discussPostService.addDiscussPost(post);

        //报错后面统一处理
        return "redirect:/community/index";
    }

    /**
     * 发布帖子
     * @return
     */
    @LoginRequired
    @RequestMapping(path = "/publish", method = RequestMethod.GET)
    public String getPublishPage(){
        return "publish";
    }

    /**
     * 帖子详情页
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(path = "/detail/{id}", method = RequestMethod.GET)
    public String getDetailPage(@PathVariable("id") int id, Model model, Page page){
        DiscussPost discussPost = discussPostService.findeDidcussPostById(id);
        model.addAttribute("post", discussPost);

        User user = userService.findById(discussPost.getUserId());
        model.addAttribute("user", user);

        //评论分页信息
        page.setLimit(5);
        page.setPath("/community/detail/" + id);
        page.setRows(discussPost.getCommentCount());

        //评论列表
        List<Comment> commentList = commentService.findCommentsByEntity(ENTITY_TYPE_POST, discussPost.getId(), page.getOffset(), page.getLimit());
        //评论ViewObject（显示对象）列表
        List<Map<String, Object>> commentVOlist = new ArrayList<>();
        if(commentList !=null){
            for (Comment comment : commentList){
                //单个评论的VO
                Map<String, Object>  commentVO = new HashMap<>();
                //评论
                commentVO.put("comment", comment);
                //用户
                commentVO.put("user", userService.findById(comment.getUserId()));
                //回复列表
                List<Comment> replyList = commentService.findCommentsByEntity(ENTITY_TYPE_COMMENT, comment.getId(), 0, Integer.MAX_VALUE);
                //回复VO列表
                List<Map<String, Object>> replyVOList = new ArrayList<>();
                if(replyList != null){
                    for (Comment reply : replyList){
                        Map<String , Object> replyVO = new HashMap<>();
                        // 回复
                        replyVO.put("reply", reply);
                        replyVO.put("user", userService.findById(reply.getUserId()));
                        // 回复目标
                        User target = reply.getTargetId() == 0 ? null : userService.findById(reply.getTargetId());
                        replyVOList.add(replyVO);
                    }
                }
                //将回复列表放入当前实体
                commentVO.put("replys", replyVOList);
                int count = commentService.findCountByEntity(ENTITY_TYPE_COMMENT, comment.getId());
                commentVO.put("replyCount", count);

                commentVOlist.add(commentVO);

            }
        }

        model.addAttribute("comments", commentVOlist);

        return "blog_post";
    }
}
