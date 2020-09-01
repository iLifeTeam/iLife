package com.ilife.douban.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reference {
    ArrayList<String> bookTagList;
    String preAuthor;
    ArrayList<String> movieTagList;
    String musicTag;
    String gamgTag;
}


