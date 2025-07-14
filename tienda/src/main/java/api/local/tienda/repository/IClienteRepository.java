package api.local.tienda.repository;

import api.local.tienda.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface IClienteRepository extends JpaRepository<Cliente, Long> {

    boolean existsByDni(String dni);

    @Query("SELECT CASE WHEN COUNT(v) > 0 THEN true ELSE false END FROM Venta v WHERE v.unCliente.idCliente = :clienteId")
    boolean tieneVentasAsociadas(@Param("clienteId") Long clienteId);
}
