package com.mtg.magicApi.card.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.mtg.magicApi.card.dto.CardRecord;
import com.mtg.magicApi.card.dto.ScryfallList;

import reactor.core.publisher.Mono;

@Service
public class MtgService {

    private static final ParameterizedTypeReference<ScryfallList<CardRecord>> CARD_LIST_TYPE =
            new ParameterizedTypeReference<>() {};

    private final WebClient webClient;

    public MtgService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<ScryfallList<CardRecord>> getCardsByName(String name) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/cards")
                        .queryParam("name", name)
                        .build())
                .retrieve()
                .bodyToMono(CARD_LIST_TYPE);
    }

    public Mono<CardRecord> getCardById(String id) {
        return this.webClient.get()
                .uri("/cards/{id}", id)
                .retrieve()
                // Si la carta no existe, puedes manejar el error 404 aquí
                .onStatus(status -> status.is4xxClientError(),
                        response -> Mono.error(new RuntimeException("Carta no encontrada")))
                .bodyToMono(CardRecord.class);
    }

    public Mono<ScryfallList<CardRecord>> getCardsByFilters(String colors, String type, int page) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/cards")
                        .queryParam("colors", colors)
                        .queryParam("type", type)
                        .queryParam("page", page)
                        .queryParam("pageSize", 20) // Limitamos para no saturar
                        .build())
                .retrieve()
                .bodyToMono(CARD_LIST_TYPE);
    }

    public Mono<String> getAllSets() {
        return this.webClient.get()
                .uri("/sets")
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<ScryfallList<CardRecord>> getCardsBySet(String setCode) {
        return this.webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/cards")
                        .queryParam("set", setCode)
                        .build())
                .retrieve()
                .bodyToMono(CARD_LIST_TYPE);
    }
}