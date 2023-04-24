package ph.agh.tiwo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ph.agh.tiwo.entity.Product;
import ph.agh.tiwo.entity.ProductList;
import ph.agh.tiwo.entity.User;
import ph.agh.tiwo.repository.UserRepository;
import ph.agh.tiwo.service.ProductListService;
import ph.agh.tiwo.service.ProductService;
import ph.agh.tiwo.service.UserService;
import ph.agh.tiwo.util.UrlMap;

import java.time.LocalDate;

@SpringBootApplication
public class TiwoApplication {
	private final UserRepository userRepository;

	public TiwoApplication(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(TiwoApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(ProductService productService, ProductListService productListService, UserService userService){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return args -> {
			productService.addProduct(Product.builder().name("chleb").cost(5.0).bought(false)
					.quantity(2.0).type("sztuk").url(UrlMap.getUrl("chleb")).build());

			productService.addProduct(Product.builder().name("mleko").cost(3.0).bought(false)
					.quantity(3.0).type("sztuk").url(UrlMap.getUrl("mleko")).build());

			ProductList pierwsza = productListService.addProductList(ProductList.builder().name("pierwszaLista")
					.description("pierwsza testowa").dueTo(LocalDate.now().plusDays(2)).build());

			productService.updateProductAddProductList("chleb", pierwsza);

			productService.updateProductAddProductList("mleko", pierwsza);

			productService.addProduct(Product.builder().name("maslo").cost(7.0).bought(false)
					.quantity(1.0).type("kostka").url(UrlMap.getUrl("maslo")).build());

			productService.addProduct(Product.builder().name("ciastka").cost(10.0).bought(false)
					.quantity(1.0).type("sztuk").url(UrlMap.getUrl("ciastka")).build());

			ProductList druga =
					productListService.addProductList(ProductList.builder().name("drugaLista").description("druga testowa")
					.dueTo(LocalDate.now().plusDays(2)).build());

			productService.updateProductAddProductList("maslo", druga);
			productService.updateProductAddProductList("ciastka", druga);

			User user = userService.addUser(User.builder().name("testName").surname("testSurname").email("testEmail")
					.password(passwordEncoder.encode("testPassword")).build());

			productListService.updateProductListAddUser("pierwszaLista", user);
			productListService.updateProductListAddUser("drugaLista", user);
		};
	}
}
