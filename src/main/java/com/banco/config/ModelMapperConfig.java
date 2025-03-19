package com.banco.config;

import com.banco.dto.CuentaDto;
import com.banco.entity.Cuenta;
import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.transform.Source;
import java.util.List;

@Configuration
public class ModelMapperConfig {
        @Bean
        public ModelMapper modelMapper() {
            ModelMapper modelMapper = new ModelMapper();

            TypeMap<Cuenta,CuentaDto> propertyMapper = modelMapper.createTypeMap(Cuenta.class, CuentaDto.class);
            propertyMapper.addMapping(Cuenta::getCliente,CuentaDto::setCliente_id);
            propertyMapper.addMapping(src -> src.getCliente().getId(), CuentaDto::setCliente_id);

            return modelMapper;
        }


}
