package com.adam.shop.service.impl;

import com.adam.shop.config.properties.FilePropertiesConfig;
import com.adam.shop.domain.dao.Product;
import com.adam.shop.helper.FileHelper;
import com.adam.shop.repository.ProductRepository;
import com.adam.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final FilePropertiesConfig filePropertiesConfig;
    private final FileHelper fileHelper;

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    @Override
    public Product update(Product product, Long id, MultipartFile file) {
        Product productDb = getById(id);
        productDb.setName(product.getName());
        productDb.setPrice(product.getPrice());
        productDb.setQuantity(product.getQuantity());
        productDb.setDescription(product.getDescription());
        if (file != null) {
            Path filePath = Paths.get(filePropertiesConfig.getProduct(), product.getId() + ".png");
            try {
                // Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                // funkcja copy jest statyczna i jest zadeklarowana w klasie finalnej
                fileHelper.copy(filePath, file.getInputStream());
                productDb.setUrl(filePath.toString());
            } catch (IOException e) {
                log.error("Porblem with saving file {}", e.getMessage());
            }
        }
        return productDb;
    }

    @Override
    public Product getById(Long id) {
        log.info("Product not in cache {}", id); //drugi parametr podstawi się w klamry
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product with " + id + "doesn't exist"));
    }

    @Override
    public void removeById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Page<Product> page(Pageable pageable) {
        return productRepository.findAll(pageable);
    }
}
