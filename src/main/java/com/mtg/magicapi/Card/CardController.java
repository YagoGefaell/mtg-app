package com.mtg.magicapi.Card;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/cards")
public class CardController {
    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping
    public Page<Card> getCards(
            @RequestParam(required = false) Integer id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String typeLine,
            @RequestParam(required = false) String rarity) {
        Pageable pageable = PageRequest.of(0, 30);

        return cardService.getCards(pageable);
    }

    @PostMapping
    public ResponseEntity<Card> addCard(@RequestBody Card card){
        Card createdCard = cardService.addCard(card);
        return new ResponseEntity<>(createdCard, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Card> updateCard(@RequestBody Card card){
        Card resultCard = cardService.updateCard(card);
        if (resultCard != null){
            return new ResponseEntity<>(resultCard, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{cardName}")
    public ResponseEntity<String> deleteCard(@PathVariable String cardName){
        cardService.deleteCard(cardName);
        return new ResponseEntity<>("Card deleted succesfully", HttpStatus.OK);
    }
}
