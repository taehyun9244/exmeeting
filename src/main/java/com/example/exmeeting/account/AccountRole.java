package com.example.exmeeting.account;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountRole {
    ACCOUNT("account", "ユーザー"),
    ADMIN("admin", "管理者");

    private final String code;
    private final String nameJp;
}
