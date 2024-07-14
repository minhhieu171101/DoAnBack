package com.example.comic_store.service.impl;

import com.example.comic_store.dto.ComicAdminDTO;
import com.example.comic_store.dto.ComicDTO;
import com.example.comic_store.dto.ComicDetailDTO;
import com.example.comic_store.dto.ServiceResult;
import com.example.comic_store.entity.Comic;
import com.example.comic_store.entity.TypeComic;
import com.example.comic_store.repository.ComicRepository;
import com.example.comic_store.repository.TypeComicRepository;
import com.example.comic_store.service.ComicService;
import com.example.comic_store.service.mapper.ComicMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ComicServiceImpl implements ComicService {

    @Autowired
    private ComicRepository comicRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ComicMapper comicMapper;

    @Autowired
    private TypeComicRepository typeComicRepository;

    @Value("${COMIC_FOLDER_PATH}")
    private String folderPath;

    @Value("${COMIC_TARGET_FOLDER_PATH}")
    private String folderTargetPath;

    public List<ComicDTO> getListComicLanding(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Comic> comicPage = comicRepository.getComicLandingPage(pageable);
        return comicPage.stream()
                .map(element -> modelMapper.map(element, ComicDTO.class))
                .toList();
    }

    @Override
    public ComicDetailDTO getComicBy(Long id) {
        Object[] object = comicRepository.getComicBy(id);
        ComicDetailDTO comicDetailDTO = comicMapper.toComicDetailDTO(object);
        return modelMapper.map(comicDetailDTO, ComicDetailDTO.class);
    }

    @Override
    public Page<ComicDTO> getPageComic(int page, int pageSize, Long typeComicId) {
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Comic> comicPage;
        if (typeComicId != null) {
            comicPage = comicRepository.getAllByTypeComicId(pageable, "s" + typeComicId + "e");
        } else {
            comicPage = comicRepository.getComicLandingPage(pageable);
        }
        return comicPage.map(element -> modelMapper.map(element, ComicDTO.class));
    }

    @Override
    public Page<ComicAdminDTO> getComicAdmin(ComicDTO comicDTO) {
        Pageable pageable = PageRequest.of(comicDTO.getPage(), comicDTO.getPageSize());
        Page<Object[]> comicPage = comicRepository.getAllComic(pageable, comicDTO.getSearchKey());
        return comicMapper.toComicAdminPageDTO(comicPage);
    }

    @Override
    public ServiceResult<String> updateComic(ComicAdminDTO comicAdminDTO, MultipartFile file) {
        // thực hiện lấy ra tên các thể loại truyện
        String[] typeNames = comicAdminDTO.getTypeName()
                .trim()
                .toLowerCase()
                .split(",");

        // Thực hiện chuẩn hóa dữ liệu tên thể loại truyện
        List<String> typeNameList = Arrays.stream(typeNames)
                .map(String::trim)
                .toList();

        // Lấy ra các thể loại truyện đã tạo
        List<TypeComic> typeComicsExist = typeComicRepository
                .findAllByTypeNameIn(typeNameList)
                .stream()
                .peek(typeComic -> typeComic.setTypeName(typeComic.getTypeName().toLowerCase()))
                .collect(Collectors.toList());

        // Lấy ra tên các thể loại truyện đã tạo
        Set<String> typeNameComicExist = typeComicsExist.stream()
                .map(TypeComic::getTypeName)
                .collect(Collectors.toSet());

        //  Lấy ra tên các thể loại truyện chưa tồn tại
        List<String> typeComicNameNotExist = typeNameList.stream()
                .filter(typename -> ! typeNameComicExist.contains(typename))
                .toList();

        if (! typeComicNameNotExist.isEmpty()) {
            List<TypeComic> typeComicsCreate = new ArrayList<>();

            // Tạo các thể loại truyện mới
            for (String typeName : typeComicNameNotExist) {
                TypeComic typeComic = new TypeComic();
                typeComic.setTypeName(typeName);
                typeComic.setCreatedAt(LocalDateTime.now());
                typeComic.setUpdatedAt(LocalDateTime.now());
                typeComicsCreate.add(typeComic);
            }
            // Lấy ra toàn bộ thể loại của truyện
            List<TypeComic> typeComicsReturn = typeComicRepository.saveAll(typeComicsCreate);
            typeComicsExist.addAll(typeComicsReturn);
        }

        Comic comic = comicMapper.toComic(comicAdminDTO);

        // Cập nhật thể loại truyện
        comic.setTypeComicIds(
                typeComicsExist
                        .stream()
                        .map(TypeComic::getId)
                        .map(typeId -> "s" + typeId + "e")
                        .collect(Collectors.joining(","))
        );

        // Thực hiện thêm hoặc cập nhật ảnh
        if (file != null) {
            comic.setImgComic(file.getOriginalFilename());
            Path path = Path.of(folderPath + file.getOriginalFilename());
            Path pathTarget = Path.of(folderTargetPath + file.getOriginalFilename());
            try {
                Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                Files.copy(file.getInputStream(), pathTarget, StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        comic.setCreatedAt(LocalDateTime.now());
        comic.setUpdatedAt(LocalDateTime.now());

        comicRepository.save(comic);
        ServiceResult<String> result = new ServiceResult<>();
        result.setStatus(HttpStatus.OK);
        if (comicAdminDTO.getId() != null) {
            result.setMessage("Cập nhật truyện thành công!");
        } else {
            result.setMessage("Thêm mới truyện thành công!");
        }
        return result;
    }

    @Override
    public ServiceResult<String> deleteComic(ComicAdminDTO comicAdminDTO) {
        Optional<Comic> comic = comicRepository.findById(comicAdminDTO.getId());
        ServiceResult<String> result = new ServiceResult<>();
        if (comic.isPresent()) {
            comicRepository.delete(comic.orElse(null));
            result.setStatus(HttpStatus.OK);
            result.setMessage("Xóa truyện thành công!");
            return result;
        }
        result.setStatus(HttpStatus.BAD_REQUEST);
        result.setMessage("Xóa truyện không thành công!");
        return result;
    }

    @Override
    public Page<ComicDTO> searchComic(ComicDTO comicDTO) {
        Pageable pageable = PageRequest.of(comicDTO.getPage(), comicDTO.getPageSize());
        Page<Object[]> objects = comicRepository.searchComic(pageable, comicDTO.getSearchKey());
        return comicMapper.toComicDTOPage(objects);
    }
}
