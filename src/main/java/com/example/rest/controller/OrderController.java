package com.example.rest.controller;

import com.example.rest.DTO.CreateOrderDTO;
import com.example.rest.DTO.FilterGoodsDTO;
import com.example.rest.entity.GoodsEntity;
import com.example.rest.entity.OrderEntity;
import com.example.rest.entity.UserEntity;
import com.example.rest.model.Goods;
import com.example.rest.service.GoodsService;
import com.example.rest.service.OrderService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private GoodsService goodsService;
    @PostMapping
    public String createOrder(CreateOrderDTO orderDTO, @AuthenticationPrincipal UserEntity user, RedirectAttributes attrs) {
        try {
            orderService.createOrder(orderDTO, user);
            attrs.addFlashAttribute("message", "Заказ сделан");
            return "redirect:/home";
        } catch (Exception e) {
            if(e.getMessage().equals("curier") || e.getCause().getCause().getMessage().equals("curier") || e.toString().equals("curier")) {
                return "redirect:/error-curiers";
            }
            attrs.addFlashAttribute("errorTitle", "Ошибка при создании");
            attrs.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/error";
        }
    }

    @GetMapping
    public String getOrders(Model model, @AuthenticationPrincipal UserEntity user) {
        List<OrderEntity> orders = orderService.getOrders(user);
        Long totalCost = orderService.getSummaryCost(user);
        model.addAttribute("orders", orders);
        model.addAttribute("cost", totalCost);
        return "orders";
    }

    @GetMapping("/add")
    public String getCreateOrdersPage(Model model) {
        CreateOrderDTO orderDto = new CreateOrderDTO();
        FilterGoodsDTO filterDto = new FilterGoodsDTO();
        List<GoodsEntity> allGoods = goodsService.getAllGoods(filterDto);
        val filterDtoFromAttr = model.getAttribute("filterDto");
        val orderDtoFromAttr = model.getAttribute("orderDto");
        List<Goods> goodsFromAttr = (List<Goods>) model.getAttribute("goods");
        CreateOrderDTO orderDtoCasted = orderDtoFromAttr == null ? orderDto : (CreateOrderDTO) orderDtoFromAttr;
        val checkedGoods = goodsService.getAllByIds(orderDtoCasted.getGoodsIds());
        val currentGoods = goodsFromAttr == null ? Goods.toModel(allGoods) : goodsFromAttr;
        model.addAttribute("goods", currentGoods);
        model.addAttribute("checkedGoods", checkedGoods);
        model.addAttribute("filterDto", filterDtoFromAttr == null ? filterDto : filterDtoFromAttr);
        model.addAttribute("orderDto", orderDtoFromAttr == null ? orderDto : orderDtoFromAttr);
        return "addOrder";
    }
    @PostMapping("/add")
    public String updateCreateOrderPage(RedirectAttributes attrs, FilterGoodsDTO dto, CreateOrderDTO orderDto) {
        List<GoodsEntity> allGoods = goodsService.getAllGoods(dto);
        attrs.addFlashAttribute("goods", Goods.toModel(allGoods));
        attrs.addFlashAttribute("filterDto", dto);
        attrs.addFlashAttribute("orderDto", orderDto);
        return "redirect:/order/add";
    }
    @PostMapping("/{id}/confirm")
    public String confirmOrder(Model model, RedirectAttributes attrs, @PathVariable Integer id, @AuthenticationPrincipal UserEntity user) {
        try {
            orderService.confirmOrder(id);
            System.out.println("success");
            List<OrderEntity> orders = orderService.getOrders(user);
            Long totalCost = orderService.getSummaryCost(user);
            model.addAttribute("orders", orders);
            model.addAttribute("cost", totalCost);
            return "orders";
        } catch (Exception e) {
            attrs.addFlashAttribute("errorTitle", "Ошибка при создании");
            attrs.addFlashAttribute("errorMessage", e.getMessage());
            System.out.println("error");
            return "redirect:/error";
        }

    }
}
