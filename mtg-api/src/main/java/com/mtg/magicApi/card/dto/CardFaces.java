package com.mtg.magicApi.card.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CardFaces(
    String name,
    @JsonProperty("image_uris") Map<String, String> imageUris
) {}
