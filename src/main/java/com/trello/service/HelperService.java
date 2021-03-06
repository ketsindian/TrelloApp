package com.trello.service;

import com.trello.model.AppUser;
import com.trello.model.BoardListXref;
import com.trello.model.ListCardXref;
import com.trello.repository.BoardListXrefRepository;
import com.trello.repository.BoardRepository;
import com.trello.repository.ListCardXrefRepository;
import com.trello.repository.UserRepository;
import com.trello.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class HelperService {

    private final BoardRepository boardRepository;
    private final ListCardXrefRepository listCardXrefRepository;
    private final BoardListXrefRepository boardListXrefRepository;
    private final UserRepository userRepository;

    @Autowired
    HelperService(BoardRepository boardRepository, ListCardXrefRepository listCardXrefRepository, BoardListXrefRepository boardListXrefRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.listCardXrefRepository = listCardXrefRepository;
        this.boardListXrefRepository = boardListXrefRepository;
        this.userRepository = userRepository;
    }

    void boardExistsById(int boardId) {
        if (!checkBoardExistsById(boardId))
            throw new ResourceNotFoundException("board not found with id : " + boardId);
    }

    boolean checkBoardExistsById(int boardId) {
        return boardRepository.existsById(boardId);
    }

    public void listExistsByBoardIdListId(BoardListXref boardListXref) {
        this.boardExistsById(boardListXref.getBoard_id());
        Example<BoardListXref> example = Example.of(boardListXref, ExampleMatcher.matchingAll());
        ;
        if (!this.checkListExistsByBoardIdListId(boardListXref.getBoard_id(), boardListXref.getList_id()))
            throw new ResourceNotFoundException("No list with id : " + boardListXref.getList_id() + " found in board with id " + boardListXref.getBoard_id());
    }

    public boolean checkListExistsByBoardIdListId(int boardId, int listId) {
        BoardListXref boardListXref = new BoardListXref();
        boardListXref.setList_id(listId);
        boardListXref.setBoard_id(boardId);
        Example<BoardListXref> example = Example.of(boardListXref, ExampleMatcher.matchingAll());
        ;
        return boardListXrefRepository.exists(example);
    }


    public void listExistsByBoardIdListId(int boardId, int listId) {
        BoardListXref boardListXref = new BoardListXref();
        boardListXref.setList_id(listId);
        boardListXref.setBoard_id(boardId);
        this.listExistsByBoardIdListId(boardListXref);
    }

    public void cardExistsByListIdCardId(ListCardXref listCardXref) {
        if (!this.checkCardExistsByListIdCardId(listCardXref))
            throw new ResourceNotFoundException("No card with id : " + listCardXref.getCard_id() + " found in list with id " + listCardXref.getList_id());
    }

    public boolean checkCardExistsByListIdCardId(ListCardXref listCardXref) {
        Example<ListCardXref> example = Example.of(listCardXref, ExampleMatcher.matchingAny());
        return listCardXrefRepository.exists(example);
    }

    public AppUser getUserFromContext() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final String mailId;
        if (principal instanceof UserDetails) {
            mailId = ((UserDetails) principal).getUsername();
        } else {
            mailId = principal.toString();
        }
        AppUser user = userRepository.getAppUserByMailId(mailId);
        if (user == null) {
            throw new ResourceNotFoundException("User not found in context");
        }
        return user;
    }
}
