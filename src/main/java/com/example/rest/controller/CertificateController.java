package com.example.rest.controller;

import com.example.rest.entity.CertificateEntity;
import com.example.rest.entity.UserEntity;
import com.example.rest.model.Certificate;
import com.example.rest.model.User;
import com.example.rest.service.CertificateService;
import com.example.rest.service.UserService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/certificate")
public class CertificateController {

    @Autowired
    private UserService userService;
    @Autowired
    private CertificateService certificateService;

    @GetMapping
    public String getCertPage(@AuthenticationPrincipal UserEntity user, Model model) {
        val received = user.getDescReceivedCertififcates();
        val donated = user.getDescDonatedCertificates();
        model.addAttribute("received", Certificate.toModel(received));
        model.addAttribute("donated", Certificate.toModel(donated));
        return "certificates";
    }
    @GetMapping("/add")
    public String getAddCertPage(Model model, RedirectAttributes attrs) {
        val newCert = new CertificateEntity();
        val newUser = new UserEntity();
        val newUserFromAttr = model.getAttribute("newUser");
        val users = userService.getAll();
        val usersFromAttr = model.getAttribute("users");
        model.addAttribute("newCert", newCert);
        model.addAttribute("users", usersFromAttr == null ? User.toModel(users) : usersFromAttr);
        model.addAttribute("newUser", newUserFromAttr == null ? newUser : newUserFromAttr);
        return "addCert";
    }
    @PostMapping("/add")
    public String updateAddCertPage(Model model, UserEntity user, RedirectAttributes attrs) {
        UserEntity newUser = UserEntity.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .build();
        List<UserEntity> users = userService.filterByFields(user);
        attrs.addFlashAttribute("users", User.toModel(users));
        attrs.addFlashAttribute("newUser", newUser);
        return "redirect:/certificate/add";
    }

    @PostMapping("/{toUserId}/add")
    public String addCertificate(
            RedirectAttributes attrs,
            @AuthenticationPrincipal UserEntity currentUser,
            CertificateEntity certInfo,
            @PathVariable UUID toUserId
    ) {
        try {
            certificateService.create(currentUser, toUserId, certInfo);
            attrs.addFlashAttribute("message", "Сертификат подарен");
            return "redirect:/certificate";
        } catch(Exception e) {
            attrs.addFlashAttribute("errorTitle", "Возникла ошибка");
            attrs.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/error";
        }
    }
}
