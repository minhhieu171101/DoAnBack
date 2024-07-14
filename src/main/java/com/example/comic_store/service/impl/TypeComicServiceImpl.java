package com.example.comic_store.service.impl;

import com.example.comic_store.dto.TypeComicDTO;
import com.example.comic_store.entity.TypeComic;
import com.example.comic_store.repository.TypeComicRepository;
import com.example.comic_store.service.TypeComicService;
import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TypeComicServiceImpl implements TypeComicService {

    @Autowired
    private TypeComicRepository typeComicRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<TypeComicDTO> getListTypeComic() {
        List<TypeComic> typeComicList = typeComicRepository.findAll();
        return typeComicList
                .stream()
                .map(typeComic -> modelMapper.map(typeComic, TypeComicDTO.class))
                .collect(Collectors.toList());
    }
}
