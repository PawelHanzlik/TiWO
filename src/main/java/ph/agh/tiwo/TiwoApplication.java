package ph.agh.tiwo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ph.agh.tiwo.dto.ProductDto;
import ph.agh.tiwo.entity.Product;
import ph.agh.tiwo.entity.ProductList;
import ph.agh.tiwo.entity.User;
import ph.agh.tiwo.entity.Warehouse;
import ph.agh.tiwo.repository.UserRepository;
import ph.agh.tiwo.service.ProductListService;
import ph.agh.tiwo.service.ProductService;
import ph.agh.tiwo.service.UserService;
import ph.agh.tiwo.service.WarehouseService;
import ph.agh.tiwo.util.ProductMap;

import java.time.LocalDate;
import java.util.Collections;

@SpringBootApplication
public class TiwoApplication {
	private final UserRepository userRepository;
	private final ProductService productService;

	public TiwoApplication(UserRepository userRepository, ProductService productService) {
		this.userRepository = userRepository;
		this.productService = productService;
	}

	public static void main(String[] args) {
		SpringApplication.run(TiwoApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(ProductService productService, ProductListService productListService, UserService userService,
								 WarehouseService warehouseService){
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return args -> {
			productService.addProduct(Product.builder().name("chleb").cost(ProductMap.getCost("chleb")).bought(false)
					.quantity(2.0).type("sztuk").url(ProductMap.getUrl("chleb")).build());

			productService.addProduct(Product.builder().name("mleko").cost(ProductMap.getCost("mleko")).bought(false)
					.quantity(3.0).type("sztuk").url(ProductMap.getUrl("mleko")).build());

			ProductList pierwsza = productListService.addProductList(ProductList.builder().name("pierwszaLista")
					.description("pierwsza testowa").dueTo(LocalDate.now().plusDays(2)).build());

			productService.updateProductAddProductList("chleb", pierwsza);

			productService.updateProductAddProductList("mleko", pierwsza);

			productService.addProduct(Product.builder().name("maslo").cost(ProductMap.getCost("maslo")).bought(false)
					.quantity(1.0).type("sztuk").url(ProductMap.getUrl("maslo")).build());

			productService.addProduct(Product.builder().name("ciastka").cost(ProductMap.getCost("ciastka")).bought(false)
					.quantity(1.0).type("sztuk").url(ProductMap.getUrl("ciastka")).build());

			ProductList druga =
					productListService.addProductList(ProductList.builder().name("drugaLista").description("druga testowa")
					.dueTo(LocalDate.now().plusDays(2)).build());

			productService.updateProductAddProductList("maslo", druga);
			productService.updateProductAddProductList("ciastka", druga);

			User user = userService.addUser(User.builder().name("testName").surname("testSurname").email("testEmail")
					.password(passwordEncoder.encode("testPassword")).money(100.0).build());

			productListService.updateProductListAddUser("pierwszaLista", user);
			productListService.updateProductListAddUser("drugaLista", user);


			productService.addProduct(Product.builder().name("drukarka").cost(ProductMap.getCost("drukarka")).bought(false)
					.quantity(50.0).type("sztuk").url(ProductMap.getUrl("drukarka")).build());
			productService.addProduct(Product.builder().name("wiertarka").cost(ProductMap.getCost("wiertarka")).bought(false)
					.quantity(30.0).type("sztuk").url(ProductMap.getUrl("wiertarka")).build());
			Warehouse warehouse =
					warehouseService.addWarehouse(Warehouse.builder().name("testWarehouse").products(Collections.emptyList()).build());
			productService.updateProductAddWarehouse("drukarka", warehouse);
			productService.updateProductAddWarehouse("wiertarka", warehouse);
			Product product = productService.buildProductToWarehouse(ProductDto.builder().name("chleb").quantity(200.0).type("sztuk").url("chleb").build(),
					warehouse);
			this.productService.addProduct(product);
			product = productService.buildProductToWarehouse(ProductDto.builder().name("mleko").quantity(1000.0).type("sztuk").url("mleko").build(),
					warehouse);
			this.productService.addProduct(product);
			product = productService.buildProductToWarehouse(ProductDto.builder().name("cukier").quantity(800.0).type("kg").url("chleb").build(),
					warehouse);
			this.productService.addProduct(product);
			product = productService.buildProductToWarehouse(ProductDto.builder().name("ciastka").quantity(600.0).type("sztuk").url("ciastka").build(),
					warehouse);
			this.productService.addProduct(product);
			product = productService.buildProductToWarehouse(ProductDto.builder().name("szynka").quantity(200.0).type("kg").url("szynka").build(),
					warehouse);
			this.productService.addProduct(product);
			product = productService.buildProductToWarehouse(ProductDto.builder().name("ser").quantity(400.0).type("kg").url("ser").build(),
					warehouse);
			this.productService.addProduct(product);
			product = productService.buildProductToWarehouse(ProductDto.builder().name("maslo").quantity(400.0).type("sztuk").url("maslo").build(),
					warehouse);
			this.productService.addProduct(product);
		};
	}
}
