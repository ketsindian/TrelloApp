package com.trello.service;

import com.trello.model.BoardListXref;
import com.trello.model.TList;
import com.trello.utils.TrelloDeleteResponse;

import java.util.List;

public interface IListService {

    public List<TList> getListByBoardId(int boardId);

    public TList getListByBoardIdListId(BoardListXref boardListXref);

    public TList addListByBoardId(int boardId,TList list);

    public TrelloDeleteResponse deleteListByBoardIdListId(BoardListXref boardListXref);

    public TList updateListByBoardIdListId(int boardId,TList list);

    public boolean listExistsByBoardIdListId(BoardListXref boardListXref);
}