package com.example.rest.controller;

import com.example.rest.DTO.CreateCurierDTO;
import com.example.rest.entity.CurierEntity;
import com.example.rest.service.CurierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
@Controller
@RequestMapping("/curiers")
public class CurierController {
    @Autowired
    private CurierService curierService;

    @GetMapping
    public String getCurierPage(Model model, RedirectAttributes attrs) {
        List<CurierEntity> all = curierService.findAll();
        model.addAttribute("curiers", all);
        model.addAttribute("newCurierItem", new CreateCurierDTO());
        return "curiers";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{curierId}/edit")
    public String editCurierInfo(RedirectAttributes attrs, CurierEntity curierItem, @PathVariable Integer curierId, Model model) {
        try {
            System.out.println("item status" + curierItem.getStatus());
            curierService.editCurierInfo(curierId, curierItem);
            attrs.addFlashAttribute("message", "Курьер изменен");
            return "redirect:/curiers";
        } catch (Exception e) {
            attrs.addFlashAttribute("errorTitle", "Ошибка при изменении");
            attrs.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/error";
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{curierId}/delete")
    public String deleteCurierInfo(RedirectAttributes attrs, @PathVariable Integer curierId, Model model) {
        try {
            curierService.delete(curierId);
            attrs.addFlashAttribute("message", "Удалено");
            return "redirect:/curiers";
        } catch (Exception e) {
            attrs.addFlashAttribute("errorTitle", "Ошибка");
            attrs.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/error";
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create")
    public String createGoods(RedirectAttributes attrs, CreateCurierDTO curierItem, Model model) {
        try {
            curierService.createCurierItem(curierItem);
            attrs.addFlashAttribute("message", "Товар создан");
            return "redirect:/curiers";
        } catch (Exception e) {
            attrs.addFlashAttribute("errorTitle", "Ошибка при создании товара");
            attrs.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/error";
        }
    }
}
