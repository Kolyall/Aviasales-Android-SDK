package com.avia.application.currencies;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class Currency {
    @Getter @Setter private String code;
    @Getter @Setter private String name;
}
