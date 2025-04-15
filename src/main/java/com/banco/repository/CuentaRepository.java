package com.banco.repository;

import com.banco.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta,Integer> {
    Boolean existsBynumeroCuenta(String numeroCuenta);
    Cuenta findBynumeroCuenta(String numeroCuenta);
    Cuenta findByUsuarioAndId(String usuario, Integer id);
    Boolean existsByUsuarioAndId(String usuario, Integer id);
    List<Cuenta> findAllByUsuario(String usuario);
}
