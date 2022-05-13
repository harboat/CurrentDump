package com.github.harboat.clients.placement;

import lombok.*;

import java.util.Collection;


@AllArgsConstructor @NoArgsConstructor
@Getter @Setter @ToString
public class Masts {
    private Collection<Integer> positions;
}
