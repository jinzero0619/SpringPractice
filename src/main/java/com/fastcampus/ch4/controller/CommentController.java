package com.fastcampus.ch4.controller;

import com.fastcampus.ch4.domain.BoardDto;
import com.fastcampus.ch4.domain.CommentDto;
import com.fastcampus.ch4.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.xml.stream.events.Comment;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    CommentService service;

    // 댓글 수정
    @PatchMapping("comments/{cno}")
    public ResponseEntity<String> modify(@PathVariable Integer cno, @RequestBody CommentDto commentDto,HttpSession session){
        String commenter = (String)session.getAttribute("id");
        commentDto.setCno(cno);
        commentDto.setCommenter(commenter);
        System.out.println("commentDto = " + commentDto);
        try {
            int rowCnt = service.modify(commentDto);
            if(rowCnt != 1)
                throw new Exception("Modify Fail");

            return new ResponseEntity<>("Modify_Ok",HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Modify_Fail",HttpStatus.BAD_REQUEST);
        }
    }


    // 댓글 저장
    @PostMapping("/comments")
    public ResponseEntity<String> write(@RequestBody CommentDto commentDto, Integer bno, RedirectAttributes rattr, HttpSession session){
        String commenter = (String)session.getAttribute("id");
//        commenter = "jinzero";
        commentDto.setCommenter(commenter);
        commentDto.setBno(bno);
        System.out.println("commentDto = " + commentDto);
        try {
            int rowCnt = service.write(commentDto);
            if(rowCnt != 1)
                throw new Exception("Write Fail");
            rattr.addFlashAttribute("msg","Write_Ok");
            return new ResponseEntity<>("Write_Ok",HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("Write_Fail",HttpStatus.BAD_REQUEST);
        }

    }


    // 댓글 삭제
    @DeleteMapping("/comments/{cno}")
    public ResponseEntity<String> remove(@PathVariable Integer cno, Integer bno, String commenter,
                                            HttpSession session) {
        commenter = (String)session.getAttribute("id");
        try {
            int rowCnt = service.remove(cno,bno,commenter);
            if(rowCnt != 1)
                throw new Exception("DELETE FAIL");
//            for(int i =51; i<= 70; i++){
//                service.remove(i,1107,commenter);
//            }

           return new ResponseEntity<>("DEL_OK",HttpStatus.OK);
        } catch (Exception e) {
           e.printStackTrace();
            return new ResponseEntity<>("DEL_FAIL",HttpStatus.BAD_REQUEST);
        }
    }


    // bno에 해당하는 comment 가져오기
    @GetMapping("/comments")  // /comments?bno=1080   GET
    public ResponseEntity<List<CommentDto>> list(Integer bno, Model m, HttpSession session) {
        List<CommentDto> list = null;
        String LoginID = (String)session.getAttribute("id");
        try {
            list = service.getList(bno);
            m.addAttribute("LoginID", LoginID);
            return new ResponseEntity<List<CommentDto>>(list, HttpStatus.OK);  // 200
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<CommentDto>>(HttpStatus.BAD_REQUEST); // 400
        }
    }
}
