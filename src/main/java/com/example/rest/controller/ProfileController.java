package com.example.rest.controller;

import com.example.rest.entity.AddressEntity;
import com.example.rest.entity.UserEntity;
import com.example.rest.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileService profileService;
    @PostMapping("/main")
    String updateProfileMainInfo(@AuthenticationPrincipal UserEntity user, UserEntity updatedUser, RedirectAttributes attrs) {
        profileService.updateMainInfo(user, updatedUser);
        attrs.addFlashAttribute("message", "Основная информация обновлена");
        return "redirect:/home";
    }
    @PostMapping("/cash")
    String updateProfileCash(@AuthenticationPrincipal UserEntity user, UserEntity updatedUser, RedirectAttributes attrs) {
        if(updatedUser.getCash() <= 0) {
            attrs.addFlashAttribute("message", "Ошибка при пополнении");
        } else {
            profileService.updateCash(user, updatedUser);
            attrs.addFlashAttribute("message", "Баланс пополнен");
        }
        return "redirect:/home";
    }
    @PostMapping("/address")
    String updateAddress(@AuthenticationPrincipal UserEntity user, AddressEntity newAddress, RedirectAttributes attrs) {
        profileService.updateAddress(user, newAddress);
        attrs.addFlashAttribute("message", "Адрес обновлен");
        return "redirect:/home";
    }
    @PostMapping("/delete")
    String removeAccount(@AuthenticationPrincipal UserEntity user, RedirectAttributes attrs) {
        profileService.removeAccount(user);
        attrs.addFlashAttribute("message", "Вы удалили свой аккаунт");
        return "redirect:/logout";
    }
}
