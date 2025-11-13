package com.aliro2.service;

import com.aliro2.dto.MenuGroupDto;
import com.aliro2.model.MapaMenu;
import com.aliro2.model.Menu;
import com.aliro2.repository.MapaMenuRepository;
import com.aliro2.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final MapaMenuRepository mapaMenuRepository;

    // ID del menú "Raíz" (padre de los grupos) según tu script SQL
    private static final int ROOT_MENU_ID = 1;
    private static final int MENU_ESTADO_ACTIVO = 1;

    @Autowired
    public MenuService(MenuRepository menuRepository, MapaMenuRepository mapaMenuRepository) {
        this.menuRepository = menuRepository;
        this.mapaMenuRepository = mapaMenuRepository;
    }

    /**
     * Obtiene la lista jerárquica de grupos de menú y sus items
     * para un tipo de usuario y centro específicos.
     */
    public List<MenuGroupDto> getDynamicMenuForUser(String usuTipo, Integer centro) {

        // 1. Obtener los IDs de menú permitidos para este rol y centro
        // Llama al método que acabamos de añadir en MapaMenuRepository
        List<MapaMenu> mappings = mapaMenuRepository.findByMmUsuTipoAndMmCentro(usuTipo, centro);

        List<Integer> menuIds = mappings.stream()
                .map(MapaMenu::getMmMnId)
                .collect(Collectors.toList());

        if (menuIds.isEmpty()) {
            return Collections.emptyList(); // No hay menús asignados
        }

        // 2. Obtener todos los objetos Menu activos de esos IDs
        // Llama al método que acabamos de añadir en MenuRepository
        List<Menu> allMenus = menuRepository.findByMnIdInAndEstadoMenuOrderByMnParentIdAscMnIdAsc(menuIds, MENU_ESTADO_ACTIVO);

        // 3. Separar los items (botones) por su ID de grupo (padre)
        Map<Integer, List<Menu>> itemsByParent = allMenus.stream()
                .filter(m -> m.getMnParentId() != ROOT_MENU_ID) // Filtra solo los hijos (botones)
                .collect(Collectors.groupingBy(Menu::getMnParentId));

        // 4. Obtener la lista de Grupos (padres)
        List<Menu> groups = allMenus.stream()
                .filter(m -> m.getMnParentId() == ROOT_MENU_ID) // Filtra solo los padres (grupos)
                .toList();

        // 5. Construir la lista final de DTOs
        List<MenuGroupDto> menuGroups = new ArrayList<>();
        for (Menu group : groups) {
            // Buscamos los hijos que le corresponden a este grupo
            List<Menu> items = itemsByParent.getOrDefault(group.getMnId(), Collections.emptyList());

            // Solo añadimos el grupo al menú si tiene al menos un botón asignado
            if (!items.isEmpty()) {
                menuGroups.add(new MenuGroupDto(group, items));
            }
        }

        return menuGroups;
    }

    // --- Métodos CRUD Estándar (que ya tenías) ---

    public List<Menu> findAll() {
        return menuRepository.findAll();
    }

    public Optional<Menu> findById(Integer id) {
        return menuRepository.findById(id);
    }

    public Menu save(Menu menu) {
        return menuRepository.save(menu);
    }

    public void deleteById(Integer id) {
        menuRepository.deleteById(id);
    }
}