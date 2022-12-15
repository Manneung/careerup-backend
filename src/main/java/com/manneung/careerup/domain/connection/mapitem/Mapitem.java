package com.manneung.careerup.domain.connection.mapitem;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity(name = "mapitem")
public class Mapitem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "map_idx")
    private int mapIdx;

    @Column(name = "item_idx")
    private int itemIdx;

    public Mapitem(int mapIdx, int itemIdx) {
        this.mapIdx = mapIdx;
        this.itemIdx = itemIdx;
    }
}
