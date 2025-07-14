package api.local.tienda.repository;

import api.local.tienda.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface IProductoRepository extends JpaRepository<Producto, Long> {
    Optional<Producto> findByCodigoProducto(Integer codigoProducto);
    void deleteByCodigoProducto(Integer codigoProducto);
    boolean existsByCodigoProducto(Integer codigoProducto);
    List<Producto> findByCantidadDisponibleLessThan(Integer cantidad);

    @Query("SELECT COALESCE(MAX(p.codigoProducto), 999) FROM Producto p")
    Integer obtenerCodigoProduto();
}

