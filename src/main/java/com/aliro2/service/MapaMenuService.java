package com.aliro2.service;

import com.aliro2.model.MapaMenu;
import com.aliro2.repository.MapaMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MapaMenuService {

    @Autowired
    private MapaMenuRepository mapaMenuRepository;

    public List<MapaMenu> findAll() {
        return mapaMenuRepository.findAll();
    }

    public Optional<MapaMenu> findById(Integer id) {
        return mapaMenuRepository.findById(id);
    }

    public MapaMenu save(MapaMenu mapaMenu) {
        return mapaMenuRepository.save(mapaMenu);
    }

    public void deleteById(Integer id) {
        mapaMenuRepository.deleteById(id);
    }
}