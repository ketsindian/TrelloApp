package com.trello.integration;

import com.trello.model.Board;
import com.trello.model.Card;
import com.trello.model.TList;
import com.trello.service.HelperService;
import com.trello.service.IBoardService;
import com.trello.service.ICardService;
import com.trello.service.IListService;
import com.trello.utils.ResourceNotFoundException;
import com.trello.utils.TrelloAuthenticationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class TrelloApiIntegrationService {

    private final IBoardService boardService;
    private final IListService listService;
    private final ICardService cardService;
    private final HelperService helperService;

    public TrelloApiIntegrationService(IBoardService boardService, IListService listService, ICardService cardService, HelperService helperService) {
        this.boardService = boardService;
        this.listService = listService;
        this.cardService = cardService;
        this.helperService = helperService;
    }

    public Board syncFromTrello(String boardId){
        final String board_url=TrelloApiConstants.BOARD_URL+boardId+TrelloApiConstants.BOARD_URL_OPTIONS+TrelloApiConstants.TRELLO_KEY+TrelloApiConstants.TRELLO_TOKEN;

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<TrelloListApiResponse[]> res=restTemplate.getForEntity(board_url,TrelloListApiResponse[].class);
        TrelloListApiResponse[] trelloListApiResponses=res.getBody();
        System.out.println(Arrays.toString(trelloListApiResponses));

//        create board
        Board board=new Board();
        board.setBoard_name("Trello");
        board.setBoard_owner_id(helperService.getUserFromContext().getUser_id());
        Board createdBoard=boardService.addBoard(board);

//      add lists in board
        if(trelloListApiResponses == null){
            throw new ResourceNotFoundException("TrelloAPI failed to respond");
        }
        for (TrelloListApiResponse trelloListApiRespons : trelloListApiResponses) {
            TList tList = new TList();
            tList.setList_name(trelloListApiRespons.getName());
            TList addedList = listService.addListByBoardId(createdBoard.getBoard_id(), tList);
            for (TrelloCardApiResponse card : trelloListApiRespons.getCards()) {
                Card cardToAdd = new Card();
                cardToAdd.setCard_name(card.getName());
                Card addedCard = cardService.addCardByListId(createdBoard.getBoard_id(), addedList.getList_id(), cardToAdd);
            }
        }
        return createdBoard;
    }
}
