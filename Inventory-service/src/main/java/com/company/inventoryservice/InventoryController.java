package com.company.inventoryservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class InventoryController {

    List<Inventory> inventoryList = new ArrayList<>();

    @GetMapping("/inventory/{productId}")
    public Inventory getInventoryDetails(@PathVariable Long productId) {
        Inventory inventory = getInventoryInfo(productId);
        return inventory;
    }

    private Inventory getInventoryInfo(Long productId) {
        populateInventoryList();
        for (Inventory inventory : inventoryList) {
            if (inventory.getProductId().equals(productId)) {
                return inventory;
            }
        }
        return null;
    }

    private void populateInventoryList() {
        inventoryList.clear();
        inventoryList.add(new Inventory(301L, 101L, true));
        inventoryList.add(new Inventory(302L, 102L,true));
        inventoryList.add(new Inventory(303L, 101L, false));
    }
}
