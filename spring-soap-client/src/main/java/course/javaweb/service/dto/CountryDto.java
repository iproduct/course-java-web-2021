package course.javaweb.service.dto;

import course.javaweb.service.wsdl.Country;
import course.javaweb.service.wsdl.Currency;
import lombok.Data;

@Data
public class CountryDto {
    protected String name;
    protected int population;
    protected String capital;
    protected Currency currency;

    public CountryDto(Country source) {
        this.name = source.getName();
        this.population = source.getPopulation();
        this.capital = source.getCapital();
        this.currency = source.getCurrency();
    }
}
