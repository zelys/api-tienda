package api.local.tienda.service;

import api.local.tienda.dto.*;
import api.local.tienda.exception.*;
import api.local.tienda.model.*;
import api.local.tienda.repository.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class VentaService implements IVentaService {

    private final IVentaRepository ventaRepo;
    private final IClienteRepository clienteRepo;
    private final IProductoRepository productoRepo;

    @Override
    @Transactional
    public VentaResponseDTO crearVenta(VentaRequestDTO dto) {
        return mapToResponseDTO(ventaRepo.save(setVenta(dto)));
    }

    @Override
    @Transactional
    public VentaResponseDTO editarVenta(Integer codigoVenta, @Valid VentaRequestDTO dto) {
        return mapToResponseDTO(updateVenta(codigoVenta, dto));
    }

    @Override
    public Venta obtenerVentaPorCodigo(Integer codigoVenta) {
        return ventaRepo.findByCodigoVenta(codigoVenta)
                .orElseThrow(() -> new ResourceNotFoundException("Venta con código " + codigoVenta + " no encontrada"));
    }

    @Override
    public List<VentaResponseDTO> obtenerVentas() {
        return ventaRepo.findAll().stream()
                .map(this::mapToResponseDTO).toList();
    }

    @Override
    @Transactional
    public void eliminarVenta(Integer codigoVenta) {
        if (!ventaRepo.existsByCodigoVenta(codigoVenta)) {
            throw new ResourceNotFoundException("Venta con código " + codigoVenta + " no encontrada");
        }
        ventaRepo.deleteByCodigoVenta(codigoVenta);
    }

    // Obtener la lista de productos de una determinada venta
    @Override
    public VentaResponseDTO detalleVenta(Integer codigoVenta) {
        Venta venta = obtenerVentaPorCodigo(codigoVenta);
        return mapToResponseDTO(venta);
    }

    /*
    Obtener la sumatoria del monto y también la cantidad
    total de ventas de una determinada fecha.
     */
    @Override
    public ResumenVentasDTO obtenerResumenVentasPorFecha(LocalDate fecha) {
        return ventaRepo.obtenerResumenVentasPorFecha(fecha)
                .orElse(new ResumenVentasDTO(fecha, 0.0, 0L));
    }

    public MayorVentaDTO obtenerMayorVenta() {
        return ventaRepo.rankingVentas()
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No existen ventas registradas"));
    }

    // METODOS DE LA CLASE

    private VentaResponseDTO mapToResponseDTO(Venta venta) {
        Cliente c = venta.getUnCliente();

        return new VentaResponseDTO(
                venta.getCodigoVenta(),
                fechaFormat(venta.getCreadoEn()),
                horaFormat(venta.getCreadoEn()),
                c.getNombre() + " " + c.getApellido(),
                venta.getTotal(),
                detalleProductos(venta)
        );
    }

    private Venta setVenta(VentaRequestDTO dto) {
        Cliente cliente = clienteRepo.findById(dto.id_cliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Integer codigoVenta = ventaRepo.obtenerCodigoVenta() + 1;

        Venta venta = new Venta();
        venta.setCodigoVenta(codigoVenta);
        venta.setFechaVenta(LocalDate.now());
        venta.setUnCliente(cliente);

        return setDetalleVenta(venta, dto);
    }

    private Venta updateVenta(Integer codigo, VentaRequestDTO dto) {
        Cliente cliente = clienteRepo.findById(dto.id_cliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Venta venta = obtenerVentaPorCodigo(codigo);
        venta.setUnCliente(cliente);

        return setDetalleVenta(venta, dto);
    }

    private List<VentaResponseDTO.DetalleResponseDTO> detalleProductos(Venta venta) {
        return venta.getDetalles().stream()
                .map(d -> {
                    Producto p = d.getProducto();
                    ProductoDTO prodDTO = new ProductoDTO(
                            p.getCodigoProducto(),
                            p.getNombre(),
                            p.getMarca()
                    );
                    return new VentaResponseDTO.DetalleResponseDTO(
                            prodDTO,
                            d.getPrecioUnitario(),
                            d.getCantidad(),
                            d.getSubtotal()
                    );
                }).toList();
    }

    private Venta setDetalleVenta(Venta venta, VentaRequestDTO dto) {
        List<DetalleVenta> detalles = new ArrayList<>();
        double totalVenta = 0;

        for (VentaRequestDTO.DetalleRequestDTO item : dto.detalles()) {
            Producto producto = productoRepo.findByCodigoProducto(item.codigo_producto())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado"));

            if (producto.getCantidadDisponible() < item.cantidad()) {
                throw new BusinessException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            // Descontar stock
            producto.setCantidadDisponible(producto.getCantidadDisponible() - item.cantidad());
            productoRepo.save(producto);

            DetalleVenta detalle = new DetalleVenta();
            detalle.setCantidad(item.cantidad());

            double precioUnitario = producto.getCosto();
            detalle.setPrecioUnitario(precioUnitario);
            detalle.setProducto(producto);

            double subtotal = precioUnitario * item.cantidad();
            detalle.setSubtotal(subtotal);
            detalle.setVenta(venta);

            detalles.add(detalle);
            totalVenta += subtotal;
        }

        venta.setTotal(totalVenta);
        venta.setDetalles(detalles);

        return venta;
    }

    private String fechaFormat(LocalDateTime fecha) {
        return fecha.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    private String horaFormat(LocalDateTime hora) {
        return hora.format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}