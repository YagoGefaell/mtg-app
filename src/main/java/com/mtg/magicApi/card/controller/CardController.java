package com.mtg.magicApi.card.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mtg.magicApi.card.dto.CardRecord;
import com.mtg.magicApi.card.dto.ScryfallList;
import com.mtg.magicApi.card.service.MtgService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/mtg")
public class CardController {

    private final MtgService mtgService;

    public CardController(MtgService mtgService) {
        this.mtgService = mtgService;
    }

    @GetMapping("/cards")
    public Mono<ScryfallList<CardRecord>> getCardsByName(@RequestParam String name) {
        return this.mtgService.getCardsByName(name);
    }

    @GetMapping("/cards/{id}")
    public Mono<CardRecord> getCardById(@PathVariable String id) {
        return this.mtgService.getCardById(id);
    }

    @GetMapping("/cards/filter")
    public Mono<ScryfallList<CardRecord>> getCardsByFilters(
            @RequestParam String colors,
            @RequestParam String type,
            @RequestParam(defaultValue = "1") int page) {
        return this.mtgService.getCardsByFilters(colors, type, page);
    }

    @GetMapping("/sets")
    public Mono<String> getAllSets() {
        return this.mtgService.getAllSets();
    }

    @GetMapping("/cards/set/{setCode}")
    public Mono<ScryfallList<CardRecord>> getCardsBySet(@PathVariable String setCode) {
        return this.mtgService.getCardsBySet(setCode);
    }
}
