package demo.internspring1.service;

import demo.internspring1.dto.request.ProdTypeRequest;
import demo.internspring1.dto.request.ProductRequest;
import demo.internspring1.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {
    //============= product type =================
    public void addProductType(ProdTypeRequest prodTypeRequest);
    public void removeProdType(int prodTypeId);
    public void updateProductType(ProdTypeRequest prodTypeRequest);
    public ProdTypeRequest getProdTypeById(int prodTypeId);
    public Page<ProdTypeRequest> findProdTypeByName(String name, Pageable pageable);
    public Page<ProdTypeRequest> findAllProdType(Pageable pageable);

    // ================ product =================
    public void addProduct(ProductRequest productRequest);
    public void removeProduct(int prodId);
    public void updateProduct(ProductRequest productRequest);
    public ProductResponse getProductById(int prodId);
    public Page<ProductResponse> findProductByName(String name, Pageable pageable);
    public Page<ProductResponse> findAllProduct(Pageable pageable);
    public Page<ProductResponse> findProductByType(Integer prodTypeId, Pageable pageable);
    public Page<ProductResponse> findProductByTypeAndName(Integer prodTypeId, String name, Pageable pageable);
}
