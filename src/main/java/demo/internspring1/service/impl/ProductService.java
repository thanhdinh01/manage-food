package demo.internspring1.service.impl;

import demo.internspring1.dto.request.ProdTypeRequest;
import demo.internspring1.dto.request.ProductRequest;
import demo.internspring1.dto.response.ProductResponse;
import demo.internspring1.exception.ExistedException;
import demo.internspring1.exception.NotFoundException;
import demo.internspring1.model.Product;
import demo.internspring1.model.ProductType;
import demo.internspring1.repository.ProductRepo;
import demo.internspring1.repository.ProductTypeRepo;
import demo.internspring1.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ProductTypeRepo productTypeRepo;
    @Autowired
    private ProductRepo productRepo;

    public ProdTypeRequest convertProdTypeRequest(ProductType productType) {
        ProdTypeRequest prodTypeRequest = new ProdTypeRequest();
        prodTypeRequest.setProductTypeId(productType.getProductTypeId());
        prodTypeRequest.setNameProductType(productType.getNameProductType());
        prodTypeRequest.setImageProductType(productType.getImageTypeProduct());
        prodTypeRequest.setCreatedAt(productType.getCreatedAt());
        prodTypeRequest.setUpdateAt(productType.getUpdateAt());

        return prodTypeRequest;
    }

    public ProductResponse convertProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        ProductType productType= product.getProductType();
        productResponse.setProductId(product.getProductId());
        productResponse.setProductType(convertProdTypeRequest(productType));
        productResponse.setNameProduct(product.getNameProduct());
        productResponse.setPrice(product.getPrice());
        productResponse.setAvatarImageProduct(product.getAvatarImageProduct());
        productResponse.setShortSummary(product.getShortSummary());
        productResponse.setContentProduct(product.getContentProduct());
        productResponse.setDiscount(product.getDiscount());
        productResponse.setStatus(product.getStatus());
        productResponse.setNumberOfViews(product.getNumberOfViews());
        productResponse.setQuantity(product.getQuantity());
        productResponse.setCreatedAt(product.getCreatedAt());

        return productResponse;
    }

    //================= product type =================
    @Override
    @Transactional
    public void addProductType(ProdTypeRequest prodTypeRequest) {
        Optional<ProductType> productTypeOptional = productTypeRepo.findByName(prodTypeRequest.getNameProductType());
        if (productTypeOptional.isPresent()) {
            throw new ExistedException("this product type is existed!");
        }
        ProductType productType = new ProductType(prodTypeRequest.getNameProductType(), prodTypeRequest.getImageProductType());
        productTypeRepo.save(productType);
    }

    @Override
    public void removeProdType(int prodTypeId) {
        Optional<ProductType> productTypeOptional = productTypeRepo.findById(prodTypeId);
        if (productTypeOptional.isEmpty()) {
            throw new NotFoundException("this product type not found!");
        }
        productTypeRepo.deleteById(prodTypeId);
    }

    @Override
    @Transactional
    public void updateProductType(ProdTypeRequest prodTypeRequest) {
        Optional<ProductType> productTypeOptional = productTypeRepo.findById(prodTypeRequest.getProductTypeId());
        if (productTypeOptional.isEmpty()) {
            throw new NotFoundException("this product type not found!");
        }
        ProductType productType = new ProductType(prodTypeRequest.getProductTypeId(), prodTypeRequest.getNameProductType(), prodTypeRequest.getImageProductType(), productTypeOptional.get().getCreatedAt());
        productTypeRepo.save(productType);
    }

    @Override
    public ProdTypeRequest getProdTypeById(int prodTypeId) {
        Optional<ProductType> productTypeOptional = productTypeRepo.findById(prodTypeId);
        if (productTypeOptional.isEmpty()) {
            throw new NotFoundException("this product type not found!");
        }

        return convertProdTypeRequest(productTypeOptional.get());
    }

    @Override
    public Page<ProdTypeRequest> findProdTypeByName(String name, Pageable pageable) {
        Page<ProductType> page = productTypeRepo.findByName(name, pageable);
        List<ProductType> list = page.getContent();
        List<ProdTypeRequest> list1 = new ArrayList<>();
        for (ProductType prod : list) {
            list1.add(convertProdTypeRequest(prod));
        }
        return new PageImpl<>(list1, pageable, page.getTotalElements());
    }

    @Override
    public Page<ProdTypeRequest> findAllProdType(Pageable pageable) {
        Page<ProductType> page = productTypeRepo.findAll(pageable);
        List<ProductType> list = page.getContent();
        List<ProdTypeRequest> list1 = new ArrayList<>();
        for (ProductType prod : list) {
            list1.add(convertProdTypeRequest(prod));
        }
        return new PageImpl<>(list1, pageable, page.getTotalElements());
    }


    // ===================== product ======================
    @Override
    @Transactional
    public void addProduct(ProductRequest productRequest) {
        Optional<Product> productOptional = productRepo.findByName(productRequest.getNameProduct());
        if (productOptional.isPresent()) {
            throw new ExistedException("this product is existed!");
        }
        Optional<ProductType> productTypeOptional = productTypeRepo.findById(productRequest.getProductTypeId());
        if (productTypeOptional.isEmpty()) {
            throw new NotFoundException("product type id " + String.valueOf(productRequest.getProductTypeId()) + " not found!");
        }
        Product product = new Product(productRequest.getProductId(), productTypeOptional.get(), productRequest.getNameProduct(), productRequest.getPrice(), productRequest.getAvatarImageProduct(),
                productRequest.getShortSummary(),productRequest.getContentProduct(), productRequest.getDiscount(), productRequest.getStatus(), productRequest.getQuantity(), productRequest.getCreatedAt());
        productRepo.save(product);
    }

    @Override
    public void removeProduct(int prodId) {
        Optional<Product> productOptional = productRepo.findById(prodId);
        if (productOptional.isEmpty()) {
            throw new NotFoundException("this product not found!");
        }
        productRepo.deleteById(prodId);
    }

    @Override
    public void updateProduct(ProductRequest productRequest) {
        Optional<Product> productOptional = productRepo.findById(productRequest.getProductId());
        if (productOptional.isEmpty()) {
            throw new NotFoundException("this product not found!");
        }
        Optional<ProductType> productType = productTypeRepo.findById(productRequest.getProductTypeId());
        if (productType.isEmpty()) {
            throw new ExistedException("product type id " + String.valueOf(productRequest.getProductTypeId()) + " not found!");
        }
        Product product = new Product(productRequest.getProductId(), productType.get(), productRequest.getNameProduct(), productRequest.getPrice(), productRequest.getAvatarImageProduct(),
                productRequest.getShortSummary(),productRequest.getContentProduct(), productRequest.getDiscount(), productRequest.getStatus(), productRequest.getQuantity(), productOptional.get().getCreatedAt());
        productRepo.save(product);

    }

    @Override
    public ProductResponse getProductById(int prodId) {
        Optional<Product> productOptional = productRepo.findById(prodId);
        if (productOptional.isEmpty()) {
            throw new NotFoundException("this product not found!");
        }

        return convertProductResponse(productOptional.get());
    }

    @Override
    public Page<ProductResponse> findProductByName(String name, Pageable pageable) {
        Page<Product> page = productRepo.findByName(name, pageable);
        List<Product> list = page.getContent();
        List<ProductResponse> list1 = new ArrayList<>();
        for (Product prod : list) {
            list1.add(convertProductResponse(prod));
        }
        return new PageImpl<>(list1, pageable, page.getTotalElements());
    }

    @Override
    public Page<ProductResponse> findAllProduct(Pageable pageable) {
        Page<Product> page = productRepo.findAll(pageable);
        List<Product> list = page.getContent();
        List<ProductResponse> list1 = new ArrayList<>();
        for (Product prod : list) {
            list1.add(convertProductResponse(prod));
        }
        return new PageImpl<>(list1, pageable, page.getTotalElements());
    }
    @Override
    public Page<ProductResponse> findProductByType(Integer prodTypeId, Pageable pageable) {
        Page<Product> page = productRepo.findByProdTypeId(prodTypeId,pageable);
        List<Product> list = page.getContent();
        List<ProductResponse> list1 = new ArrayList<>();
        for (Product prod : list) {
            list1.add(convertProductResponse(prod));
        }
        return new PageImpl<>(list1, pageable, page.getTotalElements());
    }

    @Override
    public Page<ProductResponse> findProductByTypeAndName(Integer prodTypeId,String name, Pageable pageable) {
        Page<Product> page = productRepo.findProdByTypeAndName(prodTypeId,name,pageable);
        List<Product> list = page.getContent();
        List<ProductResponse> list1 = new ArrayList<>();
        for (Product prod : list) {
            list1.add(convertProductResponse(prod));
        }
        return new PageImpl<>(list1, pageable, page.getTotalElements());
    }
}
