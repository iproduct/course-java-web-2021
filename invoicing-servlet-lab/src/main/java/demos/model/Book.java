package demos.model;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book implements Serializable {
	private long id;
	@NonNull
	private String title;
	@NonNull
	private String authors;
	private String format;
	private String isbn;
	private String publisher;
	private Date publishDate = new Date();
	private double price;
}
