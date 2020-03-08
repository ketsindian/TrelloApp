package com.trello.controller;

import com.trello.model.Card;
import com.trello.model.ListCardXref;
import com.trello.service.ICardService;
import com.trello.utils.TrelloFunctionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("board/{boardId}/list/{listId}")
public class CardController {
    private final ICardService cardService;

    @Autowired
    public CardController(ICardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/card")
    public List<Card> getCardByListId(@Valid @PathVariable int boardId, @Valid @PathVariable int listId) {
        return cardService.getCardByListId(boardId, listId);
    }

    @GetMapping("/card/{cardId}")
    public Card getCardByListIdCardId(@Valid @PathVariable int boardId, @Valid @PathVariable int listId, @Valid @PathVariable int cardId) {
        ListCardXref listCardXref = new ListCardXref();
        listCardXref.setCard_id(cardId);
        listCardXref.setList_id(listId);
        return cardService.getCardByListIdCardId(boardId, listCardXref);
    }

    @PostMapping("/card")
    public Card addCardToList(@Valid @PathVariable int boardId, @Valid @PathVariable int listId, @Valid @RequestBody Card card) {
        return cardService.addCardByListId(boardId, listId, card);
    }

    @PutMapping("/card/{cardId}")
    public Card updateCardByListIdCardId(@Valid @PathVariable int boardId, @Valid @PathVariable int listId, @Valid @RequestBody Card card, @Valid @PathVariable int cardId) {
        card.setCard_id(cardId);
        return cardService.updateCardByListIdCardId(boardId, listId, card);
    }

    @DeleteMapping("/card/{cardId}")
    public ResponseEntity deleteCardByCardIdListId(@Valid @PathVariable int boardId, @Valid @PathVariable int listId, @Valid @PathVariable int cardId) {
        ListCardXref listCardXref = new ListCardXref();
        listCardXref.setCard_id(cardId);
        listCardXref.setList_id(listId);
        TrelloFunctionResponse trelloFunctionResponse = cardService.deleteCardByListIdCardId(listCardXref);
        return new ResponseEntity(trelloFunctionResponse, HttpStatus.OK);
    }
}
