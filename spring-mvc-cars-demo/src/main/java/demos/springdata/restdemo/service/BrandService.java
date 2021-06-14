package demos.springdata.restdemo.service;

import demos.springdata.restdemo.model.Brand;

import java.util.Collection;
import java.util.List;

public interface BrandService {
    Collection<Brand> getBrands();
    Brand getBrandById(Long id);
    Brand createBrand(Brand post);
    Brand updateBrand(Brand post);
    Brand deleteBrand(Long id);
    long getBrandsCount();
    List<Brand> createBrandsBatch(List<Brand> posts);
}
