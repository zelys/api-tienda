package api.local.tienda.repository;

import api.local.tienda.dto.MayorVentaDTO;
import api.local.tienda.dto.ResumenVentasDTO;
import api.local.tienda.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IVentaRepository extends JpaRepository<Venta, Long> {
    boolean existsByCodigoVenta(Integer codigoVenta);
    Optional<Venta> findByCodigoVenta(Integer codigoVenta);
    void deleteByCodigoVenta(Integer codigoVenta);
    //
    // Query que obtiene el código de venta mayor
    //
    @Query("SELECT COALESCE(MAX(v.codigoVenta), 999) FROM Venta v")
    Integer obtenerCodigoVenta();

    //
    // Query para obtener resumen de ventas por fecha
    //
    @Query("""
            SELECT new api.local.tienda.dto.ResumenVentasDTO(
                       :fecha,
                       SUM(v.total),
                       COUNT(v))
            FROM Venta v
            WHERE v.fechaVenta = :fecha
            """)
    Optional<ResumenVentasDTO> obtenerResumenVentasPorFecha(LocalDate fecha);

    //
    // Query para obtener ranking de venta con mayor total facturado
    //
    @Query(value = """
                SELECT v.codigo_venta,
                  v.total AS total_venta,
                  CAST(SUM(det.cantidad) AS SIGNED) AS cantidad,
                  CONCAT(c.nombre, ' ', c.apellido) AS cliente
                FROM ventas v
                JOIN clientes c ON v.id_cliente = c.id_cliente
                JOIN detalles_ventas det ON v.id_venta = det.id_venta
                GROUP BY v.id_venta, v.codigo_venta, v.total, c.nombre, c.apellido
                ORDER BY v.total DESC
            """, nativeQuery = true)
    List<MayorVentaDTO> rankingVentas();
}
