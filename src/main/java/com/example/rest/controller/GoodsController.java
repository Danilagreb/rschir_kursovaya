package com.example.rest.controller;

import com.example.rest.DTO.FilterGoodsDTO;
import com.example.rest.DTO.SetDiscountDto;
import com.example.rest.entity.GoodsEntity;
import com.example.rest.model.Goods;
import com.example.rest.service.GoodsService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.UUID;

@Component
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping
    public String getGoodsPage(Model model, RedirectAttributes attrs) {
        val filterDto = new FilterGoodsDTO();
        List<GoodsEntity> allGoods = goodsService.getAllGoods(filterDto);
        val filterDtoFromAttr = model.getAttribute("filterDto");
        val goodsFromAttr = model.getAttribute("goods");
        model.addAttribute("goods", goodsFromAttr == null ? Goods.toModel(allGoods) : goodsFromAttr);
        model.addAttribute("filterDto", filterDtoFromAttr == null ? filterDto : filterDtoFromAttr);
        model.addAttribute("discountDto", new SetDiscountDto());
        model.addAttribute("newGoodsItem", new GoodsEntity());
        return "goods";
    }
    @PostMapping
    public String filterGoodsPage(RedirectAttributes attrs, FilterGoodsDTO dto) {
        List<GoodsEntity> allGoods = goodsService.getAllGoods(dto);
        attrs.addFlashAttribute("goods", Goods.toModel(allGoods));
        attrs.addFlashAttribute("filterDto", dto);
        return "redirect:/goods";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{goodsId}/discount")
    public String editGoodsDiscount(RedirectAttributes attrs, SetDiscountDto dto, @PathVariable UUID goodsId, Model model) {
        try {
            goodsService.editDiscount(goodsId, dto.getDiscount());
            attrs.addFlashAttribute("message", "Изменена скидка");
            return "redirect:/goods";
        } catch (Exception e) {
            attrs.addFlashAttribute("errorTitle", "Ошибка при изменении скидки");
            attrs.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/error";
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{goodsId}/edit")
    public String editGoodsInfo(RedirectAttributes attrs, GoodsEntity goodsItem, @PathVariable UUID goodsId, Model model) {
        try {
            goodsService.editGoodsInfo(goodsId, goodsItem);
            attrs.addFlashAttribute("message", "Товар изменен");
            return "redirect:/goods";
        } catch (Exception e) {
            attrs.addFlashAttribute("errorTitle", "Ошибка при изменении товара");
            attrs.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/error";
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/create")
    public String createGoods(RedirectAttributes attrs, GoodsEntity goodsItem, Model model) {
        try {
            goodsService.createGoodsItem(goodsItem);
            attrs.addFlashAttribute("message", "Товар создан");
            return "redirect:/goods";
        } catch (Exception e) {
            attrs.addFlashAttribute("errorTitle", "Ошибка при создании товара");
            attrs.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/error";
        }
    }
}
