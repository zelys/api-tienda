package api.local.tienda.service;

import api.local.tienda.dto.ProductoRequestDTO;
import api.local.tienda.dto.ProductoResponseDTO;

import java.util.List;

public interface IProductoService {
    void crear(ProductoRequestDTO dto);
    List<ProductoResponseDTO> listarProductos();
    void editar(Integer codigo, ProductoRequestDTO dto);
    void eliminar(Integer codigo);
    ProductoResponseDTO traerPorCodigo(Integer codigo);
    List<ProductoResponseDTO> listarFaltaDeStock();
}

