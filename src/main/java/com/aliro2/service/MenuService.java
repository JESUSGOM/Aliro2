package com.aliro2.service;

import com.aliro2.model.Menu;
import com.aliro2.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MenuService {

    @Autowired
    private MenuRepository menuRepository;

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