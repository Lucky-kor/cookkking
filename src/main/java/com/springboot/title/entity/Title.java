package com.springboot.title.entity;

import com.springboot.audit.BaseEntity;
import com.springboot.challenge.entity.Challenge;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Title extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long titleId;

    @Column(nullable = false)
    private String titleName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String colorCode;

    @OneToOne(mappedBy = "title")
    private Challenge challenge;
}
