package demos.model;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartBean implements Serializable {
	@NonNull
	private Book book;
	@NonNull
	private int quantity;
}
