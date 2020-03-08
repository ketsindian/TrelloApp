package com.trello.service;

import com.trello.model.BoardListXref;
import com.trello.model.FullList;
import com.trello.model.TList;
import com.trello.utils.TrelloFunctionResponse;

import java.util.List;

public interface IListService {

    public List<TList> getListByBoardId(int boardId);

    public TList getListByBoardIdListId(BoardListXref boardListXref);

    public TList addListByBoardId(int boardId, TList list);

    public TrelloFunctionResponse deleteListByBoardIdListId(BoardListXref boardListXref);

    public TList updateListByBoardIdListId(int boardId, TList list);

    public FullList getFullListByBoardId(int boardId);

}