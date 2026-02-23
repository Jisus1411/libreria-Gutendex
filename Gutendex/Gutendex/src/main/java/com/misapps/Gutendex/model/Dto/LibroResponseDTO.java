package com.misapps.Gutendex.model.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroResponseDTO(List<LibroDTO> results) {}
