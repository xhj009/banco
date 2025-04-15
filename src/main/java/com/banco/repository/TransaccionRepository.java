package com.banco.repository;

import com.banco.dto.TransaccionDTO;
import com.banco.entity.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransaccionRepository extends JpaRepository<Transaccion,Integer> {
    List<Transaccion> findAllByUsuario(String usuario);
    Transaccion findByUsuarioAndId(String usuario,Integer id);
    Boolean existsByUsuarioAndId(String usuario,Integer id);
}
