package api.local.tienda.service;

import api.local.tienda.dto.*;
import api.local.tienda.exception.ResourceNotFoundException;
import api.local.tienda.model.Producto;
import api.local.tienda.repository.IProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoService implements IProductoService {

    private final IProductoRepository repo;

    @Override
    @Transactional
    public void crear(ProductoRequestDTO dto) {
        repo.save(mapToRequest(dto));
    }

    @Override
    public List<ProductoResponseDTO> listarProductos() {
        return repo.findAll().stream()
                .map(this::mapToResponse).toList();
    }

    @Override
    public ProductoResponseDTO traerPorCodigo(Integer codigo) {
        validarCodigo(codigo);
        return mapToResponse(traerProducto(codigo));
    }

    @Override
    @Transactional
    public void editar(Integer codigo, ProductoRequestDTO dto) {
        repo.save(mapToProducto(codigo, dto));
    }

    @Override
    @Transactional
    public void eliminar(Integer codigo) {
        validarCodigo(codigo);
        repo.deleteByCodigoProducto(codigo);
    }

    @Override
    public List<ProductoResponseDTO> listarFaltaDeStock() {
        return repo.findByCantidadDisponibleLessThan(5)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    //
    // METODOS DE LA CLASE
    //
    private Producto traerProducto(Integer codigo) {
        validarCodigo(codigo);
        return repo.findByCodigoProducto(codigo).orElse(null);
    }

    private ProductoResponseDTO mapToResponse(Producto p) {
        return new ProductoResponseDTO(
                p.getCodigoProducto(),
                p.getNombre(),
                p.getMarca(),
                p.getCosto(),
                p.getCantidadDisponible()
        );
    }

    private Producto mapToProducto(Integer codigo, ProductoRequestDTO dto) {
        Producto producto = traerProducto(codigo);
        producto.setNombre(dto.nombre());
        producto.setMarca(dto.marca());
        producto.setCosto(dto.costo());
        producto.setCantidadDisponible(dto.cantidad_disponible());
        return producto;
    }

    private Producto mapToRequest(ProductoRequestDTO dto) {
        Producto producto = new Producto();
        Integer codigo = repo.obtenerCodigoProduto() + 1;
        producto.setCodigoProducto(codigo);
        producto.setNombre(dto.nombre());
        producto.setMarca(dto.marca());
        producto.setCosto(dto.costo());
        producto.setCantidadDisponible(dto.cantidad_disponible());
        return producto;
    }

    private void validarCodigo(Integer codigo) {
        if (!repo.existsByCodigoProducto(codigo)) {
            throw new ResourceNotFoundException("Producto con código " + codigo + " no encontrado");
        }
    }
}
