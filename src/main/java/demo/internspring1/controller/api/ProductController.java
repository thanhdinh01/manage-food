package demo.internspring1.controller.api;

import com.google.gson.*;
import demo.internspring1.dto.ResponseEntityDTO;
import demo.internspring1.dto.request.ProdTypeRequest;
import demo.internspring1.dto.request.ProductRequest;
import demo.internspring1.dto.response.ProductResponse;
import demo.internspring1.model.ProductType;
import demo.internspring1.service.impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.time.LocalDate;

@RestController
@RequestMapping(value = "/api/v1/")
public class ProductController {
    @Autowired
    private ProductService productService;
    private static final Gson gson = new GsonBuilder().registerTypeAdapter(LocalDate.class, new JsonDeserializer<LocalDate>() {
        @Override
        public LocalDate deserialize(JsonElement json, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return LocalDate.parse(json.getAsJsonPrimitive().getAsString());
        }
    }).create();

    @PostMapping(value = "add-product-type", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addProdType(@RequestBody ProdTypeRequest prodTypeReq) {
//        ProdTypeRequest prodTypeReq1= gson.fromJson(prodTypeReq, ProdTypeRequest.class);
        productService.addProductType(prodTypeReq);
        return ResponseEntity.status(HttpStatus.CREATED).body("create a product type successfully!");
    }

    @PostMapping(value = "update-product-type", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProdType(@RequestBody ProdTypeRequest prodTypeReq) {
        productService.updateProductType(prodTypeReq);
        return ResponseEntity.status(HttpStatus.OK).body("update a product type successfully!");
    }

    @DeleteMapping(value = "remove-product-type")
    public ResponseEntity<String> removePT(@RequestParam Integer ptid) {
        productService.removeProdType(ptid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("deleted a product type successfully!");
    }

    @GetMapping(value = "get-product-type/{ptid}")
    public ResponseEntityDTO<ProdTypeRequest> getPTById(@PathVariable int ptid) {
        return new ResponseEntityDTO<>(productService.getProdTypeById(ptid), HttpStatus.OK.value(), "successfully");
    }

    @GetMapping(value = "get-product-type")
    public ResponseEntityDTO<Page<ProdTypeRequest>> getPT(String name, Integer size, Integer pageV) {
        if (pageV == null) {
            pageV = 0;
        }
        if (size == null) {
            size = 5;
        }
        Pageable pageable = PageRequest.of(pageV, size);
        if (name != null) {
            return new ResponseEntityDTO<>(productService.findProdTypeByName(name, pageable), HttpStatus.OK.value(), "successfully");
        }
        return new ResponseEntityDTO<>(productService.findAllProdType(pageable), HttpStatus.OK.value(), "successfully");
    }


    // ======================== product ======================


    @PostMapping(value = "add-product", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addProd(@RequestBody ProductRequest productRequest) {
//        ProdTypeRequest prodTypeReq1= gson.fromJson(prodTypeReq, ProdTypeRequest.class);
        productService.addProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("create a product successfully!");
    }

    @PostMapping(value = "update-product", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateProd(@RequestBody ProductRequest productRequest) {
        productService.updateProduct(productRequest);
        return ResponseEntity.status(HttpStatus.OK).body("update a product successfully!");
    }

    @DeleteMapping(value = "remove-product")
    public ResponseEntity<String> removeProd(@RequestParam Integer prodid) {
        productService.removeProduct(prodid);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("deleted a product successfully!");
    }

    @GetMapping(value = "get-product/{prodid}")
    public ResponseEntityDTO<ProductResponse> getProdById(@PathVariable int prodid) {
        return new ResponseEntityDTO<>(productService.getProductById(prodid), HttpStatus.OK.value(), "successfully");
    }

    @GetMapping(value = "get-product")
    public ResponseEntityDTO<Page<ProductResponse>> getProd(Integer prodTypeId, String name, Integer size, Integer pageV) {
        if (pageV == null) {
            pageV = 0;
        }
        if (size == null) {
            size = 5;
        }
        Pageable pageable = PageRequest.of(pageV, size);
        if (prodTypeId != null && name == null) {
            return new ResponseEntityDTO<>(productService.findProductByType(prodTypeId, pageable), HttpStatus.OK.value(), "successfully");
        } else if (name != null && prodTypeId == null) {
            return new ResponseEntityDTO<>(productService.findProductByName(name, pageable), HttpStatus.OK.value(), "successfully");
        } else if (name != null && prodTypeId!=null) {
            return new ResponseEntityDTO<>(productService.findProductByTypeAndName(prodTypeId, name, pageable), HttpStatus.OK.value(), "successfully");
        } else return new ResponseEntityDTO<>(productService.findAllProduct(pageable), HttpStatus.OK.value(), "successfully");
    }
}
