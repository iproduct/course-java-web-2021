package course.javaweb.service.model;

import course.javaweb.service.wsdl.Currency;
import lombok.Data;

@Data
public class Country {
    protected String name;
    protected int population;
    protected String capital;
    protected Currency currency;

    public Country(Country source) {
        this.name = source.getName();
        this.population = source.getPopulation();
        this.capital = source.getCapital();
        this.currency = source.getCurrency();
    }
}
