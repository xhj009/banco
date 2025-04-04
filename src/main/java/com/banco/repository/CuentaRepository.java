package com.banco.repository;

import com.banco.entity.Cuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta,Integer> {
    Boolean existsBynumeroCuenta(String numeroCuenta);
    Cuenta findBynumeroCuenta(String numeroCuenta);

}
