package com.g3usa.conectmongo.Utilities;

import com.g3usa.conectmongo.Entities.User;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class responseUser {
    private responseProcess responseProcess;
    private User user;
}
