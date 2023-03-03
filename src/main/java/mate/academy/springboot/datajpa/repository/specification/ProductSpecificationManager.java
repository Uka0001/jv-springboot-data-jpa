package mate.academy.springboot.datajpa.repository.specification;

import mate.academy.springboot.datajpa.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ProductSpecificationManager implements SpecificationManager<Product>{
    private final Map<String, SpecificationProvider<Product>> providerMap;

    public ProductSpecificationManager(List<SpecificationProvider<Product>> providerMap) {
        this.providerMap = providerMap.stream()
                .collect(Collectors.toMap(SpecificationProvider::getFilterKey,
                        Function.identity()));
    }

    @Override
    public Specification<Product> get(String filterKey, String[] params) {
        if (!providerMap.containsKey(filterKey)) {
            throw new RuntimeException("Key" + filterKey + " is not supported for data filtering");
        }
        return providerMap.get(filterKey).getSpecification(params);
    }
}