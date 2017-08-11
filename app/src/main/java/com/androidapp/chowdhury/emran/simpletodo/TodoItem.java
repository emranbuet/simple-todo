package com.androidapp.chowdhury.emran.simpletodo;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.UUID;

@Table(database = TodoItemDatabase.class)
public class TodoItem extends BaseModel {
    @PrimaryKey
    UUID itemUid;

    @Column(name = "ItemName")
    public String itemName;

    public TodoItem(){
        super();
    }

    public TodoItem(String name){
        super();
        this.itemName = name;
        this.itemUid = UUID.randomUUID();
    }
}
