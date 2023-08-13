package com.croc.bonjour.dto;

import com.croc.bonjour.domain.MenuItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto {
    private List<MenuItem> items;
}
