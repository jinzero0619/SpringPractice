package com.fastcampus.ch4.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.fastcampus.ch4.dao.BoardDao;
import com.fastcampus.ch4.domain.BoardDto;
import com.fastcampus.ch4.domain.PageHandler;
import com.fastcampus.ch4.domain.SearchCondition;
import com.fastcampus.ch4.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.rmi.server.ExportException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
public class BoardController {
    @Autowired
    BoardService boardService;

    @GetMapping("/read")
    public String read(Integer bno, Integer page, Integer pageSize, Model m, HttpSession session) {
        String id = (String)session.getAttribute("id");
        try {
            BoardDto boardDto = boardService.read(bno);
            m.addAttribute("boardDto",boardDto);
            m.addAttribute("page",page);
            m.addAttribute("pageSize",pageSize);
            m.addAttribute("id",id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
            return "board";
    }

    @GetMapping("/write")
    public String write(Model m){
        m.addAttribute("mode", "new");
        return "board";
    }

    @PostMapping("/write")
    public String write(Model m,BoardDto boardDto, HttpSession session, RedirectAttributes rattr){
        String writer = (String)session.getAttribute("id");
        boardDto.setWriter(writer);

        try {
            int rowCnt  = boardService.write(boardDto);

            if(rowCnt != 1)
                throw new Exception("write Err!");

            rattr.addFlashAttribute("msg","Write_Ok");
            return "redirect:/board/list";
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("boardDto",boardDto);
            rattr.addFlashAttribute("msg","Write_Fail");
            return "board";
        }
    }

    @PostMapping("/modify")
    public String modify(Model m,BoardDto boardDto, HttpSession session, RedirectAttributes rattr){
        String writer = (String)session.getAttribute("id");
        boardDto.setWriter(writer);

        try {
            int rowCnt  = boardService.modify(boardDto);

            if(rowCnt != 1)
                throw new Exception("modify Err!");

            rattr.addFlashAttribute("msg","modify_Ok");
            return "redirect:/board/list";
        } catch (Exception e) {
            e.printStackTrace();
            m.addAttribute("boardDto",boardDto);
            rattr.addFlashAttribute("msg","modify_Fail");
            return "board";
        }
    }
    @PostMapping("/remove")
    public String remove(Integer bno, Integer page, Integer pageSize, Model m, HttpSession session, RedirectAttributes rattr){
        String writer = (String)session.getAttribute("id");
        try {
            m.addAttribute("page",page);
            m.addAttribute("pageSize",pageSize);

            int rowCnt = boardService.remove(bno,writer);

            if(rowCnt!=1)
                throw new Exception("board remove error");

             rattr.addFlashAttribute("msg","Del_Ok");
            }
        catch (Exception e) {
            e.printStackTrace();
            rattr.addFlashAttribute("msg","Del_Fail");
        }
        return "redirect:/board/list";
    }


    @GetMapping("/list")
    public String list(@ModelAttribute SearchCondition sc, Model m, HttpServletRequest request) {
        if(!loginCheck(request))
            return "redirect:/login/login?toURL="+request.getRequestURL();  // 로그인을 안했으면 로그인 화면으로 이동

//        if(sc.getPage() == null && sc.getPageSize() ==null) {
//            sc.setPage() == 1;
//            sc.getPageSize() == 10;
//        }

        try {
            int totalCnt = boardService.getSearchResultCnt(sc);
            m.addAttribute("totalCnt",totalCnt);
            PageHandler pageHandler = new PageHandler(totalCnt,sc);


            List<BoardDto> list =  boardService.getSearchSelectPage(sc);
            m.addAttribute("list",list);
            m.addAttribute("ph",pageHandler);
            m.addAttribute("page",sc.getPage());
            m.addAttribute("pageSize",sc.getPageSize());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "boardList"; // 로그인을 한 상태이면, 게시판 화면으로 이동
    }

    private boolean loginCheck(HttpServletRequest request) {
        // 1. 세션을 얻어서
        HttpSession session = request.getSession();
        // 2. 세션에 id가 있는지 확인, 있으면 true를 반환
        return session.getAttribute("id")!=null;
    }
}