package com.aliro2.dto;

import com.aliro2.model.Menu;
import java.util.List;

/**
 * DTO (Data Transfer Object) para transportar los menús
 * de forma jerárquica a la vista (Thymeleaf).
 *
 * Contiene un "Grupo" (el menú padre, ej: "Gestión de Visitantes")
 * y una lista de "Items" (los botones hijos, ej: "Entrada Visitantes").
 */
public class MenuGroupDto {

    private Menu group;
    private List<Menu> items;

    public MenuGroupDto(Menu group, List<Menu> items) {
        this.group = group;
        this.items = items;
    }

    // --- Getters y Setters ---

    public Menu getGroup() {
        return group;
    }

    public void setGroup(Menu group) {
        this.group = group;
    }

    public List<Menu> getItems() {
        return items;
    }

    public void setItems(List<Menu> items) {
        this.items = items;
    }
}