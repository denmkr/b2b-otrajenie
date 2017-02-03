package ru.dm.shop.service;

import ru.dm.shop.entity.User;
import ru.dm.shop.entity.UserRole;

/**
 * Created by Denis on 17.04.16.
 */

public interface UserRoleService {
    UserRole createUser(User user);
    UserRole createAdmin(User user);
    UserRole createPartner(User user);

    UserRole findByUserId(Long userId);

    void deleteByUserId(Long userId);

    void changeUserRole(String authority, Long userId);
}