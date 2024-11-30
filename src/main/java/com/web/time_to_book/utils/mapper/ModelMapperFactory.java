package com.web.time_to_book.utils.mapper;

import org.modelmapper.ModelMapper;

public class ModelMapperFactory {

    private ModelMapper modelMapper;

    public ModelMapper getMapper() {
        if (modelMapper == null) {
            modelMapper = initializeModelMapper(new ModelMapper());
        }

        return modelMapper;
    }

    private ModelMapper initializeModelMapper(ModelMapper modelMapper) {

        return modelMapper;
    }
}
