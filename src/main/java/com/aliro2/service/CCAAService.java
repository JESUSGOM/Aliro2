package com.aliro2.service;

import com.aliro2.model.CCAA;
import com.aliro2.repository.CCAARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CCAAService {

    @Autowired
    private CCAARepository ccaaRepository;

    public List<CCAA> findAll() {
        return ccaaRepository.findAll();
    }

    public Optional<CCAA> findById(Integer id) {
        return ccaaRepository.findById(id);
    }

    public CCAA save(CCAA ccaa) {
        return ccaaRepository.save(ccaa);
    }

    public void deleteById(Integer id) {
        ccaaRepository.deleteById(id);
    }
}