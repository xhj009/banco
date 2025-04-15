package com.banco.config;

import com.banco.dto.CuentaDTO;
import com.banco.dto.TransaccionDTO;
import com.banco.entity.Cuenta;
import com.banco.entity.Transaccion;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        TypeMap<Cuenta, CuentaDTO> propertyMapper = modelMapper.createTypeMap(Cuenta.class, CuentaDTO.class);
        //propertyMapper.addMapping(Cuenta::getCliente, CuentaDTO::setCliente_id);
        //propertyMapper.addMapping(src -> src.getCliente().getId(), CuentaDTO::setCliente_id);

//        modelMapper.createTypeMap(Transaccion.class, TransaccionDTO.class)
//                .addMappings(mapper -> {
//                    mapper.using(new ListCuentaToStringConverter())
//                            .map(Transaccion::getCuentaOrigen, TransaccionDTO::setCuentaOrigen);
//                });


        return modelMapper;
    }

    public class ListCuentaToStringConverter implements Converter<List<Cuenta>, String> {
        @Override
        public String convert(MappingContext<List<Cuenta>, String> context) {
            List<Cuenta> cuentas = context.getSource();

            if (cuentas == null || cuentas.isEmpty()) {
                return null; // o un valor por defecto como "Sin cuentas"
            }

            return cuentas.stream()
                    .map(Cuenta::getNumeroCuenta) // Asume que Cuenta tiene getNumeroCuenta()
                    .filter(numero -> numero != null && !numero.isEmpty())
                    .collect(Collectors.joining(", "));
        }
    }


}

