package com.mjc.school.service;

import com.mjc.school.data.model.News;
import com.mjc.school.dto.NewsRequestDto;
import com.mjc.school.dto.NewsResponseDto;
import com.mjc.school.mapper.NewsMapper;
import com.mjc.school.repository.NewsRepository;
import com.mjc.school.validator.Validator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class NewsService {

    private final NewsRepository newsRepository = NewsRepository.getInstance();

    public NewsService() {
    }

    public List<NewsResponseDto> getAllNews() {
        return NewsMapper.INSTANCE.newsListToDtoList(newsRepository.getAll());
    }

    public NewsResponseDto readById(Long id) {
        Validator.validateNewsId(id);
        Validator.validateNewsId(id);
        return NewsMapper.INSTANCE.newsToDto(newsRepository.getById(id));
    }

    public NewsResponseDto create(NewsRequestDto news) {
        Validator.validateDtoRequest(news);
        News model = NewsMapper.INSTANCE.dtoToNews(news);
        model.setCreateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        model.setLastUpdateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return NewsMapper.INSTANCE.newsToDto(newsRepository.create(model));
    }

    public NewsResponseDto update(NewsRequestDto news) {
        Validator.validateDtoRequest(news);
        News model = NewsMapper.INSTANCE.dtoToNews(news);
        model.setLastUpdateDate(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
        return NewsMapper.INSTANCE.newsToDto(newsRepository.update(model));
    }

    public Boolean delete(Long id) {
        validateNewsExistence(id);
        return newsRepository.delete(id);
    }

    private void validateNewsExistence(long id) {
        if (!newsRepository.ifIdExist(id)) {
            throw new RuntimeException("News not exists with id: " + id);
        }
    }
}
